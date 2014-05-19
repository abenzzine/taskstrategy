<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div id="main-content" class="col-md-11">

    <form:form class="form-horizontal" role="form" action="${pageContext.servletContext.contextPath}/tasks/new"
               method="POST" commandName="newTaskForm">
        <script>
            var reminderCount = <c:out value="${fn:length(newTaskForm.reminders)}" default="0" />;

            function addReminder(index) {
                var row = "<div id='reminderRowINDEX' class='row'>";
                var rowClose = "</div>";
                var divider = "<div id='reminderDividerINDEX' class='divider-vertical'>&nbsp;</div>";
                var reminderRow = "<div class='col-xs-5'><input  type='number' path='reminders[INDEX].quantifier' name='reminders[INDEX].quantifier' class='form-control' /></div>";
                var removeRow = "<div class='col-xs-2'><button class='btn btn-danger' type='button' onclick='removeReminder(INDEX);'><span class='glyphicon glyphicon-remove'></span></button> </div>"

                var reminderSelect = "<div class='col-xs-5'><select path='reminders[INDEX].qualifier' name='reminders[INDEX].qualifier' class='form-control'><c:forEach items='${reminderQualifiers}' var='reminderQualifier'><option value='${reminderQualifier.id}'>${reminderQualifier.name}</option></c:forEach></select></div>";
                var combined = row.replace(/INDEX/g, index) + reminderRow.replace(/INDEX/g, index) + reminderSelect.replace(/INDEX/g, index) + removeRow.replace(/INDEX/g, index) + rowClose + divider.replace(/INDEX/g, index);
                $('#reminderContainer').append(combined);
                reminderCount++;
            }

            function removeReminder(index) {
                $('#reminderRow' + index).remove();
                $('#reminderDivider' + index).remove();
            }
        </script>
        <h1>Task</h1>
        <c:if test="${errorMessage != null}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>
        <form:input type="hidden" path="id" id="id" name="id"/>
        <div class="form-group">
            <label class="col-lg-2">Title:</label>

            <div class="col-lg-6">
                <form:input type="text" path="name" id="name" name="name" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2">Due Date:</label>
            <div class="col-lg-6">
                <div class="input-append date" id="dueDateDatePicker" data-date="${newTaskForm.dueDate}"
                     data-date-format="mm/dd/yyyy">
                    <div class="input-group">
                        <span class="input-group-btn">
                               <button class="btn btn-default add-on"><span
                                       class="glyphicon glyphicon-calendar"></span></button>
                        </span>
                        <form:input type="text" path="dueDate" id="dueDate" name="dueDate" class="form-control" readonly="true"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2">Priority:</label>

            <div class="col-lg-6">
                <form:select type="text" path="priority" id="priority" name="priority" class="form-control">
                    <c:forEach items="${priorities}" var="taskPriority">
                        <form:option value="${taskPriority.id}">${taskPriority.description}</form:option>
                    </c:forEach>
                </form:select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2">Reminders:</label>
            <div class="col-lg-6">
                <button class="btn btn-default" type="button" onclick="addReminder(reminderCount);"><span
                        class="glyphicon glyphicon-bell"></span>&nbsp;&nbsp;Add
                    Reminder
                </button>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2"></label>
            <div class="col-lg-6" id="reminderContainer">
                <c:forEach items="${newTaskForm.reminders}" var="reminder" varStatus="status">
                    <div id="reminderRow${status.index}" class="row">
                        <div class="col-xs-5"><form:input type="number" path="reminders[${status.index}].quantifier"
                                                          name="reminders[${status.index}].quantifier"
                                                          class="form-control"/>
                        </div>
                        <div class="col-xs-5">
                            <form:select path="reminders[${status.index}].qualifier"
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
                            <button class='btn btn-danger' type='button'
                                    onclick='removeReminder(${status.index});'><span
                                    class='glyphicon glyphicon-remove'></span></button>
                        </div>
                    </div>
                    <div id="reminderDivider${status.index}" class="divider-vertical">&nbsp;</div>
                </c:forEach>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2">Tags:</label>

            <div class="col-lg-6">
                <form:input type="text" path="taskTags" id="taskTags" name="taskTags" class="form-control"
                            data-role="tagsinput"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-2">Description:</label>

            <div class="col-lg-6">
                <form:textarea type="text" path="description" id="description" name="description" rows="5"
                               class="form-control"/>
            </div>
        </div>
        <div class="form-group row">
            <span class="col-lg-2">&nbsp;</span>

            <div class="col-lg-3">
                <form:button type="submit" id="save" class="col-md-4 btn btn-matched form-control">Save</form:button>
            </div>
            <div class="col-lg-3">
                <a class="col-md-4 btn btn-matched form-control"
                   onclick="window.location='${pageContext.servletContext.contextPath}/tasks/'">Cancel</a>
            </div>
        </div>
    </form:form>
</div>


<script>
    var now = new Date(2001, 1, 1, 0, 0, 0, 0);
    $(function () {

        $('#dueDateDatePicker').datepicker({
            onRender: function (date) {
                return date.valueOf() < now.valueOf() ? 'disabled' : '';
            }
        });
    });
</script>