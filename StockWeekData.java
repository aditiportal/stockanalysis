import java.util.ArrayList;
import java.util.Calendar;

//Create high and low and average volume for each week

public class StockWeekData {

	private String symbol=null; 
	private ArrayList<StockData> weekdata = new ArrayList<StockData>(52);
	private int weekdatalength = 0;
	private int currentweekdataindex = 0;
 

	//public static ArrayList<StockData> getweekdata (){
		//return this.weekdata;
	//}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSymbol(){
		return this.symbol;
	}

	public int getWeekDataLength(){
		return this.weekdatalength;
	}
	
	public void addStockWeekData(StockData data) {
		StockData newdata = new StockData();

		newdata.setStockData(data);
		this.weekdata.add(newdata);
		this.weekdatalength++;
		return;
	}

	public StockData getFirstStockWeekData() {
		this.currentweekdataindex=0;
		return this.weekdata.get(this.currentweekdataindex);
	}

	public StockData getNextStockWeekData() {
		this.currentweekdataindex++;
		if (this.currentweekdataindex >= this.weekdatalength)
			return null;
		else
			return this.weekdata.get(this.currentweekdataindex);
	}

	public void addStock(Stock stock) {

		StockData weekData = new StockData();
		StockData dailyData = null, nextDailyData=null;
		double highesthigh, lowestlow;
		int volumetotal, m, prevday=7, curday;

		this.setSymbol(stock.getSymbol());

		dailyData = stock.getFirstStockDayData();

		while (dailyData != null) {
			highesthigh = volumetotal = 0;
			lowestlow = 999999999;
			
			weekData.setOpen(dailyData.getOpen());

			do {
				if (nextDailyData != null) {
					dailyData = nextDailyData;
					nextDailyData = null;
				}

				if (dailyData.getHigh() > highesthigh)
					highesthigh = dailyData.getHigh();
				if (dailyData.getLow() < lowestlow)
					lowestlow = dailyData.getLow();
				volumetotal += dailyData.getVolume();

				nextDailyData = stock.getNextStockDayData();
			} while ((nextDailyData != null) && 
				 (this.dayOfWeek(dailyData.getDate()) < this.dayOfWeek(nextDailyData.getDate())));

			weekData.setClose(dailyData.getClose());
			weekData.setHigh(highesthigh);
			weekData.setLow(lowestlow);
			weekData.setVolume(volumetotal/5);
			weekData.setRange(highesthigh - lowestlow);

			//weekData.printStockData();

			this.addStockWeekData(weekData);

			dailyData = nextDailyData;
			nextDailyData = null;
		}
		return;

	}


	public int dayOfWeek(String date) {

		String[] dateinfo = date.split("-");
		//System.out.println(dateinfo[0] + " " + dateinfo[1] + " " + dateinfo[2]);

		Calendar cal = Calendar.getInstance();

		cal.set(Integer.parseInt(dateinfo[0]),
			Integer.parseInt(dateinfo[1])-1,
			Integer.parseInt(dateinfo[2]));
		//System.out.println("Day of Week : " + cal.get(Calendar.DAY_OF_WEEK));

		return(cal.get(Calendar.DAY_OF_WEEK));
	}

}




