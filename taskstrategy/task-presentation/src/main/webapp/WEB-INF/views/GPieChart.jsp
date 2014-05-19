<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<script src="../scripts/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="http://code.jquery.com/jquery-1.9.0.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.min.js"></script>
<script src="http://cdn-na.infragistics.com/jquery/20132/latest/js/infragistics.core.js"></script>
<script src="http://cdn-na.infragistics.com/jquery/20132/latest/js/infragistics.dv.js"></script>
<link href="http://cdn-na.infragistics.com/jquery/20132/latest/css/themes/infragistics/infragistics.theme.css"
      rel="stylesheet"></link>
<link href="http://cdn-na.infragistics.com/jquery/20132/latest/css/structure/infragistics.css" rel="stylesheet"></link>
<link href="http://igniteui.com/css/apiviewer.css" rel="stylesheet" type="text/css"></link>


<style>

    #myTab.nav li a {
        font: normal 10px Verdana;
        text-decoration: none;
        position: relative;
        z-index: 1;
        padding: 10px 10px;
        border: 1px solid #CCC;

        border-bottom-color: #B7B7B7;
        color: #fff;
        font-weight: 600;
        background: #34495e;

        outline: none;
    }

    #myTab li.active a {
        color: #000000;
        background-color: #fff;
        border-bottom-width: 3px;

    }

    #dropdown-toggle.dropdown-menu.active a {
        color: #000000;
        background-color: #fff;
        border-bottom-width: 3px;
        border-bottom-color: darkblue;
    }

    #myTab li a:hover {
        cursor: pointer;
        color: #000;
        background-color: #dcdcdc;

    }

    <%--  wrapper style--%>
    .chartwrapper {
        overflow: hidden;
    }

    .chartwrapper div {
        max-height: 200px;
    }

    .piechart {

        float: left;
        margin-right: 4%;
        width: 48%;
    }

    .barchart {

        overflow: hidden;
        margin: 0px;
        max-height: 200px;
        width: 48%;
    }

    @media screen and (max-width: 400px) {
        .piechart {
            float: none;
            margin-right: 1px;
            width: auto;
            border: 0;
            border-bottom: 1px solid #000;
        }

        .barchart {
            width: auto;
        }
    }

    .clear {
        clear:both;
    }


</style>

<%--start here--%>

<div class="row">
    <div class="col-sm-12">
        <h1 class="page-header">Task Strategy Report</h1>
    </div>
</div>
<div class="row">
<div class="col-sm-12">
<div class="panel panel-default">
<div class="panel-heading">
    Metrics
</div>
<!-- /.panel-heading -->
<div class="panel-body">

<div id="metrics">

<ul id="myTab" class="nav nav-tabs">
    <li class="dropdown">
        <a href="#" id="myTabDrop1" class="dropdown-toggle"
           data-toggle="dropdown">
            Productivity Stats <b class="caret"></b></a>
        <ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop1">
            <li class="active"><a href="#today" tabindex="-1" data-toggle="tab">Today</a></li>
            <li><a href="#week" tabindex="-1" data-toggle="tab">This Week</a></li>
            <li><a href="#month" tabindex="-1" data-toggle="tab">This Month</a></li>
        </ul>
    </li>
    <li><a href="#recommendation" data-toggle="tab">
        Recommendations</a></li>


</ul>
<br>

<div id="myTabContent" class="tab-content">

