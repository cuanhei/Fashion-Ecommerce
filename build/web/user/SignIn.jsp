<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="../Style/css/style.css">
    <title>Sign In</title>
</head>
<body class="vh-100 bg-light">
    <%@ include file="Header.jsp" %>
    <div class="d-flex align-items-center justify-content-center mt-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-5">
                    <div class="bg-white shadow-sm p-4">
                        <h3 class="text-center mb-4 fw-bold">Sign In</h3>
                        <form action="signupServlet" method="post">
                            <div class="mb-3">
                                <label class="form-label">Email</label><br>
                                <input type="text" name="fullName" class="w-100" required>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Password</label><br>
                                <input type="text" name="fullName" class="w-100" required>
                            </div>
                            <p class="mt-3 text-end"><a href="SignUp.html" class="text-gray">Forgot Password</a></p>
                            <div class="d-flex align-items-center mt-3">
                                <input type="checkbox" class="ui-checkbox">
                                <label for="" class="text-gray d-flex align-items-center"><small>Remember Me</small></label>
                            </div>
                            <button type="submit" class="btn btn-dark w-100 py-2 mt-3">Sign In</button>
                            <p class="mt-3 text-center">Don't have an account? <a href="SignUp.jsp">Sign Up here</a></p>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>