package utils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import record.SowRecord;

public class DiseasedParserCSV {

	public static void setup() throws EncryptedDocumentException, InvalidFormatException, IOException {
		Workbook wb = WorkbookFactory.create(new File("DiseasedSows.xlsx"));
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rows = sheet.rowIterator();
		if (rows.hasNext()) {
			rows.next();// headers
		}
		while (rows.hasNext()) {
			Row entry = rows.next();
			String sowNo = entry.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
			if(null!= SowRecord.getSow(sowNo)) {
				SowRecord.diseasedSowList.add(SowRecord.getSow(sowNo));
			}
			else
			{
				System.out.println("Non Existent Sow is Diseased: "+sowNo);
			}
		}
		wb.close();
	}
}
