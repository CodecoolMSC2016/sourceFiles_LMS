/**
 * Created by trixi on 2017.04.24..
 */
$(document).ready(function() {
    $("#assignment").empty();
    $.ajax({
        url: "AssignmentHandler",
        type: "GET",
        success: function(data) {
            for(var i = 0; i < data.length; i++) {

            }
            $("#assignment").html(data.);
        }
    });
});