package sony.playstation.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sony.playstation.commons.ProjectConstants;
import sony.playstation.persistence.service.FeatureFlagsPersistenceService;
import sony.playstation.service.FeatureFlagsService;
import sony.playstation.web.model.IdentityInfoPresentationModel;
import sony.playstation.web.model.InputCode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration ("classpath:/spring-config-test.xml")
public class FeatureFlagsServiceImplTest {

	@Autowired
	private FeatureFlagsService flagsService;
	
	@Test
	public void getCurrentIdentityInfoTest() throws MalformedURLException, IOException, Exception{
		
		IdentityInfoPresentationModel returnModel = flagsService.getCurrentInfo();
		if(returnModel.getStatus() != null && returnModel.getStatus() == HttpStatus.OK ) {	
			assertNotNull(returnModel.getBitMap());
		}
		else {
			assertNull(returnModel.getBitMap());
		}
	}

	@Test
	public void saveIdentityInfoTest() throws MalformedURLException, IOException, Exception{
		
		InputCode inputCode = new InputCode();
		LinkedHashMap<String, Integer> bitMap = new LinkedHashMap<String, Integer>();
        bitMap.put(ProjectConstants.Asia, 0);
        bitMap.put(ProjectConstants.Korea, 0);
        bitMap.put(ProjectConstants.Europe, 0);
        bitMap.put(ProjectConstants.Japan, 1);
        bitMap.put(ProjectConstants.America, 1);
        inputCode.setBitMap(bitMap);
        IdentityInfoPresentationModel returnModel = flagsService.saveIdentityInfo(inputCode) ;
        if(returnModel.getStatus() != null && returnModel.getStatus() == HttpStatus.OK ) {	
			assertNotNull(returnModel.getBitMap());
		}
		else {
			assertNull(returnModel.getBitMap());
		}
	}
	
}
