function deleteLoginInfo(isLogout) {
    if (isLogout == "true") {
        localStorage.removeItem("auth");
    }
}