<div class="tab-pane fade in active" id="today">

    <div class="panel panel-default">
        <div class="panel-heading"><span><img src="/img/glyphicons_400_radar.png"
                                              style="float:left; background-color:floralwhite;"/>
            </span>&nbsp;&nbsp;How am I doing Today?
        </div>
        <script type="text/javascript" src="//www.google.com/jsapi"></script>
        <script type="text/javascript">
            google.load('visualization', '1', {packages: ['corechart']});
        </script>
        <script type="text/javascript">
            function drawVisualization() {
                // Create and populate the data table.
                var data = google.visualization.arrayToDataTable([
                    ['Tasks', 'Num'],
                    ['Open', ${Due_Today}],
                    ['Completed', ${Completed_Today}],
                    ['Completed Early', ${Completed_Early_Today}],
                    ['Past Due', ${Past_Due_Today}]

                ]);
                // Create and draw the visualization.
                new google.visualization.PieChart(document.getElementById('piechart')).
                        draw(data, {title: "Tasks Distribution"});
            }

            google.setOnLoadCallback(drawVisualization);
        </script>
        <script type="text/javascript">
            function drawVisualization() {
                // Create and populate the data table.
                var data1 = google.visualization.arrayToDataTable([
                    ['Priority', 'Number of Tasks' ],
                    ['Critical', ${Critical_Priority_Today}],
                    ['High', ${High_Priority_Today}],
                    ['Medium', ${Med_Priority_Today}],
                    ['Low', ${Low_Priority_Today}]

                ]);

                var options = {
                    title: "Tasks per Priority",
                    vAxis: {title: "Priority"},
                    hAxis: {title: "Number of Tasks"},
                    legend: "none",
                    chartArea: {
                        backgroundColor: {
                            stroke: '#000000',
                            strokeWidth: 1
                        }
                    }
                };


                // Create and draw the visualization.
                new google.visualization.BarChart(document.getElementById('barchart')).
                        draw(data1, options);
            }


            google.setOnLoadCallback(drawVisualization);
        </script>


        <div style="width: 100%; margin-top: 15px; font-weight: bold; text-align: center;">Today's Productivity Score
            !
        </div>

        <div style="height:80px">
            <div id="lineargauge"></div>

            <script type="text/javascript">

                function getScoreToday() {

                    var score = 0;
                    if (${Completed_Today} == 0 &&
                    ${Completed_Early_Today} ==
                    0
                )
                    {
                        score = 0;

                    }
                else
                    if (${Completed_Today} == 0 &&
                    ${Completed_Early_Today} !=
                    0
                )
                    {
                        if (${Due_Today} == 0 &&
                        ${Past_Due_Today} ==
                        0
                    )
                        {
                            score = 100;

                        }
                    else
                        {
                            score = 100 * (${Completed_Today}/(${Completed_Today} + ${Due_Today} + ${Past_Due_Today})) + 5;

                        }
                    }
                else
                    {
                        score = 100 * (${Completed_Today}/(${Completed_Today} + ${Due_Today} + ${Past_Due_Today}));

                    }
                    score = parseFloat(score).toFixed(2);
                    return score;
                }

                $(function () {


                    $("#lineargauge").igLinearGauge({
                        showToolTip: true,
                        needleToolTipTemplate: "needleToolTip",
                        width: '100%',
                        height: '80px',
                        transitionDuration: 500,
                        ranges: [
                            {
                                brush: 'red',
                                name: 'bad',
                                startValue: 0,
                                endValue: 50
                            },
                            {
                                brush: 'gold',
                                name: 'acceptable',
                                startValue: 50,
                                endValue: 70
                            },
                            {
                                brush: 'green',
                                name: 'good',
                                startValue: 70,
                                endValue: 100
                            }
                        ],
                        minimumValue: 0,
                        maximumValue: 100,
                        value: getScoreToday(),
                        needleShape: 'custom',
                        needleBrush: '#black',
                        needleOutline: '#11364D',
                        needleBreadth: 20,
                        needleInnerExtent: 0.3,
                        needleOuterExtent: 0.7,
                        needleOuterPointExtent: 0.9,
                        needleInnerPointExtent: 0.3,
                        needleInnerPointWidth: 0,
                        needleOuterPointWidth: 0.3,
                        needleInnerBaseWidth: 0,
                        needleOuterBaseWidth: 0.07,
                        formatLabel: function (evt, ui) {
                            ui.label = ui.value;
                        }
                    });
                });
            </script>

        </div>
        <br>

        <div class="panel-heading">
            <span><img src="/img/glyphicons_040_stats.png" style="float:left; background-color:floralwhite;"/></span>&nbsp;&nbsp;
            Tasks Stats
        </div>

        <table class="report-table">

            <thead>
            <tr>
                <td style="text-align: center">Description</td>
                <td style="text-align: center">Open Today</td>
                <td style="text-align: center">Completed</td>
                <td style="text-align: center">Completed Early</td>
                <td style="text-align: center">Past Due</td>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td style="text-align: center">Value</td>
                <td style="text-align: center">${Due_Today}</td>
                <td style="text-align: center">${Completed_Today}</td>
                <td style="text-align: center">${Completed_Early_Today}</td>
                <td style="text-align: center">${Past_Due_Today}</td>

            </tr>
            </tbody>
        </table>


        <br>

        <div class="panel-heading">
            <span></span><img src="/img/glyphicons_042_pie_chart.png"
                              style="float:left; background-color:floralwhite;"/></span>&nbsp;&nbsp; Charts

        </div>
        <br>

        <div class="chartwrapper">
            <div id="piechart" class="piechart"></div>
            <div id="barchart" class="barchart"></div>
            <div class="clear"></div>
        </div>
        <br>
        <!-- charts -->
    </div>

