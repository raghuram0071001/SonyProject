package sony.playstation.web.model;

import java.util.LinkedHashMap;

import sony.playstation.commons.ResponseStatus;

public class IdentityInfoPresentationModel extends ResponseStatus{

	private LinkedHashMap<String, Integer> bitMap;

	public LinkedHashMap<String, Integer> getBitMap() {
		return bitMap;
	}

	public void setBitMap(LinkedHashMap<String, Integer> bitMap) {
		this.bitMap = bitMap;
	}
}
