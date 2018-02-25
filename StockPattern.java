import java.lang.Math;
import java.util.*;

//This class takes the stock daily data and stock weekly data and identifies current patterns

public class StockPattern {
    private Stock stockdata = null;
    private StockWeekData stockweekdata = null;
    private double curslope;
    private int curtrend;

    public void setData(Stock istockdata, StockWeekData istockweekdata) {
        stockdata = istockdata;
        stockweekdata = istockweekdata;
        return;
    }

    public void generateCurrentRegressionSlope(){
        double sumx = 0;
        double sumy = 0;    

        double[] middle = new double[10];

    
        this.stockdata.setStockDataStart(this.stockdata.getHistorylength()-10);
        for (int x = 0; x < 10; x++) {
            middle[x] = this.stockdata.getNextStockDayData().getAverage();
            //System.out.println("Middle = " + middle[x]);
        }

        for (int m = 0; m < 10; m++){
            sumx += middle[m];
            sumy += m;    
        }

        double xbar = sumx / 10;
        double ybar = sumy / 10;

        double xsd = 0.0;
        double ysd = 0.0;

        for (int m = 0; m < 10; m++){
            xsd += (middle[m] - xbar) * (middle[m] - xbar);
            ysd += (m - ybar) * (m - ybar);
        }

        xsd = xsd / 10;
        ysd = ysd / 10;

        xsd = Math.sqrt(xsd);
        ysd = Math.sqrt(ysd);

        double zx = 0.0;
        double zy = 0.0;
        double slope = 0.0;

        for (int m = 0; m < 10; m++){
            zx= (middle[m] - xbar)/xsd;
            zy= (m-ybar)/ysd;
            slope += zx * zy;
        }

        slope =     slope / 9;
        this.curslope = (slope) * (ysd/xsd);

        //System.out.println("Current Slope = " + this.curslope);
    }

    public double getSlope(){
        return this.curslope;
    }    

    //-1 means downtrending, 1 means uptrending, and 0 means neither
    public void generateCurrentTrend() {
        this.generateCurrentRegressionSlope();

        if (this.curslope < 0 && Math.abs(this.curslope) > 5)
            this.curtrend = -1;
        else if (this.curslope > 0 && Math.abs(this.curslope) > 5)
            this.curtrend = 1;
        else
            this.curtrend = 0;
        //System.out.println("Current Trend= " + this.curtrend);
    }

    public int getTrend(){
        return this.curtrend;
    }    

    public void checkHarami(){
        StockData[] days = new StockData[2];

        this.generateCurrentTrend();

        this.stockdata.setStockDataStart(this.stockdata.getHistorylength()-2);
        for (int x = 0; x < 2; x++) {
            days[x] = this.stockdata.getNextStockDayData();
            //days[x].printStockData();
        }
        if ((this.getTrend() == -1) &&
            (days[0].getOpen() > days[0].getClose() && days[1].getClose() > days[1].getOpen()) && 
            (days[0].getOpen() > days[1].getClose() && days[0].getClose() < days[1].getOpen()))
            System.out.println("The Bullish Harami pattern was recognized" + "- this indicates a higher probability for reversal");
        else if ((this.getTrend() == 1) &&
                 (days[0].getClose() > days[0].getOpen() && days[1].getClose() < days[1].getOpen()) && 
                 (days[0].getClose() > days[1].getHigh() && days[0].getOpen() < days[1].getClose()))
                 System.out.println("The Bearish Harami pattern was recognized" + "- this indicates a higher probability for reversal");                
	else 
            System.out.println("No Harami pattern was recognized");
    }
}

