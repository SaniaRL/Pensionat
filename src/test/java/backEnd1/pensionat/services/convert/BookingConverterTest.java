package backEnd1.pensionat.services.convert;

import backEnd1.pensionat.DTOs.BookingDTO;
import backEnd1.pensionat.DTOs.CustomerDTO;
import backEnd1.pensionat.DTOs.DetailedBookingDTO;
import backEnd1.pensionat.DTOs.SimpleCustomerDTO;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingConverterTest {

    String name = "Maria";
    String email = "maria@mail.com";
    LocalDate startDate = LocalDate.parse("2024-05-14");
    LocalDate endDate = LocalDate.parse("2024-05-17");

    Customer customer = new Customer(name, email);
    CustomerDTO customerDTO = new CustomerDTO(name, email);
    SimpleCustomerDTO simpleCustomerDTO = new SimpleCustomerDTO(name, email);
    Booking booking = new Booking(name, email, startDate, endDate);
    BookingDTO bookingDTO = new BookingDTO(customerDTO, startDate, endDate);
    DetailedBookingDTO detailedBookingDTO = new DetailedBookingDTO(simpleCustomerDTO, startDate, endDate);

    @Test
    void bookingToDetailedBookingDTO() {
        DetailedBookingDTO actual = BookingConverter.bookingToDetailedBookingDTO(booking);

        assertEquals(actual.getId(), booking.getId());
        assertEquals(actual.getStartDate(), booking.getStartDate());
        assertEquals(actual.getEndDate(), booking.getEndDate());

        assertEquals(actual.getCustomer().getId(), booking.getCustomer().getId());
        assertEquals(actual.getCustomer().getName(), booking.getCustomer().getName());
        assertEquals(actual.getCustomer().getEmail(), booking.getCustomer().getEmail());
    }

    @Test
    void bookingDtoToBooking() {
        Booking actual = BookingConverter.bookingDtoToBooking(bookingDTO, customer);

        assertEquals(actual.getStartDate(), bookingDTO.getStartDate());
        assertEquals(actual.getEndDate(), bookingDTO.getEndDate());

        assertEquals(actual.getCustomer().getId(), customer.getId());
        assertEquals(actual.getCustomer().getName(), customer.getName());
        assertEquals(actual.getCustomer().getEmail(), customer.getEmail());
    }

    @Test
    void detailedBookingDTOtoBooking() {
    }
}