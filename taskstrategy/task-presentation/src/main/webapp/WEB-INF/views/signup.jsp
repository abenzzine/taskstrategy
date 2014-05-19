<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container">
    <div class="row">
        <div class="col-md-3"></div>
        <form:form name="signup" id="signup" class="col-md-6" action="${pageContext.servletContext.contextPath}/signup"
                   method='POST' commandName="newUser">
            <h1>Sign up</h1>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger"> ${errorMessage}
                </div>
            </c:if>
            <div class="well" id="login_container">
                <div class="form-group row">
                    <label class="col-lg-3" for="signupEmail"><strong>Email: </strong></label>

                    <div class="col-lg-9">
                        <form:input type="text" id="signupEmail" class="form-control" path="email"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-lg-3" for="signupPassword"><strong>Password: </strong></label>

                    <div class="col-lg-9">
                        <form:password id="signupPassword" class="form-control" path="password"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3"></div>
                    <div class="col-lg-9">
                        <form:button type="submit" id="signup"
                                     class="btn btn-matched btn-sm form-control">Sign up</form:button>
                    </div>
                </div>
            </div>
        </form:form>
        <div class="col-md-3"></div>
    </div>
</div>

<script text="text/javascript">
    window.onload = function () {
        document.getElementById("signupEmail").focus();
    }
</script>