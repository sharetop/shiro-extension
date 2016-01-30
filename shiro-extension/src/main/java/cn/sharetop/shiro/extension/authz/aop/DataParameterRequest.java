package cn.sharetop.shiro.extension.authz.aop;

import java.util.Map;

public interface DataParameterRequest {
	Map<String,String> getParameters();
}
