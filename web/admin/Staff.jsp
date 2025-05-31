<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="../Style/css/style.css">
    <link rel="stylesheet" href="../Style/css/Admin/staff.css">
    <title>Staff</title>
</head>
<body class="adminBody">
        
    <%@ include file="SideMenu.jsp" %>
    <%@ include file="Header.jsp" %>
  
    <div class="page bg-light d-flex align-items-center justify-content-center">
        <div class="bg-white rounded shadow-sm container-xl maxPageHeight p-3">
            <h2 class="text-center shadow-sm p-2 rounded bg-light ">Staff</h2>
            
            <!--Create Staff-->
            <button class="btn btn-sm btn-outline-primary w-100 my-3 mx-1 border shadow-sm" onclick="toggleContainer('addStaffContainer')">Add</button>
            <div class="popUpContainer align-items-center justify-content-center" id="addStaffContainer" style="display: none">
                <div class="popUpContent container-sm">
                    <div class="bg-light shadow-sm p-4">
                        <h2 class="w-100 text-center">Add Staff</h2>
                        <form action="Staff" method="post" class="bg-white rounded shadow p-5">
                            <div class="mb-4">
                                <label class="form-label">Username</label><br>
                                <input type="text" name="username" class="w-100" required>
                                <p><i class="text-gray">Please use staff ID as the username, it is okay to create without using staff ID.</i></p>
                            </div>
                            <div class="mb-4">
                                <label class="form-label">Password</label><br>
                                <input type="text" name="password" class="w-100" value="12345678" required>
                                <p><i class="text-gray">Default password for a new staff account is <b>12345678</b>, feel free to make any changes.</i></p>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Name</label><br>
                                <input type="text" name="name" class="w-100"  required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Email</label><br>
                                <input type="email" name="email" class="w-100"  required>
                            </div>
                            <input type="submit" class="btn btn-sm btn-primary w-100 py-2 mt-3" value="Add">
                            <a href="Staff.jsp" class="btn btn-sm btn-dark w-100 py-2 mt-2 shadow-sm">Cancel</a>
                        </form>
                    </div>
                </div>
            </div>
                                        
            <!--Search Bar-->
            <form class="searchBar container-sm bg-white shadow-sm mb-1 mx-auto rounded-pill border" action="Staff" method="get">
                <input type="text" name="username" placeholder="Username" value="${searchedUsername==null?"":searchedUsername}">
                <input type="submit" value="Search">
            </form>
            <p class="text-gray text-center my-2"><small><i>(${staffs.isEmpty()?"0":staffs.size()} Records Found)</i></small></p>
            
            <div id="staffsContainer">
                <c:forEach var="staff" items="${staffs}">
                    <div class="rounded shadow-sm p-2 border d-flex mx-auto my-2 justify-content-between w-100 row">
                         <div class="d-flex col-4">
                             <img src="${empty staff.imgpath ? '../Img/Profile/NoProfile.png' : staff.imgpath}" alt="" class="smallProfileImg shadow-sm me-4">
                             <div>
                                 <p><b>${staff.username}</b></p>
                                 <p>${staff.person.name}</p>
                             </div>
                         </div>
                         <div class="col-sm-2 d-sm-flex align-items-center justify-content-center d-none">
                            <p class="badge rounded-pill text-bg-danger me-1 my-1">${staff.status?"":"Banned"}</p>
                         </div>
                         <div class="col-md-1 d-flex align-items-center justify-content-end">
                            <a href="StaffDetails?staffID=${staff.id}" class="btn btn-sm btn-outline-primary w-100 my-1 my-sm-0">More</a>
                         </div>
                    </div>
                </c:forEach>

            </div>

        </div>
    </div>
    <script src="../Style/js/js.js" type="text/javascript"></script>
</body>
</html>