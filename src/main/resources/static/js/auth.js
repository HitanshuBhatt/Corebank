// -----------------------------
// GLOBAL PAGE INITIALIZATION
// -----------------------------
document.addEventListener("DOMContentLoaded", () => {

    setupLoginForm();
    setupRegisterForm();
    setupDashboard();
    setupLogout();

    // NEW — verify token on ALL protected pages
    enforceLoginProtection();
});

// -----------------------------
// JWT HELPER — GLOBAL fetch()
// -----------------------------
async function apiFetch(url, options = {}) {
    const token = localStorage.getItem("jwtToken");

    if (!token) {
        console.warn("No token found — redirecting to login");
        window.location.href = "/login";
        return;
    }

    if (!options.headers) options.headers = {};

    options.headers["Authorization"] = "Bearer " + token;

    return fetch(url, options);
}

// -----------------------------
// CHECK LOGIN ON PROTECTED PAGES
// -----------------------------
function enforceLoginProtection() {
    const protectedPages = ["/dashboard", "/accounts", "/transactions", "/loans", "/creditcards"];

    const current = window.location.pathname;

    if (protectedPages.includes(current)) {
        const token = localStorage.getItem("jwtToken");

        if (!token) {
            console.warn("Protected route accessed without JWT");
            window.location.href = "/login";
        }
    }
}

// -----------------------------
// LOGIN FORM HANDLER
// -----------------------------
function setupLoginForm() {
    const form = document.getElementById("loginForm");
    if (!form) return;

    const msg = document.getElementById("login-message");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        msg.textContent = "";
        msg.className = "message";

        const username = document.getElementById("login-username").value.trim();
        const password = document.getElementById("login-password").value.trim();

        if (!username || !password) {
            msg.textContent = "Please enter username and password.";
            msg.classList.add("error");
            return;
        }

        try {
            const res = await fetch("/api/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password }),
            });

            if (!res.ok) {
                msg.textContent = "Invalid username or password.";
                msg.classList.add("error");
                return;
            }

            const token = await res.text();

            localStorage.setItem("jwtToken", token);
            localStorage.setItem("loggedInUser", username);

            msg.textContent = "Login successful. Redirecting…";
            msg.classList.add("success");

            setTimeout(() => {
                window.location.href = "/dashboard";
            }, 500);

        } catch (err) {
            console.error(err);
            msg.textContent = "Something went wrong. Try again.";
            msg.classList.add("error");
        }
    });
}

// -----------------------------
// REGISTER FORM HANDLER
// -----------------------------
function setupRegisterForm() {
    const form = document.getElementById("registerForm");
    if (!form) return;

    const msg = document.getElementById("register-message");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        msg.textContent = "";
        msg.className = "message";

        const fullName = document.getElementById("reg-fullname").value.trim();
        const username = document.getElementById("reg-username").value.trim();
        const email = document.getElementById("reg-email").value.trim();
        const password = document.getElementById("reg-password").value.trim();

        if (!fullName || !username || !email || !password) {
            msg.textContent = "Please fill all fields.";
            msg.classList.add("error");
            return;
        }

        try {
            const res = await fetch("/api/auth/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ fullName, username, email, password }),
            });

            const text = await res.text();

            if (!res.ok) {
                msg.textContent = text || "Registration failed.";
                msg.classList.add("error");
                return;
            }

            msg.textContent = "Registration successful!";
            msg.classList.add("success");

            setTimeout(() => window.location.href = "/login", 700);

        } catch (err) {
            console.error(err);
            msg.textContent = "Something went wrong.";
            msg.classList.add("error");
        }
    });
}

// -----------------------------
// DASHBOARD USER DETAILS
// -----------------------------
function setupDashboard() {
    const usernameSpan = document.getElementById("dash-username");
    const tokenStatus = document.getElementById("token-status");

    if (!usernameSpan && !tokenStatus) return;

    const username = localStorage.getItem("loggedInUser");
    const token = localStorage.getItem("jwtToken");

    if (!token) {
        window.location.href = "/login";
        return;
    }

    if (usernameSpan) usernameSpan.textContent = username || "User";
    if (tokenStatus) tokenStatus.textContent = "JWT token loaded.";
}

// -----------------------------
// LOGOUT BUTTON
// -----------------------------
function setupLogout() {
    const logoutBtn = document.getElementById("logoutBtn");
    if (!logoutBtn) return;

    logoutBtn.addEventListener("click", () => {
        localStorage.removeItem("jwtToken");
        localStorage.removeItem("loggedInUser");
        window.location.href = "/login";
    });
}
