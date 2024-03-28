import java.lang.reflect.*;

/**
 * StockExchange Lab
 *
 * @author Aravind and Shreyas
 * @version March 2024
 * @author Period: 11
 * @author Assignment: StockExchange Lab
 * @author Sources: None
 */
public class TradeOrder
{
    private Trader  trader;
    private String  symbol;
    private boolean buyOrder;
    private boolean marketOrder;
    private int     numShares;
    private double  price;

    /**
     * tradorder constructer 
     * @param trader t
     * @param symbol s
     * @param buyOrder bo
     * @param marketOrder mo
     * @param numShares ns
     * @param price p
     */
    public TradeOrder(
        Trader trader,
        String symbol,
        boolean buyOrder,
        boolean marketOrder,
        int numShares,
        double price)
    {
        this.trader = trader;
        this.symbol = symbol;
        this.buyOrder = buyOrder;
        this.marketOrder = marketOrder;
        this.numShares = numShares;
        this.price = price;
    }


    //
    // The following are for test purposes only
    //
    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this TradeOrder.
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


    /**
     * trader
     * @return trader
     */
    public Trader getTrader()
    {
        return trader;
    }


    /**
     * symbol
     * @return the stmbol
     */
    public String getSymbol()
    {
        return symbol;
    }

    /**
     * buy
     * @return is it buy
     */
    public boolean isBuy()
    {
        return buyOrder;
    }

    /**
     * symbol
     * @return the stmbol
     */
    public boolean isSell()
    {
        return !buyOrder;
    }

    /**
     * market
     * @return market
     */
    public boolean isMarket()
    {
        return marketOrder;
    }

    /**
     * limit
     * @return the limit
     */
    public boolean isLimit()
    {
        return !marketOrder;
    }

    /**
     * shares
     * @return the shares
     */
    public int getShares()
    {
        return numShares;
    }

    /**
     * price
     * @return price
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * sub shares
     * @param shares shares
     */
    public void subtractShares(int shares)
    {
        numShares -= shares;
    }

}