</div>

<!----week-->
<div class="tab-pane fade" id="week">
<div class="panel panel-default">
    <div class="panel-heading"><span><img src="/img/glyphicons_400_radar.png"
                                          style="float:left; background-color:floralwhite;"/>
            </span>&nbsp;&nbsp;How am I doing this Week?
    </div>
    <%--<script type="text/javascript" src="//www.google.com/jsapi"></script>--%>
    <script type="text/javascript">
        function drawVisualization() {
            // Create and populate the data table.
            var dataw = google.visualization.arrayToDataTable([
                ['Tasks', 'Num'],
                ['Open', ${Due_This_Week}],
                ['Completed', ${Completed_This_Week}],
                ['Completed Early', ${Completed_Early_This_Week}],
                ['Past Due', ${Past_Due_This_Week}]

            ]);

            // Create and draw the visualization.
            new google.visualization.PieChart(document.getElementById('piechartw')).
                    draw(dataw, {title: "Tasks Distribution"});
        }


        google.setOnLoadCallback(drawVisualization);
    </script>
    <script type="text/javascript">
        function drawVisualization() {
            // Create and populate the data table.
            var dataw1 = google.visualization.arrayToDataTable([
                ['Priority', 'Number of Tasks' ],
                ['Critical', ${Critical_Priority_This_Week}],
                ['High', ${High_Priority_This_Week}],
                ['Medium', ${Med_Priority_This_Week}],
                ['Low', ${Low_Priority_This_Week}]

            ]);
            var options = {
                title: "Tasks per Priority",
                vAxis: {title: "Priority"},
                hAxis: {title: "Number of Tasks"},
                legend: "none",
                chartArea: {
                    backgroundColor: {
                        stroke: '#000000',
                        strokeWidth: 1
                    }
                }
            };


            // Create and draw the visualization.
            new google.visualization.BarChart(document.getElementById('barchartw')).
                    draw(dataw1, options);
        }


        google.setOnLoadCallback(drawVisualization);
    </script>

    <div style="width: 100%; margin-top: 20px; font-weight: bold; text-align: center;">This week's Productivity Score
        !
    </div>

    <div style="height:100px">
        <div id="lineargaugew"></div>
        <script>
            function getScoreWeek() {
                var score = 0;
                if (${Completed_This_Week} == 0 &&
                ${Completed_Early_This_Week} ==
                0
            )
                {
                    score = 0;

                }
            else
                if (${Completed_This_Week} == 0 &&
                ${Completed_Early_This_Week} !=
                0
            )
                {
                    if (${Due_This_Week} == 0 &&
                    ${Past_Due_This_Week} ==
                    0
                )
                    {
                        score = 100;

                    }
                else
                    {
                        score = 100 * (${Completed_This_Week}/(${Completed_This_Week} + ${Due_This_Week} + ${Past_Due_This_Week})) + 5;

                    }
                }
            else
                {
                    score = 100 * (${Completed_This_Week}/(${Completed_This_Week}+ ${Due_This_Week} + ${Past_Due_This_Week}));

                }
                score = parseFloat(score).toFixed(2);
                return score;
            }

            $(function () {

                $("#lineargaugew").igLinearGauge({
                    showToolTip: true,
                    needleToolTipTemplate: "needleToolTipw",
                    width: '100%',
                    height: '80px',
                    transitionDuration: 500,
                    ranges: [
                        {
                            brush: 'red',
                            name: 'bad',
                            startValue: 0,
                            endValue: 50
                        },
                        {
                            brush: 'gold',
                            name: 'acceptable',
                            startValue: 50,
                            endValue: 70
                        },
                        {
                            brush: 'green',
                            name: 'good',
                            startValue: 70,
                            endValue: 100
                        }
                    ],
                    minimumValue: 0,
                    maximumValue: 100,
                    value: getScoreWeek(),
                    needleShape: 'custom',
                    needleBrush: '#black',
                    needleOutline: '#11364D',
                    needleBreadth: 20,
                    needleInnerExtent: 0.3,
                    needleOuterExtent: 0.7,
                    needleOuterPointExtent: 0.9,
                    needleInnerPointExtent: 0.3,
                    needleInnerPointWidth: 0,
                    needleOuterPointWidth: 0.3,
                    needleInnerBaseWidth: 0,
                    needleOuterBaseWidth: 0.07,
                    formatLabel: function (evt, ui) {
                        ui.label = ui.value;
                    }
                });
            });
        </script>

    </div>
    <div class="panel-heading">
        <span><img src="/img/glyphicons_040_stats.png" style="float:left; background-color:floralwhite;"/></span>&nbsp;&nbsp;
        Tasks Stats
    </div>
    <table class="report-table">

        <thead>
        <tr>
            <td style="text-align: center">Description</td>
            <td style="text-align: center">Open This Week</td>
            <td style="text-align: center">Completed</td>
            <td style="text-align: center">Completed Early</td>
            <td style="text-align: center">Past Due</td>

        </tr>
        </thead>
        <tbody>
        <tr>
            <td style="text-align: center">Value</td>
            <td style="text-align: center">${Due_This_Week}</td>
            <td style="text-align: center">${Completed_This_Week}</td>
            <td style="text-align: center">${Completed_Early_This_Week}</td>
            <td style="text-align: center">${Past_Due_This_Week}</td>

        </tr>
        </tbody>
    </table>
    <br>

    <div class="panel-heading">
                    <span><img src="/img/glyphicons_042_pie_chart.png"
                               style="float:left; background-color:floralwhite;"/></span>&nbsp;&nbsp; Charts
    </div>
    <br>

    <div class="chartwrapper">
        <div id="piechartw" class="piechart"></div>
        <div id="barchartw" class="barchart"></div>
        <div class="clear"></div>
    </div>

