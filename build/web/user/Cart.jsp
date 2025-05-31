<%@page import="Model.CartProduct"%>
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
    <script src="../Style/js/User/cart.js"></script>
    <title>Cart</title>
</head>
<body class="bg-light">
    
    <%@ include file="Header.jsp" %>
    
    <div class="shadow-sm rounded p-3 bg-white mt-4 container-lg d-flex flex-column" id="main">
        <div class="notification bg-danger shadow-sm text-white w-100 rounded px-4 py-2 my-1 order-2 d-${cart.isEmpty()?"block":"none"}"><p><b>Your cart is EMPTY!</b></p></div>
        <div class="mx-auto row ${cart.isEmpty()?"d-none":""} order-2 w-100">
            <div class="col-lg-8">
                <c:forEach var="cartItem" items="${cart}">
                    <div class="container-sm mx-auto bg-white border rounded row productContainer shadow-sm p-lg-2 mb-3">
                        <div class="col-sm-5 d-flex justify-content-center align-items-center">
                            <img class="productImg w-100" src="${cartItem.product.imgpath==null?"../Img/Product/blank.jpg":cartItem.product.imgpath}" alt="">
                        </div>
                        <div class="col-sm-7 d-flex flex-column justify-content-between">
                            <div>
                                <div class="row d-flex justify-content-between align-items-center">
                                    <div class="col-lg-9 col-8">
                                        <h2>${cartItem.product.name}</h2>
                                    </div>
                                    <form class="col-lg-3 col-4" action="Cart" method="post">
                                        <input type="hidden" name="productID" value="${cartItem.product.id}">
                                        <input type="hidden" name="sizeID" value="${cartItem.size.id}">
                                        <input type="hidden" name="purpose" value="remove">
                                        <input type="submit" class="w-100 btn btn-sm btn-outline-danger d-flex align-items-center justify-content-center" name="remove" value="Remove">
                                    </form>
                                </div>
                                <p>${cartItem.product.description}</p>
                                <div class="d-flex mt-2">
                                    <c:forEach var="category" items="${cartItem.product.getCategories()}">
                                        <p class="badge rounded-pill text-bg-${category.tagcolor} me-1 my-1">${category.text}</p>
                                    </c:forEach>
                                </div>
                                <h6 class="mt-3">RM ${cartItem.product.sellingprice} <small><i class="text-gray">(per unit)</i></small></h6>
                                <div class="d-flex my-1 align-items-center">
                                    <p class="me-2"><b>Size :</b></p>
                                    <p class="badge rounded-pill text-bg-dark me-1">${cartItem.size.text}</p>
                                </div>
                                <small class="text-gray mb-2"><i>(Quantity of Size <b>${cartItem.size.text}</b> remaining in store: <b class="ms-1">${cartItem.product.totalQty} </b>units)</i></small>
                                <div class="d-flex align-items-center my-2">
                                    <p class="me-3">Quantity to purchase </p>
                                    <form action="Cart" method="post" class="d-flex align-items-center my-2 justify-content-between">
                                        <input type="hidden" name="purpose" value="update">
                                        <input type="hidden" name="productID" value="${cartItem.product.id}">
                                        <input type="hidden" name="sizeID" value="${cartItem.size.id}">
                                        <input type="submit" class="btn btn-sm btn-light shadow-sm border iconImg d-flex align-items-center justify-content-center" name="decrease" value="-">
                                        <input type="number" class="border mx-2" min="0" max="${cartItem.product.totalQty}" style="width: 10vh;" name="quantity" value="${cartItem.quantity}" disabled>
                                        <input type="submit" class="btn btn-sm btn-light shadow-sm border iconImg d-flex align-items-center justify-content-center" name="increase" value="+">
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
    
            <div class="col-lg-4">
                <div class="border rounded shadow-sm p-2 mt-4 mt-sm-0 container-sm">
                    <table class=" w-100">
                        <tr>
                            <th><p>No.</p></th>
                            <th><p>Product Name</p></th>
                            <th><p>Size</p></th>
                            <th class="text-center"><p>Quantity</p></th>
                            <th class="text-center"><p>Amount (RM)</p></th>
                        </tr>
                        <tr>
                            <td colspan="5"><hr class="my-2"></td>
                        </tr>
                        <%
                            List<CartProduct> cart = (List<CartProduct>) request.getAttribute("cart"); // Retrieve 'cart' attribute from request
                            int index = 1;
                            double subtotal = 0;
                            double productSubAmount = 0;
                            if (cart != null) {
                                for (CartProduct cartItem : cart) {
                                
                                productSubAmount = 0;
                                productSubAmount += cartItem.getProduct().getSellingprice() * cartItem.getQuantity();
                                subtotal += productSubAmount;
                        %>
                            <tr class="text-gray">
                                <td><p><%=index++%></p></td>
                                <td><p><%=cartItem.getProduct().getName()%></p></td>
                                <td><p><%=cartItem.getSize().getText()%></p></td>
                                <td class="text-center"><p><%=cartItem.getQuantity()%></p></td>
                                <td class="text-center"><p><%=String.format("%.2f",productSubAmount)%></p></td>
                            </tr>
                        <%
                                }
                            }
                            
                            double shippingFee = (subtotal>=1000)?0:25;
                            double tax = subtotal *0.1;
                            double total = subtotal + tax + shippingFee;
                            double vipDiscount = total*0.05;
                            if(loggedAcc.isVIP()){
                                total-= vipDiscount;
                            }
                        %>
                        <tr>
                            <td colspan="5"><hr class="my-2"></td>
                        </tr>
                        <tr>
                            <th colspan="4"><p>Sub-Total</p></th>
                            <td class="text-center text-gray"><p><%=String.format("%.2f",subtotal)%></p></td>
                        </tr>
                        <tr>
                            <th colspan="4"><p>SST Tax (10%)</p></th>
                            <td class="text-center text-gray"><p><%=String.format("%.2f",tax)%></p></td>
                        </tr>
                        <tr>
                            <th colspan="4"><p>Shipping Fees</p></th>
                            <td class="text-center text-gray"><p><%=String.format("%.2f",shippingFee)%></p></td>
                        </tr>
                        <%
                            if(loggedAcc.isVIP()){
                        %>
                        <tr>
                            <th colspan="4"><p>VIP Discount (-5%)</p></th>
                            <td class="text-center text-gray"><p>-<%=String.format("%.2f",total*0.05)%></p></td>
                        </tr>
                        <%}%>
                        <tr>
                            <td colspan="5"><hr class="my-2"></td>
                        </tr>
                        <tr>
                            <th colspan="4"><p>Total</p></th>
                            <td class="text-center text-gray"><p>RM <%=String.format("%.2f",total)%></p></td>
                        </tr>
                        <tr>
                            <td colspan="5"><hr class="my-2"></td>
                        </tr>
                        <tr>
                            <td colspan="5">
                                <a href="Payment" class="btn btn-primary w-100">Proceed Payment</a>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div class="d-flex flex-wrap justify-content-sm-around border rounded shadow-sm p-3 mb-4 mx-2 order-1">
            <div class="d-flex justify-content-between mb-3 mb-sm-0">
                <div class="bigProfileImg shadow-sm border d-flex justify-content-center align-items-center" id="freeShippingPercentage">
                   <div class="midProfileImg shadow-sm bg-white border d-flex justify-content-center align-items-center">
                        <p class="text-<%=subtotal>=1000?"success":"gray"%>"><b><%=subtotal/1000*100>=100?"100":String.format("%.2f", subtotal / 1000.0 * 100) %>%</b></p>
                   </div>
                </div>
                <div class="d-flex flex-column justify-content-center ms-2">
                    <h6 class="text-gray">Shipping Fees Offer</h6>
                    <p class="text-gray">You currently hit RM <%=subtotal%> / RM 1000.00</p>
                    <p class="text-gray"><small><i>After hitting RM 1000.00 will have a free shipping fees offer.</i></small></p>
                </div>
            </div>
            <script>
                updatePercent("freeShippingPercentage",<%=subtotal/1000*100%>);
            </script>
            <div class="d-flex justify-content-between">
                <div class="bigProfileImg shadow-sm border d-flex justify-content-center align-items-center" id="memberPercentage">
                   <div class="midProfileImg shadow-sm bg-white border d-flex justify-content-center align-items-center">
                       <p class="text-gray"><b><%=loggedAcc.isVIP()?"YES":"NO"%></b></p>
                   </div>
                </div>
                <div class="d-flex flex-column justify-content-center ms-2">
                    <h6 class="text-gray">VIP Offer</h6>
                    <p class="text-gray">You currently under a <%=loggedAcc.isVIP()?"VIP":"Member"%> role.</p>
                    <p class="text-gray"><small><i>When total spending reached RM 5000.00 will auto upgrade to VIP role.</i></small></p>
                </div>
            </div>
            <script>
                updatePercent("memberPercentage",<%=loggedAcc.isVIP()?100:0%>);
            </script>

        </div>
    </div>

</body>
</html>