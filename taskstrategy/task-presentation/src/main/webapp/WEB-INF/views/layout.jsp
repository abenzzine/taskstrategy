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
    <link href="${pageContext.servletContext.contextPath}/css/bootstrap.min.css" rel="stylesheet" type="text/css"
          media="screen"/>
    <link href="${pageContext.servletContext.contextPath}/css/datepicker.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.servletContext.contextPath}/css/bootstrap-tagsinput.css" rel="stylesheet"
          type="text/css"/>
    <link href="${pageContext.servletContext.contextPath}/css/dataTables.bootstrap.css" rel="stylesheet"
          type="text/css"/>
    <link href="${pageContext.servletContext.contextPath}/css/footable.core.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="${pageContext.servletContext.contextPath}/css/footable.metro.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="${pageContext.servletContext.contextPath}/css/footable-overrides.css" rel="stylesheet"
           type="text/css"/>
    <link href="${pageContext.servletContext.contextPath}/css/pick-a-color-1.1.8.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="${pageContext.servletContext.contextPath}/css/datepicker.css" rel="stylesheet"
          type="text/css"/>
    <link href="${pageContext.servletContext.contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/bootstrap-datepicker.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/tinycolor-0.9.15.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/js/pick-a-color-1.1.8.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/js/bootstrap-tagsinput.min.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery.metisMenu.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/sidebar.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/footable.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/footable.filter.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/footable.paginate.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/footable.sort.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/footable.striping.js"></script>
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
<body class="dashboard">
<div id="wrapper">

    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a class="navbar-brand" href="${pageContext.servletContext.contextPath}/dashboard/">
                <img src="${pageContext.servletContext.contextPath}/img/task-strategy-logo-icon.png"/>
                Task Strategy
            </a>
        </div>
        <!-- /.navbar-header -->

        <ul class="nav navbar-top-links navbar-right">
            <li><a href="http://www.facebook.com/taskstrategy"><div class="icon-facebook icomoon"></div></a></li>
            <li><a href="https://twitter.com/taskstrategy"><div class="icon-twitter icomoon"></div></a></li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <sec:authentication
                            property="principal.username"/> <span class="glyphicon glyphicon-user"></span> <span
                        class="glyphicon glyphicon-arrow-down"></span>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li><a
                            href="${pageContext.servletContext.contextPath}/profile/"> <span
                            class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;User Profile</a>
                    </li>
                    <li class="divider"></li>
                    <li><a href="${pageContext.servletContext.contextPath}/<c:url value='j_spring_security_logout' />">
                        <span class="glyphicon glyphicon-log-out"></span>&nbsp;&nbsp;Logout</a>
                    </li>
                </ul>
                <!-- /.dropdown-user -->
            </li>
            <!-- /.dropdown -->
        </ul>
        <!-- /.navbar-top-links -->

    </nav>
    <!-- /.navbar-static-top -->

    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="sidebar-search">
                    <div class="custom-search-form">
                        <input type="text" id="filter-search" class="form-control" placeholder="Search By Tag..."
                               value="${searchTag}"/>
                            <span class="input-group-btn">


                            </span>
                    </div>
                    <div class="actionBar">
                        <span class="input-group-btn-spaced">

                               <button id="filter-search-button" class="btn btn-default" type="button" title="Filter by Tag"
                                       onclick="window.location='${pageContext.servletContext.contextPath}/tasks/?tag=' + document.getElementById('filter-search').value">
                                   <span class="glyphicon glyphicon-search"></span>
                               </button>
                                     <button type="button"  title="Remove Tag Filtering"
                                             onclick="window.location='${pageContext.servletContext.contextPath}/tasks/'"
                                             class="btn btn-default"><span class="glyphicon glyphicon-remove"></span>
                                     </button>
                            <button type="button"   title="New Task"
                                    onclick="window.location='${pageContext.servletContext.contextPath}/tasks/create/'"
                                    class="btn btn-default"><span class="glyphicon glyphicon-plus"></span>
                            </button>
                            <button type="button"   title="Add Quick Tasks"
                                    data-toggle="modal" data-target="#quickTasksModal"
                                    class="btn btn-default"><span class="glyphicon glyphicon-fire"></span>
                            </button>
                            <button type="button" title="Action Mode"
                                    onclick="window.location='${pageContext.servletContext.contextPath}/action/'"
                                    class="btn btn-default"><span class="glyphicon glyphicon-circle-arrow-right"></span>
                            </button>
                        </span>
                    </div>
                    <!-- /input-group -->
                </li>
                <li class="active"><a href="${pageContext.servletContext.contextPath}/dashboard/"><span
                        class="glyphicon glyphicon-dashboard"></span>&nbsp;&nbsp;Dashboard</a></li>
                <li class="active"><a href="${pageContext.servletContext.contextPath}/action/"><span
                        class="glyphicon glyphicon-circle-arrow-right"></span>&nbsp;&nbsp;Action Mode</a></li>
                <li class="active"><a href="${pageContext.servletContext.contextPath}/tasks/"><span
                        class="glyphicon glyphicon-list"></span>&nbsp;&nbsp;All Tasks</a></li>
                <li class="active"><a href="${pageContext.servletContext.contextPath}/tasks/pastdue/"><span
                        class="glyphicon glyphicon-list"></span>&nbsp;&nbsp;Past Due</a></li>
                <li><a href="${pageContext.servletContext.contextPath}/tasks/today/"><span
                        class="glyphicon glyphicon-list"></span>&nbsp;&nbsp;Today</a></li>
                <li><a href="${pageContext.servletContext.contextPath}/tasks/week/"><span
                        class="glyphicon glyphicon-list"></span>&nbsp;&nbsp;This Week</a></li>
                <li><a href="${pageContext.servletContext.contextPath}/tasks/month/"><span
                        class="glyphicon glyphicon-list"></span>&nbsp;&nbsp;This Month</a></li>
                <li><a href="${pageContext.servletContext.contextPath}/tasks/report/"><span
                        class="glyphicon glyphicon-stats"></span>&nbsp;&nbsp;Report</a></li>
                <c:forEach items="${favoriteTags}" var="tag">
                    <li><a href="${pageContext.servletContext.contextPath}/tasks/?tag=${tag.name}"><span
                            class="glyphicon glyphicon-star"></span>&nbsp;&nbsp;
                        <span class="label label-default"
                              style="background-color: ${tag.displayColor}">${tag.name}</span>
                    </a></li>
                </c:forEach>
                <li><a href="${pageContext.servletContext.contextPath}/tags/"><span
                        class="glyphicon glyphicon-tags"></span>&nbsp;&nbsp;Tags</a></li>
            </ul>
            <!-- /#side-menu -->
        </div>
        <!-- /.sidebar-collapse -->
    </nav>
    <!-- /.navbar-static-side -->
    <div id="page-wrapper">
        <tiles:insertAttribute name="body"/>
        <div class="clear"></div>
    </div>
