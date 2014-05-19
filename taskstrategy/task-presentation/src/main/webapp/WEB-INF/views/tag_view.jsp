<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Tag Dashboard</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<div class="row">
    <div class="col-sm-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                Tag List
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
                       data-page-size="10" id="tagListView">
                    <thead>
                    <tr>
                        <th data-toggle="true"  data-sort-initial="true">Name</th>
                        <th data-sort-ignore="true">Color</th>
                        <th data-hide="phone,tablet" data-sort-ignore="true">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${errorMessage != null}">
                        <div class="alert alert-danger">${errorMessage}</div>
                    </c:if>
                    <c:if test="${successMessage != null}">
                        <div class="alert alert-success">${successMessage}</div>
                    </c:if>
                    <c:forEach items="${tags}" var="tag">
                        <tr>
                            <td><c:choose><c:when test="${tag.favorite}"><span
                                    class="glyphicon glyphicon-star"></span></c:when><c:otherwise><span
                                    class="glyphicon glyphicon-star-empty"></span></c:otherwise></c:choose>&nbsp;&nbsp;${tag.name}
                            </td>
                            <td><span style="background-color: ${tag.displayColor}">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            </td>
                            <td>
                                <a class="btn btn-matched"
                                   href="${pageContext.servletContext.contextPath}/tag/edit/${tag.name}">
                                    <span class="glyphicon glyphicon-pencil"></span>
                                </a>
                                <span class="divider">&nbsp;</span>
                                <a class="btn btn-matched"
                                   href="${pageContext.servletContext.contextPath}/tags/delete/${tag.name}">
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
    </div>
</div>

<script>
    $(document).ready(function () {
        $('.footable').footable();
    });
</script>