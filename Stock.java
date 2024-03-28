import java.util.*;
import java.lang.reflect.*;
import java.text.DecimalFormat;

/**
 * StockExchange Lab
 *
 * @author Aravind and Shreyas
 * @version March 2024
 * @author Period: 11
 * @author Assignment: StockExchange Lab
 * @author Sources: None
 */
public class Stock
{
    /**
     *decimal format
     */
    private static DecimalFormat       money = new DecimalFormat("0.00");

    private String                    stockSymbol;
    private String                    companyName;
    private double                    loPrice;
    private double                    hiPrice;
    private double                    lastPrice;
    private int                       volume;
    private PriorityQueue<TradeOrder> buyOrders;
    private PriorityQueue<TradeOrder> sellOrders;

    /**
     * the snp
     * @param s s
     * @param n n 
     * @param p p
     */
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
     * getquote
     * 
     * @return quote
     */
    public String getQuote()
    {
        String firstLine = companyName + " (" + stockSymbol + ")";
        String secondLine =
            "Price: " + lastPrice + "  hi: " + 
                hiPrice + "  lo: " + loPrice + "  vol: " + volume;
        String thirdLine = "Ask: ";
        if (buyOrders.peek() != null)
        {
            thirdLine += money.format(buyOrders.peek().getPrice())
                 + " size: " + buyOrders.peek().getShares();
        }
        else
        {
            thirdLine += "none";
        }

        thirdLine += "  Bid: ";
        if (sellOrders.peek() != null)
        {
            thirdLine += "$" + money.format(sellOrders.peek().getPrice())
                + " size: " + sellOrders.peek().getShares();
        }
        else
        {
            thirdLine += "none";
        }

        return firstLine + "\n" + secondLine + "\n" + thirdLine + "\n";

    }


    /**
     * po
     * 
     * @param order
     *            order
     */
    public void placeOrder(TradeOrder order)
    {
        String msg = "New order: ";

        if (order == null)
        {
            return;
        }

        if (order.isSell())
        {
            sellOrders.add(order);
            msg += "Sell " + order.getSymbol() + " (" + companyName + ")" + "\n";
            msg += order.getShares() + " shares at ";
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
            msg += "Buy " + order.getSymbol() + " (" + companyName + ")" + "\n";
            msg += order.getShares() + " shares at ";
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


    /**
     * reutnr s
     */
    protected void executeOrders()
    {
        System.out.println("Executing orders");
        System.out.println("Sell orders: " + sellOrders.toString());
        System.out.println("Buy orders: " + buyOrders.toString());
        while (!sellOrders.isEmpty() && !buyOrders.isEmpty()
            && sellOrders.peek().getPrice() <= buyOrders.peek().getPrice())
        {
            TradeOrder topSellOrder = sellOrders.poll();
            TradeOrder topBuyOrder = buyOrders.poll();
            int shares = 0;
            double price = 0;
            if (topSellOrder.getShares() > topBuyOrder.getShares())
            {
                shares = topBuyOrder.getShares();
                price = topSellOrder.getPrice();
                sellOrders.add(topSellOrder);
            }
            else if (topSellOrder.getShares() < topBuyOrder.getShares())
            {
                shares = topSellOrder.getShares();
                price = topSellOrder.getPrice();
                buyOrders.add(topBuyOrder);
            }
            else
            {
                shares = topSellOrder.getShares();
                price = topSellOrder.getPrice();
            }

            topSellOrder.subtractShares(shares);
            topBuyOrder.subtractShares(shares);

            volume += shares;
            if (price > hiPrice)
            {
                hiPrice = price;
            }
            if (price < loPrice)
            {
                loPrice = price;
            }

            lastPrice = price;
            topSellOrder.getTrader().receiveMessage(
                "You sold: " + shares + " " + stockSymbol + " at " +
                money.format(price) + " amt "
                + money.format(shares * price));

            topBuyOrder.getTrader().receiveMessage(
                "You bought: " + shares + " " + stockSymbol + " at " +
                money.format(price) + " amt "
                + money.format(shares * price));
        }
    }

    //
    // The following are for test purposes only
    //


    /**
     * stocks
     * @return stcok s
     */
    protected String getStockSymbol()
    {
        return stockSymbol;
    }


    /**
     * name
     * @return comp name
     */
    protected String getCompanyName()
    {
        return companyName;
    }

    /**
     * name
     * @return comp name
     */
    protected double getLoPrice()
    {
        return loPrice;
    }

    /**
     * name
     * @return comp name
     */
    protected double getHiPrice()
    {
        return hiPrice;
    }

    /**
     * name
     * @return comp name
     */
    protected double getLastPrice()
    {
        return lastPrice;
    }

    /**
     * name
     * @return comp name
     */
    protected int getVolume()
    {
        return volume;
    }

    /**
     * name
     * @return comp name
     */
    protected PriorityQueue<TradeOrder> getBuyOrders()
    {
        return buyOrders;
    }

    /**
     * name
     * @return comp name
     */
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
                str += separator + field.getType().getName() + 
                    " " + field.getName() + ":" + field.get(this);
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
