package utils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import model.Boar;
import model.BreedingRow;
import model.Sow;
import record.BoarRecord;
import record.BreedingRecord;
import record.SowRecord;

public class BreedingRecordParserCSV {

	public static void setup() throws ParseException, EncryptedDocumentException, InvalidFormatException, IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Workbook wb = WorkbookFactory.create(new File("Breeding.xlsx"));
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rows = sheet.rowIterator();
		if (rows.hasNext()) {
			rows.next();// headers
		}
		while (rows.hasNext()) {
			Row entry = rows.next();
			int counter = 0;
			BreedingRow br = new BreedingRow();
			String refNo = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			if(refNo.trim().equals(""))
				break;
			br.setRefNo(refNo);

			String sowNo = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			if(sowNo.trim().equals("")) {
				break;
			}
			if (SowRecord.getSow(sowNo) == null) {
				SowRecord.sowList.add(new Sow(sowNo));
			}
			br.setSowNo(SowRecord.getSow(sowNo));

			String sowBreed = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			if(null == SowRecord.getSow(sowNo).getBreed()) {
				SowRecord.getSow(sowNo).setBreed(sowBreed);
			}
			br.setBreed(sowBreed);

			Cell dateWeaned = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			if ("".equals(dateWeaned.toString())) {
				br.setDateWeaned(null);
			} else {
				br.setDateWeaned(sdf.parse(sdf.format(dateWeaned.getDateCellValue())));
			}

			Cell dateBreed = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			if ("".equals(dateBreed.toString())) {
				br.setDateBreed(null);
			} else {
				br.setDateBreed(sdf.parse(sdf.format(dateBreed.getDateCellValue())));
			}

			String fpDays = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			br.setFpDays(fpDays);

			String parity = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			br.setParity(parity);

			String boarNo = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			List<Boar> usedBoar = new ArrayList<>();
			for(String boars: boarNo.split("/|\\\\"))
			{
				if (BoarRecord.getBoar(boars) == null) {
					BoarRecord.boarList.add(new Boar(boars));
				}
				usedBoar.add(BoarRecord.getBoar(boars));
			}
			br.setBoarUsed(usedBoar);

			String breedingTime = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			br.setBreedingTime(breedingTime);

			String pregnancyRemarks = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			br.setPregnancyRemarks(pregnancyRemarks);

			Cell pregnancyRemarksDate = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			if ("".equals(pregnancyRemarksDate.toString())) {
				br.setPregnancyRemarksDate(null);
			} else {
				br.setPregnancyRemarksDate(sdf.parse(sdf.format(pregnancyRemarksDate.getDateCellValue())));
			}

			Cell farrowDateDue = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			if ("".equals(farrowDateDue.toString())) {
				br.setFarrowDueDate(null);
			} else {
				br.setFarrowDueDate(sdf.parse(sdf.format(farrowDateDue.getDateCellValue())));
			}

			Cell farrowDateActual = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			if ("".equals(farrowDateActual.toString())) {
				br.setFarrowActualDate(null);
			} else {
				br.setFarrowActualDate(sdf.parse(sdf.format(farrowDateActual.getDateCellValue())));
			}

			String prevBreedingNo = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			br.setPrevBreedingRefNo(prevBreedingNo);

			String prevBreedingResult = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			br.setPrevBreedingResult(prevBreedingResult);

			String liveBirth = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			br.setLive(liveBirth);

			String comments = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			br.setComments(comments);
			
			String status = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			if(!status.trim().equals("") && !SowRecord.isDiseased(sowNo)) {
				SowRecord.diseasedSowList2.add(SowRecord.getSow(sowNo));
			}
			BreedingRecord.breedingList.add(br);

		}
		wb.close();
	}
}
