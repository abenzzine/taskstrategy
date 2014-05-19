<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="main-content" class="col-md-11">
    <form:form id="profileEditForm" class="form-horizontal" role="form"
               action="${pageContext.servletContext.contextPath}/profile/update"
               method="POST" commandName="editProfileForm">
        <h1>Profile</h1>
        <c:if test="${errorMessage != null}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>
        <c:if test="${successMessage != null}">
            <div class="alert alert-success">${successMessage}</div>
        </c:if>
        <div class="form-group">
            <label class="col-lg-2">Username</label>

            <div class="col-lg-6">
                <form:input type="email" path="email" id="email" name="email" class="form-control"/>
            </div>
        </div>
    </form:form>
    <form:form id="deleteAccountForm" class="form-horizontal" role="form"
               action="${pageContext.servletContext.contextPath}/profile/delete"
               method="POST">
    </form:form>
    <div class="form-group row">
        <span class="col-lg-2">&nbsp;</span>

        <div class="col-lg-3">
            <button id="updateUser" class="col-md-4 btn btn-matched form-control"
                    onclick="$('#profileEditForm').submit();" style="margin-bottom: 10px;">Update
            </button>
        </div>
        <div class="col-lg-3">
            <a class="col-md-4 btn btn-matched form-control" onclick="$('#deleteAccountForm').submit()">Delete
                Account</a>
        </div>

    </div>
    <form:form id="resetPasswordForm" class="form-horizontal" role="form"
               action="${pageContext.servletContext.contextPath}/profile/updatepassword"
               method="POST" commandName="resetPasswordForm">
        <div class="form-group">
            <label class="col-lg-2">Password</label>

            <div class="col-lg-6">
                <form:password path="password" id="password" name="password" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2">Confirm Password</label>

            <div class="col-lg-6">
                <form:password path="confirmPassword" id="confirmPassword" name="confirmPassword" class="form-control"/>
            </div>
        </div>
    </form:form>
    <div class="form-group row">
        <span class="col-lg-2">&nbsp;</span>
        <div class="col-lg-3">
            <button id="changePassword" class="col-md-4 btn btn-matched form-control"
                    onclick="$('#resetPasswordForm').submit();">Change Password
            </button>
        </div>
    </div>
</div>
