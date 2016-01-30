package cn.sharetop.shiro.extension.authz.aop;

import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;

public class ActionAnnotationMethodInterceptor  
	extends AuthorizingAnnotationMethodInterceptor {
	
	public ActionAnnotationMethodInterceptor(){
		super(new ActionAnnotationHandler());
	}
	public ActionAnnotationMethodInterceptor(AnnotationResolver resolver){
		super(new ActionAnnotationHandler(),resolver);
	}
	
	@Override
	public void assertAuthorized(MethodInvocation mi) throws AuthorizationException {
		// TODO Auto-generated method stub
		try {
			ActionAnnotationHandler handler = (ActionAnnotationHandler) getHandler();
			
			StringBuilder sb = new StringBuilder();
			sb.append(mi.getThis().getClass().getName());
			sb.append(":");
			sb.append(mi.getMethod().getName());
			
			System.out.println("action is "+sb.toString());
			
			handler.setActionPermission(sb.toString());
			handler.assertAuthorized(this.getAnnotation(mi));
		}
		catch(AuthorizationException ae) {
			// Annotation handler doesn't know why it was called, so add the information here if possible. 
			// Don't wrap the exception here since we don't want to mask the specific exception, such as 
			// UnauthenticatedException etc. 
			if (ae.getCause() == null) ae.initCause(new AuthorizationException("Not authorized to invoke method: " + mi.getMethod()));
				throw ae;
		} 
		
	}

	
}
