package backEnd1.pensionat.services.convert;

import backEnd1.pensionat.DTOs.DetailedCustomerDTO;
import backEnd1.pensionat.DTOs.SimpleBookingDTO;
import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.Customer;

public class CustomerConverter {

    public static DetailedCustomerDTO customerToDetailedCustomerDTO(Customer customer) {
        return DetailedCustomerDTO.builder().id(customer.getId())
                .name(customer.getName()).email(customer.getEmail())
                .bookings(customer.getBookings()
                        .stream()
                        .map(booking -> SimpleBookingDTO.builder()
                                .id(booking.getId())
                                .startDate(booking.getStartDate())
                                .endDate(booking.getEndDate()).build())
                        .toList())
                .build();
    }

    public static SimpleCustomerDTO customerToSimpleCustomerDTO(Customer customer) {
        return SimpleCustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .build();
    }

    public static Customer DetailedCustomerDTOtoCustomer(DetailedCustomerDTO customer) {
        return Customer.builder().id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .bookings(customer.getBookings()
                        .stream()
                        .map(booking -> Booking.builder()
                                .id(booking.getId())
                                .startDate(booking.getStartDate())
                                .endDate(booking.getEndDate())
                                .build())
                        .toList())
                .build();
    }

    public static Customer SimpleCustomerDTOtoCustomer(SimpleCustomerDTO customer) {
        return Customer.builder().id(customer.getId())
                        .name(customer.getName())
                        .email(customer.getEmail())
                .build();
    }
}
