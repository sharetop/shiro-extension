package cn.sharetop.shiro.extension.spring.interceptor;

import java.util.Collection;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;
import org.apache.shiro.spring.aop.SpringAnnotationResolver;

import cn.sharetop.shiro.extension.authz.aop.ActionAnnotationMethodInterceptor;
import cn.sharetop.shiro.extension.authz.aop.DataAnnotationMethodInterceptor;

public class AopAllianceAnnotationsAuthorizingMethodInterceptor 
extends org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor {

	public AopAllianceAnnotationsAuthorizingMethodInterceptor(){
		super();
		
		this.methodInterceptors.add(new ActionAnnotationMethodInterceptor(new SpringAnnotationResolver()));
		this.methodInterceptors.add(new DataAnnotationMethodInterceptor(new SpringAnnotationResolver()));
		
	}
	
	
	/**
	 * 必须拦截此方法，增加对DataAnnotationMethodInterceptor的处理
	 * 
	 * 
	 * */
	
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        org.apache.shiro.aop.MethodInvocation mi = createMethodInvocation(methodInvocation);
        assertAuthorized(mi);
        
        Collection<AuthorizingAnnotationMethodInterceptor> aamis = getMethodInterceptors();
        if (aamis != null && !aamis.isEmpty()) {
            for (AuthorizingAnnotationMethodInterceptor aami : aamis) {
                if (aami.supports(mi)){
                	
                	//针对DataAnnotationMethodInterceptor，有特殊的处理
                	if(aami instanceof DataAnnotationMethodInterceptor) {
                		return ((DataAnnotationMethodInterceptor)aami).invoke(mi);
                	}
                	
                } 
            }
        }
        
        //其它情况均使用系统缺省
        return super.invoke(mi);
    }

}
