<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="com.taskstrategy.commons.domain.TaskSet" %>
<%@ page import="com.taskstrategy.web.config.Constants" %>
<%@ page import="com.taskstrategy.commons.domain.Task" %>

<%
    TaskSet set = (TaskSet) request.getAttribute(Constants.TASK_SET);
    Task task = set.getActiveTask();
    if (task == null)
    {
        session.setAttribute(Constants.TASK_SET, null);
    }
%>
<div id="main-content">
    <form name='action_form' id='action_form' action='take_action' method='POST'>
        <input type='hidden' name='taskId' id='taskId' value='<%= task==null?"":task.getId()%>'/>
        <input type='hidden' name='actionType' id='actionType' />
    <div class="pull-right actionProgressBar">
        <div>Task Set Progress</div>
        <div>
        <%

            for (int i = 0; i < set.getTaskCount(); i++)
            {
              if (set.hasReviewed(i))
              {
        %>
               <span class="glyphicon glyphicon-star"> </span>
        <%
              }
              else
              {
        %>
               <span class="glyphicon glyphicon-unchecked"> </span>
        <%
                }
            }
        %>
            </div>
    </div>
        <h1 class="page-header">Action Mode</h1>
    <div class='row'>
        <div class="col-md-9">
            <div id="taskbox">

                <% if (task == null) { %>
                <div class="actionModeCongrats">Great Job! You've Gone Through A Set of Tasks!</div>
                <div class="congratsLink">
                    <a href="${pageContext.servletContext.contextPath}/dashboard/"><button type="button" class="btn btn-primary">Back to Dashboard</button></a>
                    <a href="${pageContext.servletContext.contextPath}/action/"><button type="button" class="btn btn-primary">Another Task Set</button></a>
                </div>
                <% } else {
                    %>
                <div class="pull-left"><a href="#" data-toggle="modal" data-target="#viewTaskModal"><span class="glyphicon glyphicon-eye-open"> </span></a></div>
                  <div class="pull-right"><%=task.getDueDate()%></div>
                <%
                  if (task.getPassCount() > 1)
                  {
                      if (task.getName().toUpperCase().startsWith("READ "))
                      {
                %>
                   <div class="passnote"><em>Looks like this is a reading task. Can you read just one page?</em></div>
                   <%
                       } else {
                   %>
                   <div class="passnote"><em>You've passed this task before.  Use the &lsquo;Too Big&rsquo; option to break this task into smaller ones.  What is the smallest step you could take to make progress?</em></div>
                   <%
                       }
                   %>

                <% } %>
                    <div class="am_task word-wrap"><%=task.getName()%></div>
                <% } %>
            </div>
        </div>
        <% if (task != null) {%>
            <div class="col-md-3">
                <div class='row custom'>
                    <div class="col-md-12">
                        <button class='form-control btn btn-matched' onclick='document.getElementById("actionType").value="stuck"; document.getElementById("action_form").submit();'><span class="glyphicon glyphicon-ban-circle"></span> STUCK</button>
                    </div>
                    <div class="col-md-12">
                        <button class='form-control btn btn-matched' onclick='document.getElementById("actionType").value="breakdown"; document.getElementById("action_form").submit();'><span class="glyphicon glyphicon-resize-full"></span> TOO BIG</button>
                    </div>
                    <div class="col-md-12">
                        <button class='form-control btn btn-matched' onclick='document.getElementById("actionType").value="pass"; document.getElementById("action_form").submit();'><span class="glyphicon glyphicon-thumbs-up"></span> PASS</button>
                    </div>
                    <div class="col-md-12">
                        <button class='form-control btn btn-matched' onclick='document.getElementById("actionType").value="delete"; document.getElementById("action_form").submit();'><span class="glyphicon glyphicon-trash"></span> TRASH</button>
                    </div>
                </div>
            </div>
        <%} %>
    </div>
    <% if (task != null) {%>
        <div class='row custom'>
            <div class="col-md-12">
                <button class='form-control btn btn-high btn-success' onclick='document.getElementById("actionType").value="complete"; document.getElementById("action_form").submit();'><span class="glyphicon glyphicon-check"></span> COMPLETE</button>
            </div>
        </div>
    <%} %>
    </form>
