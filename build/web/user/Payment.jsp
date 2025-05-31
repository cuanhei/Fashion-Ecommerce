<%-- 
    Document   : Payment
    Created on : 1 May 2025, 5:04:42 pm
    Author     : LIM CUAN HEI
--%>

<%@page import="Model.CartProduct"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" href="../Img/Logo/logo.png" type="image/x-icon">
        <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
        <link rel="stylesheet" href="../Style/css/style.css">
        <title>Payment</title>
    </head>
    <body class="bg-light">
   
        <%@ include file="Header.jsp" %>
        <form class="shadow-sm rounded p-3 bg-white mt-4 container-lg d-flex flex-column" action="Payment" method="POST" onsubmit="return validatePaymentSelection();" id="main">
            <input type="hidden" name="paymentMethod" id="paymentMethod" value="">
            <div class="mx-auto row order-2 w-100">
                <div class="col-lg-8">
                    <div class="border rounded shadow-sm mt-4 mt-sm-0 container-sm p-3 ">
                        <h5 class="bg-light p-2 rounded shadow-sm mb-3"><img src="../Img/Icon/delivery.png" class="iconImg me-3">Delivery Address</h5>
                        <textarea name="address" placeholder="Address" class="w-100 border mb-5" required><%=loggedAcc.getPerson().getAddress()==null?"":loggedAcc.getPerson().getAddress()%></textarea>
                        
                        <h5 class="bg-light p-2 rounded shadow-sm mb-3"><img src="../Img/Icon/payment.png" class="iconImg me-3">Payment Method</h5>
                        <div class="d-flex align-items-center border rounded p-3">
                            <p class="btn btn-sm btn-light paymentMethodBtn me-2 shadow-sm border px-3" onclick="selectPaymentMethod('card')">Card</p>
                            <p class="btn btn-sm btn-light paymentMethodBtn shadow-sm border px-3" onclick="selectPaymentMethod('cash')">Cash</p>
                        </div>
                        <div class="paymentForm mt-5" id="card" style="display: none">
                            <h5 class="bg-light p-2 rounded shadow-sm mb-3"><img src="../Img/Icon/card.png" class="iconImg me-3">Card</h5>
                            <div class="rounded shadow-sm bg-dark p-4 pb-5 text-white">
                                <div class="d-flex justify-content-end p-3 pe-2">
                                    <img src="../Img/Icon/master.png" class="iconImg" style="transform: scale(2.5);">
                                </div>
                                <div class="mt-4">
                                    <small>CARD NUMBER</small><br>
                                    <input class="bg-dark text-white " type="text" name="cardNo" placeholder="XXXX XXXX XXXX XXXX" maxlength="16" minlength="16">
                                </div>
                                <div class="row mt-3 text-white">
                                    <div class="col col-sm-6">
                                        <small>CARD HOLDER</small><br>
                                        <input class="bg-dark text-white" type="text" name="name" placeholder="NAME" maxlength="20">
                                    </div>
                                    <div class="col col-sm-5">
                                        <small>EXPIRED DATE</small><br>
                                        <input type="date" class="bg-dark text-white" type="text" name="expDate">
                                    </div>
                                    <div class="col col-sm-1">
                                        <small>CVV</small><br>
                                        <input type="password" class="bg-dark text-white" name="cvv" maxlength="3" minlength="3" placeholder="***" style="width: 3.5vh">
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="paymentForm mt-5" id="cash" style="display: none">
                            <h5 class="bg-light p-2 rounded shadow-sm mb-3"><img src="../Img/Icon/cash.png" class="iconImg me-3">Cash</h5>
                            <div class="border rounded p-3 text-gray">
                                <p><b>Pay with cash</b></p>
                            <small class=""><i>(Using this payment method, you will need to pay to deliverer when they delivered your order.)</i></small>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="col-lg-4">
                    <div class="border rounded shadow-sm p-2 mt-4 mt-sm-0 container-sm ">
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
                            <td class="text-center text-gray"><p>-<%=String.format("%.2f",vipDiscount)%></p></td>
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
                                <input type="submit" class="btn btn-primary w-100" name="payment" value="Payment">
                                <a href="Cart" class="btn btn-dark w-100 mt-2">Back</a>
                            </td>
                        </tr>
                    </table>
                    </div>
                </div>
            </div>
       </form>
    <script src="../Style/js/User/payment.js" type="text/javascript"></script>
    </body>
</html>