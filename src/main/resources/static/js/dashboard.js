// BASE API URL for backend
const API = "/api";

// Load JWT from localStorage
const token = localStorage.getItem("token");

// Automatically attach JWT to requests
const authHeader = {
    "Authorization": "Bearer " + token,
    "Content-Type": "application/json"
};

function loadAccounts() {
    fetch(API + "/accounts", { headers: authHeader })
        .then(res => res.json())
        .then(data => {
            let html = "<h2>Your Accounts</h2>";

            data.forEach(acc => {
                html += `
                    <div class="card">
                        <h3>${acc.accountType}</h3>
                        <p>Balance: $${acc.balance}</p>
                        <p>Account No: ${acc.accountNumber}</p>
                    </div>
                `;
            });

            document.getElementById("content-area").innerHTML = html;
        });
}

function loadTransactions() {
    fetch(API + "/transactions/all", { headers: authHeader })
        .then(res => res.json())
        .then(data => {
            let html = "<h2>Transactions</h2>";

            data.forEach(t => {
                html += `
                    <div class="card">
                        <p><strong>${t.type}</strong> - $${t.amount}</p>
                        <p>${t.timestamp}</p>
                    </div>
                `;
            });

            document.getElementById("content-area").innerHTML = html;
        });
}

function loadLoans() {
    fetch(API + "/loans", { headers: authHeader })
        .then(res => res.json())
        .then(data => {
            let html = "<h2>Your Loans</h2>";

            data.forEach(l => {
                html += `
                    <div class="card">
                        <p>Amount: $${l.amount}</p>
                        <p>Status: ${l.status}</p>
                    </div>
                `;
            });

            document.getElementById("content-area").innerHTML = html;
        });
}

function loadCards() {
    fetch(API + "/cards", { headers: authHeader })
        .then(res => res.json())
        .then(data => {
            let html = "<h2>Credit Cards</h2>";

            data.forEach(c => {
                html += `
                    <div class="card">
                        <p>Card Number: **** **** **** ${c.last4}</p>
                        <p>Limit: $${c.limit}</p>
                        <p>Status: ${c.status}</p>
                    </div>
                `;
            });

            document.getElementById("content-area").innerHTML = html;
        });
}

function loadBills() {
    fetch(API + "/bills", { headers: authHeader })
        .then(res => res.json())
        .then(data => {
            let html = "<h2>Bills</h2>";

            data.forEach(b => {
                html += `
                    <div class="card">
                        <p>${b.name}</p>
                        <p>Amount: $${b.amount}</p>
                        <p>Due: ${b.dueDate}</p>
                    </div>
                `;
            });

            document.getElementById("content-area").innerHTML = html;
        });
}

// Logout
function logout() {
    localStorage.removeItem("token");
    window.location.href = "/login";
}
