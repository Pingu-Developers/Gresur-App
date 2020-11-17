package org.springframework.gresur.util;


import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;



public class Tuple4 <T1,T2,T3,T4>{
	
	@JsonIgnore
	public String name1 = "e1";
	@JsonIgnore
	public String name2 = "e2";
	@JsonIgnore
	public String name3 = "e3";
	@JsonIgnore
	public String name4 = "e4";
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private T1 e1;
	@JsonProperty(access = Access.WRITE_ONLY)
	private T2 e2;
	@JsonProperty(access = Access.WRITE_ONLY)
	private T3 e3;
	@JsonProperty(access = Access.WRITE_ONLY)
	private T4 e4;
	
	
	public Tuple4() {
	}
	
	public Tuple4(T1 e1, T2 e2,T3 e3,T4 e4) {
		this.setE1(e1);
		this.setE2(e2);
		this.setE3(e3);
		this.setE4(e4);
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
	public T3 getE3() {
		return e3;
	}

	public void setE3(T3 e3) {
		this.e3 = e3;
	}
	public T4 getE4() {
		return e4;
	}

	public void setE4(T4 e4) {
		this.e4 = e4;
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
		 	if(e3 != null) {
		 		res.put(name3, e3);
		 	}
		 	if(e4 != null) {
		 		res.put(name4, e4);
		 	}
	        return res;
	    }	
	
}
