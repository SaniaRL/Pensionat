package backEnd1.pensionat.services.convert;

import backEnd1.pensionat.DTOs.DetailedBookingDTO;
import backEnd1.pensionat.DTOs.SimpleBookingDTO;
import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.Customer;

public class BookingConverter {
    public DetailedBookingDTO bookingToDetailedBookingDTO(Booking booking) {
        return DetailedBookingDTO.builder().id(booking.getId())
                .customer(new SimpleCustomerDTO(booking.getCustomer().getId(),
                        booking.getCustomer().getName(), booking.getCustomer().getEmail()))
                .startDate(booking.getStartDate()).endDate(booking.getEndDate()).build();
    }
    public SimpleBookingDTO bookingToSimpleBookingDTO(Booking booking) {
        return SimpleBookingDTO.builder().id(booking.getId())
                .startDate(booking.getStartDate()).endDate(booking.getEndDate()).build();
    }
    public Booking DetailedBookingDTOtoBooking(DetailedBookingDTO booking) {
        return Booking.builder().id(booking.getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .customer(Customer.builder().id(booking.getCustomer().getId())
                        .name(booking.getCustomer().getName())
                        .email(booking.getCustomer().getEmail()).build()).build();
    }

    
}
