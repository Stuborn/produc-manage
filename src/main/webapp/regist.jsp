<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 引入Struts2标签库   -->
<%@ taglib prefix="s" uri="/struts-tags" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<style type="text/css">*{font-size:12px;}</style>
<title>注册页面</title>
</head>
<body>
       <div style="margin:0 auto;">
           <div style="font-size:14px;font-weight:bold;">用户注册</div>
           <div>
               <s:form action="regist" namespace="/">
                   <s:textfield name="username" style="font-size:12px;width:120px;" label="登陆名称" />
                   <s:password name="password" style="font-size:12px;width:120px;" label="登陆密码" />
                   <s:select id="role" name="role" class="ComboType" list="#{'1':'编辑人员','2':'查询人员','3':'审核人员'}" label="用户角色"></s:select>
                   <s:submit value="注册" />
               </s:form>
           </div>
       </div>    
</body>
</html>