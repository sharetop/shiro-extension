package cn.sharetop.shiro.extension.authz.aop;

import java.lang.annotation.Annotation;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import org.apache.shiro.subject.Subject;

import cn.sharetop.shiro.extension.spring.annotation.RequiresAction;

public class ActionAnnotationHandler extends AuthorizingAnnotationHandler {

	public ActionAnnotationHandler() {
		super(RequiresAction.class);
		// TODO Auto-generated constructor stub
	}
	
	private ThreadLocal<String>  actionPermission = new ThreadLocal<String>();
	public void setActionPermission(String permission){
		actionPermission.set(permission);
	}
	public String getActionPermission(){
		return actionPermission.get();
	}

	@Override
	public void assertAuthorized(Annotation a) throws AuthorizationException {
		// TODO Auto-generated method stub
		// RequiresAction rpAnnotation = (RequiresAction)a;
		
		Subject subject = getSubject();
		
		/**
		 * 此处使用拼装出的字符串『ControllerName：MethodName』
		 * */
		String permissionString=getActionPermission();
		if(!StringUtils.isEmpty(permissionString)){
			subject.checkPermission(permissionString);
			return;
		}
	}

}
