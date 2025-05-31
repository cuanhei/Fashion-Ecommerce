<%-- 
    Document   : Order
    Created on : 2 May 2025, 4:50:02 pm
    Author     : LIM CUAN HEI
--%>

<%@page import="Model.Orders"%>
<%@page import="Model.Orderlist"%>
<%@page import="javax.persistence.criteria.Order"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="../Img/Logo/logo.png" type="image/x-icon">
    <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
    <link href="../Style/css/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="../Style/css/style.css">
    <link href="../Style/css/User/order.css" rel="stylesheet" type="text/css"/>
    <title>Order</title>
</head>
<body class="bg-light">
    
    <%@ include file="Header.jsp" %>
        
    <div class="container-sm bg-white shadow-sm rounded my-4 p-4" id="main">
        
        <!--Search Bar-->
        <form class="searchBar container-sm bg-white shadow-sm mt-3 mb-5 mx-auto rounded-pill border" action="Order" method="get">
           <input type="text" name="orderID" placeholder="Order ID" value="${searchedOrderID}">
           <input type="submit" value="Search">
        </form>
        
        

        <%
            List<Orders> orders = (List<Orders>) request.getAttribute("orders");
            if (orders != null && !orders.isEmpty()) {
                for (Orders order : orders) {
        %>
                    <div class="rounded shadow-sm border p-3 container-sm mt-3 d-flex cursor-pointer order bg-white" onclick="showOnly('orderdetails','<%= order.getId()%>')">
                        <img src="../Img/Icon/package.png" class="iconImg me-3">
                        <div class="row w-100">
                            <div class="col d-flex align-items-center">
                                <p><i class="text-gray">Order ID :</i><b><%= order.getId()%></b></p>
                            </div>
                            <div class="col d-flex align-items-center justify-content-end">
                                <p><i class="text-gray">Date :</i><b> <%= order.getDateStr()%></b></p>
                            </div>
                        </div>
                    </div>

                    <div class="bg-light border shadow-sm p-3 enlarge orderdetails enlarge" id="<%= order.getId()%>" style="display:none">
                        <table class="w-100 px-4">
                            <tr>
                                <td colspan="6">
                                    <div class="d-flex align-items-center justify-content-between ">
                                        <div class="d-flex rounded p-2 align-items-center mb-3">
                                            <img src="../Img/Icon/delivery.png" class="iconImg me-3">
                                            <div>
                                                <p>Delivery Address </p>
                                                <p><b><%= order.getAddress()%></b></p>
                                            </div>
                                        </div>
                                        <div class="d-flex rounded p-2 align-items-center mb-3">
                                            <img src="../Img/Icon/<%= (order.getPayment()).getPaymethod()%>.png" class="iconImg me-3">
                                            <div>
                                                <p><%= (order.getPayment()).getPaymethod()%> </p>
                                                <p><b><%= (order.getPayment()).getDetails()%></b></p>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                             <tr>
                                <td colspan="6"><hr class="my-2"></td>
                            </tr>
                            <tr>
                                <th>Index</th>
                                <th>Image</th>
                                <th>Name</th>
                                <th>Size</th>
                                <th>Quantity</th>
                                <th>Review</th>
                            </tr>
                            <tr>
                                <td colspan="6"><hr class="my-2"></td>
                            </tr>
                            <%
                                int index = 1;
                                for(Orderlist orderlist : order.getOrderLists()){
                                    
                            %>
                                <tr>
                                    <td><%=index++%>.</td>
                                    <td> <img src="<%=orderlist.getProduct()==null?"../Img/Product/blank.jpg":orderlist.getProduct().getImgpath()%>" class="iconImg"></td>
                                    <td><%=orderlist.getProduct()==null?"<div class='tag rounded-pill bg-secondary'><p>Deleted Product</p></div>":orderlist.getProduct().getName()%></td>
                                    <td><%=orderlist.getSize().getText()%></td>
                                    <td><%=orderlist.getQuantity()%></td>
                                    <%
                                        if(orderlist.getReviewid()==null){
                                    %>
                                        <td style="width:10%;"><button class="btn btn-sm btn-primary w-100" onclick="toggleContainer('<%=orderlist.getId()%>')">Review</button></td>
                                        <div class="popUpContainer align-items-center justify-content-center" id="<%=orderlist.getId()%>" style="display: none">
                                        <div class="popUpContent container-sm">
                                            <div class="bg-white shadow-sm p-4 rounded">
                                                <h2 class="w-100 text-center">Review</h2>
                                                <form class="bg-white shadow rounded" method="post" action="CreateReview" enctype="multipart/form-data">
                                                    <input type="hidden" name="orderlistID" value="<%=orderlist.getId()%>">
                                                    <input type="hidden" name="productID" value="<%=orderlist.getProductid()%>">
                                                    <!-- Comment Field -->
                                                    <div class="mb-3">
                                                      <label for="details" class="form-label">Comment</label>
                                                      <textarea id="details" rows="4" class="border rounded w-100" name="details" placeholder="Write your review here..."></textarea>
                                                    </div>

                                                    <!-- Rating Field -->
                                                    <div class="mb-3">
                                                      <label class="form-label">Rating</label>
                                                      <div class="star-rating">
                                                        <input type="radio" id="star5-<%=orderlist.getId()%>" name="rate" value="5"><label for="star5-<%=orderlist.getId()%>">&#9733;</label>
                                                        <input type="radio" id="star4-<%=orderlist.getId()%>" name="rate" value="4"><label for="star4-<%=orderlist.getId()%>">&#9733;</label>
                                                        <input type="radio" id="star3-<%=orderlist.getId()%>" name="rate" value="3"><label for="star3-<%=orderlist.getId()%>">&#9733;</label>
                                                        <input type="radio" id="star2-<%=orderlist.getId()%>" name="rate" value="2"><label for="star2-<%=orderlist.getId()%>">&#9733;</label>
                                                        <input type="radio" id="star1-<%=orderlist.getId()%>" name="rate" value="1"><label for="star1-<%=orderlist.getId()%>">&#9733;</label>
                                                      </div>
                                                    </div>

                                                    <!-- Image Upload Field -->
                                                    <div class="mb-3">
                                                      <label for="images" class="form-label">Upload Images</label>
                                                      <input class="form-control" type="file" id="images" name="images[]" accept="image/*" multiple>
                                                    </div>

                                                    <!-- Submit Button -->
                                                    <button type="submit" class="btn btn-sm btn-primary w-100">Submit Review</button>
                                                    <a href="Order" class="btn btn-sm btn-dark w-100 mt-2">Cancel</a>
                                                  </form>
                                            </div>
                                        </div>
                                    </div>
                                    <%
                                        }
                                        else{   
                                    %>
                                        <td><a href="ProductDetails?productID=<%=orderlist.getProductid()%>&reviewID=<%=orderlist.getReviewid()%>" class="btn btn-sm btn-outline-primary w-100">View</a></td>
                                    <%
                                        }
                                    %>
                                </tr>
                            <%  
                                } 
                            %>
                            <tr>
                                <td colspan="6"><hr class="my-2"></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <th>Total</th>
                                <td>RM <%=String.format("%.2f",order.getPayment().getAmount())%></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td colspan="6"><hr class="my-2"></td>
                            </tr>
                            <tr>
                                <td colspan="6">
                                    <div class"d-flex">
                                        <p class="btn btn-sm me-1 p-2 btn-<%=order.getStatus().equals("Packing")?"primary":" bg-white"%> shadow-sm border">Packing</p>
                                        <p class="btn btn-sm me-1 p-2 btn-<%=order.getStatus().equals("Shipping")?"primary":" bg-white"%> shadow-sm border">Shipping</p>
                                        <p class="btn btn-sm me-1 p-2 btn-<%=order.getStatus().equals("Delivery")?"primary":" bg-white"%> shadow-sm border">Delivery</p>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
        <%
                }
            }else{
        %>
            <small class="d-flex justify-content-center w-100"><i class="text-gray ">(No Record Found)</i></small>
        <%    
            }
        %>

    </div>
    <script src="../Style/js/js.js"></script>
    <script src="../Style/js/User/order.js" type="text/javascript"></script>
</body>
</html>
