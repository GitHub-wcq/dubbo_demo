<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<h2>Hello World!</h2>
<h2>index</h2>
<div>
	<form action="http://localhost:8080/Z.Consumer/zookeeper/addEphemeralZnode">
		节点名称:<input name="znode" type="text" />
		节点内容:<input name="data" type="text" />
		<button type="submit">提交</button>
	</form>
</div>
</body>
</html>
