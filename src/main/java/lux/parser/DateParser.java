package lux.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * A logic unit to recognising and handling different date formats.
 */
public class DateParser {
    private static List<DateTimeFormatter> SUPPORTED_FORMATS = new ArrayList<>(List.of(
            DateTimeFormatter.ofPattern("d/MM/yyyy"),
            DateTimeFormatter.ISO_LOCAL_DATE,
            DateTimeFormatter.ofPattern("MMM d yyyy")
    ));

    /**
     * Constructs a DateParser.
     */
    public DateParser() {}

    /**
     * Returns a ParsedDate that contains the date stored as a LocalDate, and it's formatting as DateTimeFormatter.
     *
     * @param date The raw date input as a String
     * @return a Parsed Date
     */
    public static ParsedDate parseDate(String date) {
        for (DateTimeFormatter formatter : SUPPORTED_FORMATS) {
            try {
                return new ParsedDate(LocalDate.parse(date, formatter), formatter);
            } catch (DateTimeParseException ignored) {

            }
        }
        throw new IllegalArgumentException("This date format is not supported: "
                + date
                + "\nPlease use dd/mm/yyyy, yyyy-mm-dd, or MMM dd yyyy");
    }
}
