function sendMessageWithAjax() {
    let body = {};
    body.content = document.getElementById("myContent").value;
    body.userRecip = document.getElementById("myRecipientLogin").value;

    var auth = localStorage.getItem("auth");
    if (auth == null)
        window.location.href = "/login";

    $.ajax({
        method: 'POST',
        contentType: 'application/json;charset=UTF-8',
        url: "client/sendMessage/" + auth,
        data: JSON.stringify(body),
        success: function (response) {
            // document.getElementById("info").innerHTML = response;
            $('#info').text(response);
        },
        error: function (response) {
            // document.getElementById("info").innerHTML = response.responseText;
            $('#info').text("Ошибка");
        }
    });
}

/*function sendMessage(){
    let body = {};
    // body.myContent = document.getElementById("myContent").value;
    // body.myRecipLogin = document.getElementById("myRecipientLogin").value;
    body.myContent = $('#myContent').val();
    body.myRecipLogin = $('#myRecipientLogin').val();
    //{"myContent":"1","myRecipLogin":"2"}

    let response = post("/sendMessage", JSON.stringify(body));
    $('#info').text(response.responseText);
}*/
