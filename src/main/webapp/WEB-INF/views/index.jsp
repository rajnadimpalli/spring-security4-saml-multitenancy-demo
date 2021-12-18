<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
  prefix="security"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring Security SAML Multi-tenant Demo</title>
</head>
<body>
  <h2>Spring Security 4 - SAML Multi-tenant Demo</h2>
  <hr />
  <h3>
    Welcome !
    <security:authorize access="isAnonymous()">
         Guest
     </security:authorize>
    <!-- Print the logged in user name -->
    <security:authorize access="isAuthenticated()">
      <security:authentication property="principal.username" />
    </security:authorize>
  </h3>
  <security:authorize access="isAnonymous()">
    Login as <a href="user">User</a>
  </security:authorize>
  <security:authorize access="isAuthenticated()">
    <security:authorize access="hasRole('USER')">
      <a href="user">My Profile</a>
    </security:authorize>
  </security:authorize>
</body>
</html>
