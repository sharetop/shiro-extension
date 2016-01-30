package cn.sharetop.shiro.extension.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yancheng
 * 
 * 示例: <br/>
 *  #@RequiresData(props={"userName","groupNames[1]"},fields={"saleName","province"})
 * 
 * 
 * */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresData {
	
	/**
	 * UserObj中的属性名称
	 * */
	String[] props() default "";
	
	/**
	 * 需要添到DataParameterRequest.getParameters()中的字段名称
	 * */
	String[] fields() default "";
	
}
