package com.example.pensionat;

import com.example.pensionat.models.*;
import com.example.pensionat.models.events.*;
import com.example.pensionat.repositories.*;
import com.example.pensionat.services.providers.MailTemplateSeeder;
import com.example.pensionat.services.providers.RoleAndUserDataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class PensionatApplication {

    @Autowired
    private RoleAndUserDataSeeder roleAndUserDataSeeder;

    @Autowired
    private MailTemplateSeeder mailTemplateSeeder;

    public static void main(String[] args) {
        if (args.length == 0) {
            SpringApplication.run(PensionatApplication.class, args);
        } else if (Objects.equals(args[0], "fetchcontractcustomers")) {
            SpringApplication application = new SpringApplication(FetchContractCustomers.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        } else if (Objects.equals(args[0], "fetchshippers")) {
            SpringApplication application = new SpringApplication(FetchShippers.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        } else if (Objects.equals(args[0], "fetchevents")) {
            SpringApplication application = new SpringApplication(FetchEvents.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        }
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            roleAndUserDataSeeder.Seed();
            mailTemplateSeeder.Seed();
        };
    }

    //TODO Kommentera bort innan ni får 8726782672627628 extra
/*
	@Bean
	public CommandLineRunner demo(BookingRepo bookingRepo, CustomerRepo customerRepo,
								  OrderLineRepo orderLineRepo, RoomRepo roomRepo) {
		return args -> {

			List<Room> rooms = Arrays.asList(
					new Room(201L, 0),
					new Room(202L, 0),
					new Room(203L, 1),
					new Room(204L, 1),
					new Room(301L, 2),
					new Room(302L, 2),
					new Room(303L, 0),
					new Room(304L, 0),
					new Room(401L, 1),
					new Room(402L, 1),
					new Room(403L, 2),
					new Room(404L, 2));

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
					new Booking(customers.get(3), LocalDate.now().plusDays(20), LocalDate.now().plusDays(29)),
					new Booking(customers.get(0), LocalDate.now().plusDays(40), LocalDate.now().plusDays(50)),
					new Booking(customers.get(1), LocalDate.now().plusDays(14), LocalDate.now().plusDays(19)),
					new Booking(customers.get(2), LocalDate.now(), LocalDate.now().plusDays(2)),
					new Booking(customers.get(3), LocalDate.now().plusDays(10), LocalDate.now().plusDays(20))
			);

			bookingRepo.saveAll(bookings);

			List<OrderLine> orderLines = Arrays.asList(
					new OrderLine(bookings.get(0), rooms.get(5), 2),
					new OrderLine(bookings.get(0), rooms.get(3), 2),
					new OrderLine(bookings.get(1), rooms.get(2), 3),
					new OrderLine(bookings.get(2), rooms.get(1), 2),
					new OrderLine(bookings.get(2), rooms.get(2), 2),
					new OrderLine(bookings.get(2), rooms.get(5), 2),
					new OrderLine(bookings.get(3), rooms.get(1), 2),
					new OrderLine(bookings.get(4), rooms.get(2), 3),
					new OrderLine(bookings.get(5), rooms.get(1), 1),
					new OrderLine(bookings.get(6), rooms.get(2), 2),
					new OrderLine(bookings.get(6), rooms.get(1), 1),
					new OrderLine(bookings.get(7), rooms.get(2), 2),
					new OrderLine(bookings.get(3), rooms.get(5), 4)
			);

			orderLineRepo.saveAll(orderLines);
		};

	}
*/


}
