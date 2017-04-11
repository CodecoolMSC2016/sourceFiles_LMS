/**
 * Created by imre_meszesan on 11.04.17.
 */
$(document).ready(function(){
    function loadPage(){
        $("#postedContents").empty();
        $.ajax({
            url: "/CurriculumServlet",
            type: "GET",
            success: function(data){
                for (var i = 0; i < data.length; i++){
                    var title = data[i].title;
                    var htmlString = "<a class=\"list-group-item\">";

                    if ($("#role").text() == "mentor"){

                        if (!data[i].published){
                            htmlString += title + "<button title=\"" + data[i].id + "\">PUBLISH</button></a>";
                        }else {
                            htmlString += title + "<button title=\"" + data[i].id + "\">UNPUBLISH</button></a>";
                        }
                    }else {
                        htmlString += title + "</a>";
                    }
                    var maxScore = data[i].maxScore;
                    if (maxScore != undefined){
                        htmlString += maxScore;
                    }
                    $("#postedContents").append(htmlString);

                }
            }
        }).then(addButtonEvents);
    }
    function addButtonEvents(){
        $("button").click(function(){

           $.ajax({
               url: "/CurriculumServlet",
               type: "POST",
               data: {"id": $(this).attr("title")},
           });
            var button = $(this);
            console.log(1);
            console.log(button.text());
            console.log(2);
            console.log($(this).text());

            if (button.text() == "PUBLISH"){
                button.text("UNPUBLISH");
            }else{
                button.text("PUBLISH");
            }

        });
    }
    loadPage();
});