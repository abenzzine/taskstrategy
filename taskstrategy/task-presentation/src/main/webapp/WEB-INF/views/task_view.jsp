<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" %>
<%@ page import="com.taskstrategy.web.config.Constants" %>
<%
    session.setAttribute(Constants.TASK_SET, null);
%>
<div class="row">
    <div class="col-sm-12">
        <h1 class="page-header">Task List
            <small style="word-wrap: break-word">${subtitle}</small>
        </h1>
    </div>
    <!-- /.col-lg-12 -->
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
                            <li><a data-toggle="modal" data-target="#quickTasksModal"
                                   href="${pageContext.servletContext.contextPath}/tasks/create/"><span
                                    class="glyphicon glyphicon-fire"></span>&nbsp;&nbsp;Add Quick Tasks</a>
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
                        <th data-sort-ignore="true" data-hide="phone,tablet">Actions</th>
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
<!-- /.modal -->
