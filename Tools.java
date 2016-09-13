package general;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import api.QuarterData;
public class Tools {

	public static List<String> appendStringToList(List<String> ls, String s){
		List<String> result = new ArrayList<String>();
		for(String foo: ls)
			result.add(foo + s);
		return result;
	}
	public static Map<String, Double> copyMap(Map<String, Double> m){
		Map<String, Double> foo = new HashMap<String, Double>();
		for(String s: m.keySet())
			foo.put(s, m.get(s));
		return foo;
	}
	
	public static double calc(List<Datum> ls, String ratio, boolean squared){
		List<Double> dubs = new ArrayList<Double>();
		for(Datum x : ls){
			dubs.add( x.getValue(ratio));
		}
		return calcD(dubs, ratio, squared);
	}
	public static double calcD(List<Double> ls, String ratio, boolean squared){
		double result = 0;
		int count = 0;
		for(Double x : ls){
			count++;
			if(squared)
				x *=x;
			result +=x;
		}
		result /= count;
		return result;
	}
	public static void printSetToFile(File f, Set<String> ls){
		try {
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(f));
			for(String s: ls)
				fileWriter.write(s + "\n");
			fileWriter.close();
		} catch (IOException e) {
			exceptionEnd("error in printing", e);
		}
	}
	public static String joinListD(List<Double> foo){
		String x = "";
		int i = 0;
		while(i<foo.size()){
			Double d = foo.get(i);
			if(d ==null)
				x = x + "null";
			else
				x = x + d.toString();
			if(i!=foo.size()-1)
				x = x+ ",";
			i++;
		}
		return x;
	}
	public static String joinListS(List<String> foo){
		String x = "";
		int i = 0;
		while(i<foo.size()){
			x = x +foo.get(i);
			if(i!=foo.size()-1)
				x = x+ ",";
			i++;
		}
		return x;
	}
	//systematic error handling, ending the program
	public static void exceptionEnd(String code, Exception e){
		System.out.println(code);
		e.printStackTrace();
		System.exit(1);
	}
	public static void printMap(Map<?, ?> x){
		for(Object key: x.keySet()){
			System.out.println(key.toString() + " -> " + x.get(key));
		}
	}
	public static Map<String,Map<Integer, QuarterData>> convertToMap(List<QuarterData> list) {
		Map<String, Map<Integer,QuarterData>> result = new HashMap<String, Map<Integer, QuarterData>>();
		for(QuarterData x: list){
			if(!result.containsKey(x.name))
				result.put(x.name, new HashMap<Integer, QuarterData>());
			result.get(x.name).put(x.getDateNumQrt(), x);
		}
		return result;
	}
	public static Integer convertToQuarter(int year, int month){
		return year*10 + (month+2)/3;
	}
	//takes the list of raw index values and converts them to fwd percentages of length q
	public static Map<Integer, Double> convertToPerc(Map<Integer, Double> index, int q) {
		Map<Integer, Double> result = new HashMap<Integer,Double>();
		for(Integer i : index.keySet()){
			double curr = index.get(i);
			int futureDate = iterateDate(i, q);
			double fwd = curr;
			if(index.containsKey(futureDate))
				fwd = index.get(futureDate);
			result.put(i, (fwd-curr)/curr);
		}
		return result;
	}
	public static int iterateDate(int code, int i){
		int year = code/10;
		int qrt = code%10 +i;
		while(qrt>4){
			qrt-=4;
			year++;
		}
		return year *10 + qrt;
	}
	public static List<QuarterData> removeEndYear(List<QuarterData> ls, int endYr) {
		List<QuarterData> result = new ArrayList<QuarterData>();
		for(QuarterData x: ls){
			if(x.date.year<endYr)
				result.add(x);
		}
		return result;
	}
}