</div>
</div>

<!-- month -->

<div class="tab-pane " id="month">
<div class="panel panel-default">
<div class="panel-heading"><span><img src="/img/glyphicons_400_radar.png"
                                      style="float:left; background-color:floralwhite;"/>
    </span>&nbsp;&nbsp;How am I doing this Month?
</div>

<script type="text/javascript">
    function drawVisualization() {
        // Create and populate the data table.
        var datam = google.visualization.arrayToDataTable([
            ['Tasks', 'Num'],
            ['Open', ${Due_This_Month}],
            ['Completed', ${Completed_This_Month}],
            ['Completed Early', ${Completed_Early_This_Month}],
            ['Past Due', ${Past_Due_This_Month}]

        ]);

        // Create and draw the visualization.
        new google.visualization.PieChart(document.getElementById('piechartm')).
                draw(datam, {title: "Tasks Distribution"});
    }


    google.setOnLoadCallback(drawVisualization);
</script>
<script type="text/javascript">
    function drawVisualization() {
        // Create and populate the data table.
        var datam1 = google.visualization.arrayToDataTable([
            ['Priority', 'Number of Tasks' ],
            ['Critical', ${Critical_Priority_This_Month}],
            ['High', ${High_Priority_This_Month}],
            ['Medium', ${Med_Priority_This_Month}],
            ['Low', ${Low_Priority_This_Month}]

        ]);

        var options = {
            title: "Tasks per Priority",
            vAxis: {title: "Priority"},
            hAxis: {title: "Number of Tasks"},
            legend: "none",
            chartArea: {
                backgroundColor: {
                    stroke: '#000000',
                    strokeWidth: 1
                }
            }
        };


        // Create and draw the visualization.
        new google.visualization.BarChart(document.getElementById('barchartm')).
                draw(datam1,options);
    }


    google.setOnLoadCallback(drawVisualization);
