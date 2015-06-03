package attune.client;

import com.sun.corba.se.impl.presentation.rmi.ExceptionHandler;
import org.slf4j.Logger;

/**
 * Created by sudnya on 5/27/15.
 */
public class AttuneConfigurable {
    protected String auth_token;
    protected String endpoint;
    protected String middleware;
    protected boolean disabled;
    protected ExceptionHandler exceptionHandler;
    protected long timeout;
    protected Logger logger;
    protected boolean logging_enabled;

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public boolean isLogging_enabled() {
        return logging_enabled;
    }

    public void setLogging_enabled(boolean logging_enabled) {
        this.logging_enabled = logging_enabled;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getMiddleware() {
        return middleware;
    }

    public void setMiddleware(String middleware) {
        this.middleware = middleware;
    }

    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public void setExceptionHandler(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }


}
