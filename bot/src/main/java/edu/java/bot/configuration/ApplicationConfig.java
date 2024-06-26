package edu.java.bot.configuration;

import edu.java.bot.configuration.retrying.BackoffType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.Duration;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,
    @NotBlank
    String scrapperBaseUrl,
    @NotNull
    Kafka kafka,
    Retry retry
) {
    public record Kafka(
        @NotBlank
        String bootstrapServers,
        @NotBlank
        String groupId,
        @NotBlank
        String topicName,
        @Positive
        Integer partitions,
        @Positive
        Integer replicationFactor) {
    }

    public record Retry(
        @NotNull
        @PositiveOrZero
        Integer maxAttempts,
        @NotNull
        Duration minBackoff,
        @NotNull
        BackoffType backoffType,
        @NotEmpty
        Set<HttpStatus> httpStatuses
    ) {
    }
}
