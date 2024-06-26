package edu.java.domain.jooq.utils;

import edu.java.domain.jooq.generated.tables.Link;
import edu.java.dto.dao.LinkDto;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.jetbrains.annotations.Nullable;
import org.jooq.Record4;
import org.jooq.RecordMapper;

public class LinkDtoRecordMapper
    implements RecordMapper<Record4<Long, String, OffsetDateTime, OffsetDateTime>, LinkDto> {
    Link link = Link.LINK;

    @Override
    public @Nullable LinkDto map(Record4<Long, String, OffsetDateTime, OffsetDateTime> record4) {
        return new LinkDto(
            record4.get(link.ID, Long.class),
            URI.create(record4.get(link.URL, String.class)),
            record4.get(link.UPDATED_AT, OffsetDateTime.class).withOffsetSameInstant(ZoneOffset.UTC),
            record4.get(link.LAST_CHECK_TIME, OffsetDateTime.class).withOffsetSameInstant(ZoneOffset.UTC)
        );
    }
}
