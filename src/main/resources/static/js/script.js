
const addRoomButtons = document.querySelectorAll('.add-room-btn');
const removeRoomButtons = document.querySelectorAll('.remove-room-btn');

addRoomButtons.forEach(function(td) {
    td.addEventListener("click", function() {
        console.log("Room clicked");
        let room = td.parentNode;
        if(room){
            const roomID = room.querySelector(".room-id").textContent;
            const roomType = room.querySelector(".room-type").textContent;

            console.log("Room-id: " + roomID);

            addRoom(roomID, roomType);
        }
        else {
            console.log("Room is null")
        }
    });
});

removeRoomButtons.forEach(function(td) {
    td.addEventListener("click", function() {
        console.log("Room clicked");
        let room = td.parentNode;
        if(room){
            const roomID = room.querySelector(".room-id").textContent;
            const roomType = room.querySelector(".room-type").textContent;

            console.log("Room-id: " + roomID);
            removeRoom(roomID, roomType);
        }
        else {
            console.log("Room is null")
        }
    });
});


function addRoom(roomID, roomType) {
    let chosenRooms = JSON.parse(localStorage.getItem("chosenRooms")) || [];
    let availableRooms = JSON.parse(localStorage.getItem("availableRooms")) || [];

    chosenRooms.push({
        id: roomID,
        roomType: roomType
    });
    localStorage.setItem("chosenRooms", JSON.stringify(chosenRooms));

    availableRooms = availableRooms.filter(room => room.id !== roomID);
    localStorage.setItem("availableRooms", JSON.stringify(availableRooms))

    const xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8080/booking", true);
    xhr.send(roomID);
}

function removeRoom(roomID, roomType){
    let chosenRooms = JSON.parse(localStorage.getItem("chosenRooms")) || [];
    let availableRooms = JSON.parse(localStorage.getItem("availableRooms")) || [];

    availableRooms.push({
        id: roomID,
        roomType: roomType
    });
    localStorage.setItem("availableRooms", JSON.stringify(availableRooms));

    chosenRooms = chosenRooms.filter(room => room.id !== roomID);
    localStorage.setItem("chosenRooms", JSON.stringify(chosenRooms))
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