</script>

<div style="width: 100%; margin-top: 20px; font-weight: bold; text-align: center;">This Month's Productivity Score !
</div>

<div style="height:100px">
    <div id="lineargaugem"></div>
    <script>
        function getScoreMonth() {
            var score = 0;
            if (${Completed_This_Month} == 0 &&
            ${Completed_Early_This_Month} ==
            0
        )
            {
                score = 0;

            }
        else
            if (${Completed_This_Month} == 0 &&
            ${Completed_Early_This_Month} !=
            0
        )
            {
                if (${Due_Today} == 0 &&
                ${Past_Due_This_Month} ==
                0
            )
                {
                    score = 100;

                }
            else
                {
                    score = 100 * (${Completed_This_Month}/(${Completed_This_Month} + ${Due_This_Month} + ${Past_Due_This_Month})) + 5;

                }
            }
        else
            {
                score = 100 * (${Completed_This_Month}/(${Completed_This_Month} + ${Due_This_Month} + ${Past_Due_This_Month}));

            }
            score = parseFloat(score).toFixed(2);
            return score;
        }

        $(function () {

            $("#lineargaugem").igLinearGauge({
                showToolTip: true,
                needleToolTipTemplate: "needleToolTipm",
                width: '100%',
                height: '80px',
                transitionDuration: 500,
                ranges: [
                    {
                        brush: 'red',
                        name: 'bad',
                        startValue: 0,
                        endValue: 50
                    },
                    {
                        brush: 'gold',
                        name: 'acceptable',
                        startValue: 50,
                        endValue: 70
                    },
                    {
                        brush: 'green',
                        name: 'good',
                        startValue: 70,
                        endValue: 100
                    }
                ],
                minimumValue: 0,
                maximumValue: 100,
                value: getScoreMonth(),
                needleShape: 'custom',
                needleBrush: '#black',
                needleOutline: '#11364D',
                needleBreadth: 20,
                needleInnerExtent: 0.3,
                needleOuterExtent: 0.7,
                needleOuterPointExtent: 0.9,
                needleInnerPointExtent: 0.3,
                needleInnerPointWidth: 0,
                needleOuterPointWidth: 0.3,
                needleInnerBaseWidth: 0,
                needleOuterBaseWidth: 0.07,
                formatLabel: function (evt, ui) {
                    ui.label = ui.value;
                }
            });
        });

    </script>
    <script>

        $(function setRecommendations() {

            var recscore = getScoreMonth();
            var myRecom = new String();

            switch (true) {


                case(0 <= recscore && recscore <= 50):

                    myRecom += '<br/>' +
                            "After revewing the previous month results we recommend the following to enhance your score." +
                            '<br/>' + '<br/>' +

                            "acquire time management skills and follow these tips." + '<br/>' + '<br/>' +

                            "1) Prepare the next day tasks the night before." + '<br/>' +

                            "2) Schedule your tasks with accuracy to avoid a lot of past due tasks." + '<br/>' +

                            "3) Set you priorities with precision. " + '<br/>' +

                            "4) Start your day early.Take time to sit, think, and plan. " + '<br/>' +

                            "5) Take advantage of your internal prime time, where you are the most alert and productive. " + '</br>' +

                            "6) Use the Action mode feature to breakdown big tasks to sets of subtasks."
                    document.getElementById('content').innerHTML = myRecom;
                    break;
                case (50 < recscore && recscore <= 70):
                    myRecom += '<br/>' +
                            '<br/>' + "After revewing your results for the previous month we recommend the following." +
                            '<br/>' + '<br/>' +

                            "focus on these areas and follow these tips." + '<br/>' + '<br/>' +

                            "1) Check your tasks regularly and update as needed." + '<br/>' +
                            "2) Accurately prioritize your tasks. " + '<br/>' +
                            "3) Estimate your completion date with precesion. " + '<br/>' +
                            "4) Prepare the next day tasks the night before." + '<br/>' +
                            "5) Take advantage of your internal prime time, where you are the most alert and productive. "

                    document.getElementById('content').innerHTML = myRecom;
                    break;
                case (70 < recscore && recscore <= 100):
                    myRecom += '<br/>' +
                            '<br/>' + "After revewing your results for the previous month we recommend the following." +
                            '<br/>' + '<br/>' +

                            "maintain and improve your productive habits." + '<br/>' + '<br/>' +

                            "1) Set some exciting goals." + '<br/>' +

                            "2) Reward yourself for finishing a big task." + '<br/>' +

                            "3) Get rid of time wasters. This includes Instant Messenger... " + '<br/>' +

                            "4) Prioritize and review the priority of your tasks ahead of time. " + '<br/>' +

                            "5) Find a mentor. learn from those who have already achieved success, you will save yourself a lot of time and energy. "

                    document.getElementById('content').innerHTML = myRecom;
                    break;

            }
        });

    </script>

