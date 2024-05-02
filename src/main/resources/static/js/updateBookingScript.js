const addRoomButtons = document.querySelectorAll('.add-room-btn');
const removeRoomButtons = document.querySelectorAll('.remove-room-btn');
const availableRooms = document.getElementById('update-booking-empty-rooms');

function showAvailableRoomsDiv() {
    availableRooms.style.display = 'flex';
}

addRoomButtons.forEach(e => {
    e.addEventListener("click", () => {
        add(e)
    });
});

removeRoomButtons.forEach(e => {
    e.addEventListener("click", () => {
        remove(e)
        showAvailableRoomsDiv()
    });
});

const add = (e) => {
    console.log("Room clicked");
    let room = e.parentNode;
    if(room){
        const roomID = room.querySelector(".room-id").textContent;
        const roomType = room.querySelector(".room-type").textContent;
        const extraBeds = room.querySelector(".bed-number").value;

        console.log("Room-id: " + roomID);

        addRoom(roomID, roomType, extraBeds, room, e);
    }
    else {
        console.log("Room is null")
    }
}

const remove = (e) => {
    console.log("Room clicked");
    let room = e.parentNode;
    if (room) {
        const roomID = room.querySelector(".room-id").textContent;
        const roomType = room.querySelector(".room-type").textContent;

        console.log("Room-id: " + roomID);
        removeRoom(roomID, roomType, room, e);
    } else {
        console.log("Room is null")

    }
}

function addRoom(roomID, roomType, extraBeds, room, e) {
    let chosenRooms = JSON.parse(localStorage.getItem("chosenRooms")) || [];
    let availableRooms = JSON.parse(localStorage.getItem("availableRooms")) || [];

    chosenRooms.push({
        id: roomID,
        roomType: roomType,
        extraBeds: extraBeds
    });
    localStorage.setItem("chosenRooms", JSON.stringify(chosenRooms));

    availableRooms = availableRooms.filter(room => room.id !== roomID);
    localStorage.setItem("availableRooms", JSON.stringify(availableRooms))

    let roomElement = room.lastElementChild;
    roomElement.children.item(0).src = "/images/white_delete.png";
    roomElement.removeEventListener("click", add);
    roomElement.addEventListener("click", () => remove(e))
    document.getElementById("ChosenList").appendChild(room);
}

function removeRoom(roomID, roomType, room, e){
    let chosenRooms = JSON.parse(localStorage.getItem("chosenRooms")) || [];
    let availableRooms = JSON.parse(localStorage.getItem("availableRooms")) || [];

    availableRooms.push({
        id: roomID,
        roomType: roomType
    });
    localStorage.setItem("availableRooms", JSON.stringify(availableRooms));

    chosenRooms = chosenRooms.filter(room => room.id !== roomID);
    localStorage.setItem("chosenRooms", JSON.stringify(chosenRooms))

    let roomElement = room.lastElementChild;
    roomElement.children.item(0).src = "/images/white_plus.png";
    roomElement.removeEventListener("click", remove);
    roomElement.addEventListener("click", () => add(e))
    document.getElementById("AvailableList").appendChild(room);
}

document.addEventListener('DOMContentLoaded', function() {
    addChosenRoomsToLocalStorage();

    const closeButton = document.getElementById('closeButton');
    const errorPopup = document.getElementById('errorPopup');

    let avList = Array.prototype.slice.call(document.getElementById("AvailableList").children);
    let chList = Array.prototype.slice.call(document.getElementById("ChosenList").children);

    chList.forEach(c => {
        console.log(c.firstElementChild.innerHTML);
    });

    avList.forEach(a => {
        chList.forEach(c => {
            if(a.firstElementChild.innerHTML === c.firstElementChild.innerHTML) {
                a.classList.add("hidden");
            }
        })
    })

    if(avList.length !== 0) {
        chList.forEach(c => {
            let bool = false;
            avList.forEach(a => {
                if (a.firstElementChild.innerHTML === c.firstElementChild.innerHTML) {
                    bool = true;
                }
            })

            if (!bool) {
                c.classList.add("read-only");
                console.log(c);
                let extraBeds = c.children[2].children[0].children[0];
                console.log(extraBeds);
                extraBeds.type = 'hidden';
            }
        })
    }

});

//CSS ville inte funka när jag hade ID på denna. Då gick id före class hidden eller nåt.
const warning = document.querySelector('.delete-warning')
const overlay = document.getElementById('overlay')


function deleteWarning(){
    warning.classList.remove('hidden')
    overlay.classList.remove('hidden')
}

function closeDiv(){
    warning.classList.add('hidden')
    overlay.classList.add('hidden')
}

function validateBooking() {
    addChosenRoomsToLocalStorage();
    submitBooking()
}

function submitBooking() {
    let id = document.getElementById("booking-id").textContent;
    let name = document.getElementById("booking-name").textContent;
    let email = document.getElementById("booking-email").textContent;
    let startDate = document.getElementById("start-date").value;
    let endDate = document.getElementById("end-date").value;


    localStorage.setItem("id", id);
    localStorage.setItem("name", name);
    localStorage.setItem("email", email);
    localStorage.setItem("startDate", startDate);
    localStorage.setItem("endDate", endDate);

    let chosenRooms = JSON.parse(localStorage.getItem("chosenRooms")) || [];

    let bookingData = {
        id: id,
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
            const parser = new DOMParser();
            const htmlDoc = parser.parseFromString(xhr.responseText, 'text/html');
            document.open();
            document.write(htmlDoc.documentElement.outerHTML);
            document.close();
        }
    }
}

function addChosenRoomsToLocalStorage() {
    const chosenRoomsList = document.querySelectorAll('.room-av-row') || [];
    const chosenRooms = [];

    chosenRoomsList.forEach(r => {
        let id = r.children[0].textContent;
        let roomType = r.children[1].textContent;
        let extraBeds = r.children[2].children[0].children[0].value;

        chosenRooms.push({
            id: id,
            roomType: roomType,
            extraBeds: extraBeds
        });

        localStorage.setItem("chosenRooms", JSON.stringify(chosenRooms))

    })
}