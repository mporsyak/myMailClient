function addNewUserWithAjax() {
    let body = {};
    body.login = $('#newLogin').val();
    body.password = $('#newPassword').val();

    $.ajax({
        method: 'POST',
        contentType: 'application/json;charset=UTF-8',
        url: 'addUser',
        data: JSON.stringify(body),
        success: function (response) {
            // document.getElementById("info").innerHTML = response;
            $('#info').text(response);
        },
        error: function (response) {
            // document.getElementById("info").innerHTML = response.responseText;
            $('#info').text(response.responseText);
        }
    });
}

/*
function addNewUser() {
    let body = {};
    // body.newLogin = document.getElementById("newLogin").value;
    // body.newPassword = document.getElementById("newPassword").value;
    body.newLogin = $('#newLogin').val();
    body.newPassword = $('#newPassword').val();

    let response = post("/addUser",JSON.stringify(body));
    // document.getElementById("info").innerHTML = response.responseText;
    $('#info').text(response.responseText);
}*/
