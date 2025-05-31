<%-- 
    Document   : ProductDetails
    Created on : 15 Mar 2025, 11:52:15 am
    Author     : LIM CUAN HEI
--%>

<%@page import="Model.Account"%>
<%@page import="Model.Reviewreply"%>
<%@page import="Model.Reviewimg"%>
<%@page import="Model.Review"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="../Style/css/style.css">
    <link rel="stylesheet" href="../Style/css/User/shop.css">
    <link rel="stylesheet" href="../Style/css/User/productDetails.css">
    <title>Product Details</title>
</head>
<body class="bg-light">
   
    <%@ include file="Header.jsp" %>
    
    <div class="container-sm mx-auto bg-white shadow-sm rounded row productContainer p-lg-2 p-3 pb-lg-4 my-lg-4 mt-4" id="main">
        <div class="col-sm-5 d-flex justify-content-center align-items-center">
            <img class="productImg" src="${product.imgpath==null?"../Img/Product/blank.jpg":product.imgpath}" alt="">
        </div>
        <div class="col-sm-7 d-flex flex-column justify-content-between">
            <div>
                <h2>${product.name}</h2>
                <p>${product.description}</p>
                <div class="d-flex mt-2">
                    <c:forEach var="category" items="${product.getCategories()}">
                        <p class="badge rounded-pill text-bg-${category.tagcolor} me-1 my-1">${category.text}</p>
                    </c:forEach>
                </div>
            </div>
            <div class="d-flex flex-column py-3">
                <h6>RM ${String.format("%.2f",product.sellingprice)} <small><i class="text-gray">(per unit)</i></small></h6>
                <small><i class="text-gray">Date Released : ${product.getDateRelease()}</i></small>
            </div>

            <hr>

            <div class="d-flex my-3">
                <c:forEach var="stockQty" items="${stockQuantities}">
                    <p class="btn btn-${stockQty.value > 0 ?"light":"secondary opacity-25"} shadow-sm btn-sm me-1 ${stockQty.value > 0 ?"sizeBtn":"disableSizeBtn"} border"  onclick='showPurchaseForm("${stockQty.value > 0? stockQty.key.id:""}")'>${stockQty.key.text}</p>
                </c:forEach>
            </div>
            <c:forEach var="stockQty" items="${stockQuantities}">
                <form method="post" action="AddToCart" id="${stockQty.key.id}" class="purcahseForm" style="display: none">
                    <input type="hidden" name="sizeID" value="${stockQty.key.id}">
                    <input type="hidden" name="productID" value="${product.id}">
                    <small class="text-gray mb-3"><i>(Quantity of Size <b>${stockQty.key.text}</b> remaining in store: <b class="ms-1">${stockQty.value} </b>units)</i></small>
                    <p>Number of <b>${stockQty.key.text}</b> size unit(s) to order : </p>
                    <input type="number" name="quantity" min="0" max="${stockQty.value}" class="border rounded w-100">
                    <hr class="my-3">
                    <%
                        if(loggedAcc.getUsername()==null){
                    %>
                        <p class="btn btn-secondary opacity-50 btn-sm my-2 w-100">Add To Cart</p>
                    <%
                        }else{
                    %>
                        <input type="submit" class="btn btn-primary btn-sm my-2 w-100" value="Add To Cart">
                    <%
                        }
                    %>
                </form>
            </c:forEach>
            <p class="text-gray opacity-75 purcahseForm"><i>(Please select the size that you wanted to purchase.)</i></p>
            <hr class="my-3 purcahseForm">

