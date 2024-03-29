package edu.java.service;

import edu.java.dto.dao.LinkDto;
import edu.java.exceptions.ChatNotExistException;
import edu.java.exceptions.LinkNotExistException;
import java.net.URI;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

public interface LinkService {
    /**
     * Add url tracking for user with given chat id
     * If the user is not registered, then register him
     *
     * @param tgChatId - user's telegram chat id
     * @param url      - url to track
     * @return info about a tracked link
     */
    LinkDto add(long tgChatId, URI url);

    /**
     * Stop tracking link
     * Removes a link from database, if there are no more users who are tracking the link
     *
     * @param tgChatId - user's telegram chat id
     * @param url      - url to remove
     * @return info about a removed link
     */
    LinkDto remove(long tgChatId, URI url) throws ChatNotExistException, LinkNotExistException;

    /**
     * Retrieve all links, that were checked more than N units of time ago
     *
     * @param interval - units of time
     * @return list of suitable links
     */
    List<LinkDto> findAllOldLinks(Duration interval);

    /**
     * Retrieve all links, tracked by certain user
     *
     * @param tgChatId - user's telegram chat id
     * @return list of tracked links
     */
    List<LinkDto> getAllSubscriptions(long tgChatId);

    /**
     * Modify updateAt link's field in db
     *
     * @param linkId       - link to modify
     * @param newUpdatedAt - new updatedAt value
     */
    void markNewUpdate(Long linkId, OffsetDateTime newUpdatedAt);

    /**
     * Updates last_check_time field in db
     *
     * @param linkId           - link to update
     * @param newLastCheckTime - new last_check_time value
     * @return number of affected rows
     */
    int markNewCheck(List<Long> linkId, OffsetDateTime newLastCheckTime);

    /**
     * Return all subscribed users to a certain resource
     *
     * @param linkId - resource id
     * @return list of users
     */
    List<Long> getAllSubscribers(Long linkId);
}
