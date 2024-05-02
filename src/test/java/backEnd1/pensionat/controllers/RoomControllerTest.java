package backEnd1.pensionat.controllers;

import backEnd1.pensionat.Models.Room;
import backEnd1.pensionat.Repositories.RoomRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoomRepo mockRepo;

    @BeforeEach
    public void init() {
        Room room1 = new Room(201L, 0, null);
        Room room2 = new Room(203L, 1, null);
        Room room3 = new Room(302L, 2, null);

        when(mockRepo.findAll()).thenReturn(Arrays.asList(room1, room2, room3));
    }

    @Test
    void getAllRooms() throws Exception {
        this.mvc.perform(get("/room/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":201,\"roomType\":\"SINGLE\"}," +
                        "{\"id\":203,\"roomType\":\"DOUBLE\"}," +
                        "{\"id\":302,\"roomType\":\"PREMIUM\"}]"));
    }

    @Test // Får inte denna att fungera. Beror det på att roomType är ett Enum?
    void addRoom() throws Exception {
        this.mvc.perform(post("/room/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":501, \"roomType\":\"DOUBLE\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Room added")));
    }
    /*
    @PostMapping("/add")
    public String addRoom(@RequestParam Long id, @RequestParam RoomType roomType) {
        return roomService.addRoom(new RoomDTO(id, roomType));
    }
     */

    @Test
    void removeRoomById() throws Exception {
        this.mvc.perform(get("/room/203/remove"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Room 203 removed")));
    }
}