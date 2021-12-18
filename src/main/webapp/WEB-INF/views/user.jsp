<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring Security SAML Multi-tenant Demo</title>
</head>
<body>
  <h2>Spring Security 4 - SAML Multi-tenant Demo</h2>
  <hr />
  <h3>User dashboard  </h3>
  <security:authorize access="isAuthenticated()">
     <b>Welcome! <security:authentication property="principal.username" /></b>
  </security:authorize>
  <br />
  <security:authorize access="isAuthenticated()">
    <a href="/">Home</a>
  </security:authorize>
</body>
</html>
