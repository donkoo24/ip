package lux.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A date item that stores date and it's format.
 */
public class ParsedDate {
    private LocalDate date;
    private DateTimeFormatter dateFormat;

    /**
     * Constructs a ParsedDate with date stored as LocalDate and it's formatting as a DateTimeFormatter.
     *
     * @param date The date.
     * @param format The date format the date was written with.
     */
    public ParsedDate (LocalDate date, DateTimeFormatter format) {
        this.date = date;
        this.dateFormat = format;
    }

    /**
     * Returns a String of the date in a format that the user specified.
     *
     * @return Date as a String in the format it was previously formatted in.
     */
    @Override
    public String toString() {
        return date.format(dateFormat);
    }
}
