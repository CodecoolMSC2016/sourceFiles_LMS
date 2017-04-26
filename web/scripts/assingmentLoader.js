/**
 * Created by imre_meszesan on 11.04.17.
 */
$(function(){
    var pageContets = {};
    function loadPage(){
        $("#sortable").empty();
        $.ajax({
            url: "CurriculumServlet",
            type: "GET",
            success: function(data){
                var anchorClass;
                var type;
                var scoreSpan;
                for (var i = 0; i < data.length; i++){
                    scoreSpan = "";
                    var title = data[i].title;
                    var servletUrl;
                    if (data[i].maxScore == undefined)
                    {
                        servletUrl = "/load-text-page";
                        anchorClass = "button textpage";
                        type="Text page";
                    }
                    else {
                        servletUrl = "/load-assignment-page";
                        anchorClass = "button assignment";
                        type = "Assignment";
                        scoreSpan = "<div title=\"" + data[i].id + "\" class=\"meta maxscore\">" + data[i].maxScore + "</div>";

                    }
                    var htmlString =
                        "<form action=" + servletUrl + " method=\"GET\"><li name=\"" + data[i].id + "\" class=\"" + anchorClass+ "\">" +
                        "<span class = \"title\">" + title + "</span>" +
                        "<span class = \"meta type\">" + type + "</span>" + scoreSpan;
                    if ($("#role").text() == "mentor"){
                        var buttonTag = "<button class = \"btn btn-default btn-xs publishbutton\" title=\"" + data[i].id + "\">";
                        if (!data[i].published){
                            htmlString += buttonTag + "Publish</button></li></form>";
                        }else {
                            htmlString += buttonTag + "Unpublish</button></li></form>";
                        }
                    }

                    pageContets[data[i].id] = data[i].text;
                    $("#sortable").append(htmlString);

                }
            }
        }).then(addButtonEvents).then(makeSortable).then(addTextPageLinks);
    }
    function addButtonEvents(){
        $("button").click(function(){

           $.ajax({
               url: "CurriculumServlet",
               type: "POST",
               data: {"id": $(this).attr("name"), "doPublish": "true"}
           });
            var button = $(this);
            console.log(button.text());
            console.log($(this).text());

            if (button.text() == "Publish"){
                button.text("Unpublish");
            }else{
                button.text("Publish");
            }

        });
    }
    function makeSortable(){
        if ($("#role").text() == "mentor"){
            $( function() {
                $( "#sortable" ).sortable({
                    stop: function() {
                        var order = [];
                        $("#sortable form li").each(function(){
                            $.ajax({
                                url: "CurriculumServlet",
                                type: "POST",
                                data: {"id": $(this).attr("name"), "index": $(this).parent().index(), "doPublish": "false"}
                            })
                        });
                    }
                });
                $( "#sortable" ).disableSelection();
            } );
        }
    }

    function addTextPageLinks(){
        var titles = $("#sortable form li .title");
        titles.click(function(){
            var inputData = $("<input>")
                .attr("type", "hidden")
                .attr("name", "id")
                .attr("value", $(this).parent().attr("name"));
            alert($(this).parent().attr("name"));
            var form = $(this).parent().parent();
            form.append(inputData);
            form.submit();
        });
        titles.hover(function(){
            $(this).css('cursor','pointer');
        });
    }
    loadPage();
});