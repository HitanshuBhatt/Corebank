document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("loginForm");

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        try {
            const response = await fetch("/api/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password })
            });

            if (!response.ok) {
                alert("Invalid username or password");
                return;
            }

            const token = await response.text();

            // SAVE TOKEN UNDER ONE CONSISTENT KEY
            localStorage.setItem("token", token);

            console.log("TOKEN SAVED:", token);

            window.location.href = "/dashboard";

        } catch (err) {
            alert("Login error: " + err);
        }
    });
});
