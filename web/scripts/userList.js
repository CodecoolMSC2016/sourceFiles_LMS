/**
 * Created by trixi on 2017.04.27..
 */
$(document).ready(function () {


    $("#sortable").empty();
    $.ajax({
        url: "UserListHandler",
        type: "GET",
        success: function (data) {
            var displayUsers;
            for (var i = 0; i < data.length; i++) {
                var role = data[i].role;
                var name = data[i].name;
                var email = data[i].email;
                if($("#role").text() == "mentor") {
                    displayUsers = "<div class=\"button " + role + "\">" +
                    "<span class=\"title\">" +
                    name + "</span>" +
                    "<span class=\"meta type\">" +
                    role +
                    "<span class=\"meta expiry\">" +
                    "   Email: " +
                    email +
                    "</span></div>";
                }
                else{
                    displayUsers = "<div class=\"button " + role + "\">" +
                    "<span class=\"title\">" +
                    name + "</span>" +
                    "<span class=\"meta type\">" +
                    role +
                    "</span></div>";
                }

                $("#sortable").append(displayUsers);
            }
        }

    });
});
