package com.example.pensionat.services.impl;

import com.example.pensionat.dtos.*;
import com.example.pensionat.services.convert.OrderLineConverter;
import com.example.pensionat.services.convert.RoomConverter;
import com.example.pensionat.services.convert.RoomTypeConverter;
import com.example.pensionat.services.interfaces.RoomService;
import com.example.pensionat.models.Room;
import com.example.pensionat.repositories.RoomRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServicelmpl implements RoomService {

    final private RoomRepo roomRepo;
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void addToModel(int currentPage, Model model){
        Page<DetailedRoomDTO> r = getAllRoomsPage(currentPage);
        model.addAttribute("allRooms", r.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", r.getTotalElements());
        model.addAttribute("totalPages", r.getTotalPages());
    }

    @Override
    public Page<DetailedRoomDTO> getAllRoomsPage(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, 6);
        Page<Room> page = roomRepo.findAll(pageable);
        return page.map(RoomConverter::roomToDetailedRoomDto);
    }

    @Override
    public RoomDTO getRoomByID(Long Id) {
        Room room = roomRepo.findById(Id).orElse(new Room());
        return RoomDTO.builder().id(room.getId()).roomType(RoomTypeConverter.convertFromInt(room.getTypeOfRoom())).build();
    }

    @Override
    public List<RoomDTO> findAvailableRooms(BookingFormQueryDTO query) {
        LocalDate startDate = query.getStartDate();
        LocalDate endDate = query.getEndDate();

        String jpqlQuery = "SELECT r FROM Room r WHERE r.id NOT IN (" +
                "SELECT o.room.id FROM OrderLine o WHERE o.booking.id IN (" +
                "SELECT b.id FROM Booking b WHERE b.endDate >= :startDate AND b.startDate <= :endDate" +
                ")" +
                ")";

        return entityManager.createQuery(jpqlQuery, Room.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList().stream().map(RoomConverter::roomToRoomDto).toList();
    }

    @Override
    public List<SimpleOrderLineDTO> filterNotInChosenRooms(BookingFormQueryDTO query,
                                                           List<SimpleOrderLineDTO> chosenRooms) {

        List<SimpleOrderLineDTO> allRooms = findAvailableRooms(query).stream()
                .map(OrderLineConverter::roomToSimpleOrderLineDTO).toList();

        return allRooms.stream().filter(r -> !(chosenRooms.contains(r))).toList();
    }

    public List<RoomDTO> findAvailableRooms(BookingFormQueryDTO query, long id) {
        String jpqlQuery = "SELECT r FROM Room r WHERE r.id NOT IN (" +
                "SELECT o.room.id FROM OrderLine o WHERE o.booking.id IN (" +
                "SELECT b.id FROM Booking b WHERE b.endDate >= :startDate AND b.startDate <= :endDate " +
                "AND b.id != :bookingID" +
                ")" +
                ")";

        return entityManager.createQuery(jpqlQuery, Room.class)
                .setParameter("startDate", query.getStartDate())
                .setParameter("endDate", query.getEndDate())
                .setParameter("bookingID", id)
                .getResultList().stream().map(RoomConverter::roomToRoomDto).toList();
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

        System.out.println("Startdate: " + startDate);
        System.out.println("Enddate: " + endDate);
        System.out.println("Wanted rooms: " + wantedRooms);
        System.out.println("Wanted beds: " + wantedBeds);
        System.out.println("Number of rooms: " + numberOfRooms);
        System.out.println("Max number of beds: " + maxNumberOfBeds);

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