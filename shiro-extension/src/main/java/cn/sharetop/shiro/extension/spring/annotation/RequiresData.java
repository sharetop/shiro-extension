package cn.sharetop.shiro.extension.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yancheng
 * 
 * 示例: <br/>
 *  #@RequiresData({prop="userName",field="profileName"})
 *  #@RequiresData({prop="groupNames",index=0,field="province"})
 * 
 * 
 * */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresData {
	
	/**
	 * UserObj中的属性名称，如果类型为List，与index结合使用
	 * */
	String[] props() default "";
	
	/**
	 * 字段名称
	 * */
	String[] fields() default "";
	
	
	
}
