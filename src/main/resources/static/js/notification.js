const bell = document.getElementById("bell-icon");
const panel = document.getElementById("notif-panel");
const list = document.getElementById("notif-list");
const badge = document.getElementById("notif-count");

function loadNotifications() {
    fetch("/api/notifications/unread-count")
        .then(r => r.json())
        .then(count => badge.textContent = count);

    fetch("/api/notifications")
        .then(r => r.json())
        .then(data => {
            list.innerHTML = "";
            data.forEach(n => {
                list.innerHTML += `
                    <div class="notification-item">
                        <strong>[${n.type}]</strong> ${n.message}<br>
                        <small>${n.createdAt.replace("T", " ")}</small>
                    </div>
                `;
            });
        });
}

bell.onclick = () => {
    panel.style.display = panel.style.display === "block" ? "none" : "block";

    fetch("/api/notifications/mark-read", { method: "POST" });
    badge.textContent = 0;
};

setInterval(loadNotifications, 4000);
loadNotifications();
