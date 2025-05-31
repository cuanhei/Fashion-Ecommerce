<%@page import="java.util.List"%>
<%@page import="Model.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="../Style/css/style.css">
    <link rel="stylesheet" href="../Style/css/Admin/customer.css">
    <title>Customer</title>
</head>
<body class="adminBody">
    
    <%@ include file="SideMenu.jsp" %>
    <%@ include file="Header.jsp" %>
    
    <%
        List<Account> customers = (List<Account>)request.getAttribute("customers");
    %>
    
    <div class="page bg-light d-flex align-items-center justify-content-center">
        <div class="bg-white rounded shadow-sm container-xl maxPageHeight p-3">
            <h2 class="text-center shadow-sm p-2 rounded bg-light mb-3">Customer</h2>
            <!--Search Bar-->
            <form class="searchBar container-sm bg-white shadow-sm mb-1 mx-auto rounded-pill border" action="Customer" method="get">
                <input type="text" name="username" placeholder="Username" value="${searchedUsername}">
                <input type="submit" value="Search">
            </form>
            
            <p class="text-gray text-center my-2"><small><i>(<%= customers.size()%> Records Found)</i></small></p>

            <div id="customerMainContainer">
                <% for(Account cust : customers) { %>
                    <div class="row d-flex justify-content-between p-2 shadow-sm rounded border mx-1 mb-2">

                        <div class="col-sm-3 d-flex">
                            <div class="d-flex align-items-center ">
                                <img src="<%= cust.getImgpath()==null?"../Img/Profile/NoProfile.png":cust.getImgpath()%>" alt="" class="smallProfileImg">
                            </div>

                            <div class="d-flex flex-column justify-content-center ms-3 text-start text-gray">
                                <h6 class="m-0"><%= cust.getUsername()%></h6>
                            </div>
                        </div>

                        <div class="col-sm-1 d-sm-flex align-items-center justify-content-center d-none">
                            <p class="badge rounded-pill text-bg-<%=cust.getRoleTagColor()%> me-1 my-1"><%=cust.getRoleStr()%></p>
                        </div>
                        <div class="col-sm-1 d-sm-flex align-items-center justify-content-center d-none">
                            <p class="badge rounded-pill text-bg-danger me-1 my-1"><%=cust.getStatus()?"":"Banned"%></p>
                        </div>

                        <div class="col-sm-2 d-sm-flex align-items-center justify-content-center d-none">
                            <p class="text-gray"><i>Date Register : <b><%= cust.getDatereg() %></b></i></p>
                        </div>

                        <form class="col-sm-1 d-flex align-items-center justify-content-end mt-2 mt-sm-0" action="CustomerDetails" method="Post">
                            <input type="hidden" name="ID" value="<%=cust.getId()%>">
                            <input type="submit" class="btn btn-sm btn-outline-primary w-100 my-1 my-sm-0" value="More">
                        </form>

                    </div>
                <% } %>
            </div>
            
        </div>
    </div>
</body>
</html>