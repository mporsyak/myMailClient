function showIncome() {
    showModal("client/showIncomeMessages");
}

function showOutcome() {
    showModal("client/showOutcomeMessages");
}

function showModal(url){
    $.ajax({
        method: 'GET',
        contentType: 'application/json;charset=UTF-8',
        url: url,
        data: null,
        success: function (response) {
            getMessages(JSON.parse(response));
            $('#exampleModal').modal();
        }
    });
}

function getMessages(messages){
    var myTBody = document.getElementById("myTBody");
    myTBody.innerHTML = "";

    for (i = 0; i < messages.length; i++){
        var tr = document.createElement("tr");
        myTBody.appendChild(tr);

        var tdContent = document.createElement("td");
        tdContent.innerHTML = "<p class='text-break'>" + messages[i].content + "</p>";
        tr.appendChild(tdContent);

        var tdGoal = document.createElement("td");
        tdGoal.innerHTML = messages[i].goal;
        tr.appendChild(tdGoal);
    }
}