package sony.playstation.persistence.service;

import java.net.URL;

import sony.playstation.web.model.InputCode;

public interface FeatureFlagsPersistenceService {

	public Object[] getCurrentIdentityInfo(String url) throws Exception;

	public Object[] saveIdentityInfo(String url, InputCode inputCode) throws Exception;
}
