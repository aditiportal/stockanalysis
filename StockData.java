public class StockData { 
    
    private String date=null; //Format = YYYY-MM-DD
    private double open=0.0;
    private double close=0.0;
    private double high=0.0;
    private double low=0.0;
    private int volume=0;
    private double range=0;

    public void setStockData(String idate, double iopen, double iclose, double ihigh, double ilow, int ivolume) {

        this.date = idate;
        this.open = iopen;
        this.close = iclose;
        this.high = ihigh;
        this.low = ilow;
        this.volume = ivolume;

        return;
    }

    public void setStockData(String idate, double iopen, double iclose, double ihigh, double ilow, int ivolume, double irange) {

        this.date = idate;
        this.open = iopen;
        this.close = iclose;
        this.high = ihigh;
        this.low = ilow;
        this.volume = ivolume;
        this.range = irange;

        return;
    }

    public void setStockData(StockData data) {

        this.date = data.getDate();
        this.open = data.getOpen();
        this.close = data.getClose();
        this.high = data.getHigh();
        this.low = data.getLow();
        this.volume = data.getVolume();
        this.range = data.getRange();

        return;
    }

    public void resetStockData() {
        date=null;
        open=0.0;
        close=0.0;
        high=0.0;
        low=0.0;
        volume=0;
        range=0;
        return;
    }

    public void setDate(String date) {
        this.date = date;
        return;
    }

    public void setOpen(double open) {
        this.open=open;
        return;
    }

    public void setClose(double close) {
        this.close=close;
        return;
    }

    public void setHigh(double high) {
        this.high=high;
        return;
    }

    public void setLow(double low) {
        this.low=low; return;
    }

    public void setVolume(int volume) {
        this.volume=volume;
        return;
    }

    public void setRange(double range) {
        this.range=range;
        return;
    }

    public void printStockData(){
        System.out.println("Date = " + this.date + "," + "Open = " + this.open + "," + "Close = " + this.close + "," + "High = " + this.high + "," + "Low = " + this.low + "," + "Volume = " + this.volume + "," + "Range = " + this.range);
        return;
    }

    public String getDate() {
        return this.date;
    }

    public double getOpen() {
        return this.open;
    }

    public double getClose() {
        return this.close;
    }

    public double getHigh() {
        return this.high;
    }

    public double getLow() {
        return this.low;
    }

    public int getVolume() {
        return this.volume;
    }

    public double getRange() {
        return this.range;
    }

    public double getAverage() {
        return ((this.open+this.close)/2);
    }
}
    
