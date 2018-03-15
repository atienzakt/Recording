package record.asofmonth;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import model.BreedingRow;
import model.FarrowingRow;
import record.BreedingRecord;
import record.FarrowingRecord;
import utils.BreedingRecordParserCSV;
import utils.FarrowingRecordParserCSV;

public class AsOfMonth {

	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, ParseException, IOException {
		BreedingRecordParserCSV.setup();
		FarrowingRecordParserCSV.setup();	
		Calendar c = Calendar.getInstance();
		c.set(2018, 0, 1);
		Date from = c.getTime();
		
		c.set(2018, 0, 31);
		Date to = c.getTime();
		
		List<FarrowingRow> farrowDates = FarrowingRecord.filterByFarrowDate(from, to);
		List<BreedingRow> breedDates = BreedingRecord.filterByBreedDate(from, to);
		
		BigDecimal numberOfBreeding = BigDecimal.valueOf(breedDates.stream().count());
		BigDecimal numberOfFarrowed = BigDecimal.valueOf(farrowDates.stream().count());
		
		BigDecimal litterSize = farrowDates.stream()
				.map(x -> new BigDecimal(x.getTotalFar()))
				.reduce(BigDecimal.ZERO,BigDecimal::add); 
		
		BigDecimal liveBirth = farrowDates.stream()
				.map(x -> new BigDecimal(x.getLive()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		
		BigDecimal weaningMortalities = farrowDates.stream()
				.map(x -> new BigDecimal(x.getMortality()))
				.reduce(BigDecimal.ZERO,BigDecimal::add);
		Calendar pregnancyFrom = Calendar.getInstance();
		pregnancyFrom.setTime(to);
		pregnancyFrom.add(Calendar.MONTH, -4);
		
		Map<String,BreedingRow> latestBreedings = new HashMap<>();
		for(BreedingRow b:BreedingRecord.filterByBreedDate(pregnancyFrom.getTime(), to)) {
			latestBreedings.put(b.getSowNo().getSowNo(), b);
		}
		
		for(BreedingRow b:latestBreedings.values()) {
			if(b.getPregnancyRemarks().equals("-RB")|| b.getPregnancyRemarks().equals("+AB")) {
				System.out.println(b.getRefNo()+ " || "+b.getSowNo().getSowNo() + " || "+ b.getPregnancyRemarks());
			}
		}
		
		
	}
}
