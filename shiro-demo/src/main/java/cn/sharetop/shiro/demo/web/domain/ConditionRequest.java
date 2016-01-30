package cn.sharetop.shiro.demo.web.domain;

import java.util.HashMap;
import java.util.Map;

import cn.sharetop.shiro.extension.authz.aop.DataParameterRequest;

public class ConditionRequest implements DataParameterRequest{
	
	public String author;
	
	private Map<String,String> params = new HashMap<String,String>();
	
	@Override
	public Map<String, String> getParameters() {
		// TODO Auto-generated method stub
		return params;
	}
	
	public void setParameters(Map<String,String> p){
		this.params=p;
	}

}
