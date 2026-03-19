document.addEventListener("DOMContentLoaded", () => {

    const toasts = document.querySelectorAll(".toast-message");

    toasts.forEach(toast => {
        // Auto-remove element after fade-out
        setTimeout(() => {
            toast.remove();
        }, 4000);
    });

});
