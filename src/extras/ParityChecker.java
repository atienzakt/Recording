package extras;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import model.BreedingRow;
import model.Sow;
import record.BreedingRecord;
import record.FarrowingRecord;
import record.SowRecord;

public class ParityChecker {

	public static Map<String,Integer> parityCheck() {
		System.out.println("Parity Check");
		List<BreedingRow> breedingList = BreedingRecord.breedingList.stream()
				.filter( x -> !SowRecord.isDiseased(x.getSowNo().getSowNo())).collect(Collectors.toList());
		Map<String,Integer> parityMap = new HashMap<>();
		
		for(Sow s:SowRecord.sowList.stream().filter( s -> !SowRecord.isDiseased(s.getSowNo())).collect(Collectors.toList())) {
			parityMap.put(s.getSowNo(), -1);
		}
		
		for(BreedingRow br:breedingList) {
			
			if(null != SowRecord.getSow(br.getSowNo().getSowNo())) {
				if(-1 == parityMap.get(br.getSowNo().getSowNo())) {
					parityMap.put(br.getSowNo().getSowNo(), 0);
				}
				//System.out.println(br.getPregnancyRemarks() + " |||| "+ FarrowingRecord.findRefNo(br.getRefNo()));
				if(br.getPregnancyRemarks().trim().equals("+") && null != FarrowingRecord.findRefNo(br.getRefNo())) {
					int parity = parityMap.get(br.getSowNo().getSowNo().trim());
					parityMap.put(br.getSowNo().getSowNo(), parity+1);
				}
			}
			else {
				System.out.println("Sow Not Found: "+br.getRefNo()+ " || "+br.getSowNo().getSowNo());
			}
		}
		
		Map<String,Integer> result = parityMap;
		for(Entry<String, Integer> s:result.entrySet()) {
			if(!s.getValue().toString().equals(SowRecord.getSow(s.getKey()).getParity())) {
			 System.out.println(s.getKey()+ " ||| "+s.getValue() + " ||| "+SowRecord.getSow(s.getKey()).getParity());
			}
		}
		Collections.reverse(BreedingRecord.breedingList);
		
		for(Entry<String, Integer> s:result.entrySet()) {
			for(BreedingRow br:BreedingRecord.breedingList) {
				
				if(br.getSowNo().getSowNo().equals(s.getKey())) {
					//System.out.println(" |||||| " +br.getRefNo()+ " ||| "+br.getSowNo().getSowNo() + " |||| "+ s.getKey());
					Integer g = Integer.sum(s.getValue(), -1);
					if(s.getValue().equals(0) && br.getParity().trim().equalsIgnoreCase("gilt") && (!br.getPregnancyRemarks().equals("+") || null == FarrowingRecord.findRefNo(br.getRefNo()))) {
						
					}
					else if(s.getValue().toString().contains(br.getParity().trim()) && (!br.getPregnancyRemarks().equals("+") || null == FarrowingRecord.findRefNo(br.getRefNo()))) {

					}
					else if(g.toString().contains(br.getParity().trim()) && br.getPregnancyRemarks().equals("+") && null != FarrowingRecord.findRefNo(br.getRefNo())){
						
					}
					else if(s.getValue().equals(1) && br.getParity().trim().equalsIgnoreCase("gilt") && br.getPregnancyRemarks().equals("+") && null != FarrowingRecord.findRefNo(br.getRefNo())) {
						
					}
					else {
						System.out.println(br.getSowNo().getSowNo() + " ||| "+br.getParity() + " ||| "+s.getValue());

					}
					break;
				}
				
			}
		}
		

		Collections.reverse(BreedingRecord.breedingList);
		return parityMap;
	}
}
