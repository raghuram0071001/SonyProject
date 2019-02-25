package sony.playstation.commons;

import org.springframework.http.HttpStatus;

public class ResponseStatus {

	private HttpStatus status;
	private String statusCode;

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
		this.setStatusCode(status.toString());
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	
}
