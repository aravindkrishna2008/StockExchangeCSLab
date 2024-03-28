import java.lang.reflect.*;
import java.util.*;

/**
 * StockExchange Lab
 *
 * @author Aravind and Shreyas
 * @version March 2024
 * @author Period: 11
 * @author Assignment: StockExchange Lab
 * @author Sources: None
 */
public class StockExchange
{
    private Map<String, Stock> listedStocks;

    //
    // The following are for test purposes only
    //
    /**
     * the listed stocks
     * 
     * @return map
     */
    protected Map<String, Stock> getListedStocks()
    {
        return listedStocks;
    }


    /**
     * <p>
     * A generic toString implementation that uses reflection to print names and
     * values of all fields <em>declared in this class</em>. Note that
     * superclass fields are left out of this implementation.
     * </p>
     * 
     * @return a string representation of this StockExchange.
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
                str += separator + field.getType().getName() + " " +
                    field.getName() + ":" + field.get(this);
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
     * @param symbol
     *            symbol
     * @return quote for given stock
     */
    public String getQuote(String symbol)
    {
        if (listedStocks.get(symbol) == null)
        {
            return symbol + " not found";
        }
        return listedStocks.get(symbol).getQuote();
    }


    /**
     * the list stock method
     * @param symbol symbol
     * @param name name
     * @param price price
     */
    public void listStock(String symbol, String name, double price)
    {
        Stock stock = new Stock(symbol, name, price);
        if (listedStocks == null)
        {
            listedStocks = new HashMap<String, Stock>();
        }
        listedStocks.put(symbol, stock);
    }


    /**
     * @param order
     *            order
     */
    public void placeOrder(TradeOrder order)
    {
        if (listedStocks.get(order.getSymbol()) == null)
        {
            order.getTrader().receiveMessage((order.getSymbol() 
                + " not found"));
            return;
        }
        listedStocks.get(order.getSymbol()).placeOrder(order);
    }
}
