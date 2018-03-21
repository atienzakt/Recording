package utils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import model.Boar;
import model.FarrowingRow;
import record.BoarRecord;
import record.BreedingRecord;
import record.FarrowingRecord;
import record.SowRecord;

public class FarrowingRecordParserCSV {

	public static void setup() throws ParseException, IOException, EncryptedDocumentException, InvalidFormatException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Workbook wb = WorkbookFactory.create(new File("Farrowing.xlsx"));
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rows = sheet.rowIterator();
		if (rows.hasNext()) {
			rows.next();// headers
		}
		while (rows.hasNext()) {
			Row entry = rows.next();
			int counter = 0;
			FarrowingRow f = new FarrowingRow();
			
			String refNo = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			if(refNo.trim().equals(""))
				break;
			f.setRefNo(refNo);

			Cell farDate = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			f.setFarDate("".equals(farDate.toString()) ? null : (sdf.parse(sdf.format(farDate.getDateCellValue()))));

			String sowNo = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			f.setSowNo(SowRecord.getSow(sowNo));

			String breed = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			f.setBreed(breed);

			String boarNo = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			List<Boar> usedBoar = new ArrayList<>();
			for(String boars: boarNo.split("/|\\\\"))
			{
				if (BoarRecord.getBoar(boars) == null) {
					System.out.println("Ref No: "+ refNo + " || Boar Has no Reference In Breeding");
					BoarRecord.boarList.add(new Boar(boars));
				}
				usedBoar.add(BoarRecord.getBoar(boars));
			}
			f.setBoarUsed(usedBoar);
			
			String totalFar = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			f.setTotalFar(totalFar);

			String liveBirth = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			f.setLive(liveBirth);

			String female = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			f.setFemale(female);

			String male = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			f.setMale(male);

			String stillbirth = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			f.setSb(stillbirth);

			String mummified = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			f.setMm(mummified);

			String abw = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			f.setAbw(abw);

			String mortality = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			f.setMortality(mortality.trim().equals("")?"0":mortality);

			Cell weanDate = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			f.setWeanDate("".equals(weanDate.toString()) ? null : (sdf.parse(sdf.format(weanDate.getDateCellValue()))));

			String weanCount = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			f.setTotalWean(weanCount.trim().equals("")?"0":weanCount);

			String weanAge = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			f.setAgeWean(weanAge.trim().equals("")?"0":weanAge);

			String aww = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			f.setAww(aww.trim().equals("")?"0":aww);

			String remarks = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			f.setComments(remarks);
			
			if((remarks.toLowerCase().contains("disease") 
					|| remarks.toLowerCase().contains("cull")) && !SowRecord.isDiseased(sowNo)) {
				SowRecord.diseasedSowList.add(SowRecord.getSow(sowNo));
			}

			f.setBreedingRow(BreedingRecord.findRefNo(refNo));

			FarrowingRecord.farrowingList.add(f);

		}
		wb.close();
	}
}
