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

document.addEventListener("DOMContentLoaded", function () {
    const orderLines = JSON.parse(localStorage.getItem("chosenRooms")) || [];

    const tbody = document.querySelector(".show-rooms-confirmation-page");

    orderLines.forEach(function(orderLine) {
        const row = document.createElement("tr");
        row.classList.add("room-row-confirmation-page");

        const roomIdCell = document.createElement("td");
        roomIdCell.textContent = orderLine.id;
        row.appendChild(roomIdCell);

        const roomTypeCell = document.createElement("td");
        roomTypeCell.textContent = orderLine.roomType;
        row.appendChild(roomTypeCell);

        const bedsCell = document.createElement("td");
        bedsCell.textContent = orderLine.extraBeds;
        row.appendChild(bedsCell);

        tbody.appendChild(row);

        localStorage.clear();
    });
});


