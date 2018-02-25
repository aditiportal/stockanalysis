import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Stock { 
    
    private String symbol=null; 
    private int daydatalength = 0;
    private ArrayList<StockData> daydata = new ArrayList<StockData>(500);
    private int currentdaydataindex = 0;
    

    public void Stock(String symbol) {
        this.setSymbol(symbol);
    }

    public void setSymbol(String isymbol) {
        this.symbol = isymbol;
    }
    
    public String getSymbol(){
        return this.symbol;
    }

    public int getHistorylength(){
        return this.daydatalength;
    }
    
    public void addStockData(StockData data) {
        StockData newdata = new StockData();

        newdata.setStockData(data);
        this.daydata.add(newdata);
        this.daydatalength++;
        return;
    }

    public StockData getFirstStockDayData() {
        this.currentdaydataindex=0;
        return this.daydata.get(this.currentdaydataindex);
    }

    public boolean setStockDataStart(int position) {
        if (position-1>=getHistorylength())
            return false;
        this.currentdaydataindex=position-1;
        return true;
    }

    public StockData getNextStockDayData() {
        this.currentdaydataindex++;
        if (this.currentdaydataindex >= this.daydatalength)
            return null;
        else
            return this.daydata.get(this.currentdaydataindex);
    }

    public StockData getStockDayDataForDate(String datestring) {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date matchdate = null;
        Date currentdate = null;

        try {
            matchdate = dateformat.parse(datestring);
        } catch (ParseException e) {
            Logger log = Logger.getLogger(Stock.class.getName()); 
            log.log(Level.SEVERE, e.toString(), e);
            return null;
        }

        for (int index=0; index < this.daydatalength; index++) {
            try {
                currentdate = dateformat.parse(this.daydata.get(index).getDate());
            } catch (ParseException e) {
                Logger log = Logger.getLogger(Stock.class.getName()); 
                log.log(Level.SEVERE, e.toString(), e);
                return null;
            }
            // System.out.println(matchdate + ",  " + currentdate + ", " + this.daydata.get(index).getDate() + ", High = " + this.daydata.get(index).getHigh());

            if (currentdate.equals(matchdate))
                return this.daydata.get(index);
        }
        return null;
    }

    // Update History from CSV data file from yahoo in format - Date,Open,High,Low,Close,Adj Close,Volume
    public void updateStockHistory(String csvdatafile) {  

        String line=null;
        StockData data = new StockData();
        int lineno=0;

        try { 
            
            FileReader file=null;

            file = new FileReader(csvdatafile);

            BufferedReader bufreader = new BufferedReader(file);  
            
            line = bufreader.readLine(); lineno++;
            while((line = bufreader.readLine()) != null){ 
                lineno++;

                String[] stockinfo = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                //System.out.println(stockinfo[0] + " " + stockinfo[1] + " " + stockinfo[2] + " " + stockinfo[3] + " " + stockinfo[4] + " " + stockinfo[5]);
                if (stockinfo[1].equals("null")) {
                    System.out.println("Skipping "+stockinfo[0] + " " + stockinfo[1] + " " + stockinfo[2] + " " + stockinfo[3] + " " + stockinfo[4] + " " + stockinfo[5]);
                    continue;
                }

                data.setStockData(stockinfo[0],
                                  Double.parseDouble(stockinfo[1]),
                                  Double.parseDouble(stockinfo[4]),
                                  Double.parseDouble(stockinfo[2]),
                                  Double.parseDouble(stockinfo[3]),
                                  Integer.parseInt(stockinfo[6]));
                //data.printStockData();

                this.addStockData(data);
                //System.out.println("History = " + this.getHistorylength()); 
            }
            
        } catch (IOException e) {
            Logger log = Logger.getLogger(Stock.class.getName()); 
            log.log(Level.SEVERE, e.toString(), e);
            return;
        }
        
    }
}