</div>
<div class="panel-heading">
    <span><img src="/img/glyphicons_040_stats.png" style="float:left;
    background-color:floralwhite;"/></span>&nbsp;&nbsp; Tasks Stats
</div>
<table class="report-table">
    <thead>
    <tr>
        <td style="text-align: center">Description</td>
        <td style="text-align: center">Open This Month</td>
        <td style="text-align: center">Completed</td>
        <td style="text-align: center">Completed early</td>
        <td style="text-align: center">Past Due</td>

    </tr>
    </thead>
    <tbody>
    <tr>
        <td style="text-align: center">Value</td>
        <td style="text-align: center">${Due_This_Month}</td>
        <td style="text-align: center">${Completed_This_Month}</td>
        <td style="text-align: center">${Completed_Early_This_Month}</td>
        <td style="text-align: center">${Past_Due_This_Month}</td>

    </tr>
    </tbody>
</table>
<br>

<div class="panel-heading">
    <span><img src="/img/glyphicons_042_pie_chart.png"
               style="float:left; background-color:floralwhite;"/></span>&nbsp;&nbsp; Charts
</div>
<br>

<div class="chartwrapper">
    <div id="piechartm" class="piechart"></div>
    <div id="barchartm" class="barchart"></div>
    <div class="clear"></div>
</div>
</div>
</div>

<div class="tab-pane fade" id="recommendation">
    <div class="panel panel-default">
        <div class="panel-heading">
            <span class="glyphicon glyphicon-arrow-up"></span>&nbsp;&nbsp;Boost Your Productivity!
        </div>
        <div id="content" style="padding-left:10px; margin-left: 10px;">

        </div>

        <br>
    </div>
</div>

</div>
</div>
</div>
</div>
</div>
<!-- script for gauge tootltip was put here because was causing issue with charts -->
<script id="needleToolTip" type="text/x-jquery-tmpl">
    <div class='ui-lineargauge-needle-tooltip'>
       <span id="prod_score">
         <script>
             document.getElementById("prod_score").innerHTML = "Score: " + getScoreToday();
         </script>
           </span>
           </div>
</script>
<script id="needleToolTipw" type="text/x-jquery-tmpl">
    <div class='ui-lineargauge-needle-tooltip'>
        <span id="prodw_score">
         <script>
             document.getElementById("prodw_score").innerHTML = "Score: " + getScoreWeek();
         </script>
            </span>
    </div>
</script>

<script id="needleToolTipm" type="text/x-jquery-tmpl">
    <div class='ui-lineargauge-needle-tooltip'>
         <span id="prodm_score">
         <script>
             document.getElementById("prodm_score").innerHTML = "Score: " + getScoreMonth();
         </script>
         </span>
         </div>
</script>
</div>





