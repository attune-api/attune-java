package attune.client;

import com.fasterxml.jackson.databind.JavaType;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


public class ApiInvoker {
    private static ApiInvoker INSTANCE = new ApiInvoker();
    private Map<InitConfig, Client> configInstanceMap = new HashMap<>();
    private Map<String, String> defaultHeaderMap = new HashMap<String, String>();

    /**
    * ISO 8601 date time format.
    * http://en.wikipedia.org/wiki/ISO_8601
    */
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    /**
    * ISO 8601 date format.
    * https://en.wikipedia.org/wiki/ISO_8601
    */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    static {
        // Use UTC as the default time zone.
        DATE_TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));

        // Set default User-Agent.
        setUserAgent("Java-Swagger");
    }

    public static void setUserAgent(String userAgent) { INSTANCE.addDefaultHeader("User-Agent", userAgent); }

    public static Date parseDateTime(String str) {
        try {
          return DATE_TIME_FORMAT.parse(str);
        } catch (java.text.ParseException e) {
          throw new RuntimeException(e);
        }
    }

    public static Date parseDate(String str) {
        try {
          return DATE_FORMAT.parse(str);
        } catch (java.text.ParseException e) {
          throw new RuntimeException(e);
        }
    }

    public static String formatDateTime(Date datetime) { return DATE_TIME_FORMAT.format(datetime); }

    public static String formatDate(Date date) { return DATE_FORMAT.format(date); }

    public static String parameterToString(Object param) {
        if (param == null) {
            return "";
        } else if (param instanceof Date) {
            return formatDateTime((Date) param);
        } else if (param instanceof Collection) {
            StringBuilder b = new StringBuilder();
            for(Object o : (Collection)param) {
                if(b.length() > 0) {
                    b.append(",");
                }
                b.append(String.valueOf(o));
            }
            return b.toString();
        } else {
            return String.valueOf(param);
        }
    }

    public static ApiInvoker getInstance() { return INSTANCE; }

    public void addDefaultHeader(String key, String value) { defaultHeaderMap.put(key, value); }

    public String escapeString(String str) {
        try {
            return URLEncoder.encode(str, "utf8").replaceAll("\\+", "%20");
        } catch(UnsupportedEncodingException e) {
            return str;
        }
    }

    public static Object deserialize(String json, String containerType, Class cls) throws ApiException {
        if(null != containerType) {
            containerType = containerType.toLowerCase();
        }

        try {
            if("list".equals(containerType) || "array".equals(containerType)) {
                JavaType typeInfo = JsonUtil.getJsonMapper().getTypeFactory().constructCollectionType(List.class, cls);
                List response = (List<?>) JsonUtil.getJsonMapper().readValue(json, typeInfo);
                return response;
            } else if(String.class.equals(cls)) {
                if(json != null && json.startsWith("\"") && json.endsWith("\"") && json.length() > 1)
                    return json.substring(1, json.length() - 2);
                else
                    return json;
            } else {
                return JsonUtil.getJsonMapper().readValue(json, cls);
            }
        } catch (IOException e) {
            throw new ApiException(500, e.getMessage());
        }
    }

    public static String serialize(Object obj) throws ApiException {
        try {
            if (obj != null)
                return JsonUtil.getJsonMapper().writeValueAsString(obj);
            else
                return null;
        } catch (Exception e) {
            throw new ApiException(500, e.getMessage());
        }
    }

    private String buildQueryFromQueryParams(Map<String, String> queryParams) {
        StringBuilder b = new StringBuilder();
        for(String key : queryParams.keySet()) {
            String value = queryParams.get(key);
            if (value != null) {
                if (b.toString().length() == 0)
                    b.append("?");
                else
                    b.append("&");
                b.append(escapeString(key)).append("=").append(escapeString(value));
            }
        }
        return b.toString();
    }

    private Builder getBuilderWithCorrectHeader(Client client, Map<String, String>headerParams, String target) {
        //Builder builder = client.resource(host + path + querystring).accept("application/json");
        WebTarget webTarget = client.target(target);
        Builder builder     = webTarget.request(MediaType.APPLICATION_JSON);
        for(String key : headerParams.keySet()) {
            builder = builder.header(key, headerParams.get(key));
        }

        for(String key : defaultHeaderMap.keySet()) {
            if(!headerParams.containsKey(key)) {
                builder = builder.header(key, defaultHeaderMap.get(key));
            }
        }
        return builder;
    }

    public String invokeAPI(AttuneConfigurable attuneConfig, String path, String method, Map<String, String> queryParams, Object body,
        Map<String, String> headerParams, String contentType, String userAgent) throws ApiException {

        //get client from hashmap (if already present)
        Client client      = getClient(attuneConfig);

        //build query string for api request
        String querystring = buildQueryFromQueryParams(queryParams);

        //build a request from header
        String target      = attuneConfig.getEndpoint() + path + querystring;
        Builder builder    = getBuilderWithCorrectHeader(client, headerParams, target);

        // categorize request into GET, PUT, POST and accordingly do varying things
        String retVal       = null;
        Response response   = null;

        try {
            //process API request
            if ("GET".equals(method)) {
                response = (Response) builder.get(Response.class);
            } else if ("POST".equals(method)) {
                if (body == null) {
                    response = builder.post(null, Response.class);
                } else {
                    builder  = builder.header("Content-type", contentType);
                    builder  = builder.header("User-Agent", userAgent);
                    response = builder.post(Entity.entity(serialize(body), MediaType.APPLICATION_JSON), Response.class);
                }
            } else if ("PUT".equals(method)) {
                if (body == null) {
                    response = builder.put(null, Response.class);
                } else {
                        builder  = builder.header("Content-type", contentType);
                        builder  = builder.header("User-Agent", userAgent);
                        response = builder.put(Entity.entity(serialize(body), MediaType.APPLICATION_JSON), Response.class);
                }
            } else if ("DELETE".equals(method)) {
                if (body == null) {
                    response = builder.delete(Response.class);
                } else {
                    builder = builder.header("Content-type", contentType);
                    builder = builder.header("User-Agent", userAgent);

                    //response = builder.delete(Entity.entity(serialize(body), MediaType.APPLICATION_JSON), Response.class);
                    // Not going to send DELETE with entity body. See http://stackoverflow.com/questions/25229880/how-to-send-enclose-data-in-delete-request-in-jersey-client
                    response = builder.delete(Response.class);
                }
            }

            // based on response status, either throw exception or return
            if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
                retVal = null;
            } else if (response.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
                if (response.hasEntity()) {
                    retVal = response.readEntity(String.class);
                } else {
                    retVal = "";
                }
            } else if (response.getStatusInfo().getFamily() == Family.CLIENT_ERROR) {
                throw new ApiException(response.getStatus(), " Client error occurred");
            } else if (response.getStatusInfo().getFamily() == Family.SERVER_ERROR) {
                throw new ApiException(response.getStatus(), "Server error occurred");
            }
        } catch (ProcessingException p) {
            throw new ApiException(response.getStatus(), p.getMessage());
        } catch (WebApplicationException w) {
            throw new ApiException(response.getStatus(), w.getMessage());
        } finally {
            if (response != null)
                response.close();
            return retVal;
        }
    }

    private Double convertToMilliseconds(Double seconds) {
        return seconds*1000.0;
    }

    private ClientConfig getClientConfigWithParams(AttuneConfigurable attuneConfig) {
      ClientConfig clientConfig = new ClientConfig();

      clientConfig.property(ClientProperties.READ_TIMEOUT, convertToMilliseconds(attuneConfig.getReadTimeout()).intValue());
      clientConfig.property(ClientProperties.CONNECT_TIMEOUT, convertToMilliseconds(attuneConfig.getConnectionTimeout()).intValue());

      PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager();
      connectionManager.setMaxTotal(attuneConfig.getMaxPossiblePoolingConnections());
      connectionManager.setDefaultMaxPerRoute(attuneConfig.getMaxConnections());

      clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, connectionManager);
      return clientConfig;
    }

    private Client getClient(AttuneConfigurable config) {
        InitConfig initConfig = config.getInitConfig();

        synchronized (ApiInvoker.class) {
            if (!configInstanceMap.containsKey(initConfig)) {
                synchronized (ApiInvoker.class) {
                    Client client = ClientBuilder.newClient(getClientConfigWithParams(config));
                    //if(isDebug)
                    //  client.addFilter(new LoggingFilter());
                    configInstanceMap.put(initConfig, client);
                }
            }
        }
        return configInstanceMap.get(initConfig);
    }
}
