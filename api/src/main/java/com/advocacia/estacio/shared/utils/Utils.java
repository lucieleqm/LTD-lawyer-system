//package com.advocacia.estacio.utils;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//public class Utils {
//
//	public static LocalDate localDateToString(String string) {
//		String[] stringFormat = string.split("/");
//		if(stringFormat[0].length() == 1) {
//			stringFormat[0] = "0" + stringFormat[0];
//		}
//		if(stringFormat[1].length() == 1) {
//			stringFormat[1] = "0" + stringFormat[1];
//		}
//		string = String.format("%s/%s/%s", stringFormat[0], stringFormat[1], stringFormat[2]);
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//		return LocalDate.parse(string, formatter);
//	}
//
//	public static LocalDateTime stringToLocalDateTime(String string) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//		return LocalDateTime.parse(string, formatter);
//	}
//
//	public static String localDateToString(LocalDate localDate) {
//		return String.format("%s/%s/%s", localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
//	}
//
//	public static String localDateTimeToString(LocalDateTime localDateTime) {
//		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//		return localDateTime.format(format);
//	}
//}
