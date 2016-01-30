package cn.sharetop.shiro.extension.authz.aop;

import java.lang.annotation.Annotation;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import cn.sharetop.shiro.extension.spring.annotation.RequiresData;

public class DataAnnotationHandler extends AuthorizingAnnotationHandler {

	public DataAnnotationHandler() {
		super(RequiresData.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void assertAuthorized(Annotation a) throws AuthorizationException {
		// TODO Auto-generated method stub
		// RequiresAction rpAnnotation = (RequiresAction)a;
		// do nothing
	}

}
