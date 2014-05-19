<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="main-content" class="col-md-11">

    <form:form id="tagEditForm" class="form-horizontal" role="form"
               action="${pageContext.servletContext.contextPath}/tag/update"
               method="POST" commandName="newTagForm">
        <h1>Tag</h1>
        <c:if test="${errorMessage != null}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>
        <div class="form-group">
            <label class="col-lg-2">Title:</label>

            <div class="col-lg-5">
                <form:input type="text" path="name" id="name" name="name" class="form-control" readonly="true"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2">Favorite:</label>

            <div class="col-lg-5">
                <form:input type="hidden" path="favorite" id="favorite" name="favorite" class="form-control"/>
                <a id="checkFavorite" class="btn btn-default"
                   onclick="$('#favorite').val('false');setupView();">
                    <span class="glyphicon glyphicon-star"></span>
                </a>
                <a id="checkUnFavorite" class="btn btn-default"
                   onclick="$('#favorite').val('true');setupView();">
                    <span class="glyphicon glyphicon-star-empty"></span>
                </a>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2">Display Color:</label>

            <div class="col-lg-5">
                <form:input type="hidden" path="color" id="color" name="color" class="pick-a-color"/>
            </div>
        </div>
        <div class="form-group row">
            <span class="col-lg-2">&nbsp;</span>

            <div class="col-lg-2">
                <form:button type="submit" id="save" class="col-md-4 btn btn-matched form-control"
                             style="margin-bottom: 10px;">Save</form:button>
            </div>
            <div class="col-lg-2">
                <a class="col-md-4 btn btn-matched form-control"
                   onclick="window.location='${pageContext.servletContext.contextPath}/tags/'">Cancel</a>
            </div>

        </div>
    </form:form>
</div>

<script type="text/javascript">
    function setupView() {
        if ($("#favorite").val() == "true") {
            $("#checkFavorite").show();
            $("#checkUnFavorite").hide();
        } else {
            $("#checkFavorite").hide();
            $("#checkUnFavorite").show();
        }
    }
    setupView();
    $(".pick-a-color").pickAColor();
</script>