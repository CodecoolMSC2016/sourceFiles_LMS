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
                    if (data[i].maxScore == undefined)
                    {
                        anchorClass = "button textpage";
                        type="Text page";
                    }
                    else {
                        anchorClass = "button assignment";
                        type = "Assignment";
                        scoreSpan = "<div title=\"" + data[i].id + "\" class=\"meta maxscore\">" + data[i].maxScore + "</div>";

                    }
                    var htmlString =
                        "<a title=\"" + data[i].id + "\" class=\"" + anchorClass+ "\">" +
                        "<span class = \"title\">" + title + "</span>" +
                        "<span class = \"meta type\">" + type + "</span>" + scoreSpan;
                    if ($("#role").text() == "mentor"){
                        var buttonTag = "<button class = \"btn btn-default btn-xs publishbutton\" title=\"" + data[i].id + "\">";
                        if (!data[i].published){
                            htmlString += buttonTag + "Publish</button></a>";
                        }else {
                            htmlString += buttonTag + "Unpublish</button></a>";
                        }
                    }

                    pageContets[data[i].id] = data[i].text;
                    $("#sortable").append(htmlString);

                }
            }
        }).then(addButtonEvents).then(makeSortable).then(addTextPageLinks).then(addAssingmentPageLinks);
    }
    function addButtonEvents(){
        $("button").click(function(){

           $.ajax({
               url: "CurriculumServlet",
               type: "POST",
               data: {"id": $(this).attr("title"), "doPublish": "true"}
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
                        $("#sortable a").each(function(){
                            $.ajax({
                                url: "CurriculumServlet",
                                type: "POST",
                                data: {"id": $(this).attr("title"), "index": $(this).index(), "doPublish": "false"}
                            })
                        });
                    }
                });
                $( "#sortable" ).disableSelection();
            } );
        }
    }

    function addTextPageLinks(){
        $("#sortable a .button.textpage").click(function(){
           $.ajax({
               url : "./load-text-page",
               type: "GET",
               data: {"id": $(this).attr("title"), "role": $("#role").text()},
               success: function(data) {
                   $("#sortable").empty();
                   $("#content").append(data["text"]);
                   var link = $("#redirect");
                   link.attr("href", "./curriculum.jsp");
                   link.text("Back to Curriculum Page");
               }
           });
        });
    }

    function addAssingmentPageLinks(){
        $("#sortable a .button.assignment").click(function(){
            $.ajax({
                url : "./load-assignment-page",
                type: "GET",
                data: {"id": $(this).attr("title"), "role": $("#role").text()},
                success: function(data){
                    $("#sortable").empty();
                    $("#content").append(data["text"]);
                    var link = $("#redirect");
                    link.attr("href", "./curriculum.jsp");
                    link.text("Back to Curriculum Page");
                }
            });
        });
    }
    loadPage();
});