package backEnd1.pensionat.services.impl;

import backEnd1.pensionat.Models.Room;
import backEnd1.pensionat.Repositories.RoomRepo;
import backEnd1.pensionat.services.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServicelmpl implements RoomService {

    final private RoomRepo roomRepo;

    @Override
    public List<Room> getAllRooms(){
        return roomRepo.findAll();
    }
    @Override
    public String addRoom(Room r){
        roomRepo.save(r);
        return "Room added";
    }
    @Override
    public String removeRoomById(Long id) {
        roomRepo.deleteById(id);
        return "Room " + id + " removed";
    }
}