</div>
<% if (task != null) {%>
<div class="modal" id="viewTaskModal" tabindex="-1" role="dialog" aria-labelledby="editTaskModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
         <form:form class="form-horizontal" role="form" action="${pageContext.servletContext.contextPath}/tasks/new"
           method="POST" commandName="newTaskForm">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Edit Task</h4>
            </div>
            <div class="modal-body">

                <h1>Task</h1>
                <c:if test="${errorMessage != null}">
                    <div class="alert alert-danger">${errorMessage}</div>
                </c:if>
                <form:input type="hidden" path="id" id="id" name="id"/>
                <div class="form-group">
                    <label class="col-lg-2">Title:</label>

                    <div class="col-lg-6">
                        <form:input disabled="true" type="text" path="name" id="name" name="name" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2">Due Date:</label>
                    <div class="col-lg-6">
                        <div class="input-append date" id="dueDateDatePicker" data-date="${newTaskForm.dueDate}"
                             data-date-format="mm/dd/yyyy">
                            <div class="input-group">
                                <form:input disabled="true" type="text" path="dueDate" id="dueDate" name="dueDate" class="form-control" readonly="true"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2">Priority:</label>

                    <div class="col-lg-6">
                        <form:select disabled="true" type="text" path="priority" id="priority" name="priority" class="form-control">
                            <c:forEach items="${priorities}" var="taskPriority">
                                <form:option value="${taskPriority.id}">${taskPriority.description}</form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2">Reminders:</label>
                    <div class="col-lg-6">

                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2"></label>
                    <div class="col-lg-6" id="reminderContainer">
                        <c:forEach items="${newTaskForm.reminders}" var="reminder" varStatus="status">
                            <div id="reminderRow${status.index}" class="row">
                                <div class="col-xs-5"><form:input disabled="true" type="number" path="reminders[${status.index}].quantifier"
                                                                  name="reminders[${status.index}].quantifier"
                                                                  class="form-control"/>
                                </div>
                                <div class="col-xs-5">
                                    <form:select disabled="true" path="reminders[${status.index}].qualifier"
                                                 name="reminders[${status.index}].qualifier" class="form-control">
                                        <c:forEach items="${reminderQualifiers}" var="reminderQualifier">
                                            <c:choose>
                                                <c:when test="${reminderQualifier.id == reminder.qualifier}">
                                                    <form:option selected="true"
                                                                 value="${reminderQualifier.id}">${reminderQualifier.name}</form:option>
                                                </c:when>
                                                <c:otherwise>
                                                    <form:option
                                                            value="${reminderQualifier.id}">${reminderQualifier.name}</form:option>
                                                </c:otherwise>
                                            </c:choose>

                                        </c:forEach>
                                    </form:select>
                                </div>
                                <div class='col-xs-2'>

                                </div>
                            </div>
                            <div id="reminderDivider${status.index}" class="divider-vertical">&nbsp;</div>
                        </c:forEach>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2">Tags:</label>

                    <div class="col-lg-6">
                        <form:input disabled="true" type="text" path="taskTags" id="taskTags" name="taskTags" class="form-control"
                                    data-role="tagsinput"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2">Description:</label>

                    <div class="col-lg-6">
                        <form:textarea disabled="true" type="text" path="description" id="description" name="description" rows="5"
                                       class="form-control"/>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <div class="form-group row">
                    <span class="col-lg-2">&nbsp;</span>

                    <div class="col-lg-3">

                    </div>
                    <div class="col-lg-3">
                        <a class="col-md-4 btn btn-matched form-control" href="#" data-dismiss="modal">Close</a>
                    </div>
                </div>
            </div>
         </form:form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<% } %>