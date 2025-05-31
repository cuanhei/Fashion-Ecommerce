<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="loggedAcc" scope="session" class="Model.Account" />

<%
    if(loggedAcc == null || !loggedAcc.isAdmin()){
        response.sendRedirect("../user/Home");
    }
    
    String currentPage = request.getRequestURI();
%>

<link rel="stylesheet" href="../Style/css/Admin/sideMenu.css">
<div id="sideMenu" class="shadow-sm bg-white">
    <div class="d-flex flex-column py-5">
        <%if(loggedAcc.isManager()){%>
         <a href="Dashboard?reportType=year" class="w-100 py-1 px-3 d-flex align-items-center <%= currentPage.contains("Dashboard")? "activeSideMenu" : "" %>">
             <img class="opacity-75" src="../Img/Icon/dashboard.png" alt="">
             <p class="text-gray ms-3">Dashboard</p>
         </a>
         <%}%>
         <a href="Product?categoryID=all" class="w-100 py-1 px-3 d-flex align-items-center <%= currentPage.contains("Product") || currentPage.contains("ProductDetails") || currentPage.contains("Review")? "activeSideMenu" : "" %>">
             <img class="opacity-75" src="../Img/Icon/shop.png" alt="">
             <p class="text-gray ms-3">Product</p>
         </a>
         <a href="Customer" class="w-100 py-1 px-3 d-flex align-items-center <%= currentPage.contains("Customer") || currentPage.contains("CustomerDetails")? "activeSideMenu" : "" %>">
             <img class="opacity-75" src="../Img/Icon/customer.png" alt="">
             <p class="text-gray ms-3">Customer</p>
         </a>
         <a href="Order?status=all" class="w-100 py-1 px-3 d-flex align-items-center <%= currentPage.contains("Order")? "activeSideMenu" : "" %>">
             <img class="opacity-75" src="../Img/Icon/order.png" alt="">
             <p class="text-gray ms-3">Order</p>
         </a>
        <%if(loggedAcc.isManager()){%>
         <a href="Staff" class="w-100 py-1 px-3 d-flex align-items-center <%= currentPage.contains("Staff") || currentPage.contains("StaffDetails") ? "activeSideMenu" : "" %>">
             <img class="opacity-75" src="../Img/Icon/staff.png" alt="">
             <p class="text-gray ms-3">Staff</p>
         </a>
         <%}%>
         <a href="Profile.jsp" class="w-100 py-1 px-3 d-flex align-items-center <%= currentPage.contains("Profile")? "activeSideMenu" : "" %>">
            <img class="opacity-75" src="../Img/Icon/profile.png" alt="">
            <p class="text-gray ms-3">Profile</p>
        </a>
         <a href="SignOut" class="w-100 py-1 px-3 d-flex align-items-center">
             <img class="opacity-75" src="../Img/Icon/signOut.png" alt="">
             <p class="text-gray ms-3">Sign Out</p>
         </a>
    </div>
    <footer class="text-center">
         <small class="text-gray"><%= application.getInitParameter("copyrightText") %></small>
    </footer>
 </div>