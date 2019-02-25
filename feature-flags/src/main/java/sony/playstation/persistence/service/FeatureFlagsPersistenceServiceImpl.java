package sony.playstation.persistence.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import sony.playstation.commons.ProjectConstants;
import sony.playstation.web.model.CurrentIdentityInfo;
import sony.playstation.web.model.InputCode;

@Component
public class FeatureFlagsPersistenceServiceImpl implements FeatureFlagsPersistenceService {

	
	@Override
	public Object[] getCurrentIdentityInfo(String url) throws Exception {
	   
		Object[] o = new Object[2];
		try {
			//Create a GET connection and make a call to the server
			HttpURLConnection con;
			URL currentInfoUrl = new URL(url);
			con = (HttpURLConnection) currentInfoUrl.openConnection();
	        con.setRequestMethod(ProjectConstants.getMethod);
	        con.setRequestProperty("User-Agent", ProjectConstants.userAgent);
	        con.setRequestProperty("Content-Type", ProjectConstants.contentType);
	        
	        int status = con.getResponseCode();
	        
	        if(status == HttpStatus.OK.value()) {
	            o[0] = HttpStatus.OK;
	            //Since the service returned with 200 status code now convert the response to json string and return
	        	o[1] = responseString(con);
	        }
	        else {
	        	o[0] = HttpStatus.valueOf(status);
	        	o[1] = null;
	        }
	        
		}catch(ConnectException cE) {
			System.out.println("Connection Exception occurred " +cE);
			o[0] = HttpStatus.SERVICE_UNAVAILABLE;
			o[1] = null;
		}
		catch(Exception e) {
			System.out.println("Exception occurred " +e);
			o[0] = HttpStatus.INTERNAL_SERVER_ERROR;
			o[1] = null;
		}
		
		return o;
	}
	
	@Override
	public Object[] saveIdentityInfo(String url, InputCode inputCode) throws Exception {
		
	Object[] o = new Object[2];
		
		try {
			//Create a GET connection and make a call to the server
			int infoValue = getIntegerValueByBitMap(inputCode.getBitMap());
			HttpURLConnection con;
			URL updateInfoUrl = new URL(url);
			con = (HttpURLConnection) updateInfoUrl.openConnection();
			CurrentIdentityInfo identityInfo = new CurrentIdentityInfo();
			identityInfo.setName(ProjectConstants.identityInfoName);
			identityInfo.setValue(infoValue);
			
			ObjectWriter obw = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String inputJsonString = obw.writeValueAsString(identityInfo);
			
			 con.setDoOutput(true);
	         con.setRequestMethod("POST");
	         con.setRequestProperty("User-Agent", "Java client");
	         con.setRequestProperty("Content-Type", "application/json");
	         byte[] postData = inputJsonString.getBytes(StandardCharsets.UTF_8);
	         try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
	             wr.write(postData);
	         }
	         
	        int status = con.getResponseCode();
	        
	        if(status == HttpStatus.OK.value()) {
	            o[0] = HttpStatus.OK;
	            //Since the service returned with 200 status code now convert the response to json string and return
	        	o[1] = responseString(con);
	        }
	        else {
	        	o[0] = HttpStatus.valueOf(status);
	        	o[1] = null;
	        }
	        
		}catch(ConnectException cE) {
			System.out.println("Connection Exception occurred " +cE);
			o[0] = HttpStatus.SERVICE_UNAVAILABLE;
			o[1] = null;
		}
		catch(Exception e) {
			System.out.println("Exception occurred " +e);
			o[0] = HttpStatus.INTERNAL_SERVER_ERROR;
			o[1] = null;
		}
		
		return o;
	}
	
private String responseString(HttpURLConnection con) throws IOException, NullPointerException {
			
	StringBuilder content = null;
	try (BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()))) {

		String line;
        content = new StringBuilder();

        while ((line = in.readLine()) != null) {
            content.append(line);
        }
    }
	return (content != null ? content.toString() : null);
	
	}

private int getIntegerValueByBitMap(LinkedHashMap<String, Integer> bitMap) {
	
	int identityInfoValue = 0;
    if(bitMap != null && bitMap.size() > 0) {
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append(bitMap.get(ProjectConstants.Asia)).append(bitMap.get(ProjectConstants.Korea)).append(bitMap.get(ProjectConstants.Europe))
    	.append(bitMap.get(ProjectConstants.Japan)).append(bitMap.get(ProjectConstants.America));
    
    	if(sb != null) {
    		  int num = Integer.parseInt(sb.toString().replaceFirst("^0+(?!$)", ""));
    		  identityInfoValue = convertBinaryToDecimal(num);
    	}
    }
    return identityInfoValue;
}

private int convertBinaryToDecimal(int binaryValueOfBitMap){
	
	int decimalValue = 0;
	try {
		    int num = binaryValueOfBitMap; 
		    int dec_value = 0; 		      
		    // Initializing base  
		    // value to 1, i.e 2^0 
		    int base = 1; 
		      
		    int temp = num; 
		    while (temp > 0) 
		    { 
		        int last_digit = temp % 10; 
		        temp = temp / 10; 
		          
		        dec_value += last_digit * base; 
		          
		        base = base * 2; 
		    } 
		    decimalValue = dec_value;
	}
	catch(Exception e) {
		System.out.println("Exception occurred "+e);

	}
	return decimalValue;
		
}

}
