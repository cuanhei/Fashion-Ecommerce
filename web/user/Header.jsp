<jsp:useBean id="loggedAcc" scope="session" class="Model.Account" />
<%
    String currentPage = request.getRequestURI();
%>
<link rel="stylesheet" href="../Style/css/User/header.css">
<!--Header-->
<header class="w-100 shadow-sm py-2 bg-white">
    <div class="container-sm d-flex justify-content-sm-between justify-content-center align-items-center">
        <!--Logo-->
        <img src="../Img/Logo/logo.png" id="logoImg">

        <!--Menus-->
        <div class="d-flex justify-content-between align-items-center bg-white" id="headerMenuContainer">
            <a href="Home"><img src="../Img/Icon/home.png" alt="" class="iconImg ms-sm-3 <%= currentPage.contains("Home") ? "" : "opacity-50" %>"></a>
            <a href="Shop?categoryID=all"><img src="../Img/Icon/shop.png" alt="" class="iconImg ms-sm-3 <%= currentPage.contains("Shop") || currentPage.contains("ProductDetails") ? "" : "opacity-50" %>"></a>
            
            <!--Guest-->
            <%
                if (loggedAcc.getId() == null) {
            %>
                    <a href='SignUp.jsp'><img src='../Img/Icon/guest.png' class='iconImg ms-sm-3 <%= currentPage.contains("SignUp") || currentPage.contains("SignIn") ? "" : "opacity-50" %>'></a>
            <%
                }
                else {
            %>
            <!--User-->
                <a href='Cart'><img src='../Img/Icon/cart.png' class='iconImg ms-sm-3 <%=currentPage.contains("Cart") || currentPage.contains("Payment")?"":"opacity-50"%>'></a>
                <a href='Order'><img src='../Img/Icon/order.png' class='iconImg ms-sm-3 <%=currentPage.contains("Order")?"":"opacity-50"%>'></a>
                <a href='Profile.jsp'><img src='${loggedAcc.imgpath != null ? loggedAcc.imgpath : '../Img/Profile/NoProfile.png'}' class='smallProfileImg rounded-pill ms-sm-3 shadow-sm'></a>
            <%
                }
            %>
        </div>
    </div>
</header>