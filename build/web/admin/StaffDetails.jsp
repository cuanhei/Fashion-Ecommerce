<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="../Style/css/style.css">
    <script src="../Style/js/js.js"></script>
    <title>Staff Details</title>
</head>
<body class="bg-light adminBody">
    
    <%@ include file="SideMenu.jsp" %>
    <%@ include file="Header.jsp" %>
    
    <div class="page d-flex align-items-center justify-content-center my-lg-4 ">
        <form class="container-sm bg-white shadow-sm rounded p-3 mt-5" method="post" action="UpdateStaff" enctype="multipart/form-data" onsubmit="confirmSubmission('Are you sure you want to make changes on this staff?')">
            <input type="hidden" name="staffID" value="${staff.id}">
            <h2 class="text-center shadow-sm p-2 rounded bg-light mb-3">Staff Details</h2>
            <!--Fixed Information-->
            <div class="bg-light shadow-sm rounded border py-1">
                <p class="text-gray px-3"><i><b>Fixed Information</b></i></p><hr>
                <div class="row d-flex align-items-center justify-content-around p-2">
                    <div class="col-lg-3">
                        <p class="text-gray"><i>Role</i></p>
                        <div class="tag rounded-pill bg-${staff.getRoleTagColor()}"><p>${staff.getRoleStr()}</p></div>
                    </div>
                    <div class="col-lg-3">
                        <p class="text-gray"><i>Date Joined</i></p>
                        <p class="text-gray"><b>${staff.datereg}</b></p>
                    </div>
                </div>
            </div>

           
            <!--Editable Information-->
            <div class="mt-4 row">
            
                <div class="col">
                    <p class="text-gray text-center mb-2"><b>Profile Image</b></p>
                    <input type="file" id="profilePicInput" name="profilePicInput" style="display: none;" onchange="previewPhoto(event, 'profileImg')" >
                    <img class="roundImg w-80 d-flex mx-auto cursor-pointer shadow-sm border"
                         id="profileImg"
                         src="${empty staff.imgpath ? '../Img/Profile/NoProfile.png' : staff.imgpath}" 
                         alt="Profile Pic"
                         onclick="triggerFileInput('profilePicInput')">

                </div>

                <div class="col-lg-8 px-3 d-block">

                    <p class="text-gray mt-4"><b>Username</b></p>
                    <input class="w-100" type="text" name="username" value="${staff.username}"><br>
                    <small class="text-gray"><i>Once username had changed, you are required to use the changed username to sign in the system.</i></small>
                    
                    <p class="text-gray mt-4"><b>Password</b></p>
                    <input class="w-100" type="password" name="password" value="${staff.password}"><br>
                    <small class="text-gray"><i>You are not able to reveal your password to prevent sensitive information leaked.</i></small>
                    
                    <p class="text-gray"><b>Name</b></p>
                    <input class="w-100" type="text" name="name" value="${staff.person.name}"><br>
                    <small class="text-gray"><i>Your name may appear around the system where you contribute or are mentioned.</i></small>

                    <p class="text-gray mt-4"><b>Email</b></p>
                    <input class="w-100" type="text" name="email" value="${staff.person.email}"><br>
                    <small class="text-gray"><i>Once email had changed, you are required to use the changed email to sign in the system.</i></small>

                    <p class="text-gray mt-4"><b>Address</b></p>
                    <textarea class="w-100 border" name="address" id="">${staff.person.address}</textarea><br>
                    <small class="text-gray"><i>Your current residential address to ensure that all deliveries and correspondence reach you accurately.</i></small>

                    <p class="text-gray mt-4"><b>Gender</b></p>
                    <div class="d-flex rounded border justify-content-around p-2">
                        <div class="d-flex align-items-center justify-content-center">
                            <input type="radio" ${(staff.person.gender=='m')?"checked":""} value="m" name="gender">
                            <label class="ms-1 text-gray"><p>Male</p></label>
                        </div>
                        <div class="d-flex align-items-center justify-content-center" >
                            <input type="radio" ${(staff.person.gender=='f')?"checked":""} value="f" name="gender">
                            <label class="ms-1 text-gray" ><p>Female</p></label>
                        </div>
                        <div class="d-flex align-items-center justify-content-center">
                            <input type="radio" ${(staff.person.gender=='o')?"checked":""} value="o" name="gender">
                            <label class="ms-1 text-gray"><p>Other</p></label>
                        </div>
                    </div>
                    <small class="text-gray"><i>Gender select is for appropriate pronoun use.</i></small>

                    <p class="text-gray mt-4"><b>IC</b></p>
                    <input class="w-100" type="text" name="ic" value="${staff.person.ic}"><br>
                    <small class="text-gray"><i>Your IC number is used solely for identification and record-keeping.</i></small>

                    <p class="text-gray mt-4"><b>Contact</b></p>
                    <input class="w-100" type="text" name="contact" value="${staff.person.contact}"><br>
                    <small class="text-gray"><i>A contact number is required so your supervisor or admin can reach you.</i></small>

                    <br>
                    <input class="btn btn-primary btn-sm mt-5 w-100" type="submit" value="Update">
                    <input class="btn btn-${staff.status?"danger":"success"} btn-sm mt-2 w-100" type="submit" name="ban" value="${staff.status?"Ban":"Unban"}">
                    <a href="Staff" class="btn btn-dark btn-sm mt-2 w-100">Back</a>
                    <input class="btn btn-outline-danger btn-sm mt-2 w-100" type="submit" name="delete" value="Delete">
                </div>

            </div>

        </form>
    </div>
    <script src="../Style/js/js.js" type="text/javascript"></script>
</body>
</html>
