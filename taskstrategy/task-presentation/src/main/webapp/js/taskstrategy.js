$(document).ready(function () {
    // new windows for external links and pdfs
    $("a").each(function () {
        if (this.href.indexOf(".pdf") > 0 || (this.href.indexOf(document.location.hostname) < 0 && this.href.indexOf("javascript:") < 0))
            $(this).attr("target", "_blank");
    });

    // smooth scrolling for hash links
    $('a').click(function() {
        var elementClicked = $(this).attr("href");
        var destination = $(elementClicked).offset().top;
        $("html:not(:animated),body:not(:animated)").animate({ scrollTop: destination-15}, 500 );
        return false;
    });

    function formatDate(jsDate){
        return ((jsDate.getMonth()+1)<10?("0"+(jsDate.getMonth()+1)):(jsDate.getMonth()+1)) + "/" +
            (jsDate.getDate()<10?("0"+jsDate.getDate()):jsDate.getDate()) + "/" +
            jsDate.getFullYear();
    }

	// initialize datepicker with default date and disable past dates
    if ($('.datepicker').length > 0) {
        if($('.datepicker').val().length < 1) {
            var defaultDate = new Date();
            defaultDate.setDate(defaultDate.getDate() + 1);
            $('.datepicker').val(formatDate(defaultDate));
        }
    }

    var nowTemp = new Date();
    var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);

	$('.datepicker').datepicker({
        onRender: function(date) {
            return date.valueOf() <= now.valueOf() ? 'disabled' : '';
        }
    }).on('changeDate', function(ev) {
        $(".datepicker.dropdown-menu").hide();
	});
	
	// responsive style for taginput field
	$(".bootstrap-tagsinput").addClass($(".bootstrap-tagsinput").prev().attr("class"));

    // add tags when focus out
    $(".bootstrap-tagsinput input").focusout(function () {
        $("#taskTags").tagsinput('add', $(this).val());
        $(this).val(""); //Clear input field once tag is added
    });

    // tag filter enter-key press
    $("#filter-search").keypress(function (event) {
        if (event.keyCode == 13) {
            $('#filter-search-button').click();
            return false;
        }
    });

    //Stop homepage demo video on modal close
    $("#learn-more-modal").on("hidden.bs.modal", function () {
        var src = $("#learn-more-modal iframe").attr("src");
        $("#learn-more-modal iframe").attr("src", "");
        $("#learn-more-modal iframe").attr("src", src);
    });
});