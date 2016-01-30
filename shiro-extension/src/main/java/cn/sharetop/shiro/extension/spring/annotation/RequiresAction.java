package cn.sharetop.shiro.extension.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author yancheng
 * <br><br>
 * 声明为操作控制权限的方法 <br>
 * 使用示例：<br>
 * # @RequiresAction("编辑用户") <br>
 * # @RequestMapping(...) <br>
 * # @ResponseBody BaseResp postEditUser(@RequestBody UserInfoReq req){...} <br>
 * 
 * */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresAction {
	String value() default "";
}
