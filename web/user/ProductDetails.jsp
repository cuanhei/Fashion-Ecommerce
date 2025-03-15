<%-- 
    Document   : ProductDetails
    Created on : 15 Mar 2025, 11:52:15 am
    Author     : LIM CUAN HEI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    
    <div class="container-sm mx-auto bg-white shadow-sm rounded row productContainer p-lg-2 p-3 pb-lg-4 my-lg-4 mt-4">
        <div class="col-sm-5 d-flex justify-content-center align-items-center">
            <img class="productImg" src="../../Img/Icon/shirt.png" alt="">
        </div>
        <div class="col-sm-7 d-flex flex-column justify-content-between">
            <div>
                <h2>OffWhite shirt</h2>
                <p>Lorem, ipsum dolor sit amet consectetur adipisicing elit. Vel repellat, culpa placeat ut a qui voluptatem praesentium ipsam incidunt. Magni optio sequi commodi officiis dignissimos temporibus eum consequuntur cum voluptatum.</p>
                <div class="d-flex mt-2">
                    <p class="badge rounded-pill text-bg-primary me-1">Clothes</p>
                    <p class="badge rounded-pill text-bg-primary me-1">Clothes</p>
                    <p class="badge rounded-pill text-bg-primary me-1">Clothes</p>
                </div>
            </div>
            <div class="d-flex flex-column py-3">
                <h6>RM 100.00 <small><i class="text-gray">(per unit)</i></small></h6>
                <small><i class="text-gray">Date Released : 5 June 2024</i></small>
            </div>

            <hr>

            <div class="d-flex my-3">
                <button class="btn btn-dark shadow-sm btn-sm me-1 sizeBtn">XS</button>
                <button class="btn btn-secondary shadow-sm border btn-sm me-1 sizeBtn opacity-25" disabled>XS</button>
                <button class="btn btn-light shadow-sm border btn-sm me-1 sizeBtn">XS</button>
                <button class="btn btn-light shadow-sm border btn-sm me-1 sizeBtn">XS</button>
                <button class="btn btn-light shadow-sm border btn-sm me-1 sizeBtn">XS</button>
            </div>
            <small class="text-gray mb-2"><i>(Quantity of Size <b>L</b> remaining in store: <b class="ms-1">200 </b>units)</i></small>
            <div class="d-flex align-items-center">
                <p class="me-3">Quantity to purchase </p>
                <button class="btn btn-sm btn-light shadow-sm border iconImg d-flex align-items-center justify-content-center">-</button>
                <input type="number" class="border mx-2">
                <button class="btn btn-sm btn-light shadow-sm border iconImg d-flex align-items-center justify-content-center">+</button>
            </div>
            
            <hr class="my-3">

            <table>
                <tr>
                    <td><p>Shipping fees</p></td>
                    <td><p class="text-end"><b>5%</b></p></td>
                </tr>
                <tr>
                    <td><p>SST Tax</p></td>
                    <td><p class="text-end"><b>10%</b></p></td>
                </tr>
                <tr>
                    <td><p>Total</p></td>
                    <td><p class="text-end"><b>RM 400.00</b></p></td>
                </tr>
            </table>

            <button class="btn btn-primary btn-sm my-2">Add To Cart</button>
            <button class="btn btn-dark btn-sm">Back</button>
        </div>
        <div>
            <hr class="mt-4">
            <h6 class="text-gray text-center py-2">Review</h6>
            <div class="container-sm shadow-sm border rounded p-3 mb-2">
                <div class="d-flex">
                    <img src="https://th.bing.com/th/id/OIP.tLotgCDtzgTdwJcTiXWRCwHaEK?rs=1&pid=ImgDetMain" alt="" class="smallProfileImg shadow-sm me-2">
                    <div>
                        <p><b>Name Lim</b></p>
                        <p><small>Posted at : 10 : 00</small></p>
                    </div>
                </div>
                <hr class="my-2">
                <p>Lorem, ipsum dolor sit amet consectetur adipisicing elit. Quo iusto iure error? Quidem, esse quis ipsum eligendi ab culpa fugit, asperiores natus minima et saepe! Ea consequuntur voluptate iste quo suscipit, esse id ratione hic neque, animi consectetur itaque. Aliquid culpa voluptatum placeat quibusdam unde modi cupiditate odio sequi dolore.</p>
                <div class="d-flex mt-2 flex-wrap">
                    <img src="https://th.bing.com/th/id/OIP.tLotgCDtzgTdwJcTiXWRCwHaEK?rs=1&pid=ImgDetMain" alt="" class="reviewImg rounded me-1 mb-1">
                    <img src="https://th.bing.com/th/id/OIP.tLotgCDtzgTdwJcTiXWRCwHaEK?rs=1&pid=ImgDetMain" alt="" class="reviewImg rounded me-1">
                    <img src="https://th.bing.com/th/id/OIP.tLotgCDtzgTdwJcTiXWRCwHaEK?rs=1&pid=ImgDetMain" alt="" class="reviewImg rounded me-1">
                </div>
            </div>
        </div>
    </div>
</body>
</html>