package backEnd1.pensionat;

import backEnd1.pensionat.Enums.RoomType;
import backEnd1.pensionat.Models.Booking;
import backEnd1.pensionat.Models.Customer;
import backEnd1.pensionat.Models.OrderLine;
import backEnd1.pensionat.Models.Room;
import backEnd1.pensionat.Repositories.BookingRepo;
import backEnd1.pensionat.Repositories.CustomerRepo;
import backEnd1.pensionat.Repositories.OrderLineRepo;
import backEnd1.pensionat.Repositories.RoomRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class PensionatApplication {

	public static void main(String[] args) {
		SpringApplication.run(PensionatApplication.class, args);
	}

	//TODO Kommentera bort innan ni får 8726782672627628 extra
	/*
	@Bean
	public CommandLineRunner demo(BookingRepo bookingRepo, CustomerRepo customerRepo,
								  OrderLineRepo orderLineRepo, RoomRepo roomRepo) {
		return args -> {

			List<Room> rooms = Arrays.asList(
					new Room(201L, RoomType.SINGLE),
					new Room(202L, RoomType.SINGLE),
					new Room(203L, RoomType.DOUBLE),
					new Room(204L, RoomType.DOUBLE),
					new Room(301L, RoomType.PREMIUM),
					new Room(302L, RoomType.PREMIUM));

			roomRepo.saveAll(rooms);

			List<Customer> customers = Arrays.asList(
					new Customer("Basse", "basse@email.com"),
					new Customer("Eddie", "eddie@email.com"),
					new Customer("Simon", "simon@email.com"),
					new Customer("Sania", "sania@email.com")
			);

			customerRepo.saveAll(customers);

			List<Booking> bookings = Arrays.asList(
					new Booking(customers.get(0), LocalDate.now(), LocalDate.now().plusDays(3)),
					new Booking(customers.get(1), LocalDate.now().plusDays(4), LocalDate.now().plusDays(9)),
					new Booking(customers.get(2), LocalDate.now(), LocalDate.now().plusDays(2)),
					new Booking(customers.get(3), LocalDate.now().plusDays(10), LocalDate.now().plusDays(20))
			);

			bookingRepo.saveAll(bookings);

			List<OrderLine> orderLines = Arrays.asList(
					new OrderLine(bookings.get(0), rooms.get(5), 2),
					new OrderLine(bookings.get(0), rooms.get(3), 1),
					new OrderLine(bookings.get(1), rooms.get(2), 0),
					new OrderLine(bookings.get(2), rooms.get(1), 0),
					new OrderLine(bookings.get(2), rooms.get(2), 1),
					new OrderLine(bookings.get(2), rooms.get(5), 2),
					new OrderLine(bookings.get(3), rooms.get(5), 0)
			);

			orderLineRepo.saveAll(orderLines);
		};
	}

	 */
}
