<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
    .navbar-brand { display: none; }
</style>
<div class="jumbotron frontimg">
    <div class="container row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <h1>Task Strategy</h1>
            <p>The only task manager you'll ever need. <br /><span style="font-size:30px;">It's Free!</span></p>
            <br />
            <div class="row">
                <div class="col-sm-4">
                    <a href="${pageContext.servletContext.contextPath}/signup/" class="btn btn-success form-control"><img alt="logo" src="${pageContext.servletContext.contextPath}/img/task-strategy-logo-icon.png" />&nbsp;&nbsp;Sign Up</a>
                </div>
                <div class="col-sm-4">
                    <a class="btn form-control" data-toggle="modal" data-target="#learn-more-modal"><span class="glyphicon glyphicon-play"></span>&nbsp;&nbsp;Learn more</a>
                </div>
            </div>
        </div>
        <div class="col-md-2"></div>
    </div>
</div>
<div id="home-arrow-bottom" class="row">
    <div class="col-sm-2 col-sm-offset-5">
        <a href="#learn-more" class="btn form-control"><span class="glyphicon glyphicon-chevron-down"></span></a>
    </div>
</div>
<div id="learn-more" class="container">
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8">
            <div class="row">
                <div class="col-md-7">
                    <img class="img-responsive" alt="action-mode" src="${pageContext.servletContext.contextPath}/img/home-thumbnails/home-thumbnail-1.png" />
                </div>
                <div class="col-md-5">
                    <h2>What Sets Us Apart?</h2>
                    <p>Task Strategy is an application that goes beyond traditional task management applications by
                        providing tools and features that help actively guide users to higher productivity. Features
                        such as, action mode - where a single task is displayed for better focus, repeating reminders
                        and tag filtering are a subset of what Task Strategy offers.
                    </p>
                </div>
            </div>
            <br /><br />
            <div class="row">
                <div class="col-md-7">
                    <h2>Fully compatible with desktop, tablets, and mobile</h2>
                    <p>
                        Our application is designed to be friendly and usable whether you are at home, in the office, or on the go.
                    </p>
                </div>
                <div class="col-md-5">
                    <img class="img-responsive" alt="action-mode" src="${pageContext.servletContext.contextPath}/img/home-thumbnails/home-thumbnail-2.png" />
                </div>
            </div>
            <br /><br />
            <div class="row">
                <div class="col-md-7">
                    <img class="img-responsive" alt="action-mode" src="${pageContext.servletContext.contextPath}/img/home-thumbnails/home-thumbnail-3.png" />
                </div>
                <div class="col-md-5">
                    <h2>Introducing Action Mode!</h2>
                    <p>
                        Action Mode, along with many other features, is a great is a great way to stay organized,
                        focus on specific task items, and quickly act upon those items. For example, it gives you the ability to efficiently
                        invoke actions upon each of your tasks. This is just among one of the many powerful functions included in our application.
                    </p>
                </div>
            </div>
            <br /><br />
            <div class="row">
                <div class="col-md-7">
                    <h2>Reports</h2>
                    <p>
                        Want to view data to help better strategize your tasks?
                        Being able to dive directly into this information has never been so easy.
                        You will be able to view the specific details and the metrics of your tasks and their statuses.
                        The reports functionality has what you need to do just that, and more!
                    </p>
                </div>
                <div class="col-md-5">
                    <img class="img-responsive" alt="action-mode" src="${pageContext.servletContext.contextPath}/img/home-thumbnails/chart.png" />
                </div>
            </div>
            <br /><br />
            <div class="row">
                <div class="col-md-7">
                    <img class="img-responsive" alt="action-mode" src="${pageContext.servletContext.contextPath}/img/home-thumbnails/manager.png" />
                </div>
                <div class="col-md-5">
                    <h2>Made for Task Managers by Task Managers</h2>
                    <p>Our team has used multiple task management applications and found pros and cons for each of them.
                        Therefore, we wanted to create a powerful application that can maximize productivity as well
                        as be easily accessible on multiple devices.
                    </p>
                </div>
            </div>
        </div>
        <div class="col-md-2"></div>
    </div>
    <br /><br />
</div>
<div id="home-bottom-sign-up">
    <div class="container">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <h2>What are you waiting for? Try It Now!</h2>
            <div class="row">
                <div class="col-sm-5">
                    <a href="${pageContext.servletContext.contextPath}/signup/" class="btn btn-success form-control"><img alt="logo" src="${pageContext.servletContext.contextPath}/img/task-strategy-logo-icon.png" />&nbsp;&nbsp;Sign Up</a>
                </div>
            </div>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>
<br />
<div class="modal fade" id="learn-more-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <iframe src="//player.vimeo.com/video/86281120" width="500" height="375" frameborder="0" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe> <p><a href="http://vimeo.com/86281120">Task Strategy</a> from <a href="http://vimeo.com/user4322704">Terry McKee</a> on <a href="https://vimeo.com">Vimeo</a>.</p>
            </div>
        </div>
    </div>
</div>