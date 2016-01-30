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
	
	/**
	 * 将props取出值并与fields一一对应，构造出Map并返回
	 * */
	@SuppressWarnings("unchecked")
	private Map<String,String> _addParameters(String[] props,String[] fields,Class<?> clz,Object principal) throws Exception {
		Map<String,String> params = new HashMap<String,String>();
		
		for(int i=0;i<props.length;i++){
			String prop = props[i];
			String field = fields[i];
			int index = -1;
			
			//处理数组的情况，当前只支持List
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
		
		//待调用的目标对象及参数列表
		Object obj = methodInvocation.getThis();
		Object[] args = methodInvocation.getArguments();
		
		//当前声明的注解
		RequiresData an = (RequiresData)this.getAnnotation(methodInvocation);
		
		//当前登录用户
		Object principal = this.getSubject().getPrincipal();
		Class<?> clz = principal.getClass();
		
		String[] props = an.props();
		String[] fields = an.fields();
		
		//遍历方法参数数组，找到DataParameterRequest类型的参数
		for(Object o : args){
			if( o instanceof DataParameterRequest ){
				//取出它的parameters，此为一个Map类型的成员
				Map<String,String> m = (Map<String,String>)((DataParameterRequest)o).getParameters();
				if(m!=null){
					//将要添加的信息注入到parameters中
					Map<String,String> mm = this._addParameters(props, fields, clz, principal);
					m.putAll(mm);
				}
			}
		}
		
		//继续调用目标类的目标方法，传入修改过的参数数组
		return methodInvocation.getMethod().invoke(obj, args);
		
	}

	
}
