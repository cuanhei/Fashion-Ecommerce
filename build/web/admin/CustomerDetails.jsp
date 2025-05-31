<%@page import="Model.Orders"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="../Img/Logo/logo.png" type="image/x-icon">
    <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="../Style/css/style.css">
    <link rel="stylesheet" href="../Style/css/Admin/customerDetails.css">
    <title>Customer Details</title>
</head>
<body class="bg-light pt-5">
    
    <%@ include file="SideMenu.jsp" %>
    <%@ include file="Header.jsp" %>

    <div class="page bg-light d-flex align-items-center justify-content-center ">
        <form class="bg-white rounded shadow-sm container-xl maxPageHeight p-3 mb-5" method="post" action="UpdateCustomer" enctype="multipart/form-data" onsubmit="return confirmSubmission('Are you sure you want to make changes on this Customer?')">

            <h2 class="text-center shadow-sm p-2 rounded bg-light mb-3">Customer</h2>
            
            <!--Fixed Information-->
            <div class="bg-light shadow-sm rounded border py-1">
                <p class="text-gray px-3"><i><b>Fixed Information</b></i></p><hr>
                <div class="row d-flex align-items-center justify-content-around p-2">
                    <div class="col-lg-3">
                        <p class="text-gray"><i>Role</i></p>
                        <div class="tag rounded-pill bg-${customer.getRoleTagColor()}"><p>${customer.getRoleStr()}</p></div>
                    </div>
                    <div class="col-lg-3">
                        <p class="text-gray"><i>Date Joined</i></p>
                        <p class="text-gray"><b>${customer.getDatereg()}</b></p>
                    </div>
                </div>
            </div>

            <input type="hidden" name="customerID" value="${customer.getId()}">
            
            <!--Editable Information-->
            <div class="mt-4 row">
            
                <div class="col-lg-2">
                    <p class="text-gray text-center mb-2"><b>Profile Image</b></p>
                    <input type="file" name="profilePicInput" id="profilePicInput" accept="image/*" onchange="previewPhoto(event,'profileImg')" style="display: none;" <%=loggedAcc.isManager()?"":"disabled"%>>
                    <img class="roundImg  w-80 d-flex mx-auto cursor-pointer shadow-sm border" id="profileImg"
                         src="${customer.getImgpath()==null?"../Img/Profile/NoProfile.png":customer.getImgpath()}" 
                            alt="Profile Pic" 
                            onclick="triggerFileInput('profilePicInput')">
                </div>

                <div class="col-lg-5 px-3 d-block">

                    <p class="text-gray"><b>Username</b></p>
                    <input class="w-100" type="text" name="username" value="${customer.username}" <%=loggedAcc.isManager()?"":"disabled"%>><br>
<!--                    <small class='text-danger'><i>Error</i></small><br>-->
                    <small class="text-gray"><i>Please do not change the username of customer unless they required you to change for them.</i></small>
                    
                    <p class="text-gray mt-4"><b>Password</b></p>
                    <input class="w-100" type="password" name="password" value="${customer.password}" <%=loggedAcc.isManager()?"":"disabled"%>><br>
                    <small class="text-gray"><i>Please do not change the password of customer unless they required you to change for them.</i></small>
                    
                    <p class="text-gray mt-4"><b>Name</b></p>
                    <input class="w-100" type="text" name="name" value="${customer.person.name}" <%=loggedAcc.isManager()?"":"disabled"%>><br>
                    <small class="text-gray"><i>Customer name may appear around the system where they contribute or are mentioned.</i></small>

                    <p class="text-gray mt-4"><b>Email</b></p>
                    <input class="w-100" type="text" name="email" value="${customer.person.email}" <%=loggedAcc.isManager()?"":"disabled"%>><br>
                    <small class="text-gray"><i>Customer email for contact them.</i></small>
                    
                     <p class="text-gray mt-4"><b>Address</b></p>
                    <textarea class="w-100 border" name="address" id="" <%=loggedAcc.isManager()?"":"disabled"%>>${customer.person.address}</textarea><br>
                    <small class="text-gray"><i>Your current residential address to ensure that all deliveries and correspondence reach you accurately.</i></small>

                    <p class="text-gray mt-4"><b>Gender</b></p>
                    <div class="d-flex rounded border justify-content-around p-2">
                        <div class="d-flex align-items-center justify-content-center">
                            <input type="radio" ${(customer.person.gender=='m')?"checked":""} value="m" name="gender" <%=loggedAcc.isManager()?"":"disabled"%>>
                            <label class="ms-1 text-gray"><p>Male</p></label>
                        </div>
                        <div class="d-flex align-items-center justify-content-center" >
                            <input type="radio" ${(customer.person.gender=='f')?"checked":""} value="f" name="gender" <%=loggedAcc.isManager()?"":"disabled"%>>
                            <label class="ms-1 text-gray" ><p>Female</p></label>
                        </div>
                        <div class="d-flex align-items-center justify-content-center">
                            <input type="radio" ${(customer.person.gender=='o')?"checked":""} value="o" name="gender" <%=loggedAcc.isManager()?"":"disabled"%>>
                            <label class="ms-1 text-gray"><p>Other</p></label>
                        </div>
                    </div>
                    <small class="text-gray"><i>Gender select is for appropriate pronoun use.</i></small>

                    <br>
                    <%if(loggedAcc.isManager()){%>
                    <input class="btn btn-primary btn-sm mt-5 w-100" type="submit" name="Update" value="Update">
                    <input class="btn btn-${customer.status?"danger":"success"} btn-sm mt-2 w-100" type="submit" name="ban" value="${customer.status?"Ban":"Unban"}">
                    <%}%>
                    <a href="Customer" class="btn btn-dark btn-sm mt-2 w-100" >Back</a>
                    <%if(loggedAcc.isManager()){%>
                    <input class="btn btn-outline-danger btn-sm mt-2 w-100" type="submit" name="delete" value="Delete">
                    <%}%>
                </div>
                <div class="col-md-5" id="orderContainer">
                    <h6 class="text-center shadow-sm p-2 rounded bg-light mb-3 border">Customer Order</h6>
                    <%
                        List<Orders> orders = (List<Orders>) request.getAttribute("orders");
                        if (orders != null) {
                            for (Orders order : orders) {
                    %>
                    <div class="rounded shadow-sm border p-3 container-sm mt-3 d-flex cursor-pointer">
                        <img src="../Img/Icon/order.png" class="iconImg me-3">
                        <div class="row w-100">
                            <div class="col d-flex align-items-center">
                                <p><i class="text-gray">Order ID :</i><b> <%=order.getId()%></b></p>
                            </div>
                            <div class="col d-flex align-items-center justify-content-end">
                                <p><i class="text-gray">Date :</i><b> <%=order.getDateStr()%></b></p>
                            </div>
                            <div class="col d-flex align-items-center justify-content-end">
                                <a href="Order?OrderID=<%=order.getId()%>" class="btn btn-sm btn-outline-primary">More</a>
                            </div>
                        </div>
                    </div>
                    <%
                            }
                        }
                    %>
                </div>

            </div>

        </form>
    </div>
    <script src="../Style/js/js.js"></script>
</body>
</html>