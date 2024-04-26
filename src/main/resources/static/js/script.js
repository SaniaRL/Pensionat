
const addRoomButtons = document.querySelectorAll('.add-room-btn');
const removeRoomButtons = document.querySelectorAll('.remove-room-btn');

addRoomButtons.forEach(e => {
    e.addEventListener("click", () => add(e));
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

        console.log("Room-id: " + roomID);

        addRoom(roomID, roomType, room, e);
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

function addRoom(roomID, roomType, room, e) {
    let chosenRooms = JSON.parse(localStorage.getItem("chosenRooms")) || [];
    let availableRooms = JSON.parse(localStorage.getItem("availableRooms")) || [];

    chosenRooms.push({
        id: roomID,
        roomType: roomType
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

function validateForm(){

}


//onClick

function submitBooking(){
    //Hämta chosenRooms från localstorage
    let chosenRooms = JSON.parse(localStorage.getItem("chosenRooms")) || [];

    //Stoppa i body i anrop  @PostMapping("/submitBooking")
    let xhr = new XMLHttpRequest();
    let url = "/confirmBooking";

    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");

    let data = JSON.stringify(chosenRooms);
    xhr.send(data);

    xhr.onreadystatechange = function () {
        // Om förfrågan är färdig och svaret är OK (status 200)
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Hantera svaret här om det behövs
            console.log(xhr.responseText);
        }
    };
}