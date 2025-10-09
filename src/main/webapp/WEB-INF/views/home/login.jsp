<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/base/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>MIB - Login</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Motherboard & Hardware Integrated Barcode System (MIB)">
        <meta name="author" content="FTC">

        <!-- Animated css -->
        <link href="${contextPath}/resources/statflow/css/animate.css" rel="stylesheet">

        <!-- Bootstrap font icons css -->
        <link rel="stylesheet" href="${contextPath}/resources/statflow/fonts/bootstrap/bootstrap-icons.css">

        <!-- Main css -->
        <link rel="stylesheet" href="${contextPath}/resources/statflow/css/main.min.css">


        <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
          <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

        <!-- Le favicon -->
        <link rel="shortcut icon" href="${contextPath}/resources/img/favicon.ico">

    </head>

    <body class="login-bg">

        <!-- Container starts -->
        <div class="container">

            <!-- Auth wrapper starts -->
            <div class="auth-wrapper">

                <!-- Form starts -->
                 <form id="login-form" action="${contextPath}/" class="form" method="post">

                    <div class="auth-box">
                        <a href="${contextPath}/" class="auth-logo mb-4">
                            <img src="${contextPath}/resources/statflow/images/mib2.png" alt="MIB">
                        </a>

                        <h4 class="mb-4">Login</h4>

                        <div class="mb-3">
                            <label class="form-label" for="login-username">User ID <span class="text-danger">*</span></label>
                            <input type="text" id="login-username" class="form-control" placeholder="User ID" name="username">
                        </div>

                        <div class="mb-2">
                            <label class="form-label" forPasswordpwd">Password <span class="text-danger">*</span></label>
                            <div class="input-group">
                                <input type="password" name="password" id="password" class="form-control" placeholder="Enter password">
                                <button class="btn btn-outline-secondary" type="button">
                                    <i class="bi bi-eye-slash" id="togglePassword"></i>
                                </button>
                            </div>
                        </div>

                        <!--                        <div class="d-flex justify-content-end mb-3">
                                                    <a href="forgot-password.html" class="text-decoration-underline">Forgot Password?</a>
                                                </div>-->

                        <div class="mb-3 d-grid gap-2">
                            <button type="submit" class="btn btn-primary">Login</button>
                        </div>

                    </div>

                </form>
                <!-- Form ends -->

            </div>
            <!-- Auth wrapper ends -->

        </div>
        <!-- Container ends -->

        <script>
            const togglePassword = document
                    .querySelector('#togglePassword');
            const password = document.querySelector('#password');
            togglePassword.addEventListener('click', () => {
                // Toggle the type attribute using
                // getAttribure() method
                const type = password
                        .getAttribute('type') === 'password' ?
                        'text' : 'password';
                password.setAttribute('type', type);
                // Toggle the eye and bi-eye icon
                this.classList.toggle('bi-eye');
            });
        </script>

    </body>

</html>