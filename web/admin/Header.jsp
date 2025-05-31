<%@page contentType="text/html" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="../Style/css/Admin/header.css">
<!--Header-->
<header class="w-100 shadow-sm d-flex align-items-center bg-white">
    <div class="container-xl d-flex justify-content-between align-items-center">
        <!--Logo-->
        <img src="../Img/Logo/logo.png" id="logoImg">

        <!--Menus-->
        <a href="Profile.jsp" class="d-flex justify-content-between align-items-center text-decoration-none" id="headerMenuContainer">
            <img src="${loggedAcc.imgpath != null ? loggedAcc.imgpath : '../Img/Profile/NoProfile.png'}" alt="" class="smallProfileImg shadow-sm mx-1">
            <div class="d-flex align-items-start flex-column">
                <p class="text-gray"><b><jsp:getProperty name="loggedAcc" property="username" /></b></p>
                <p class="text-gray"><small><i>${loggedAcc.person.email}</i></small></p>
            </div>
        </a>
    </div>
</header>
            