</div>
<div class="modal fade" id="quickTasksModal" tabindex="-1" role="dialog" aria-labelledby="quickTasksModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Quick Tasks Entry</h4>
            </div>
            <div class="modal-body">
                <p>Just thought of something? Add as many tasks as you'd like by entering each on a separate line:</p>

                <form name="quickTasksForm" id="quickTasksForm" action="${pageContext.servletContext.contextPath}/tasks/quickTasks" method="POST">
                    <label for="txtaTasks">Tasks:</label>
                    <textarea name="txtaTasks" id="txtaTasks" placeholder="Enter your tasks here."></textarea>
                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <span class="divider">&nbsp;</span>
                <button type="button" class="btn btn-matched" onClick="processQuickTasks()">Save changes</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<script>
    function processQuickTasks() {
        $.ajax({url: "${pageContext.servletContext.contextPath}/tasks/quickTasks",
            type: "POST",
            datatype: "text",
            data: "txtaTasks=" + document.getElementById("txtaTasks").value,
            async: false,
            cache: false,
            success: function () {
                location.reload();
            },
            error: function (request, status, error) {
                alert("Unable to add tasks: " + error);
            }
        });

        $('#quickTasksModal').modal('hide');
        return true;
    }
</script>
<!-- /#page-wrapper -->
</body>
</html>