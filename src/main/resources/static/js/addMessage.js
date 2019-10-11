function sendMessageWithAjax() {
    let body = {};
    body.myContent = document.getElementById("myContent").value;
    body.myRecipLogin = document.getElementById("myRecipientLogin").value;

    $.ajax({
        method: 'POST',
        contentType: 'application/json;charset=UTF-8',
        url: 'sendMessage',
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
