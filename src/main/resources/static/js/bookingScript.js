const addRoomButtons = document.querySelectorAll('.add-room-btn');
const removeRoomButtons = document.querySelectorAll('.remove-room-btn');
const chosenRooms = document.getElementById('booking-chosen-rooms');

addRoomButtons.forEach(e => {
    e.addEventListener("click", () => {
        add(e)
        chosenRooms.style.display = 'flex';
    });
});

removeRoomButtons.forEach(e => {
    e.addEventListener("click", () => remove(e));
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
//    document.getElementById("AvailableList").removeChild(room);
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
//    document.getElementById("ChosenList").removeChild(room);
}

function saveListsToLocalStorage(availableRooms, chosenRooms) {
    localStorage.setItem("availableRooms", JSON.stringify(availableRooms));
    localStorage.setItem("chosenRooms", JSON.stringify(chosenRooms));
}

function getListsFromLocalStorage() {
    const availableRooms = JSON.parse(localStorage.getItem("availableRooms")) || [];
    const chosenRooms = JSON.parse(localStorage.getItem("chosenRooms")) || [];
    return { availableRooms, chosenRooms };
}

function addFormToLocalStorage(){
    const startDate = document.getElementById("start-date").value;
    const endDate = document.getElementById("end-date").value;

    localStorage.setItem("startDate", startDate);
    localStorage.setItem("endDate", endDate);
}


function clearRooms() {
    localStorage.removeItem("chosenRooms");
    addFormToLocalStorage()
}

function submitBooking() {
    const chosenRooms = localStorage.getItem("chosenRooms")
    console.log("chosenRooms: " + chosenRooms);
    if (chosenRooms === null) {

    } else {
        //Fan måste ju skicka inställningarna också
        let startDate = localStorage.getItem("startDate");
        let endDate = localStorage.getItem("endDate");

        document.getElementById('start-date').value = startDate;
        document.getElementById('end-date').value = endDate;

        window.location.href = "/customer/customerOrNot";
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const closeButton = document.getElementById('closeButton');
    const errorPopup = document.getElementById('errorPopup');

    closeButton.addEventListener('click', function() {
        console.log("Button clicked")
        errorPopup.style.display = 'none';
        window.location.href="http://localhost:8080/booking";
    });
});