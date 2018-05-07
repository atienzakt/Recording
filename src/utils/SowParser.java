package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import model.Sow;
import record.SowRecord;

public class SowParser {

	public static void setup() throws EncryptedDocumentException, InvalidFormatException, FileNotFoundException, IOException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Workbook wb = WorkbookFactory.create(new FileInputStream("Sows.xlsx"));
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rows = sheet.rowIterator();
		if (rows.hasNext()) {
			rows.next();// headers
		}
		
		while(rows.hasNext()) {
			Row entry = rows.next();
			int counter = 0;
			
			String sowNo = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
			Sow s = new Sow(sowNo);
			
			Date birthDate = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getDateCellValue();
			s.setBirthDate(birthDate);
			String status = entry.getCell(counter++, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
			SowRecord.sowList.add(s);
			if(!status.trim().equals("")) {
				SowRecord.diseasedSowList.add(s);
				s.setStatus(status);
			}
		}
		wb.close();
	}
}
