package edu.java.bot.telegram.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.scrapper.ScrapperClient;
import edu.java.bot.telegram.message.ReplyMessages;
import edu.java.exceptions.ApiErrorException;
import edu.java.scrapper.LinkResponse;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class TrackCommand implements Command {
    private final ScrapperClient scrapperClient;

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "start tracking content";
    }

    @Override
    public String helpMessage() {
        return "Use this command to track resources. \nSyntax - /track <resource_name>";
    }

    @SuppressWarnings("ReturnCount")
    @Override
    public SendMessage handle(Update update) {
        long userId = update.message().chat().id();
        String[] tokens = update.message().text().split(" ");

        if (tokens.length < 2) {
            log.warn("Received /track without arguments");
            return new SendMessage(userId, helpMessage());
        }

        String resourceURI = tokens[1];
        LinkResponse response;

        try {
            response = scrapperClient.addLink(
                userId,
                new URI(resourceURI)
            );
        } catch (ApiErrorException e) {
            log.warn("Catch ApiErrorException");
            return new SendMessage(
                userId,
                e.getMessage()
            );
        } catch (URISyntaxException e) {
            log.warn("Bot received invalid link = %s on track command".formatted(resourceURI));
            return new SendMessage(userId, ReplyMessages.INVALID_URL.getText());
        }

        return new SendMessage(
            userId,
            "New resource added: %s".formatted(response.url().toString())
        );
    }
}
