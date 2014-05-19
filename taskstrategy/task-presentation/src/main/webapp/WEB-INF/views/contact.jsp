<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div id="main-content" class="container">
    <div class="row">
        <div class="col-md-3"></div>
        <form:form name="contactForm" id="contactForm" class="col-md-6" action="${pageContext.servletContext.contextPath}/contact" method='POST' commandName="contactForm">
            <h1>Questions or comments?</h1>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger"> ${errorMessage}
                </div>
            </c:if>
            <c:if test="${successMessage != null}">
                <div class="alert alert-success">${successMessage}</div>
            </c:if>
            <div class="well" id="login_container">
                <div class="form-group row">
                    <label class="col-lg-3" for="contactName"><strong>Name: </strong></label>
                    <div class="col-lg-9">
                        <form:input type="text" id="contactName" class="form-control" path="name" />
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-lg-3" for="contactEmail"><strong>Email: </strong></label>
                    <div class="col-lg-9">
                        <form:input type="text" id="contactEmail" class="form-control" path="email" />
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-lg-3" for="contactComments"><strong>Comments: </strong></label>
                    <div class="col-lg-9">
                        <form:textarea rows="5" id="contactComments" class="form-control" path="comments"></form:textarea>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3"></div>
                    <div class="col-lg-9">
                        <button type="submit" class="btn btn-matched btn-sm form-control">Send</button>
                    </div>
                </div>
            </div>
        </form:form>
        <div class="col-md-3"></div>
    </div>
</div>