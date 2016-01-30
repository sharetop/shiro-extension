package cn.sharetop.shiro.demo.web.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.sharetop.shiro.demo.web.domain.SystemUserInfo;

public class DemoShiroRealm extends AuthorizingRealm {

	@Override
	public String getName(){
		return this.getClass().getName();
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		SystemUserInfo u = (SystemUserInfo)principals.getPrimaryPrincipal();
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRole("administrator");
		info.addStringPermission("system-console:*");
		
		//测试 RequiresAction 注解
		if(u.getUserName().equalsIgnoreCase("aaa")){
			info.addStringPermission("cn.sharetop.shiro.demo.web.controller.HomeController:showHello");
		}
		
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		// TODO Auto-generated method stub
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String uname = token.getUsername().trim();
		String upassword = String.valueOf(token.getPassword()).trim();
		
		if(uname.equalsIgnoreCase("aaa") && upassword.equalsIgnoreCase("aaa")){
			SystemUserInfo uinfo = new SystemUserInfo("aaa","用户A");
			uinfo.getGroupNames().add("湖南");
			uinfo.getGroupNames().add("湖北");
			
			return new SimpleAuthenticationInfo(uinfo,upassword,getName());
			
		}
		else if(uname.equalsIgnoreCase("bbb") && upassword.equalsIgnoreCase("bbb")){
			SystemUserInfo uinfo = new SystemUserInfo("bbb","用户B");
			uinfo.getGroupNames().add("北京");
			
			return new SimpleAuthenticationInfo(uinfo,upassword,getName());
		}
		else
			throw new AuthenticationException();
	}

}
