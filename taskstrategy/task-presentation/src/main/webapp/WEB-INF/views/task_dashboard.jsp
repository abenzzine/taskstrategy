<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" %>
<div class="row">
    <div class="col-sm-12">
        <h1 class="page-header">Task Dashboard</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<c:if test="${actionModeNotification && (pastDueCount > 0 || dueTodayCount > 0)}">
    <div class="row">
        <div class="col-sm-12">
            <div class="alert alert-notification">
                <div class="row">
                    <div class="col-xs-1">
                        <span class="glyphicon glyphicon-flash dashboardInfo"></span>
                    </div>
                    <div class="col-xs-11 text-left" style="margin-top:10px;">
                        <span class="announcement-heading-small">Hey! You have tasks you need to complete today, get started in <a
                                class="alert-link"
                                href="${pageContext.servletContext.contextPath}/action/"><span
                                class="glyphicon glyphicon-circle-arrow-right"></span> Action Mode</a>!
                        </span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>
<div class="row">
    <div class="col-lg-3">
        <div class="panel panel-danger">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-6">
                        <span class="glyphicon glyphicon-exclamation-sign dashboardInfo"></span>
                    </div>
                    <div class="col-xs-6 text-right">
                        <p class="announcement-heading">${pastDueCount}</p>

                        <p class="announcement-text">Past Due</p>
                    </div>
                </div>
            </div>
            <a href="${pageContext.servletContext.contextPath}/tasks/pastdue/">
                <div class="panel-footer announcement-bottom">
                    <div class="row">
                        <div class="col-xs-6">
                            View
                        </div>
                        <div class="col-xs-6 text-right">
                            <span class="glyphicon glyphicon-circle-arrow-right"></span>
                        </div>
                    </div>
                </div>
            </a>
        </div>
    </div>
    <div class="col-lg-3">
        <div class="panel panel-warning">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-6">
                        <span class="glyphicon glyphicon-warning-sign dashboardInfo"></span>
                    </div>
                    <div class="col-xs-6 text-right">
                        <p class="announcement-heading">${dueTodayCount}</p>

                        <p class="announcement-text">Due Today</p>
                    </div>
                </div>
            </div>
            <a href="${pageContext.servletContext.contextPath}/tasks/today/">
                <div class="panel-footer announcement-bottom">
                    <div class="row">
                        <div class="col-xs-6">
                            View
                        </div>
                        <div class="col-xs-6 text-right">
                            <span class="glyphicon glyphicon-circle-arrow-right"></span>
                        </div>
                    </div>
                </div>
            </a>
        </div>
    </div>

    <div class="col-lg-3">
        <div class="panel panel-info">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-6">
                        <span class="glyphicon glyphicon-info-sign dashboardInfo"> </span>
                    </div>
                    <div class="col-xs-6 text-right">
                        <p class="announcement-heading">${dueWeekCount}</p>

                        <p class="announcement-text">Due This Week</p>
                    </div>
                </div>
            </div>
            <a href="${pageContext.servletContext.contextPath}/tasks/week/">
                <div class="panel-footer announcement-bottom">
                    <div class="row">
                        <div class="col-xs-6">
                            View
                        </div>
                        <div class="col-xs-6 text-right">
                            <span class="glyphicon glyphicon-circle-arrow-right"></span>
                        </div>
                    </div>
                </div>
            </a>
        </div>
    </div>

    <div class="col-lg-3">
        <div class="panel panel-success">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-xs-6">
                        <span class="glyphicon glyphicon-check dashboardInfo"> </span>
                    </div>
                    <div class="col-xs-6 text-right">
                        <p class="announcement-heading">${completedThisWeek}</p>

                        <p class="announcement-text">Completed This Week</p>
                    </div>
                </div>
            </div>
            <a href="${pageContext.servletContext.contextPath}/done">
                <div class="panel-footer announcement-bottom">
                    <div class="row">
                        <div class="col-xs-6">
                            View
                        </div>
                        <div class="col-xs-6 text-right">
                            <span class="glyphicon glyphicon-circle-arrow-right"></span>
                        </div>
                    </div>
                </div>
            </a>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-sm-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                Task List
                <div class="pull-right">
                    <div class="btn-group">
                        <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                            Actions
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu pull-right" role="menu">
                            <li><a href="${pageContext.servletContext.contextPath}/tasks/create/"><span
                                    class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;New Task</a>
                            </li>
                            <li><a href="${pageContext.servletContext.contextPath}/action/"><span
                                    class="glyphicon glyphicon-circle-arrow-right"></span>&nbsp;&nbsp;Action Mode</a>
                            </li>
                            <li><a href="${pageContext.servletContext.contextPath}/done"><span
                                    class="glyphicon glyphicon-list"></span>&nbsp;&nbsp;Completed Tasks</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <div class="row">
                    <div class="col-sm-3">
                        <input id="filter" class="form-control" placeholder="Search..." type="text"/>
                    </div>
                </div>
                <div class="divider-vertical">&nbsp;</div>
                <table class="footable" data-sort="true" data-filter="#filter"
                       data-page-navigation=".pagination"
                       data-page-size="10" id="taskListView">
                    <thead>
                    <tr>
                        <th width="50%" data-toggle="true">Name</th>
                        <th data-sort-initial="true" data-type="numeric" data-hide="phone">Due</th>
                        <th data-hide="phone" data-type="numeric" class="sorting">Priority</th>
                        <th width="20%" data-hide="phone,tablet" data-sort-ignore="true">Tags</th>
                        <th data-sort-ignore="true" data-hide="phone">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${errorMessage != null}">
                        <div class="alert alert-danger">${errorMessage}</div>
                    </c:if>
                    <c:if test="${successMessage != null}">
                        <div class="alert alert-success">${successMessage}</div>
                    </c:if>
                    <c:forEach items="${tasks}" var="task">
                        <tr>
                            <td class="word-wrap">
                                <strong>${task.name}</strong>
                            </td>
                            <fmt:formatDate var="today" value='<%= new java.util.Date()%>' pattern="yyyy-MM-dd"/>
                            <c:choose>
                                <c:when test="${task.dueDate < today}">
                                    <td style="color:red" data-value="${task.dueDate.time}"><span
                                            class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;&nbsp;${task.dueDate}
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td data-value="${task.dueDate.time}">${task.dueDate}</td>
                                </c:otherwise>
                            </c:choose>
                            <td data-value="${task.priority.id}">${task.priority.description}</td>
                            <td>
                                <c:forEach items="${task.taskTags}" var="tag">
                            <span class="label label-default"
                                  style="background-color: ${tag.displayColor}">${tag.name}</span>
                                </c:forEach>
                            </td>
                            <td>
                                <a class="btn btn-success"
                                   href="${pageContext.servletContext.contextPath}/done/${task.id}">
                                    <span class="glyphicon glyphicon-check"></span>
                                </a>
                                <span class="divider">&nbsp;</span>
                                <a class="btn btn-matched"
                                   href="${pageContext.servletContext.contextPath}/tasks/edit/${task.id}">
                                    <span class="glyphicon glyphicon-pencil"></span>
                                </a>
                                <span class="divider">&nbsp;</span>
                                <a class="btn btn-matched"
                                   href="${pageContext.servletContext.contextPath}/tasks/delete/${task.id}">
                                    <span class="glyphicon glyphicon-trash"></span>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="5">
                            <div class="pagination pagination-center hide-if-no-paging"></div>
                        </td>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
        <!-- /.panel-body -->
    </div>
    <!-- /.panel -->
</div>

<script>
    $(document).ready(function () {
        $('.footable').footable();
    });
</script>
