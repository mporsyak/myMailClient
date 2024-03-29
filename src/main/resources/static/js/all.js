function loadUserMessages(user){
    var auth = localStorage.getItem("auth");
    if (auth == null)
        window.location.href = "/";

    $.ajax({
        method: 'GET',
        contentType: 'application/json;charset=UTF-8',
        url: "client/allMessages/" + user + "/" + auth,
        data: null,
        success: function (response) {
            buildMessageView(user, JSON.parse(response));
            openModal();
            document.getElementById("myRecipientLogin").value = user;
        }
    });
}

function buildMessageView(user, messages){
    var labelMsgFrom = document.getElementById("labelMsgFrom");
    labelMsgFrom.innerHTML = "Сообщения от пользователя: " + user;

    var msgContainer = document.getElementById("msgContainer");
    msgContainer.innerHTML = "";

    for (i = 0; i < messages.length; i++){
        var div = document.createElement("div");
        div.setAttribute("class", "row "  + (messages[i].myMsg ? "justify-content-end" : "justify-content-start"));

        var msgDiv = document.createElement("div");
        msgDiv.setAttribute("class", "col-12 d-flex flex-column " + (messages[i].myMsg ? "align-items-end" : "align-items-start"));
        msgDiv.setAttribute("style", "max-width: 70%");
        div.appendChild(msgDiv);

        var msgDivContainer = document.createElement("div");
        msgDivContainer.setAttribute("class", "card card-body mb-2 " + (messages[i].myMsg ? "bg-success" : "bg-primary"));
        msgDiv.appendChild(msgDivContainer);

        var msg = document.createElement("div");
        msg.innerHTML = "<p class='text-left text-break'>"  + messages[i].content + "</p>";
        msgDivContainer.appendChild(msg);

        var timeDiv = document.createElement("div");
        timeDiv.setAttribute("class", "msg-time text-right");
        timeDiv.innerHTML = messages[i].createMsgTime.substr(11, 5);
        msgDivContainer.appendChild(timeDiv);

        msgContainer.appendChild(div);
    }
}

function openModal() {
    $('#exampleModal').modal();
}

function loadUsersTable(){
    var auth = localStorage.getItem("auth");
    if (auth == null)
        window.location.href = "/";

    $.ajax({
        method: 'GET',
        contentType: 'application/json;charset=UTF-8',
        url: "client/allUsers/" + auth,
        data: null,
        success: function (response) {
            buildUserTable(JSON.parse(response));
        },
        error: function (response) {
            alert("Ошибка");
            window.location.href = "/";
        }
    });
}

function buildUserTable(users){
    var tBody = document.getElementById("allUsersTable");
    tBody.innerHTML = "";

    for (i = 0; i < users.length; i++){
        var tr = document.createElement("tr");
        tBody.appendChild(tr);

        var tdLogin = document.createElement("td");
        tdLogin.innerHTML = "<p onclick=\"loadUserMessages('" + users[i] + "');\">" + users[i] + "</p>";
        tr.appendChild(tdLogin);
    }
}

function sendMessageWithAjax() {
    let body = {};
    body.content = document.getElementById("myContent").value;
    body.userRecip = document.getElementById("myRecipientLogin").value;

    var auth = localStorage.getItem("auth");
    if (auth == null)
        window.location.href = "/";

    $.ajax({
        method: 'POST',
        contentType: 'application/json;charset=UTF-8',
        url: 'client/sendMessage/' + auth,
        data: JSON.stringify(body),
        success: function (response) {
            loadUserMessages(body.userRecip);
        },
        error: function (response) {
        }
    });
}