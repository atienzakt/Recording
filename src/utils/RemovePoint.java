package utils;

public class RemovePoint {

	public static String remove(String s) {
		return s.split("\\.").length>0?s.split("\\.")[0]:s;
	}
}
