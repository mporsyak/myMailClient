function loginWithAjax() {
    let body = {};
    body.login = $('#newLogin').val();
    body.password = $('#newPassword').val();

    $.ajax({
        method: 'POST',
        contentType: 'application/json;charset=UTF-8',
        url: 'client/login',
        data: JSON.stringify(body),
        success: function (response) {
            // document.getElementById("info").innerHTML = response;
            localStorage.setItem("auth", response);
            window.location.href = "/all";
        },
        error: function (response) {
            // document.getElementById("info").innerHTML = response.responseText;
            $('#info').text("Неверные данные");
            localStorage.clear();
        }
    });
}