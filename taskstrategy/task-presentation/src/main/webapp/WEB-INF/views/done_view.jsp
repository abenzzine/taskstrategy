<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" %>
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Completed Task Dashboard</h1>
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
                            <li><a href="${pageContext.servletContext.contextPath}/tasks/"><span
                                    class="glyphicon glyphicon-list"></span>&nbsp;&nbsp;Tasks</a></li>
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
                       data-page-navtigation=".pagination"
                       data-page-size="10" id="taskListView">
                    <thead>
                    <tr>
                        <th width="50%" data-toggle="true">Name</th>
                        <th data-sort-initial="true" data-type="numeric" data-hide="phone">Date Completed</th>
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
                    <c:forEach items="${done}" var="task">
                        <tr>
                            <td style="word-wrap: break-word">
                                <strong><del>${task.name}</del></strong>
                            </td>
                            <td data-value="${task.lastModifiedDate.time}"><fmt:formatDate value="${task.lastModifiedDate}" pattern="MM/dd/yyyy"/></td>
                            <td data-value="${task.priority.id}">${task.priority.description}</td>
                            <td>
                                <c:forEach items="${task.taskTags}" var="tag">
                            <span class="label label-default"
                                  style="background-color: ${tag.displayColor}">${tag.name}</span>
                                </c:forEach>
                            </td>
                            <td>
                                <a class="btn btn-matched"
                                   href="${pageContext.servletContext.contextPath}/tasks/uncomplete?id=${task.id}">
                                    <span class="glyphicon glyphicon-backward"></span>
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