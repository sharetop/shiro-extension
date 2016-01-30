<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Hello Shiro</title>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
</head>
<body>

<c:choose>
<c:when test="${message!=null}">
<h1>${message}</h1>
</c:when>
<c:otherwise>
<h2  style="color:red">No message</h2>
</c:otherwise>
</c:choose>

<a href="javascript:send()">data</a>

<a href="logout">logout</a>

<script>
function post_json(url, body, success) {
	$.ajax({
		url : url,
		type : 'post',
		data : JSON.stringify(body),
		contentType : 'application/json;charset=utf-8',
		success : function(result, status, xhr) {
			success(result, status, xhr);
		}
	});

}

function send(){
	post_json('data',
			{ 	parameters:{
					key:'china',
					sdate:'201011',
					edate:'201601'
				},
				author:'yancheng'
			},
			function(result,status,xhr){
				console.log(result);
	});
	
}
</script>
</body>
</html>