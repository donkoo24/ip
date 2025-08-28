import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ParsedDate {
    private LocalDate date;
    private DateTimeFormatter dateFormat;

    public ParsedDate (LocalDate date, DateTimeFormatter format) {
        this.date = date;
        this.dateFormat = format;
    }

    @Override
    public String toString() {
        return date.format(dateFormat);
    }
}
