document.addEventListener('DOMContentLoaded',(event)=>{LoadUser();});
function LoadUser(){
    fetch("/auth/me")
        .then(response => response.json())
        .then(data => {
            document.getElementById("username").innerText = data.username;
        })
        .catch(() => {
            document.getElementById("username").innerText = "Error loading user";
        });
}

// Logout function
function logout() {
    fetch("/logout", {
        method: "POST"
    }).then(() => {
        window.location.href = "/login.html";
    });
}