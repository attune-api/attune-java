package attune.client;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.ws.rs.core.Response.Status.Family;

public class ApiException extends Exception {
  /**
	 * 
	 */
	private static final long serialVersionUID = 5270803207072687832L;

	int code = 0;
  String message = null;

  public ApiException() {}

  public ApiException(int code, String message) {
	  super(message);
    this.code = code;
    this.message = message;
  }

	public ApiException(int code, Throwable cause) {
		super(cause);
		this.code = code;
	}

  public int getCode() {
    return code;
  }
  
  public void setCode(int code) {
    this.code = code;
  }
  
  public String getMessage() {
    return message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isRetriable() {
	  return Family.familyOf(code) == Family.SERVER_ERROR || isCauseRetriable();
  }

  protected boolean isCauseRetriable() {
	  Throwable t = this.getCause();
	  return (null != t) && (t instanceof ConnectException || t instanceof SocketTimeoutException);
  }
}