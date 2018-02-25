import java.io.File;

public class Main {  
    
    public static void main(String [] args){
        StockData data=null;
        Stock stock=new Stock();
        StockWeekData stockweek = new StockWeekData();
        StockPattern stockpattern = new StockPattern();

        if (args.length < 2) {
            System.out.println("Invoke with two arguments. <Stock Symbol> and <Name of file with Historical Data>");
            return;
        }

        File file = new File(args[1]);
        if (!file.exists() || file.isDirectory()) {
            System.out.println("File does not exist or is a directory.");
            return;
        }

        stock.setSymbol(args[0]);
        stock.updateStockHistory(args[1]);
        System.out.println("Symbol = " + stock.getSymbol() + ", " + "Day History Length = " + stock.getHistorylength()); 

        stockweek.addStock(stock);
        System.out.println("Symbol = " + stock.getSymbol() + ", " + "Week History Length = " + stockweek.getWeekDataLength()); 

        stockpattern.setData(stock, stockweek);
        stockpattern.generateCurrentTrend();
        System.out.println("Slope = " + stockpattern.getSlope() + ", " + "Trend = " + stockpattern.getTrend()); 
        stockpattern.checkHarami();

        return;
    }

}
