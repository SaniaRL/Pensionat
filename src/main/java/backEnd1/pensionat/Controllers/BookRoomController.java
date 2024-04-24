package backEnd1.pensionat.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookRoomController {

    @RequestMapping("/booking")
    public String bookingForm(@RequestParam(required = false) String query, Model model){
        return "booking";
    }
}
