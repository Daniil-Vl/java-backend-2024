/*
 * This file is generated by jOOQ.
 */

package edu.java.domain.jooq.generated.tables.records;

import edu.java.domain.jooq.generated.tables.Subscription;
import java.beans.ConstructorProperties;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class SubscriptionRecord extends UpdatableRecordImpl<SubscriptionRecord> implements Record2<Long, Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>SUBSCRIPTION.CHAT_ID</code>.
     */
    public void setChatId(@NotNull Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>SUBSCRIPTION.CHAT_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getChatId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>SUBSCRIPTION.LINK_ID</code>.
     */
    public void setLinkId(@NotNull Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>SUBSCRIPTION.LINK_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getLinkId() {
        return (Long) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record2<Long, Long> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row2<Long, Long> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return Subscription.SUBSCRIPTION.CHAT_ID;
    }

    @Override
    @NotNull
    public Field<Long> field2() {
        return Subscription.SUBSCRIPTION.LINK_ID;
    }

    @Override
    @NotNull
    public Long component1() {
        return getChatId();
    }

    @Override
    @NotNull
    public Long component2() {
        return getLinkId();
    }

    @Override
    @NotNull
    public Long value1() {
        return getChatId();
    }

    @Override
    @NotNull
    public Long value2() {
        return getLinkId();
    }

    @Override
    @NotNull
    public SubscriptionRecord value1(@NotNull Long value) {
        setChatId(value);
        return this;
    }

    @Override
    @NotNull
    public SubscriptionRecord value2(@NotNull Long value) {
        setLinkId(value);
        return this;
    }

    @Override
    @NotNull
    public SubscriptionRecord values(@NotNull Long value1, @NotNull Long value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SubscriptionRecord
     */
    public SubscriptionRecord() {
        super(Subscription.SUBSCRIPTION);
    }

    /**
     * Create a detached, initialised SubscriptionRecord
     */
    @ConstructorProperties({"chatId", "linkId"})
    public SubscriptionRecord(@NotNull Long chatId, @NotNull Long linkId) {
        super(Subscription.SUBSCRIPTION);

        setChatId(chatId);
        setLinkId(linkId);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised SubscriptionRecord
     */
    public SubscriptionRecord(edu.java.domain.jooq.generated.tables.pojos.Subscription value) {
        super(Subscription.SUBSCRIPTION);

        if (value != null) {
            setChatId(value.getChatId());
            setLinkId(value.getLinkId());
            resetChangedOnNotNull();
        }
    }
}
