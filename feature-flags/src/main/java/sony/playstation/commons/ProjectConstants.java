package sony.playstation.commons;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class ProjectConstants {

	public static final String getCurrentInfoURL = "http://localhost:12300/featureflags";
	public static final String updateIdentityInfoURL = "http://localhost:12300/featureflags";
	public static final String getMethod = "GET";
	public static final String postMethod = "POST";
	public static final String userAgent = "Java client";
	public static final String contentType = "application/json";
	public static final String identityInfoName = "Identity_Information";

	public static final String Asia = "Asia";
	public static final String Korea = "Korea";
	public static final String Europe = "Europe";
	public static final String Japan = "Japan";
	public static final String America = "America";
	
	public static final Map<Integer,String> regionMapping = new HashMap<Integer,String>();
	
	static {
		regionMapping.put(4,Asia);
		regionMapping.put(3,Korea);
		regionMapping.put(2,Europe);
		regionMapping.put(1,Japan);
		regionMapping.put(0,America);
		
	}
	
	
	
	public static final String HttpStatus_OK = HttpStatus.OK.toString();
	public static final String HttpStatus_Server_Error = HttpStatus.INTERNAL_SERVER_ERROR.toString();
	public static final String HttpStatus_No_Content = HttpStatus.NO_CONTENT.toString();
	public static final String HttpStatus_Server_UnAvailable = HttpStatus.SERVICE_UNAVAILABLE.toString();
}
