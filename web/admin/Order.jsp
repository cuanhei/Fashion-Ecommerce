<%-- 
    Document   : Order
    Created on : 3 May 2025, 11:07:16 pm
    Author     : LIM CUAN HEI
--%>

<%@page import="java.util.List"%>
<%@page import="Model.Orders"%>
<%@page import="Model.Orderlist"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="../Img/Logo/logo.png" type="image/x-icon">
    <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="../Style/css/style.css">
    <link rel="stylesheet" href="../Style/css/Admin/order.css">
    <script src="../Style/js/js.js"></script>
    <title>Order</title>
</head>
<body class="bg-light adminBody">
    
    <%@ include file="SideMenu.jsp" %>
    <%@ include file="Header.jsp" %>
    
    <div class="page bg-light d-flex align-items-center justify-content-center">
        <div class="bg-white rounded shadow-sm maxPageHeight p-3 container-lg">
            <h2 class="text-center shadow-sm p-2 rounded bg-light mb-3">Order</h2>
           
            <div class="d-flex mb-2 flex-wrap">
                <a class='btn-${status.equals("all")?"primary":"secondary"} btn btn-sm me-1' href='Order?status=all'>All</a>
                <a class='btn-${status.equals("Packing")?"primary":"secondary"} btn btn-sm me-1' href='Order?status=Packing'>Packing</a>      
                <a class='btn-${status.equals("Shipping")?"primary":"secondary"} btn btn-sm me-1' href='Order?status=Shipping'>Shipping</a>      
                <a class='btn-${status.equals("Delivery")?"primary":"secondary"} btn btn-sm me-1' href='Order?status=Delivery'>Delivery</a>      
            </div>
            
            <!--Search Bar-->
            <form class="searchBar container-lg bg-white shadow-sm mb-1 mx-auto rounded-pill border mt-3" action="Order" method="get">
               <input type="text" name="OrderID" placeholder="Order ID" value="${searchedOrderID}">
               <input type="submit" value="Search">
            </form>
            
            <p class="text-gray text-center my-2">
                <small><i>(${orders.size()} Records Found)</i></small>
            </p>

            
            <div id="orderMainContainer">
                <%
                    List<Orders> orders = (List<Orders>) request.getAttribute("orders");
                    if (orders != null) {
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
                                <td colspan="5">
                                    <div class="row">
                                        <div class="d-flex rounded p-2 align-items-center mb-3 col">
                                            <img src="../Img/Icon/delivery.png" class="iconImg me-3">
                                            <div>
                                                <p>Delivery Address </p>
                                                <p><b><%= order.getAddress()%></b></p>
                                            </div>
                                        </div>
                                        <div class="d-flex rounded p-2 align-items-center mb-3 col">
                                            <img src="../Img/Icon/<%= (order.getPayment()).getPaymethod()%>.png" class="iconImg me-3">
                                            <div>
                                                <p><%= (order.getPayment()).getPaymethod()%> </p>
                                                <p><b><%= (order.getPayment()).getDetails()%></b></p>
                                            </div>
                                        </div>
                                        <div class="d-flex rounded p-2 align-items-center mb-3 col">
                                            <img src="<%= (order.getCustomer()==null)?"../Img/Profile/NoProfile.png":(order.getCustomer().getImgpath()==null)?"../Img/Profile/NoProfile.png":order.getCustomer().getImgpath()%>" class="smallProfileImg me-3">
                                            <div>
                                                <p>Customer</p>
                                                <% if(order.getCustomer()==null){ %>
                                                    <div class="tag rounded-pill bg-secondary"><p>Deleted User</p></div>
                                                <%}else{%>
                                                    <p><b><%=order.getCustomer().getUsername()%></b></p>
                                                <%}%>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                             <tr>
                                <td colspan="5"><hr class="my-2"></td>
                            </tr>
                            <tr>
                                <th>Index</th>
                                <th>Image</th>
                                <th>Name</th>
                                <th>Size</th>
                                <th>Quantity</th>
                            </tr>
                            <tr>
                                <td colspan="5"><hr class="my-2"></td>
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
                                </tr>
                            <%  
                                } 
                            %>
                            <tr>
                                <td colspan="5"><hr class="my-2"></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <th>Total</th>
                                <td>RM <%=order.getPayment().getAmount()%></td>
                            </tr>
                            <tr>
                                <td colspan="5"><hr class="my-2"></td>
                            </tr>
                            <tr>
                                <td colspan="5">
                                    <form class="w-100 d-flex flex-wrap justify-content-between mt-3 mb-2" method="post" action="Order">
                                        <input type="hidden" name="orderID" value="<%=order.getId()%>">
                                        <div class="radio-inputs">
                                            <label class="radio"> 
                                                <input type="radio" name="status" value="Packing" <%=(order.getStatus().equals("Packing")?"checked":"")%>/> <span class="name px-2">Packing</span>
                                            </label>
                                            
                                            <label class="radio"> 
                                                <input type="radio" name="status" value="Shipping" <%=(order.getStatus().equals("Shipping")?"checked":"")%>/> <span class="name px-2">Shipping</span>
                                            </label>
                                            
                                            <label class="radio"> 
                                                <input type="radio" name="status" value="Delivery" <%=(order.getStatus().equals("Delivery")?"checked":"")%>/> <span class="name px-2">Delivery</span>
                                            </label>
                                        </div>
                                        <input class="btn btn-sm btn-outline-primary px-4" type="submit" name="Update" value="Update">
                                    </form>
                                </td>
                            </tr>
                        </table>
                    </div>
        <%
                }
            }else{
        %>
            <small><i class="text-gray text-center">(No Record Found)</i></small>
        <%    
            }
        %>
            </div>
        </div>
    </div>
    <script src="../Style/js/Admin/order.js" type="text/javascript"></script>
    <script src="../Style/js/js.js" type="text/javascript"></script>
</body>
</html>

