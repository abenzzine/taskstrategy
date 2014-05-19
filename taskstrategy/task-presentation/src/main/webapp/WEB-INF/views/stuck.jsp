<%@ page import="com.taskstrategy.commons.domain.TaskSet" %>
<%@ page import="com.taskstrategy.web.config.Constants" %>
<%@ page import="com.taskstrategy.commons.domain.Task" %>
<%

    TaskSet set = (TaskSet) request.getAttribute(Constants.TASK_SET);
    Task task = set.getActiveTask();
%>
<div id='action_mode_div'>
    <h1>Stuck! </h1>
    <p>OK, You're not quite sure what to do with the following task: <em>"<%=task.getName()%>"</em>.  Here are some things to help you:</p>
    <form name='action_form' id='action_form' action='take_action' method='POST'>
    <ul>
        <input type='hidden' name='taskId' id='taskId' value='<%=task.getId()%>'/>
        <input type='hidden' name='actionType' id='actionType' />

        <li>A smaller task is easier to get done than a larger one. What is the simplest thing you can do to make progress on this? </li>
        <li>Sometimes we forget the big picture. What is the larger goal that you are trying to accomplish with ${task.getName()}?</li>
        <li>Don't try to do everything on your own. Can someone else help with <%=task.getName()%>?</li>
        <li>Think about <%=task.getName()%> from a different perspective.  How would an expert handle this? How would someone doing this for the first time approach this?</li>
    </ul>

        <button class="btn btn-default btn-primary" style='margin-right: 5px;' onclick='document.getElementById("actionType").value="redisplay"; document.getElementById("actionForm").submit();'>Go Back</button>
    </form>

</div>