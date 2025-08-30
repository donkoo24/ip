package lux.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateParserTest {
    @Test
    public void dateParserTestSlash() {
        ParsedDate pd = DateParser.parseDate("2/12/2019");
        assertEquals("2/12/2019", pd.toString());
    }

    @Test
    public void dateParserTestIso() {
        ParsedDate pd = DateParser.parseDate("2019-12-02");
        assertEquals("2019-12-02", pd.toString());
    }

    @Test
    public void dateParserTestAmerican() {
        ParsedDate pd = DateParser.parseDate("Dec 2 2019");
        assertEquals("Dec 2 2019", pd.toString());
    }

    @Test
    public void dateParserTestIllegalArguments() {
        assertThrows(IllegalArgumentException.class, () -> DateParser.parseDate("02-12-2019 1800"));
        assertThrows(IllegalArgumentException.class, () -> DateParser.parseDate("2/1/2019"));
        assertThrows(IllegalArgumentException.class, () -> DateParser.parseDate("31 Dec 2018"));
    }

}