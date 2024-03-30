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

        if (order.isSell())
        {
            sellOrders.add(order);
            msg += "Sell " + order.getSymbol() 
                + " (" + companyName + ")" + "\n";
            msg += order.getShares() + " shares at ";
            if (order.isMarket())
            {
                msg += "market";
            }
            else
            {
                msg += order.getPrice();
            }

        }

        if (order.isBuy())
        {
            buyOrders.add(order);
            msg += "Buy " + order.getSymbol() 
                + " (" + companyName + ")" + "\n";
            msg += order.getShares() + " shares at ";
            if (order.isMarket())
            {
                msg += "market";
            }
            else
            {
                msg += order.getPrice();
            }
        }
        order.getTrader().receiveMessage(msg);
        executeOrders();

    }


    /**
     * reutnr s
     */
    protected void executeOrders()
    {
        while (!sellOrders.isEmpty() && !buyOrders.isEmpty())
        {
            TradeOrder topSellOrder = sellOrders.peek();
            TradeOrder topBuyOrder = buyOrders.peek();

            if (topSellOrder.isLimit() && topBuyOrder.isLimit()
                && topBuyOrder.getPrice() < topSellOrder.getPrice())
            {
                break;
            }

            int sharesToTrade = Math.min(topSellOrder.getShares(), 
                topBuyOrder.getShares());
            double tradePrice;

            if (topSellOrder.isLimit() && topBuyOrder.isLimit())
            {
                tradePrice = topSellOrder.getPrice();
            }
            else if (topSellOrder.isMarket() && topBuyOrder.isMarket())
            {
                tradePrice = lastPrice;
            }
            else if (topSellOrder.isMarket())
            {
                tradePrice = topBuyOrder.getPrice();
            }
            else
            {
                tradePrice = topSellOrder.getPrice();
            }

            topSellOrder.subtractShares(sharesToTrade);
            topBuyOrder.subtractShares(sharesToTrade);

            if (topSellOrder.getShares() == 0)
            {
                sellOrders.remove();
            }

            if (topBuyOrder.getShares() == 0)
            {
                buyOrders.remove();
            }

            volume += sharesToTrade;
            if (tradePrice > hiPrice)
            {
                hiPrice = tradePrice;
            }
            if (tradePrice < loPrice)
            {
                loPrice = tradePrice;
            }

            lastPrice = tradePrice;

            String sellMessage = "You sold: "
                + sharesToTrade + " " + stockSymbol + " at "
                + money.format(tradePrice) + " amt " 
                + money.format(sharesToTrade * tradePrice);
            String buyMessage = "You bought: "
                + sharesToTrade + " " + stockSymbol + " at "
                + money.format(tradePrice) + " amt " 
                + money.format(sharesToTrade * tradePrice);

            topSellOrder.getTrader().receiveMessage(sellMessage);
            topBuyOrder.getTrader().receiveMessage(buyMessage);
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
