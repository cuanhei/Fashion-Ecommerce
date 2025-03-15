<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    
    <div class="shadow-sm rounded p-3 bg-white mt-4 container-lg">
        <div class="d-flex flex-wrap justify-content-sm-around border rounded shadow-sm p-3 mb-4 mx-2">
            <div class="d-flex justify-content-between mb-3 mb-sm-0">
                <div class="bigProfileImg shadow-sm border d-flex justify-content-center align-items-center" id="freeShippingPercentage">
                   <div class="midProfileImg shadow-sm bg-white border d-flex justify-content-center align-items-center">
                        <p class="text-gray"><b>100%</b></p>
                   </div>
                </div>
                <div class="d-flex flex-column justify-content-center ms-2">
                    <h6 class="text-gray">Shipping Fees Offer</h6>
                    <p class="text-gray">You currently hit RM 90.00 / RM 1000.00</p>
                    <p class="text-gray"><small><i>After hitting RM 1000.00 will have a free shipping fees offer.</i></small></p>
                </div>
            </div>
            <script>
                updatePercent("freeShippingPercentage",10);
            </script>
            <div class="d-flex justify-content-between">
                <div class="bigProfileImg shadow-sm border d-flex justify-content-center align-items-center" id="memberPercentage">
                   <div class="midProfileImg shadow-sm bg-white border d-flex justify-content-center align-items-center">
                        <p class="text-gray"><b>100%</b></p>
                   </div>
                </div>
                <div class="d-flex flex-column justify-content-center ms-2">
                    <h6 class="text-gray">Member Offer</h6>
                    <p class="text-gray">You currently under a member role.</p>
                    <p class="text-gray"><small><i>When total spending reached RM 5000.00 will upgrade to VIP role.</i></small></p>
                </div>
            </div>
            <script>
                updatePercent("memberPercentage",10);
            </script>

        </div>
        <div class="mx-auto row">
            <div class="col-lg-8">
                <div class="container-sm mx-auto bg-white border rounded row productContainer shadow-sm p-lg-2">
                    <div class="col-sm-5 d-flex justify-content-center align-items-center">
                        <img class="productImg" src="../../Img/Icon/shirt.png" alt="">
                    </div>
                    <div class="col-sm-7 d-flex flex-column justify-content-between">
                        <div>
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="w-80">
                                    <h2>OffWhite shirt</h2>
                                </div>
                                <input type="checkbox" class="ui-checkbox me-0">
                            </div>
                            <p>Lorem, ipsum dolor sit amet consectetur adipisicing elit. Vel repellat, culpa placeat ut a qui voluptatem praesentium ipsam incidunt. Magni optio sequi commodi officiis dignissimos temporibus eum consequuntur cum voluptatum.</p>
                            <div class="d-flex mt-2">
                                <p class="badge rounded-pill text-bg-primary me-1">Clothes</p>
                                <p class="badge rounded-pill text-bg-primary me-1">Clothes</p>
                                <p class="badge rounded-pill text-bg-primary me-1">Clothes</p>
                            </div>
                            <h6 class="mt-3">RM 100.00 <small><i class="text-gray">(per unit)</i></small></h6>
                            <div class="d-flex my-3">
                                <button class="btn btn-dark shadow-sm btn-sm me-1 sizeBtn">XS</button>
                                <button class="btn btn-secondary shadow-sm border btn-sm me-1 sizeBtn opacity-25" disabled>XS</button>
                                <button class="btn btn-light shadow-sm border btn-sm me-1 sizeBtn">XS</button>
                                <button class="btn btn-light shadow-sm border btn-sm me-1 sizeBtn">XS</button>
                                <button class="btn btn-light shadow-sm border btn-sm me-1 sizeBtn">XS</button>
                            </div>
                            <small class="text-gray mb-2"><i>(Quantity of Size <b>L</b> remaining in store: <b class="ms-1">200 </b>units)</i></small>
                            <div class="d-flex align-items-center my-2">
                                <p class="me-3">Quantity to purchase </p>
                                <button class="btn btn-sm btn-light shadow-sm border iconImg d-flex align-items-center justify-content-center">-</button>
                                <input type="number" class="border mx-2" style="width: 10vh;">
                                <button class="btn btn-sm btn-light shadow-sm border iconImg d-flex align-items-center justify-content-center">+</button>
                                <div class="w-100 d-flex justify-content-end">
                                    <button class="btn btn-sm btn-outline-danger ms-3"> Remove</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    
            <div class="col-lg-4">
                <div class="border rounded shadow-sm p-2 mt-4 mt-sm-0 container-sm">
                    <table class=" w-100">
                        <tr>
                            <th><p>No.</p></th>
                            <th><p>Product Name</p></th>
                            <th class="text-center"><p>Quantity</p></th>
                            <th class="text-center"><p>Amount (RM)</p></th>
                        </tr>
                        <tr>
                            <td colspan="4"><hr class="my-2"></td>
                        </tr>
                        <tr class="text-gray">
                            <td><p>1</p></td>
                            <td><p>OffWhite Shirt</p></td>
                            <td class="text-center"><p>5</p></td>
                            <td class="text-center"><p>300.00</p></td>
                        </tr>
                        <tr>
                            <td colspan="4"><hr class="my-2"></td>
                        </tr>
                        <tr>
                            <th colspan="3"><p>Sub-Total</p></th>
                            <td class="text-center text-gray"><p>300.00</p></td>
                        </tr>
                        <tr>
                            <th colspan="3"><p>SST Tax (10%)</p></th>
                            <td class="text-center text-gray"><p>300.00</p></td>
                        </tr>
                        <tr>
                            <th colspan="3"><p>Shipping Fees</p></th>
                            <td class="text-center text-gray"><p>25.00</p></td>
                        </tr>
                        <tr>
                            <th colspan="3"><p>Member Discount</p></th>
                            <td class="text-center text-gray"><p>-10.00</p></td>
                        </tr>
                        <tr>
                            <td colspan="4"><hr class="my-2"></td>
                        </tr>
                        <tr>
                            <th colspan="3"><p>Total</p></th>
                            <td class="text-center text-gray"><p>RM 1000.00</p></td>
                        </tr>
                        <tr>
                            <td colspan="4"><hr class="my-2"></td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <button class="btn btn-primary w-100">Proceed Payment</button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>

</body>
</html>