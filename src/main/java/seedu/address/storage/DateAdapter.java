package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, LocalDateTime> {
    public static final String[] DATE_PARSE_FORMAT_UNTIMED_CHOICE = {"[dd-MMM-uuuu h:mma]"};

    private DateTimeFormatter dateFormat;
    
    private DateTimeFormatter setDateFormatter() {

        DateTimeFormatterBuilder formatterBuilder = new DateTimeFormatterBuilder();
        formatterBuilder.append(DateTimeFormatter.ofPattern(""));
        for(String format : DATE_PARSE_FORMAT_UNTIMED_CHOICE)
            formatterBuilder.append(DateTimeFormatter.ofPattern(format));
        DateTimeFormatter formatter = formatterBuilder.toFormatter(Locale.UK);
        return formatter;
    }
    
    @Override
    public String marshal(LocalDateTime storedData) throws Exception {
        dateFormat = setDateFormatter();
        synchronized (dateFormat) {
            return dateFormat.format(storedData);
        }
    }

    @Override
    public LocalDateTime unmarshal(String storedData) throws Exception {
        dateFormat = setDateFormatter();
        synchronized (dateFormat) {
            return LocalDateTime.parse(storedData, dateFormat);
        }
    }

}