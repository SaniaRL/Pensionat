
document.addEventListener("DOMContentLoaded", function () {
    const name = localStorage.getItem("name");
    const email = localStorage.getItem("email");
    const customerP = document.getElementById("customer-name-email")

    customerP.textContent = name + ", " + email;

    const checkin = localStorage.getItem("startDate");
    const checkinP = document.getElementById("checkin-p");

    checkinP.textContent = "Incheckning: " + checkin;

    const checkout = localStorage.getItem("endDate");
    const checkoutP = document.getElementById("checkout-p");

    checkoutP.textContent = "Utcheckning: " + checkout;
    }
)
