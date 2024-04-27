function submitBooking() {
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;

    localStorage.setItem("name", name);
    localStorage.setItem("email", email);

    //Fan måste ju skicka inställningarna också
    let startDate = localStorage.getItem("startDate");
    let endDate = localStorage.getItem("endDate");

    //Hämta chosenRooms från localstorage
    let chosenRooms = JSON.parse(localStorage.getItem("chosenRooms")) || [];

    //Vet inte om detta är så bra idk
    let bookingData = {
        name: name,
        email: email,
        startDate: startDate,
        endDate: endDate,
        chosenRooms: chosenRooms
    };

    //Stoppa i body i anrop  @PostMapping("/submitBooking")
    let xhr = new XMLHttpRequest();
    let url = "/submitBookingCustomer";

    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");

    let data = JSON.stringify(bookingData);
    console.log("data = " + data);
    xhr.send(data);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
        }
    }
}