import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class LocalTimeConverter implements AttributeConverter<LocalTime, String> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public String convertToDatabaseColumn(LocalTime localTime) {
        if (localTime == null) {
            return null;
        }
        return localTime.format(formatter);
    }

    @Override
    public LocalTime convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return LocalTime.parse(dbData, formatter);
    }
}