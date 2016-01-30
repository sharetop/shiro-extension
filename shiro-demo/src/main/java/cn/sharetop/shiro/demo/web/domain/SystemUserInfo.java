package cn.sharetop.shiro.demo.web.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * DEMO 用户实体
 * 
 * */
public class SystemUserInfo implements java.io.Serializable{
	private static final long serialVersionUID = -3940727016379283759L;

	public String userName;
	public String realName;
	public List<String> groupNames=new ArrayList<String>();
	
	public SystemUserInfo(){
		
	}
	public SystemUserInfo(String u,String r){
		userName=u;
		realName=r;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public List<String> getGroupNames() {
		return groupNames;
	}
	public void setGroupNames(List<String> groupNames) {
		this.groupNames = groupNames;
	}
	
	
}
