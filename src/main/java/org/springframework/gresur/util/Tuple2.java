package org.springframework.gresur.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class Tuple2 <T1,T2>{
	
	@JsonIgnore
	public String name1 = "e1";
	@JsonIgnore
	public String name2 = "e2";
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private T1 e1;
	@JsonProperty(access = Access.WRITE_ONLY)
	private T2 e2;

	public Tuple2() {
	}
	
	public Tuple2(T1 e1 , T2 e2) {
		this.setE1(e1);
		this.setE2(e2);
	}

	public T1 getE1() {
		return e1;
	}

	public void setE1(T1 e1) {
		this.e1 = e1;
	}

	public T2 getE2() {
		return e2;
	}

	public void setE2(T2 e2) {
		this.e2 = e2;
	}
	
	@JsonAnyGetter
    public Map<String, Object> any() {
	 	
	 	Map<String,Object> res = new HashMap<>();	 
	 	
	 	if(e1 != null) {
	 		res.put(name1, e1);
	 	}
	 	if(e2 != null ) {
	 		res.put(name2, e2);
	 	}
	 	return res;
	}
}