<!--            <table>
                <tr>
                    <td><p>Shipping fees</p></td>
                    <td><p class="text-end"><b>RM 25.00</b></p></td>
                </tr>
                <tr>
                    <td><p>SST Tax</p></td>
                    <td><p class="text-end"><b>10%</b></p></td>
                </tr>
                <tr>
                    <td><p>Total</p></td>
                    <td><p class="text-end"><b>RM 400.00</b></p></td>
                </tr>
            </table>-->

            
            <a href="Shop?categoryID=all" class="btn btn-dark btn-sm">Back</a>
        </div>
        <div>
            <hr class="mt-4">
            <h6 class="text-gray text-center py-2">Review</h6>
            <%
                List<Review> reviews = (List<Review>) request.getAttribute("reviews");
                if (!reviews.isEmpty()) {
            
                    for (Review review : reviews) {
                    Account reviewAcc = review.getAccount();
            %>
                <div class="container-sm shadow-sm border rounded p-3 mb-2">
                    <div class="d-flex align-items-center justify-content-between">
                        <div class="d-flex">
                            <%
                                if(reviewAcc != null){
                            %>
                                <img src="<%=review.getAccount().getImgpath()==null?"../Img/Profile/NoProfile.png":review.getAccount().getImgpath()%>" alt="" class="smallProfileImg shadow-sm me-2">
                                <div>
                                    <p><b><%=review.getAccount().getUsername()%></b></p>
                                    <p><small>Posted at : <%=review.getDate()%></small></p>
                                </div>
                            <%  }else{ %>
                                <img src="../Img/Profile/NoProfile.png" alt="" class="smallProfileImg shadow-sm me-2">
                                <div>
                                    <div class="tag rounded-pill bg-secondary"><p>Deleted User</p></div>
                                    <p><small>Posted at : <%=review.getDate()%></small></p>
                                </div>
                            <%}%>
                        </div>
                        <div class="d-flex align-items-center">
                        <%
                            int printedStar = 0;
                            for(printedStar = 0; printedStar < review.getRate(); printedStar++){
                        %>
                            <h6 class="text-warning me-1">&#9733;</h6>
                        <%
                            }
                            for(printedStar = printedStar; printedStar < 5; printedStar++){
                        %>
                            <h6 class="text-gray me-1">&#9733;</h6>
                        <%
                            }
                        %>
                        </div>
                        
                    </div>
                    <hr class="my-2">
                    <p><%=review.getDetails()%></p>
                    <div class="d-flex mt-2 flex-wrap">
                        <%
                            if(!review.getReviewImg().isEmpty()){
                                for(Reviewimg img : review.getReviewImg()){
                            
                        %>
                        <img src="<%=img.getImgpath()%>" alt="" class="reviewImg rounded me-1 mb-1">
                        <%
                                }
                            }
                        %>
                    </div>
                    <% if(!review.getReplies().isEmpty()){ %>
                        <p class="text-center text-gray cursor-pointer" onclick="toggleShowReplies(this);toggleContainerBlock('Replies<%=review.getId()%>');">Show Replies</p>
                        <div id="Replies<%=review.getId()%>" style="display: none">
                            <hr class="w-100 my-2">
                            <% for(Reviewreply reply : review.getReplies()){%>
                            <div class="shadow-sm p-2 rounded bg-light mb-2">
                                <div class="d-flex">
                                    <%if(reply.getAccount()==null){%>
                                    <img src="../Img/Profile/NoProfile.png" alt="" class="smallProfileImg shadow-sm me-2">
                                    <%}else{%>
                                    <img src="<%=reply.getAccount().getImgpath()==null?"../Img/Profile/NoProfile.png":reply.getAccount().getImgpath()%>" alt="" class="smallProfileImg shadow-sm me-2">
                                    <%}%>
                                    <div>
                                        <p><b><%=reply.getAccount()==null?"Account Deleted":reply.getAccount().getUsername()%></b></p>
                                        <p><small>Posted at : <%=reply.getDatereply()%></small></p>
                                    </div>
                                </div>
                                <hr class="w-100 my-1">
                                <p><%=reply.getDetails()%></p>
                            </div>
    
                            <%}%>
                        </div>
                    <% }%>
                </div>  
            <%
                    }
                }else{
            %>
                <p class="text-gray text-center"><i>(No Reviews Found)</i></p>
            <%
                }
            %>
        </div>
    </div>
    <script src="../Style/js/User/productDetails.js" type="text/javascript"></script>
    <script src="../Style/js/js.js" type="text/javascript"></script>
</body>
</html>