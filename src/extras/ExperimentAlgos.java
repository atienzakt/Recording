package extras;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.BreedingRow;
import record.BreedingRecord;
import record.SowRecord;

public class ExperimentAlgos {

	public static final BigDecimal MULTIPLIER_CONSTANT = new BigDecimal(1);
	public static Map<String,BigDecimal> BreedingPerformance(boolean worstFirst) {
		
		 
		List<BreedingRow> breedingList = BreedingRecord.breedingList.stream()
				.filter( x -> !SowRecord.isDiseased(x.getSowNo().getSowNo())).collect(Collectors.toList());
		
		
		Map<String,String> parityMap = new HashMap<>();
		Map<String,BigDecimal> scoreMap = new HashMap<>();
		Map<String,BigDecimal> multiplierMap = new HashMap<>(); 
		
		//Instantiate all scores to 0
		for(BreedingRow br: breedingList) {
			scoreMap.put(br.getSowNo().getSowNo(), BigDecimal.valueOf(0));
		}
		
		for(BreedingRow br: breedingList) {
			
			//Instantiate Counters
			if(null == multiplierMap.get(br.getSowNo().getSowNo())) {
				multiplierMap.put(br.getSowNo().getSowNo(), new BigDecimal(1));
			}
			
			//If change parity subtract counter/multiplier
			if(null == parityMap.get(br.getSowNo().getSowNo())) {
				parityMap.put(br.getSowNo().getSowNo(), br.getParity());
			}
			else {
				//Exists therefore parity change
				if(!parityMap.get(br.getSowNo().getSowNo()).equals(br.getParity())) {
					multiplierMap.put(br.getSowNo().getSowNo(), multiplierMap.get(br.getSowNo().getSowNo()).multiply(MULTIPLIER_CONSTANT));
				}
			}
			
			
			
			//Scoring
			if(! (br.getPregnancyRemarks().trim().equals("+") || br.getPregnancyRemarks().trim().equals(""))) {
				if(null == scoreMap.get(br.getSowNo().getSowNo())) {
					scoreMap.put(br.getSowNo().getSowNo(), multiplierMap.get(br.getSowNo().getSowNo()));
				}
				else {
					scoreMap.put(br.getSowNo().getSowNo(), scoreMap.get(br.getSowNo().getSowNo()).add(multiplierMap.get(br.getSowNo().getSowNo()) ));
				}
			}
			
		}
		Comparator<BigDecimal> c = Comparator.naturalOrder();
		if(worstFirst) {
			c = Comparator.reverseOrder();
		}
		return scoreMap.entrySet().stream().sorted(Map.Entry.comparingByValue(c)).
				collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue,newValue)->oldValue, LinkedHashMap::new));

	}
}
