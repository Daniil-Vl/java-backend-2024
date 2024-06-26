package edu.java.service.link_update_searching.searchers.github;

import edu.java.client.github.GithubClient;
import edu.java.dto.LinkUpdate;
import edu.java.dto.dao.LinkDto;
import edu.java.dto.github.GithubEventResponse;
import edu.java.service.LinkService;
import edu.java.service.link_update_searching.searchers.LinkUpdateSearcher;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GithubLinkUpdateSearcher implements LinkUpdateSearcher {
    private static final String HOST_NAME = "github.com";
    private static final Integer NUMBER_OF_UPDATES = 1;

    private final GithubClient githubClient;
    private final LinkService linkService;

    @Override
    public String host() {
        return HOST_NAME;
    }

    @Override
    public List<LinkUpdate> getUpdates(LinkDto linkDto) {
        URI url = linkDto.url();
        String[] pathTokens = url.getPath().split("/");

        String author = pathTokens[1];
        String repository = pathTokens[2];

        List<GithubEventResponse> latestUpdates = githubClient.getLatestUpdates(
            author,
            repository,
            NUMBER_OF_UPDATES
        );

        return latestUpdates
            .stream()
            .filter(githubEventResponse -> githubEventResponse.updatedAt().isAfter(linkDto.updatedAt()))
            .map(githubEventResponse -> processUpdate(githubEventResponse, linkDto.id(), url))
            .toList();
    }

    private LinkUpdate processUpdate(GithubEventResponse update, Long resourceId, URI url) {
        linkService.markNewUpdate(
            resourceId,
            update.updatedAt()
        );

        return new LinkUpdate(
            resourceId,
            url.toString(),
            buildDescriptionMessage(update)
        );
    }

    private String buildDescriptionMessage(GithubEventResponse update) {
        Optional<GithubEventType> eventTypeOptional = Arrays.stream(GithubEventType.values())
            .filter(githubEventType -> githubEventType.getType().equals(update.type()))
            .findFirst();

        if (eventTypeOptional.isEmpty()) {
            return "New update";
        }

        GithubEventType type = eventTypeOptional.get();

        return type.getDescriptionMessage()
            + " in repository "
            + update.repo().name()
            + " from user "
            + update.actor().login();
    }

    @Getter
    @RequiredArgsConstructor
    private enum GithubEventType {
        PUSH_EVENT("PushEvent", "New commit"),
        ISSUE_NEW_COMMENT("IssueCommentEvent", "New issue comment"),
        PULL_REQUEST_REVIEW_EVENT("PullRequestReviewEvent", "New pull request review");

        private final String type;
        private final String descriptionMessage;
    }
}
