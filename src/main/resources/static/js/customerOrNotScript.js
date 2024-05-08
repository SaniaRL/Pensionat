function submitBooking() {
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;

    let xhr1 = new XMLHttpRequest();
    let url1 = "/customer/blacklisted/" + email;

    xhr1.open("GET", url1, true);
    xhr1.send();

    xhr1.onreadystatechange = function () {
        if (xhr1.readyState === 4 && xhr1.status === 200) {
            if (!xhr1.responseText.includes("bookingConfirmation")) {
                const parser = new DOMParser();
                const htmlDoc = parser.parseFromString(xhr1.responseText, 'text/html');
                document.open();
                document.write(htmlDoc.documentElement.outerHTML);
                document.close();
            } else {
                localStorage.setItem("name", name);
                localStorage.setItem("email", email);

                //Fan måste ju skicka inställningarna också
                let startDate = localStorage.getItem("startDate");
                let endDate = localStorage.getItem("endDate");

                //Hämta chosenRooms från localstorage
                let chosenRooms = JSON.parse(localStorage.getItem("chosenRooms")) || [];

                //Vet inte om detta är så bra idk
                let bookingData = {
                    id: -1,
                    name: name,
                    email: email,
                    startDate: startDate,
                    endDate: endDate,
                    chosenRooms: chosenRooms
                };

                //Stoppa i body i anrop  @PostMapping("/submitBooking")
                let xhr2 = new XMLHttpRequest();
                let url2 = "/submitBookingCustomer";

                xhr2.open("POST", url2, true);
                xhr2.setRequestHeader("Content-Type", "application/json");

                let data = JSON.stringify(bookingData);
                console.log("data = " + data);
                xhr2.send(data);

                xhr2.onreadystatechange = function () {
                    if (xhr2.readyState === 4 && xhr2.status === 200) {
                        console.log(xhr2.responseText);
                        const parser = new DOMParser();
                        const htmlDoc = parser.parseFromString(xhr2.responseText, 'text/html');
                        document.open();
                        document.write(htmlDoc.documentElement.outerHTML);
                        document.close();
                    }
                }
            }
        }
    }
}