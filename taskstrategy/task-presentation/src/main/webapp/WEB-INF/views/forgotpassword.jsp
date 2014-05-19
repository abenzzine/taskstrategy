<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="row">
        <div class="col-md-3"></div>
        <form name='forgotPassword' action="${pageContext.servletContext.contextPath}/forgot-password"
              class="form-horizontal col-md-6" method='POST' commandName="forgotPasswordForm">
            <h1>Reset Password</h1>
            <c:if test="${successMessage != null}">
                <div class="alert alert-success">${successMessage}</div>
            </c:if>
            <div class="well" id="login_container">
                <div class="form-group">
                    <label class="col-lg-3" for="email"><strong>Email: </strong></label>
                    <div class="col-lg-9">
                        <input class="form-control" type='text' id="email" name='email' value=''>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3"></div>
                    <div class="col-lg-9">
                        <button type="submit" id="signin" class="btn btn-primary btn-sm form-control">Reset
                            Password
                        </button>
                    </div>
                </div>
            </div>
        </form>
        <div class="col-md-3"></div>
    </div>
</div>