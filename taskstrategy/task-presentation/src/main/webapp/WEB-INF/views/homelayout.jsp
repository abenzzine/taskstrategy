<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Task Strategy</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.servletContext.contextPath}/img/favicon.ico" rel="shortcut icon"/>
    <link href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="${pageContext.servletContext.contextPath}/css/datepicker.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.servletContext.contextPath}/css/bootstrap-tagsinput.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.servletContext.contextPath}/css/pick-a-color-1.1.8.min.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.servletContext.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/bootstrap-datepicker.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/tinycolor-0.9.15.min.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/pick-a-color-1.1.8.min.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/bootstrap-tagsinput.min.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery.metisMenu.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/sidebar.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/taskstrategy.js"></script>
    <script>
        (function (i, s, o, g, r, a, m) {
            i['GoogleAnalyticsObject'] = r;
            i[r] = i[r] || function () {
                (i[r].q = i[r].q || []).push(arguments)
            }, i[r].l = 1 * new Date();
            a = s.createElement(o),
                    m = s.getElementsByTagName(o)[0];
            a.async = 1;
            a.src = g;
            m.parentNode.insertBefore(a, m)
        })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

        ga('create', 'UA-47023084-1', 'taskstrategy.com');
        ga('send', 'pageview');
    </script>
</head>
<body>
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.servletContext.contextPath}/">
                <img src="${pageContext.servletContext.contextPath}/img/task-strategy-logo-icon.png"/>
                Task Strategy
            </a>
        </div>
        <!-- /.navbar-header -->

        <div class="sidebar-collapse">
            <ul class="nav navbar-top-links navbar-right">
                <li><a href="http://www.facebook.com/taskstrategy"><div class="icon-facebook icomoon"></div></a></li>
                <li><a href="https://twitter.com/taskstrategy"><div class="icon-twitter icomoon"></div></a></li>
                <li><a data-toggle="modal" data-target="#login-modal" onClick="setTimeout(function(){document.getElementById('email').focus();},500); return true;">Login</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">About <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="/about/">About Task Strategy</a></li>
                        <li><a href="http://www.taskstrategy.com:8080/blog" target="_blank">Blog</a></li>
                    </ul>
                </li>
                <li><a href="/contact/">Contact</a></li>

            </ul>
        </div>

    </nav>
    <div class="modal fade"  id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <form name='f' action="<c:url value='j_spring_security_check' />" class="form-horizontal"
                          method='POST'>
                        <h2>Sign in</h2>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">
                                Your login attempt was not successful, try again.
                                <script type="text/javascript">
                                    $("#login-modal").modal('show');
                                </script>
                            </div>
                        </c:if>
                        <div class="well" id="login_container">
                            <div class="form-group">
                                <label class="col-lg-3" for="email"><strong>Email: </strong></label>

                                <div class="col-lg-9">
                                    <input class="form-control" type='text' id="email" name='j_username' value=''>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-3" for="password"><strong>Password: </strong></label>

                                <div class="col-lg-9">
                                    <input class="form-control" type='password' name='j_password' id="password">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3"></div>
                                <div class="col-lg-9">
                                    <button type="submit" id="signin" class="btn btn-matched btn-sm form-control">Sign
                                        in
                                    </button>
                                </div>
                            </div>
                            <div class="row">
                                <br/>
                                <a href="${pageContext.servletContext.contextPath}/forgot-password" class="col-md-8">Forgot
                                    Password?</a>
                                <a href="${pageContext.servletContext.contextPath}/signup" class="col-md-4">Register
                                    Now</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div id="page">
        <tiles:insertAttribute name="body"/>
        <div class="clear"></div>
    </div>
    <div id="footer" class="container">
        <p class="pull-right">
            <a href="${pageContext.servletContext.contextPath}/privacy-policy">Privacy Policy</a> |
            <a href="${pageContext.servletContext.contextPath}/terms-of-service">Terms of Service</a>
            <br />
            <em>&copy; Task Strategy 2014</em>
        </p>
    </div>
 <c:if test="${showLogin}">
 <script type="text/javascript">
       $("#email").val('${email}');
       $('#login-modal').modal('show');
 </script>
 </c:if>
</body>
</html>