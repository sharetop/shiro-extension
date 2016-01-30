package cn.sharetop.shiro.extension.authz.aop;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.aop.MethodInvocation;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;
import org.apache.shiro.util.StringUtils;

import cn.sharetop.shiro.extension.spring.annotation.RequiresData;

public class DataAnnotationMethodInterceptor  
	extends AuthorizingAnnotationMethodInterceptor {
	
	public DataAnnotationMethodInterceptor(){
		super(new DataAnnotationHandler());
	}
	public DataAnnotationMethodInterceptor(AnnotationResolver resolver){
		super(new DataAnnotationHandler(),resolver);
	}
	

	@Override
	public void assertAuthorized(MethodInvocation mi) throws AuthorizationException {
		// TODO Auto-generated method stub
		try {
			DataAnnotationHandler handler = (DataAnnotationHandler) getHandler();
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
	
	@SuppressWarnings("unchecked")
	private Map<String,String> _addParameters(String[] props,String[] fields,Class<?> clz,Object principal) throws Exception {
		Map<String,String> params = new HashMap<String,String>();
		
		for(int i=0;i<props.length;i++){
			String prop = props[i];
			String field = fields[i];
			int index = -1;
			
			String[] strs = StringUtils.tokenizeToStringArray(prop, "[]");
			if(strs.length>1){
				prop = strs[0];
				index = Integer.valueOf(strs[1]);
			}
			
			String propValue = "";
			Field p = clz.getDeclaredField(prop);
			if(Modifier.PRIVATE==p.getModifiers()){
				String m_getter_name = "get"+StringUtils.uppercaseFirstChar(prop);
				Method method = clz.getDeclaredMethod(m_getter_name);
				Object ret = method.invoke(principal);
				if(index>-1 && ret instanceof List<?>){
					propValue = ((List<Object>)ret).get(index).toString();
				}
				else
					propValue = ret.toString();
			}
			else{
				Object ret = p.get(principal);
				if(index>-1 && ret instanceof List<?>){
					propValue = ((List<Object>)ret).get(index).toString();
				}
				else {
					propValue = ret.toString();
				}
			}
			System.out.println(propValue);
			
			params.put(field, propValue);
			
		}
		return params;
	}
	
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		// TODO Auto-generated method stub
		assertAuthorized(methodInvocation);
		
		Object obj = methodInvocation.getThis();
		Object[] args = methodInvocation.getArguments();
		
		RequiresData an = (RequiresData)this.getAnnotation(methodInvocation);
		Object principal = this.getSubject().getPrincipal();
		Class<?> clz = principal.getClass();
		
		String[] props = an.props();
		String[] fields = an.fields();
		
		for(Object o : args){
			if( o instanceof DataParameterRequest ){
				Map<String,String> m = (Map<String,String>)((DataParameterRequest)o).getParameters();
				if(m!=null){
					Map<String,String> mm = this._addParameters(props, fields, clz, principal);
					m.putAll(mm);
				}
			}
		}
		
		return methodInvocation.getMethod().invoke(obj, args);
		
	}

	
}
