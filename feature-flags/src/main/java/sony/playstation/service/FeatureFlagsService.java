package sony.playstation.service;

import java.io.IOException;
import java.net.MalformedURLException;

import sony.playstation.web.model.IdentityInfoPresentationModel;
import sony.playstation.web.model.InputCode;

public interface FeatureFlagsService {

	public IdentityInfoPresentationModel getCurrentInfo() throws MalformedURLException, IOException, Exception; 

	public IdentityInfoPresentationModel saveIdentityInfo(InputCode inputCode) throws MalformedURLException, IOException, Exception; 
}
