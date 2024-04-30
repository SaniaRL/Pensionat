package backEnd1.pensionat.services.convert;

import backEnd1.pensionat.DTOs.BookingDTO;
import backEnd1.pensionat.DTOs.DetailedBookingDTO;
import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.Customer;
import org.springframework.stereotype.Component;

@Component
public class BookingConverter {
    public static DetailedBookingDTO bookingToDetailedBookingDTO(Booking booking) {
        return DetailedBookingDTO.builder().id(booking.getId())
                .customer(new SimpleCustomerDTO(booking.getCustomer().getId(),
                        booking.getCustomer().getName(), booking.getCustomer().getEmail()))
                .startDate(booking.getStartDate()).endDate(booking.getEndDate()).build();
    }
    public static Booking bookingDtoToBooking(BookingDTO b, Customer c) {
        return Booking.builder().customer(c).startDate(b.getStartDate()).endDate(b.getEndDate()).build();
    }

    public static Booking detailedBookingDTOtoBooking(DetailedBookingDTO booking) {
        //TODO Sök om kunden finns
        SimpleCustomerDTO customer = booking.getCustomer();
        return Booking.builder().id(booking.getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .customer(Customer.builder().id(customer.getId())
                        .name(customer.getName())
                        .email(customer.getEmail()).build())
                .build();
    }
    /*
    public static SimpleBookingDTO bookingToSimpleBookingDTO(Booking booking) {
        return SimpleBookingDTO.builder().id(booking.getId())
                .startDate(booking.getStartDate()).endDate(booking.getEndDate()).build();
    }
    public static Booking SimpleBookingDTOtoBooking(SimpleBookingDTO booking) {
        return Booking.builder().id(booking.getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }
     */
}