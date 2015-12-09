package attune.client;

import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response.Status;

import org.assertj.core.api.Condition;

public class WireMockUtils {

	/**
	 * Verify that a url path has not had any POST calls.
	 * @param path
	 */
	public static void ensureNoPostSoFarTo(String path) {
		verify(0, postRequestedFor(urlEqualTo(path)));
	}

	public static void ensureNoGetSoFarTo(String path) {
		verify(0, getRequestedFor(urlEqualTo(path)));
	}

	/**
	 * Verify that certain strings were received by the server at a specific url path.
	 * @param path the url subpath
	 * @param bodyParts one of more pieces of strings to check that the server received
	 */
	public static void verifyBodyParts(String path, String... bodyParts) {
		for (String id:bodyParts) { // better way to do this..?
			verify(postRequestedFor(urlEqualTo(path))
        		.withHeader("Content-Type", equalTo("application/json"))
        		.withHeader(HttpHeaders.ACCEPT_ENCODING, equalTo("gzip,x-gzip"))
        		.withRequestBody(containing(id)));
		}
	}

	public static class ApiExceptionCodeCondition extends Condition<Throwable> {
		private final Status status;
		public ApiExceptionCodeCondition(Status status) {
			this.status = status;
		}

		@Override
		public boolean matches(Throwable value) {
			return (value instanceof ApiException) &&
					(((ApiException) value).code == this.status.getStatusCode());
		}
		
	};

	public static final Condition<Throwable> codeBadRequest = new ApiExceptionCodeCondition(Status.BAD_REQUEST);

	public static final Condition<Throwable> code404 = new ApiExceptionCodeCondition(Status.NOT_FOUND);

	public static final Condition<Throwable> codeInternalServerError = new ApiExceptionCodeCondition(Status.INTERNAL_SERVER_ERROR);

	public static final Condition<Throwable> codeGatewayTimeout = new ApiExceptionCodeCondition(Status.GATEWAY_TIMEOUT);
}
