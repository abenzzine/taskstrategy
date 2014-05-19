<%@ page import="com.taskstrategy.web.config.Constants" %>
<%@ page import="com.taskstrategy.commons.domain.TaskSet" %>
<%@ page import="com.taskstrategy.commons.domain.Task" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    TaskSet taskset = (TaskSet) request.getAttribute(Constants.TASK_SET);
    Task task = taskset.getActiveTask();
%>
<div id="main-content" class="container">
    <h1>Task Too Big</h1>
    <h2><%=task.getName()%></h2>
    <p>Break this task down into smaller tasks with separate lines.</p>
    <div id='taskarea'>
        <form name='subtaskCreationForm' id='subtaskCreationForm' action="/create_subtasks" method='post'>
            <input type='hidden' name='taskId' id='taskId' value='<%=task.getId()%>'/>
            <label for='subtasks'>Subtasks: </label>
            <textarea id='subtasks' name='subtasks' class="col-md-12 form-control" placeholder="Child task #1"></textarea>
            <div class="row custom">
                <div class="col-md-3">
                    <button class="btn btn-primary form-control" onclick='document.getElementById("subtaskCreationForm").submit()'>Create Subtasks</button>
                </div>
                <div class="col-md-3">
                    <a href="${pageContext.servletContext.contextPath}/action/"><button class="btn btn-default form-control" type="button">Cancel</button></a>
                </div>
            </div>
        </form>
    </div>
</div>