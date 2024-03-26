import java.util.*;
import java.lang.reflect.*;
import java.text.DecimalFormat;

/**
 * Represents a stock in the SafeTrade project
 */
public class Stock
{
    public static DecimalFormat       money = new DecimalFormat("0.00");

    private String                    stockSymbol;
    private String                    companyName;
    private double                    loPrice, hiPrice, lastPrice;
    private int                       volume;
    private PriorityQueue<TradeOrder> buyOrders, sellOrders;

    public Stock(String s, String n, double p)
    {
        stockSymbol = s;
        companyName = n;
        lastPrice = p;
        loPrice = p;
        hiPrice = p;
        volume = 0;
        buyOrders = new PriorityQueue<TradeOrder>(new PriceComparator());
        sellOrders = new PriorityQueue<TradeOrder>(new PriceComparator(false));
    }


    /**
     * dsfadkfjsa;kldfas;kldf
     * 
     * @return jdsfklasdf sdafkjsdflkds;af
     */
    public String getQuote()
    {
        String firstLine = companyName + " (" + stockSymbol + ")";
        String secondLine =
            "Price: " + lastPrice + "  hi: " + hiPrice + "  lo: " + loPrice + "  vol: " + volume;
        // String thirdLine = "Ask: " + buyOrders.peek().getPrice() + " size: "
        // + buyOrders.peek().getShares() + " Bid: " +
        // sellOrders.peek().getPrice() + " size: "
        // + sellOrders.peek().getShares();
        String thirdLine = "Ask: ";
        if (buyOrders.peek() != null)
        {
            thirdLine += buyOrders.peek().getPrice() + " size: " + buyOrders.peek().getShares();
        }
        else
        {
            thirdLine += "none";
        }

        thirdLine += "  Bid: ";
        if (sellOrders.peek() != null)
        {
            thirdLine += sellOrders.peek().getPrice() + " size: " + sellOrders.peek().getShares();
        }
        else
        {
            thirdLine += "none";
        }

        return firstLine + "\n" + secondLine + "\n" + thirdLine + "\n";

    }


    public void placeOrder(TradeOrder order)
    {
        String msg = "New order: ";

        if (order.isSell())
        {
            buyOrders.add(order);
            msg += "Sell " + order.getSymbol() + "(" + companyName + ")" + "\n";
            msg += order.getShares() + "shares at ";
            if (order.isMarket())
            {
                msg += "market";
            }
            else
            {
                msg += order.getPrice();
            }
            buyOrders.add(order);

        }

        if (order.isBuy())
        {
            buyOrders.add(order);
            msg += "Buy " + order.getSymbol() + "(" + companyName + ")" + "\n";
            msg += order.getShares() + "shares at ";
            if (order.isMarket())
            {
                msg += "market";
            }
            else
            {
                msg += order.getPrice();
            }
            buyOrders.add(order);
        }
        order.getTrader().receiveMessage(msg);
        executeOrders();

    }


    protected void executeOrders()
    {

    }

    //
    // The following are for test purposes only
    //


    protected String getStockSymbol()
    {
        return stockSymbol;
    }


    protected String getCompanyName()
    {
        return companyName;
    }


    protected double getLoPrice()
    {
        return loPrice;
    }


    protected double getHiPrice()
    {
        return hiPrice;
    }


    protected double getLastPrice()
    {
        return lastPrice;
    }


    protected int getVolume()
    {
        return volume;
    }


    protected PriorityQueue<TradeOrder> getBuyOrders()
    {
        return buyOrders;
    }


    protected PriorityQueue<TradeOrder> getSellOrders()
    {
        return sellOrders;
    }


    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this Stock.
     */
    public String toString()
    {
        String str = this.getClass().getName() + "[";
        String separator = "";

        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields)
        {
            try
            {
                str += separator + field.getType().getName() + " " + field.getName() + ":"
                    + field.get(this);
            }
            catch (IllegalAccessException ex)
            {
                System.out.println(ex);
            }

            separator = ", ";
        }

        return str + "]";
    }
}
