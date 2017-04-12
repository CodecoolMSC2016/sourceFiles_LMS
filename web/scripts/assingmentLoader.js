/**
 * Created by imre_meszesan on 11.04.17.
 */
$(document).ready(function(){
    var pageContets = {};
    function loadPage(){
        $("#sortable").empty();
        $.ajax({
            url: "/CurriculumServlet",
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
                        scoreSpan = "<span class=\"meta maxscore\">" + data[i].maxScore + "</span>";

                    }

                    var htmlString =
                        "<a title=\"" + data[i].id + "\" class=\"" + anchorClass+ "\">" +
                        "<span class = \"title\">" + title + "</span>" +
                        "<span class = \"meta type\">" + type + "</span>" + scoreSpan;

                    if ($("#role").text() == "mentor"){

                        if (!data[i].published){
                            htmlString += "<button style = \"float: right;\" class = \"btn btn-default btn-xs\" title=\"" + data[i].id + "\">Publish</button></a>";
                        }else {
                            htmlString += "<button style = \"float: right;\" class = \"btn btn-default btn-xs\" title=\"" + data[i].id + "\">Unpublish</button></a>";
                        }
                    }else {
                        htmlString += "</a>";
                    }
                    pageContets[data[i].id] = data[i].text;
                    var maxScore = data[i].maxScore;
                    if (maxScore != undefined){
                        htmlString += maxScore;
                    }
                    $("#sortable").append(htmlString);

                }
            }
        }).then(addButtonEvents).then(makeSortable);
    }
    function addButtonEvents(){
        $("button").click(function(){

           $.ajax({
               url: "/CurriculumServlet",
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
                                url: "/CurriculumServlet",
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
    loadPage();
});