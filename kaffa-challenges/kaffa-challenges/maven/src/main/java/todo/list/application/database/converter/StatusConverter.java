package todo.list.application.database.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import todo.list.application.database.shared.enums.Status;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        return dbData == null ? null : Status.valueOf(dbData);
    }
}
