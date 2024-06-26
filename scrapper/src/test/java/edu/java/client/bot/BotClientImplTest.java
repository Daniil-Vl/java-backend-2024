package edu.java.client.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.java.ApiErrorResponse;
import edu.java.LinkUpdateRequest;
import edu.java.client.AbstractClientServerTest;
import edu.java.retrying.RetryFilter;
import edu.java.retrying.backoff.ConstantBackoff;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BotClientImplTest extends AbstractClientServerTest {

    private BotClient botClient;

    @Override
    protected void initClient() {
        botClient = new BotClientImpl(
            server.baseUrl(),
            new RetryFilter(new ConstantBackoff(Duration.ZERO), Set.of(), 0)
        );
    }

    @Test
    void givenValidLinkUpdate_whenSendRequest_thenStatusOk() {
        List<LinkUpdateRequest> requestBody = List.of(new LinkUpdateRequest(
            1L,
            "url",
            "description",
            List.of(1L, 2L)
        ));
        String expectedResponseBody = "Обновление обработано";

        server.stubFor(
            post("/updates")
                .willReturn(aResponse().withStatus(200).withBody(expectedResponseBody))
        );

        String response = botClient.sendLinkUpdateRequest(requestBody);

        assertThat(response).isEqualTo(expectedResponseBody);
    }

    @Test
    void givenInvalidLinkUpdate_whenSendRequest_thenThrowIllegalArgumentException() throws JsonProcessingException {
        List<LinkUpdateRequest> requestBody = List.of(new LinkUpdateRequest(
            -1L,
            "url",
            "description",
            List.of(1L, 2L)
        ));
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            "Invalid argument in the request body",
            "400",
            "exception name",
            "exception message",
            List.of()
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String responseBodyString = objectMapper.writeValueAsString(errorResponse);

        server.stubFor(
            post("/updates")
                .willReturn(aResponse().withStatus(400)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(responseBodyString))
        );

        assertThatThrownBy(() -> botClient.sendLinkUpdateRequest(requestBody))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
