package general;

public class Date {
	public int year;
	public int month;
	public int day;
	
	public Date(int year, int month, int day){
		this.year = year;
		this.month = month;
		this.day = day;
	}
	public Date(int year, int quarter){
		this.year = year;
		this.month = quarter * 3 -2;
		this.day = 1;
	}
	public static Date parseDate(String s, String format){
		String[] split = null;
		if(format.contains("/"))	
			split = s.split("/");
		else if(format.contains("-"))
			split = s.split("-");
		Integer[] foo = new Integer[3];
		for(int i = 0; i<3; i++)
			foo[i] = Integer.parseInt(split[i]);
		switch(format.replaceAll("/|-", "")){
			case "yyyymmdd":
				return new Date(foo[0], foo[1], foo[2]);
			case "mmddyyyy":
				return new Date(foo[2], foo[0], foo[1]);
			default:
				return null;
		}
	}
	public int getQuarter(){
		return (month +2)/3;
	}
	public String toString(){
		return String.format("%d/%d/%d", month, day, year);
	}
	public int getDateNumQrt(){
		return year*10 + getQuarter();
	}
	public double getDateNumQrtDouble() {
		return(double)year +((double)getQuarter()-1)/4;
	}
	public String iterateDownDateString(int i){
		int d = day -i;
		int m = month;
		int y = year;
		if(d<1){
			m--;
			d+=30;
		}
		if(m<1){
			y--;
			m+=12;
		}
		return String.format("%s/%s/%s", y, m, d);
	}
	
}
