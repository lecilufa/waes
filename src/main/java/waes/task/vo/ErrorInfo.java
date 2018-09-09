package waes.task.vo;

/**
 * when business exception happened, present error info to rest client
 *
 */
public class ErrorInfo {

	/**
	 * full name of the exception
	 */
    private String exception;
    /**
     * message containing in the exception
     */
    private String message;
    /**
     * http status code according to this exception
     */
    private int code;
    
    
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
    
    

}
