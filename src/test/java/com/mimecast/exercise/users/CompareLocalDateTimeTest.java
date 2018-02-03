package com.mimecast.exercise.users;

import static com.mimecast.exercise.users.CompareLocalDateTime.describeTimeBetween;
import static java.time.LocalDateTime.parse;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CompareLocalDateTimeTest {

    @Test(expected = IllegalArgumentException.class)
    public void throws_iae_when_latter_date_given_first() {
        describeTimeBetween(parse("2018-02-01T10:15:30"), parse("2018-02-01T09:00:00"));
    }

    @Test
    public void describes_the_divergence_between_two_date_times_in_a_human_readable_way() {
        assertEquals("0 seconds ago",
                describeTimeBetween(parse("2018-02-01T10:15:30"), parse("2018-02-01T10:15:30")));
        assertEquals("1 second ago",
                describeTimeBetween(parse("2018-02-01T10:15:30"), parse("2018-02-01T10:15:31")));
        assertEquals("59 seconds ago",
                describeTimeBetween(parse("2018-02-01T10:15:30"), parse("2018-02-01T10:16:29")));

        assertEquals("1 minute ago",
                describeTimeBetween(parse("2018-02-01T10:15:30"), parse("2018-02-01T10:16:30")));
        assertEquals("45 minutes ago",
                describeTimeBetween(parse("2018-02-01T10:15:00"), parse("2018-02-01T11:00:31")));

        assertEquals("1 hour ago",
                describeTimeBetween(parse("2018-02-01T10:15:30"), parse("2018-02-01T11:15:30")));
        assertEquals("23 hours ago",
                describeTimeBetween(parse("2018-02-01T10:15:30"), parse("2018-02-02T10:10:30")));

        assertEquals("1 day ago",
                describeTimeBetween(parse("2018-02-01T10:15:30"), parse("2018-02-02T11:15:30")));

        assertEquals("100 days ago",
                describeTimeBetween(parse("2018-02-01T10:15:30"), parse("2018-05-12T11:15:30")));
    }
}