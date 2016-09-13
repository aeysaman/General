package general;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import api.Value;
import kMeansClustering.Point;

public class Read {
	public static List<Point> readDataToPoints(File f) throws IOException{
 		List<Point> result = new ArrayList<Point>();
 		BufferedReader read = new BufferedReader(new FileReader(f));
 		List<String> topLine = readTopLine(read.readLine());
 		String line;
 		while((line =read.readLine())!=null){
 			Datum foo = Datum.createDatum(line, topLine, "yyyy/mm/dd");
 			if(foo != null)
 				result.add((Point) foo);
 		}
 		read.close();
 		return result;
	}
	public static List<Integer> readTopLineToInt(String line){
		List<String> strings = readTopLine(line);
		List<Integer> ints = new ArrayList<Integer>();
		for(String s: strings)
			ints.add(Integer.parseInt(s));
		return ints;
	}
	public static List<String> readTopLine(String s){
		String[] strArray = s.split(",");
		List<String> result = new ArrayList<String>();
		for(int i = 0; i<strArray.length;i++)
			result.add(strArray[i]);
		return result;
	}
	public static Map<Integer, List<String>> readAllSecurities(File f) {
		Map<Integer, List<String>> result = new HashMap<Integer, List<String>>();
		Scanner scan = openScan(f);
		List<Integer> index = readTopLineToInt(scan.nextLine());
		for(Integer i : index)
			result.put(i, new ArrayList<String>());
		while(scan.hasNextLine()){
			String[] s = scan.nextLine().split(",");
			for(int i = 0; i<s.length; i++){
				String x = s[i];
				if(x.length()>1)
					result.get(index.get(i)).add(x);
			}
		}
		return result;
	}
	public static Map<String, Value> readTerminalValues(File f) {
		Map<String, Value> result = new HashMap<String, Value>();
		Scanner scan = openScan(f);
		while(scan.hasNextLine()){
			String[] s = scan.nextLine().split(",");
			String security = s[0] + " Equity";
			String[] date = s[1].split("/");
			Value v = new Value(security,Double.parseDouble(s[2]),Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1]) );
			result.put(security, v);
		}
		return result;
	}
	public static Scanner openScan(File f){
		Scanner scan = null;
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			Tools.exceptionEnd("error in reading index",e);
		}
		return scan;
	}
	public static Map<String, Value> readIndex(File f){
		Map<String, Value> result = new HashMap<String, Value>();
		Scanner fileReader = openScan(f);
		while (fileReader.hasNextLine()){ 
			String[] items = fileReader.nextLine().split(",");
			String[] date = items[0].split("/");
			int year = Integer.parseInt(date[2]);
			int month = Integer.parseInt(date[0]);
			int day = Integer.parseInt(date[1]);
			Double x = Double.parseDouble(items[1]);
			Value v = new Value("index", x, year, month, day);
			result.put(v.date.toString(), v);
		}
		fileReader.close();
		return result;
	}
	public static List<String> readCSVtoList(File f){
		List<String> result = new ArrayList<String>();
		Scanner fileReader = openScan(f);
		while (fileReader.hasNextLine()){ 
			String[] items = fileReader.nextLine().split(",");
			result.add(items[0]);
		}
		fileReader.close();
		return result;
	}
	public static Map<String, String> readCSVtoMap (File f){
		Map<String, String> result = new HashMap<String,String>();
		Scanner fileReader = openScan(f);
		while (fileReader.hasNextLine()){ 
			String[] items = fileReader.nextLine().split(",");
			result.put(items[0], items[1]);
		}
		fileReader.close();
		return result;
	}
	public static Set<String> readSecurities(File f){
		Set<String> result = new HashSet<String>();
		Scanner scan = openScan(f);
		scan.nextLine();
		while(scan.hasNextLine()){
			String[] line = scan.nextLine().split(",");
			for(String s: line){
				if(s.length()>5)
					result.add(s);
			}
		}
		return result;
	}
}
