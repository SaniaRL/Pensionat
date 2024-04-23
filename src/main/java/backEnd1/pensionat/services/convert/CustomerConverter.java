package backEnd1.pensionat.services.convert;

import backEnd1.pensionat.DTOs.DetailedCustomerDTO;
import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.Models.Customer;

public class CustomerConverter {
    private final BookingConverter bookingConverter;

    public CustomerConverter(){
        bookingConverter = new BookingConverter();
    }

    public DetailedCustomerDTO customerToDetailedCustomerDTO(Customer customer) {
        return DetailedCustomerDTO.builder().id(customer.getId())
                .name(customer.getName()).email(customer.getEmail())
                .bookings(customer.getBookings()
                        .stream()
                        .map(bookingConverter::bookingToSimpleBookingDTO)
                        .toList()).build();
    }

    public SimpleCustomerDTO customerToSimpleCustomerDTO(Customer customer) {
        return SimpleCustomerDTO.builder().id(customer.getId()).name(customer.getName())
                .email(customer.getEmail()).build();
    }

    public Customer DetailedCustomerDTOtoCustomer(DetailedCustomerDTO customer) {
        return Customer.builder().id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .bookings(customer.getBookings()
                        .stream()
                        .map(bookingConverter::SimpleBookingDTOtoBooking).toList()).build();
    }

    public Customer SimpleCustomerDTOtoCustomer(SimpleCustomerDTO customer) {
        return Customer.builder().id(customer.getId())
                        .name(customer.getName())
                        .email(customer.getEmail()).build();
    }
}
