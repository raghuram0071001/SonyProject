package sony.playstation.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sony.playstation.commons.ProjectConstants;
import sony.playstation.persistence.service.FeatureFlagsPersistenceService;
import sony.playstation.web.model.CurrentIdentityInfo;
import sony.playstation.web.model.IdentityInfoPresentationModel;
import sony.playstation.web.model.InputCode;

@Component
public class FeatureFlagsServiceImpl implements FeatureFlagsService{

	@Autowired
	private FeatureFlagsPersistenceService featureFlagsPersistenceService;

	@Override
	public IdentityInfoPresentationModel getCurrentInfo() throws Exception {
		
		Object[] persistenceObject = null; // Object Returned From Persistence Call (HttpRequest to FF Service)
		IdentityInfoPresentationModel returnModel = new IdentityInfoPresentationModel(); // Object being returned to the front end presentation
		try {
		persistenceObject = featureFlagsPersistenceService.getCurrentIdentityInfo(ProjectConstants.getCurrentInfoURL);
		
		returnModel = convertToPresentationAndReturnObj(persistenceObject);
		
		}
		catch(Exception e) {
			System.out.println("Exception occurred "+e);
			returnModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			returnModel.setBitMap(null);
		}
		return returnModel;
	}
	
	@Override
	public IdentityInfoPresentationModel saveIdentityInfo(InputCode inputCode) throws MalformedURLException, IOException, Exception {
		
		Object[] persistenceObject = null; // Object Returned From Persistence Call (HttpRequest to FF Service)
		IdentityInfoPresentationModel returnModel = new IdentityInfoPresentationModel();  // Object being returned to the front end presentation
		try {
			persistenceObject = featureFlagsPersistenceService.saveIdentityInfo(ProjectConstants.updateIdentityInfoURL, inputCode);	
			
			returnModel = convertToPresentationAndReturnObj(persistenceObject);
		}
		catch(Exception e) {
			System.out.println("Exception occurred "+e);
			returnModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			returnModel.setBitMap(null);
		}
		return returnModel;
	}
	
	
	//Conversion of response of JSON STRING to respective java object
	private List<CurrentIdentityInfo> convertPersistenceResponseToPresentation(String responseString) throws JsonParseException, JsonMappingException, IOException {
		
		List<CurrentIdentityInfo> infoList = null;
		try {
	    if(responseString != null) {	    
		ObjectMapper om = new ObjectMapper();
		TypeReference<List<CurrentIdentityInfo>> typeRef  = new TypeReference<List<CurrentIdentityInfo>>() {};
        infoList = om.readValue(responseString, typeRef) ;
	    }
		}
		catch(Exception e) {
			System.out.println("Exception occurred "+e);
		}
		return infoList;
	}
	
	
	private Object[] convertIntegerValueToBitMap(int identityInfoValue){
		
		Object[] o = new Object[2];
		LinkedHashMap<String,Integer> bitMap = new LinkedHashMap<String,Integer>();

		try {
		int[] binaryNum = new int[5];
		int n = identityInfoValue;
		int i = 0;
		while (n > 0) {
			// storing reminder in binary array
			binaryNum[i] = ( n % 2);
			n = n / 2;
			i++;
		}

		for(int k=binaryNum.length - 1; k>=0; k--) {
			bitMap.put(ProjectConstants.regionMapping.get(k) , binaryNum[k]);
		}
		o[0] = HttpStatus.OK;
		o[1] = bitMap;
		}
		catch(ArrayIndexOutOfBoundsException aE) {
			System.out.println("The Identity_Information value is not compatible with current web application");
			o[0] = HttpStatus.INSUFFICIENT_STORAGE;
			o[1] = null;
		}
		catch(Exception e) {
			System.out.println("Exception occurred "+e);
			o[0] = HttpStatus.INTERNAL_SERVER_ERROR;
			o[1] = null;
		}
		return o;		
	}
	
	private Object[] computeAndPrepareBitMap(List<CurrentIdentityInfo> infoList, Object[] persistenceObject) {
		
		Object[] returnObject = new Object[2]; 
		if(infoList != null && infoList.size() > 0) {
			 for(CurrentIdentityInfo identityInfo: infoList) {
				 if(identityInfo.getName().equals(ProjectConstants.identityInfoName)) {
					 
					 returnObject = convertIntegerValueToBitMap(identityInfo.getValue());
					 break;					 
				 }
			 }
			}
			else {
				returnObject = persistenceObject;
			}
		
		return returnObject;
	}

	private IdentityInfoPresentationModel convertToPresentationAndReturnObj(Object[] persistenceObject) throws JsonParseException, JsonMappingException, IOException {
		
		IdentityInfoPresentationModel returnModel = new IdentityInfoPresentationModel();
		if(persistenceObject[0] == HttpStatus.OK && persistenceObject[1] != null){
			//Convert to presentation object
			List<CurrentIdentityInfo> infoList  = convertPersistenceResponseToPresentation((String) persistenceObject[1]);
			
			//Now look for the value of Idendtity_Information from the list of objects, prepare bit map 
			Object[] o  = computeAndPrepareBitMap(infoList, persistenceObject);
			returnModel.setStatus((HttpStatus)o[0]);
			if(o[1] != null) {
			returnModel.setBitMap((LinkedHashMap<String,Integer>)o[1]);
			}
		}
		else if(persistenceObject[0] == HttpStatus.OK && persistenceObject[1] == null) {
			returnModel.setStatus(HttpStatus.NO_CONTENT);
			returnModel.setBitMap(null);
		}else {
			returnModel.setStatus((HttpStatus)persistenceObject[0]);
			returnModel.setBitMap(null);
		}
		
		return returnModel;
	}
}
