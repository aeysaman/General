package general;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.QuarterData;

public class Datum {
	public static void main(String[] args){
		String n = "APPLE";
		List<String> fields = new ArrayList<String>();
		QuarterData foo = new QuarterData(fields, n, 2000, 2);
		fields.add("PE");
		fields.add("PB");
		fields.add("PC");
		fields.add("MktCap");
		foo.enterValue("PE", 20.0);
		foo.enterValue("PB", .5);
		foo.enterValue("PC", 3.0);
		System.out.println(foo.exportDataJoined(fields));
		System.out.println(foo.missingCount());
	}
	
	public String name;
	public Date date;
	public Map<String, Double> data;
	
	public Datum(String name, Date date){
		this.name = name;
		this.date = date;
		this.data = new HashMap<String, Double>();
	}
	public Datum(String name, Date date, Map<String, Double> data){
		this.name = name;
		this.date = date;
		this.data = data;
	}
	public int missingCount(List<String> fields){
		int count = 0;
		for(String f: fields)
			if(!hasValue(f))
				count++;
		return count;
	}
	public Double getValue(String s){
		if(!data.containsKey(s))
			return null;
		return data.get(s);
	}
	public boolean hasValue(String s){
		return data.get(s)!=null;
	}
	public void setValue(String s, Double d){
		data.replace(s, d);
	}
	public void enterValue(String s, Double d){
		this.data.put(s,d);
	}
	public void removeValue(String s){
		data.remove(s);
	}
	public void enterMap(Map<String, Double> x) {
		for(String f : x.keySet())
			enterValue(f, x.get(f));
	}
	public String export(List<String> fields){
		String s = "";
		for(String f: fields)
			s += ("," + getValue(f));
		return s.substring(1);
	}
	//if line contains "null" values, then null is returned 
	public static Datum createDatum(String line, List<String> fields, String DateFormat){
		Map<String, Double> foo = new HashMap<String, Double>();
		String[] split = line.split(",");
		
		Date date = null;
		String name = "";
		for(int i = 0; i<fields.size(); i++){
			String field = fields.get(i);
			String item = split[i];
			try{
				if(item.equals("null"))
					continue;
				switch (field){
				case "Date":
					date = Date.parseDate(item, DateFormat);
					break;
				case "Security":
				case "Name":
					name = item;
					break;
				default:
					foo.put(field, Double.parseDouble(item));
					break;
				}
			}
			catch(Exception e){
				System.out.println(field + "->" + item);
			}
		}
		return new Datum(name, date, foo);
	}
	public int getDateNumQrt(){
		return date.getDateNumQrt();
	}
	public List<String> exportData(List<String> index){
		List<String> result = new ArrayList<String>();
		for(String s: index){
			try{
				result.add(Double.toString(getValue(s)));
			}
			catch(NullPointerException e){
				result.add("null");
			}
		}
		return result;
	}
	public String exportDataJoined(List<String> list) {
		List<String> foo = exportData(list);
		return String.join(",", foo);
	}
}
