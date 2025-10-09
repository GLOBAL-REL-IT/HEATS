<%-- 
    Document   : login_new
    Created on : Mar 12, 2025, 3:08:05 PM
    Author     : zbqb9x
--%>

<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="stylesheet" href="${contextPath}/resources/vendor/login/css/style.da4c60c4.css">
        <link href="${contextPath}/resources/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
        <script nomodule defer src="${contextPath}/resources/vendor/login/js/index.runtime.278a0afe.js"></script>
        <script type="module" src="${contextPath}/resources/vendor/login/js/index.runtime.ebb6f73b.js"></script>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>HEATs | Login</title>
        <!--<link rel="shortcut icon" href="${contextPath}/resources/img/favicon.ico">-->
        <link rel="icon" type="image/x-icon" href="${contextPath}/resources/img/heat.ico">
        <link rel="preload" as="image" href="${contextPath}/resources/vendor/login/css/body.1416b330.jpg">
        <link rel="preload" as="image" href="${contextPath}/resources/vendor/login/css/body-dark.4bbdaf3b.png">
        <link rel="preload" as="font" href="${contextPath}/resources/vendor/login/css/RobotoCondensed-Regular.7970a12f.woff2" type="font/woff2" crossorigin>
        <link rel="preload" as="font" href="${contextPath}/resources/vendor/login/css/RobotoCondensed-Medium.5275db9e.woff2" type="font/woff2" crossorigin>
        <link rel="preload" as="font" href="${contextPath}/resources/vendor/login/css/RobotoCondensed-Bold.0b0ce0b4.woff2" type="font/woff2" crossorigin>
    </head>
    <style>
        .img3 {
            width: 55px; /* Sets a fixed width */
            height: 18px; /* Sets a fixed height */
        }
    </style>

    <body class="align-items-center d-flex p-5"> 
        <div class="card m-auto mw-400 p-8 w-100" id="login"> 
            <center><h2 class="font-link-rms fs-4 text-body-emphasis" style="font-weight:bold">Welcome to Rel Hardware & Equipment Activity Tracking System (HEATs)</h2></center>
            <div id="logo">
                <a href="${contextPath}/">
                    <img src="${contextPath}/resources/vendor/login/img/heat.png" alt="Logo" width="85%" />
                </a>
            </div>
            <center>
                <br><div class="mb-5 text-body-secondary">Please sign in to continue to the system</div> </center>
            <form id="login-form" action="${contextPath}/" class="form" method="post">

                <c:if test="${not empty error}">
                    <div class="alert alert-danger">
                        <a class="close" data-dismiss="alert" href="#" aria-hidden="true">&times;</a>
                        <strong>${error}</strong>
                    </div>
                </c:if>
                <div class="form-group">
                    <label for="login-username"> Username</label>
                    <input type="text" class="form-control" id="login-username" placeholder="Username" name="username">
                </div>
                <div class="form-group">
                    <label for="login-password">Password</label>
                    <input type="password" class="form-control" id="login-password" placeholder="Password" name="password">
                </div>
                &nbsp;
                <div class="form-group">
                    <center>
                        <button type="submit" id="login-btn" class="btn btn-primary btn-block">Signin <i class="bi bi-box-arrow-in-right h4"></i></button>
                    </center>
                </div>
            </form>
            <div class="fs-7 text-center"> 
                <!--                <a href="register.html" class="d-block link-body mb-1">Register for a new account</a>
                                <a href="forgot-password.html" class="d-block link-body">Forgot password?</a> -->
                <!--                <a href="#" class="d-block link-body mb-1">Register for a new account</a>
                                <a href="#" class="d-block link-body">Forgot password?</a> -->
            </div> 
        </div> 
        <div class="app-footer">
            <img class="img3" src="${contextPath}/resources/onsemi logo.webp" alt="onsemi">
            <span>© HEATs 2025</span>
        </div>
        <script>var t = localStorage.getItem("color-mode") || "light";t = "dark" === t ? "dark" : "light", document.documentElement.setAttribute("data-bs-theme", t);</script> 
        <script src="${contextPath}/resources/vendor/login/js/index.09b43a9f.js" type="module"></script>
        <script src="${contextPath}/resources/vendor/login/js/index.56671464.js" nomodule defer></script> 
        <script src="${contextPath}/resources/vendor/login/js/vendor.3eca90c9.js" type="module"></script>
        <script src="${contextPath}/resources/vendor/login/js/vendor.01d48514.js" nomodule defer></script> 
        <!--<script src="${contextPath}/resources/js/Login.js"></script>-->
    </body>
</html>
