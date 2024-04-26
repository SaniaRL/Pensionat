
const addRoomButtons = document.querySelectorAll('.add-room-btn');

addRoomButtons.forEach(function(td) {
    td.addEventListener("click", function() {
        console.log("Room clicked");
        let room = td.parentNode;
        if(room){
            const roomID = room.querySelector(".room-id").textContent;
            const roomType = room.querySelector(".room-type").textContent;

            console.log("Room-id: " + roomID);

            let chosenRooms = JSON.parse(localStorage.getItem("chosenRooms")) || [];

            addRoom(chosenRooms, roomID, roomType);
        }
        else {
            console.log("Room is null")
        }
    });
});

function addRoom(list, roomID, roomType) {
    list.push({
        id: roomID,
        roomType: roomType
    });

    localStorage.setItem("chosenRooms", JSON.stringify(list));
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