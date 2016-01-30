package cn.sharetop.shiro.demo.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.sharetop.shiro.demo.web.domain.ConditionRequest;
import cn.sharetop.shiro.extension.spring.annotation.RequiresAction;
import cn.sharetop.shiro.extension.spring.annotation.RequiresData;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = { "Accept=text/html" })
	public ModelAndView postLogin(
			 @RequestParam("u_name") String login_name
			,@RequestParam("u_pswd") String login_password) {

		ModelAndView mv = new ModelAndView();

		try {
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(login_name, login_password);

			subject.login(token);

			mv.setViewName("redirect:/hello");

		} catch (Exception ex) {
			ex.printStackTrace();
			mv.addObject("error", ex.getMessage());
			mv.setViewName("redirect:/hello");
		}

		return mv;

	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET, headers = { "Accept=text/html" })
	public ModelAndView getLogout() {
		ModelAndView mv = new ModelAndView();

		SecurityUtils.getSubject().logout();

		mv.setViewName("redirect:/index.jsp");
		
		return mv;
	}

	@RequiresAction("调用showHello这个方法")
	@RequestMapping(value="/hello"
		,method=RequestMethod.GET
		,headers = {"Accept=text/html"})
	public ModelAndView showHello(){
		ModelAndView mv = new ModelAndView();
		
		String cnt = "Hello @RequiresAction()";
		mv.addObject("message", cnt);
		
		mv.setViewName("hello");
		return mv;
	}
	
	@RequiresData(props={"realName","groupNames[1]"},fields={"saleName","province"})
	@RequestMapping(value = "/data", method = RequestMethod.POST, headers = {
			"Content-Type=application/json;charset=utf-8", "Accept=application/json" })
	public @ResponseBody Map<String,String> showData(@RequestBody ConditionRequest req){
		Map<String,String> resp = new HashMap<String,String>();
		
//		StringBuilder sb = new StringBuilder();
//		sb.append("Hello @RequiresData()\r\n");
//		
//		for(String k :req.params.keySet()){
//			sb.append("k=");
//			sb.append(k);
//			sb.append(",v=");
//			sb.append(req.params.get(k));
//			sb.append(";");
//		}
//		
//		
//		resp.put("message", sb.toString());
		
		resp.put("message", JSON.toJSONString(req));
		return resp;
	}
	
}
