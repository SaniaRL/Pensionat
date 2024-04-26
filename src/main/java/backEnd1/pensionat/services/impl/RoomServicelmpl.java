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

    public List<RoomDTO> findAvailableRooms(BookingFormQueryDTO query){
        LocalDate startDate = query.getStartDate();
        LocalDate endDate = query.getEndDate();
        int beds = query.getBeds();
        int rooms = query.getRooms();

        //Kolla alla rum
        //Som inte finns på en order_line
        //Som har en bokning
        //Som har startdatum mindre eller lika med slut ??
        //Som har slutdatum större eller lika med start ??

        //ASSÅ ORKAR INTE TÄNKA SÅ ATM ÄR DET ALLA RUM YES
        //Klockan är snart 22 chilla asså
        String jpqlQuery = "SELECT r FROM Room r ";

        return entityManager.createQuery(jpqlQuery, Room.class)
                .getResultList().stream().map(r -> RoomDTO.builder()
                        .id(r.getId())
                        .roomType(RoomTypeConverter.convertFromInt(r.getTypeOfRoom()))
                        .build()).toList();
    }
}
