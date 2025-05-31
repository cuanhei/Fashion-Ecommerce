<%-- 
    Document   : 404
    Created on : 7 May 2025, 1:10:52 pm
    Author     : LIM CUAN HEI
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
    <link href="../Style/css/style.css" rel="stylesheet" type="text/css"/>
    <title>Page Not Found</title>
</head>
<body class="d-flex align-items-center justify-content-center bg-light vh-100">
    <div class="bg-white rounded shadow-sm text-center p-5 ">
        <h3 ><b>Oops! The URL or page not found.</b></h3><br>
        <p class="text-gray">This might cause by <%= application.getInitParameter("companyName") %> does not developed this page. Please double check the URL or just back to Home page.</p><br>
        <a href="http://localhost:8080/JavaEcommerce/user/Home"><p>Back To Home Page</p></a>
    </div>
</body>
</html>

