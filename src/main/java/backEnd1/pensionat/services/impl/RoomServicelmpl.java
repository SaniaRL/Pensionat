package backEnd1.pensionat.services.impl;

import Exceptions.RoomAvailabilityException;
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

    //TODO Idk ska vi inte ha model idk
    @Override
    public RoomDTO getRoomByID(Long Id) {
        Room room = roomRepo.findById(Id).orElse(new Room());
        return RoomDTO.builder().id(room.getId()).roomType(RoomTypeConverter.convertFromInt(room.getTypeOfRoom())).build();

    }

    @Override
    public Room roomDtoToRoom(RoomDTO room) {
        return Room.builder().id(room.getId()).typeOfRoom(RoomTypeConverter.convertToInt(room.getRoomType())).build();
    }

    @Override
    public RoomDTO roomToRoomDto(Room room) {
        return RoomDTO.builder().id(room.getId()).roomType(RoomTypeConverter.convertFromInt(room.getTypeOfRoom())).build();
    }

    @Override
    public List<RoomDTO> findAvailableRooms(BookingFormQueryDTO query) throws RoomAvailabilityException {
        LocalDate startDate = query.getStartDate();
        LocalDate endDate = query.getEndDate();

        String jpqlQuery = "SELECT r FROM Room r WHERE r.id NOT IN (" +
                "SELECT o.room.id FROM OrderLine o WHERE o.booking.id IN (" +
                "SELECT b.id FROM Booking b WHERE b.endDate >= :startDate AND b.startDate <= :endDate" +
                ")" +
                ")";;

                //TODO Uppdatera metod så r ->
        return entityManager.createQuery(jpqlQuery, Room.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList().stream().map(r -> RoomDTO.builder()
                        .id(r.getId())
                        .roomType(RoomTypeConverter.convertFromInt(r.getTypeOfRoom()))
                        .build()).toList();
    }

    @Override
    public String enoughRooms(BookingFormQueryDTO query, List<RoomDTO> queryRooms) {

        LocalDate startDate = query.getStartDate();
        LocalDate endDate = query.getEndDate();

        int wantedRooms = query.getRooms();
        int numberOfRooms = queryRooms.size();

        int wantedBeds = query.getBeds();
        int maxNumberOfBeds = (int) queryRooms.stream()
                .map(room -> room.getRoomType().getMaxNumberOfBeds()).count();

        if(startDate.isAfter(endDate)) {
            return "Startdatum måste vara före slutdatum.";
        }

        if(startDate.isBefore(LocalDate.now())) {
            return "Tidigaste accepterade startDatum är dagens datum.";
        }

        if(wantedRooms > wantedBeds) {
            return "Vill du boka fler rum än sängar? Det är orimligt!";
        }

        if(wantedRooms > numberOfRooms) {
            return "Det önskade antalet rum överstiger antalet lediga rum.";
        }

        if(wantedBeds > maxNumberOfBeds) {
            return "Det önskade antalet sängar överstiger antalet lediga sängar";
        }

        return "";
    }
}
