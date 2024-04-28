package backEnd1.pensionat.services.impl;

import backEnd1.pensionat.DTOs.BookingFormQueryDTO;
import backEnd1.pensionat.DTOs.RoomDTO;
import backEnd1.pensionat.Models.Room;
import backEnd1.pensionat.Repositories.RoomRepo;
import backEnd1.pensionat.services.convert.RoomTypeConverter;
import backEnd1.pensionat.services.interfaces.RoomService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServicelmpl implements RoomService {

    final private RoomRepo roomRepo;
    @PersistenceContext
    EntityManager entityManager;

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

    //TODO Idk
    @Override
    public Room getRoomByID(Long Id) {
        return roomRepo.findById(Id).orElse(new Room());
    }

    @Override
    public Room roomDtoToRoom(RoomDTO room) {
        return Room.builder().id(room.getId()).typeOfRoom(RoomTypeConverter.convertToInt(room.getRoomType())).build();
    }

    @Override
    public RoomDTO roomToRoomDto(Room room) {
        return RoomDTO.builder().id(room.getId()).roomType(RoomTypeConverter.convertFromInt(room.getTypeOfRoom())).build();
    }

    public List<RoomDTO> findAvailableRooms(BookingFormQueryDTO query){
        LocalDate startDate = query.getStartDate();
        LocalDate endDate = query.getEndDate();
        //TODO onödig info:
        int beds = query.getBeds();
        int rooms = query.getRooms();

        String jpqlQuery = "SELECT r FROM Room r WHERE r.id NOT IN (" +
                "SELECT o.room.id FROM OrderLine o WHERE o.booking.id IN (" +
                "SELECT b.id FROM Booking b WHERE b.endDate >= :startDate AND b.startDate <= :endDate" +
                ")" +
                ")";;

        return entityManager.createQuery(jpqlQuery, Room.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList().stream().map(r -> RoomDTO.builder()
                        .id(r.getId())
                        .roomType(RoomTypeConverter.convertFromInt(r.getTypeOfRoom()))
                        .build()).toList();
    }
}
