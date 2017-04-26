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
                        "<li name=\"" + data[i].id + "\" class=\"" + anchorClass+ "\"><form action=" + servletUrl + " method=\"GET\">" +
                        "<span class = \"title\">" + title + "</span></form>" +
                        "<span class = \"meta type\">" + type + "</span>" + scoreSpan;
                    if ($("#role").text() == "mentor"){
                        var buttonTag = "<button class = \"btn btn-default btn-xs publishbutton\" name=\"" + data[i].id + "\">";
                        if (!data[i].published){
                            htmlString += buttonTag + "Publish</button></li>";
                        }else {
                            htmlString += buttonTag + "Unpublish</button></li>";
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
                        $("#sortable li").each(function(){
                            $.ajax({
                                url: "CurriculumServlet",
                                type: "POST",
                                data: {"id": $(this).attr("name"), "index": $(this).index(), "doPublish": "false"}
                            })
                        });
                    }
                });
                $( "#sortable" ).disableSelection();
            } );
        }
    }

    function addTextPageLinks(){
        var titles = $("#sortable li form  .title");
        titles.click(function(){
            var inputData = $("<input>")
                .attr("type", "hidden")
                .attr("name", "id")
                .attr("value", $(this).parent().parent().attr("name"));
            alert($(this).parent().parent().attr("name"));
            var form = $(this).parent();
            form.append(inputData);
            form.submit();
        });
        titles.hover(function(){
            $(this).css('cursor','pointer');
        });
    }
    loadPage();
});