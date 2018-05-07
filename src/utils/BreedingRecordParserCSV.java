package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import model.Boar;
import model.BreedingRow;
import model.Sow;
import record.BoarRecord;
import record.BreedingRecord;
import record.SowRecord;

public class BreedingRecordParserCSV {

	public static void setup() throws EncryptedDocumentException, InvalidFormatException, IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Workbook wb = WorkbookFactory.create(new FileInputStream("Breeding.xlsx"));
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
				try {
					br.setDateWeaned(sdf.parse(sdf.format(dateWeaned.getDateCellValue())));
				} catch (IllegalStateException  | ParseException e) {
					Alert a = new Alert(AlertType.ERROR);
					a.setTitle("Error In Input for Breeding");
					a.setHeaderText("Error in Weaned Date in Breeding, should only be date or blank");
					StringBuilder sb = new StringBuilder();
					for(StackTraceElement ste: e.getStackTrace()) {
						sb.append(ste.toString());
						sb.append(System.lineSeparator());
					}
					a.setContentText(sb.toString());
					Optional<ButtonType> exit = a.showAndWait();
					if(exit.isPresent() || !exit.isPresent()) {
						System.exit(0);
					}
					
					
				}
			}

			Cell dateBreed = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			if ("".equals(dateBreed.toString())) {
				br.setDateBreed(null);
			} else {
				try {
					br.setDateBreed(sdf.parse(sdf.format(dateBreed.getDateCellValue())));
				} catch (IllegalStateException  |ParseException e) {
					Alert a = new Alert(AlertType.ERROR);
					a.setTitle("Error In Input for Breeding");
					a.setHeaderText("Error in Date Breed in Breeding, should only be date or blank");
					StringBuilder sb = new StringBuilder();
					for(StackTraceElement ste: e.getStackTrace()) {
						sb.append(ste.toString());
						sb.append(System.lineSeparator());
					}
					a.setContentText(sb.toString());
					Optional<ButtonType> exit = a.showAndWait();
					if(exit.isPresent() || !exit.isPresent()) {
						System.exit(0);
					}
				}
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
				try {
					br.setPregnancyRemarksDate(sdf.parse(sdf.format(pregnancyRemarksDate.getDateCellValue())));
				} catch (IllegalStateException  |ParseException e) {
					Alert a = new Alert(AlertType.ERROR);
					a.setTitle("Error In Input for Breeding");
					a.setHeaderText("Error in Pregnancy Remarks Date in Breeding, should only be date or blank");
					StringBuilder sb = new StringBuilder();
					for(StackTraceElement ste: e.getStackTrace()) {
						sb.append(ste.toString());
						sb.append(System.lineSeparator());
					}
					a.setContentText(sb.toString());
					Optional<ButtonType> exit = a.showAndWait();
					if(exit.isPresent() || !exit.isPresent()) {
						System.exit(0);
					}
				}
			}

			Cell farrowDateDue = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			if ("".equals(farrowDateDue.toString())) {
				br.setFarrowDueDate(null);
			} else {
				try {
					br.setFarrowDueDate(sdf.parse(sdf.format(farrowDateDue.getDateCellValue())));
				} catch (IllegalStateException  |ParseException e) {
					Alert a = new Alert(AlertType.ERROR);
					a.setTitle("Error In Input for Breeding");
					a.setHeaderText("Error in Farrow Due Date in Breeding, should only be date or blank");
					StringBuilder sb = new StringBuilder();
					for(StackTraceElement ste: e.getStackTrace()) {
						sb.append(ste.toString());
						sb.append(System.lineSeparator());
					}
					a.setContentText(sb.toString());
					Optional<ButtonType> exit = a.showAndWait();
					if(exit.isPresent() || !exit.isPresent()) {
						System.exit(0);
					}
				}
			}

			Cell farrowDateActual = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			if ("".equals(farrowDateActual.toString())) {
				br.setFarrowActualDate(null);
			} else {
				try {
					br.setFarrowActualDate(sdf.parse(sdf.format(farrowDateActual.getDateCellValue())));
				} catch (IllegalStateException  |ParseException e) {
					Alert a = new Alert(AlertType.ERROR);
					a.setTitle("Error In Input for Breeding");
					a.setHeaderText("Error in Farrow Actual Date in Breeding, should only be date or blank");
					StringBuilder sb = new StringBuilder();
					for(StackTraceElement ste: e.getStackTrace()) {
						sb.append(ste.toString());
						sb.append(System.lineSeparator());
					}
					a.setContentText(sb.toString());
					Optional<ButtonType> exit = a.showAndWait();
					if(exit.isPresent() || !exit.isPresent()) {
						System.exit(0);
					}
				}
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
				SowRecord.diseasedSowList.add(SowRecord.getSow(sowNo));
				Sow deadSow =  SowRecord.getSow(sowNo);
				if(status.toLowerCase().contains("cull") && (deadSow.getStatus()== null)) {
					deadSow.setStatus("Cull");
				}
				else if( (status.toLowerCase().contains("disease") || status.toLowerCase().contains("mortal")) 
						&& (deadSow.getStatus()== null)) {
					deadSow.setStatus("Deceased");
				}
				else if(null!=deadSow.getStatus()) {
					System.out.println("Breeding Duplicate Row Tagged as deceased, not a big concern: "+refNo + " || For Sow: "+sowNo);
				}
			}
			BreedingRecord.breedingList.add(br);

		}
		wb.close();
	}
}
