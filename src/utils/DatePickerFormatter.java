package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.util.StringConverter;

public final class DatePickerFormatter {

	private static StringConverter<LocalDate> sc;

	public static StringConverter<LocalDate> get() {
		if (sc == null) {
			sc = new StringConverter<LocalDate>() {
				private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

				@Override
				public String toString(LocalDate localDate) {
					if (localDate == null)
						return "";
					return dateTimeFormatter.format(localDate);
				}

				@Override
				public LocalDate fromString(String dateString) {
					if (dateString == null || dateString.trim().isEmpty()) {
						return null;
					}
					return LocalDate.parse(dateString, dateTimeFormatter);
				}
			};
		}
		return sc;
	}
}
