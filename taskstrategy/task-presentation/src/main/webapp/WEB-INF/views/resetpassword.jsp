<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="main-content" class="col-md-11">
    <c:if test="${errorMessage != null}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>
    <c:if test="${successMessage != null}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <form:form id="resetPasswordForm" class="form-horizontal" role="form"
               action="${pageContext.servletContext.contextPath}/resetpassword"
               method="POST" commandName="resetPasswordForm">
        <form:hidden path="userId" id="userId" name="userId" class="form-control"/>
        <form:hidden path="resetId" id="resetId" name="resetId" class="form-control"/>
        <div class="form-group">
            <label class="col-lg-2">Password</label>

            <div class="col-lg-10">
                <form:password path="password" id="password" name="password" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2">Confirm Password</label>

            <div class="col-lg-10">
                <form:password path="confirmPassword" id="confirmPassword" name="confirmPassword" class="form-control"/>
            </div>
        </div>
        <div class="col-lg-3">
            <button type="submit" id="changePassword" class="col-md-4 btn btn-primary form-control">Change
                Password
            </button>
        </div>
    </form:form>
</div>
