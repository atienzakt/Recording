package record;

import java.util.ArrayList;
import java.util.List;

import model.Boar;

public class BoarRecord {
	public static List<Boar> boarList = new ArrayList<Boar>();

	public static Boar getBoar(String boarNo) {
		for (Boar b : boarList) {
			if (b.getBoarNo().equals(boarNo)) {
				return b;
			}
		}
		return null;
	}
}
