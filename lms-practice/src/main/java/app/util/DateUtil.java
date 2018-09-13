package app.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {
	public static LocalDateTime miliToDate(long milliseconds) {
		return Instant.ofEpochMilli(milliseconds)
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
	}
}
