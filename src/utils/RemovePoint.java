package utils;

public class RemovePoint {

	public static String remove(String s) {
		if(null == s) {
			return s;
		}
		return s.split("\\.").length>0?s.split("\\.")[0]:s;
	}
}
