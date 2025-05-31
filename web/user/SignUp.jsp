<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="../Style/css/style.css">
    <title>Sign Up</title>
</head>
<body class="bg-light">
    <%@ include file="Header.jsp" %>
    <%
        String created = (String)session.getAttribute("successNoti");
        if(created!=null){
            out.print("<div class=\" notification bg-success shadow-sm text-white w-100 rounded px-4 py-2 my-1 justify-content-between\" id=\"notiContainer\">");
            out.print("<p><b>" + created + "</b></p>");
            out.print("<p onclick=\"hideContainer('notiContainer')\" style=\"cursor:pointer;\"><b>X</b></p>");
            out.print("</div>");
            session.removeAttribute("successNoti");
        }
    %>
    <div class="d-flex align-items-center justify-content-center mt-5" id="main">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-md-5">
                    <div class="bg-white shadow-sm p-4">
                        <h3 class="text-center mb-4 fw-bold">Sign Up</h3>
                        <form action="SignUp" method="post" onsubmit="return confirmSignUp()">
                            <% 
                                Map<String, String> error = (Map<String, String>) session.getAttribute("error");
                                if (error == null) {
                                    error = new HashMap<String, String>();
                                }
                                session.removeAttribute("error");
                            %>
                            <div class="mb-3">
                                <label class="form-label">Username</label><br>
                                <input type="text" name="username" class="w-100" value="<%=(error.get("username") != null) ? error.get("username") : "" %>" required>
                                <p><i class="text-danger"><%= (error.get("usernameError") != null) ? error.get("usernameError") : "" %></i></p>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Password</label><br>
                                <input type="password" name="password" class="w-100" value="<%=(error.get("password") != null) ? error.get("password") : "" %>" required>
                                <p><i class="text-danger"><%= (error.get("passwordError") != null) ? error.get("passwordError") : "" %></i></p>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Confirm Password</label><br>
                                <input type="password" name="conPassword" class="w-100" value="<%=(error.get("conPassword") != null) ? error.get("conPassword") : "" %>" required>
                                <p><i class="text-danger"><%= (error.get("conPasswordError") != null) ? error.get("conPasswordError") : "" %></i></p>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Name</label><br>
                                <input type="text" name="name" class="w-100" value="<%=(error.get("name") != null) ? error.get("name") : "" %>" required>
                                <p><i class="text-danger"><%= (error.get("nameError") != null) ? error.get("nameError") : "" %></i></p>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Email</label><br>
                                <input type="email" name="email" class="w-100" value="<%=(error.get("email") != null) ? error.get("email") : "" %>" required>
                                <p><i class="text-danger"><%= (error.get("emailError") != null) ? error.get("emailError") : "" %></i></p>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Contact</label><br>
                                <input type="text" name="contact" class="w-100" value="<%=(error.get("contact") != null) ? error.get("contact") : "" %>" required>
                                <p><i class="text-danger"><%= (error.get("contactError") != null) ? error.get("contactError") : "" %></i></p>
                            </div>
                            <div class="d-flex align-items-center my-4">
                                <input type="checkbox" class="ui-checkbox" required>
                                <label for="" class="text-gray d-flex align-items-center"><small>I agree to the <a href="terms.html" class="text-gray">Terms and Conditions</a> and <a href="privacy.html" class="text-gray">Privacy Policy</a>.</small> </label>
                            </div>
                            <button type="submit" class="btn btn-dark w-100 py-2">Sign Up</button>
                            <p class="mt-3 text-center">Already have an account? <a href="SignIn.jsp">Sign In here</a></p>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="../Style/js/User/signUp.js" type="text/javascript"></script>
    <script src="../Style/js/js.js" type="text/javascript"></script>
</body>
</html>