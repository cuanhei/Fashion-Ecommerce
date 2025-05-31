<%-- 
    Document   : 500
    Created on : 7 May 2025, 1:44:52 pm
    Author     : LIM CUAN HEI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
    <link href="../Style/css/style.css" rel="stylesheet" type="text/css"/>
    <title>System Error</title>
</head>
<body class="d-flex align-items-center justify-content-center bg-light vh-100">
    <div class="bg-white rounded shadow-sm text-center p-5 ">
        <h3 ><b>Oops! Something went wrong.</b></h3><br>
        <p class="text-gray">Sorry for the inconvenient while browsing <%= application.getInitParameter("companyName") %> website.</p><br>
        <p class="text-gray">Please contact us through email : <%= application.getInitParameter("companyEmail") %>. And we will get back to you as fast as possible!</p><br>
        <a href="http://localhost:8080/JavaEcommerce/user/Home"><p>Back To Home Page</p></a>
    </div>
</body>
</html>
