<%-- 
    Document   : Review
    Created on : 4 May 2025, 10:07:29 am
    Author     : LIM CUAN HEI
--%>

<%@page import="Model.Reviewreply"%>
<%@page import="Model.Review"%>
<%@page import="java.util.List"%>
<%@page import="Model.Reviewimg"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="../Style/css/style.css">
    <link href="../Style/css/Admin/review.css" rel="stylesheet" type="text/css"/>
    <title>Product Review</title>
</head>
<body class="adminBody bg-light">
    
    <%@ include file="SideMenu.jsp" %>
    <%@ include file="Header.jsp" %>
    
    <div class="page bg-light d-flex align-items-center justify-content-center">
        <div class="bg-white rounded shadow-sm container-xl maxPageHeight p-3">
            <h2 class="text-center shadow-sm p-2 rounded bg-light mb-3">Product Reviews</h2>
            <div class="p-3 rounded d-flex align-items-center justify-content-between mb-3">
                <div class="d-flex align-items-center">
                    <img src="${product.imgpath==null?"../Img/Product/blank.jpg":product.imgpath}" alt="" class="iconImg align-items-center d-flex me-3">
                    <p><b>${product.name}</b></p>
                </div>
                <div class="d-flex align-items-center">
                    <p>Rate : ${product.rate}/5</p> <h6 class="text-warning me-1 ms-2 my-auto">&#9733;</h6>
                </div>
            </div>
            
            <div id="reviewMainContainer">
            <%
                List<Review> reviews = (List<Review>) request.getAttribute("reviews");
                if (reviews != null && !reviews.isEmpty()) {
                    for (Review review : reviews) {
            %>
                <div class="container-sm shadow-sm border rounded p-3 mt-2">
                    <div class="d-flex align-items-center justify-content-between">
                        <div class="d-flex">
                            <%if(review.getAccount()==null){%>
                            <img src="../Img/Profile/NoProfile.png" alt="" class="smallProfileImg shadow-sm me-2">
                            <%}else{%>
                            <img src="<%=review.getAccount().getImgpath()==null?"../Img/Profile/NoProfile.png":review.getAccount().getImgpath()%>" alt="" class="smallProfileImg shadow-sm me-2">
                            <%}%>
                            <div>
                                <%if(review.getAccount()==null){%>
                                <div class="tag rounded-pill bg-secondary"><p>Deleted User</p></div>
                                <%}else{%>
                                <p><b><%=review.getAccount().getUsername()%></b></p>
                                <%}%>
                                <p><small>Posted at : <%=review.getDate()%></small></p>
                            </div>
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
                        <div class="d-flex align-items-center justify-content-between">
                            <button class="btn btn-sm btn-outline-primary me-1" onclick="toggleContainerBlock('<%=review.getId()%>')">Reply</button>
                            <form method="post" action="Review" onsubmit="confirmSubmission('Are you sure you want to delete this review?')">
                                <input type="hidden" name="purpose" value="delete">
                                <input type="hidden" name="productID" value="${product.id}">
                                <input type="hidden" name="reviewID" value="<%=review.getId()%>">
                                <input type="submit" name="delete" value="Delete" class="btn btn-sm btn-outline-danger">
                            </form>
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
                    <form method="post" action="Review" style="display: none" id="<%=review.getId()%>">
                        <input type="hidden" name="purpose" value="reply">
                        <input type="hidden" name="productID" value="${product.id}">
                        <input type="hidden" name="reviewID" value="<%=review.getId()%>">
                        <hr class="w-100">
                        <textarea placeholder="Message..." class="border rounded w-100 my-3" name="message"></textarea>
                        <input type="submit" name="reply" value="Reply" class="btn btn-sm w-100 btn-primary">
                    </form>
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
            <a href="Product?categoryID=all" class="btn btn-sm btn-dark w-100">Back</a>
        </div>
    </div>
    <script src="../Style/js/Admin/review.js" type="text/javascript"></script>
    <script src="../Style/js/js.js" type="text/javascript"></script>
</body>
</html>