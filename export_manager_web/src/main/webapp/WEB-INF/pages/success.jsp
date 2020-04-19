<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    操作成功
<a href="index.jsp">1</a> 相同路径下 相对路径(相对于本页面而言)<br/>
<a href="/index.jsp">2</a> 相对路径 相对于tomcat下项目而言 不会有项目名称<br/>
 <a href="/day18/index.jsp">3</a> 相对路径 相对于tomcat下项目而言 自己加项目名称 可以访问index.jsp<br/>
 <a href="${pageContext.request.contextPath}/index.jsp">4</a> 相对路径 相对于tomcat下项目而言
自己加项目名称 可以访问index.jsp 动态获得项目名称<br/>

    <a href="http://localhost:8080/day18/index.jsp">2</a>

</body>
</html>
