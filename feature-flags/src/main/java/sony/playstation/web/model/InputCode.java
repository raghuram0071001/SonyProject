package sony.playstation.web.model;

import java.util.LinkedHashMap;

public class InputCode {

	private String code;
	private LinkedHashMap<String, Integer> bitMap;


	public LinkedHashMap<String, Integer> getBitMap() {
		return bitMap;
	}

	public void setBitMap(LinkedHashMap<String, Integer> bitMap) {
		this.bitMap = bitMap;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
