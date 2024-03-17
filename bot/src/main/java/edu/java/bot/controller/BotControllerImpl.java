package edu.java.bot.controller;

import edu.java.LinkUpdateResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class BotControllerImpl implements BotController {
    @Override
    public String sendUpdate(@Valid LinkUpdateResponse linkUpdateResponse) {
        return "Update processed";
    }
}
