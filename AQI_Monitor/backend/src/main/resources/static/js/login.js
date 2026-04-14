function togglePassword()
{
    const password = document.getElementById("password");
    const icon = document.getElementById("toggleIcon");

    if(password.type === "password")
    {
        password.type = "text";
        icon.setAttribute("name","eye");
    }
    else
    {
        password.type = "password";
        icon.setAttribute("name","eye-off");
    }
}


