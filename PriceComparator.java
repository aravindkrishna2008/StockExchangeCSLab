
/**
 * StockExchange Lab
 *
 * @author Aravind and Shreyas
 * @version March 2024
 * @author Period: 11
 * @author Assignment: StockExchange Lab
 * @author Sources: None
 */
public class PriceComparator
    implements java.util.Comparator<TradeOrder>
{
    /**
     *This is a field
     */
    private boolean asc;

    /**
     * this is price comparotr
     */
    public PriceComparator()
    {
        this.asc = true;
    }


    /**
     * price c two
     * @param asc asc
     */
    public PriceComparator(boolean asc)
    {
        this.asc = asc;
    }

    /**
     * price c two
     * @param order1 order
     * @param order2 order
     * @return comparison
     */
    public int compare(TradeOrder order1, TradeOrder order2)
    {
        if (order1.isMarket() && order2.isMarket())
        {
            return 0;
        }
        if (order1.isMarket() && order2.isLimit())
        {
            return -1;
        }
        if (order1.isLimit() && order2.isMarket())
        {
            return 1;
        }

        double value;

        if (this.asc)
        {
            value = (order1.getPrice() - order2.getPrice() ) * 100;
        }
        else
        {
            value = (order2.getPrice() - order1.getPrice()) * 100;
        }

        return (int)Math.round((value));

    }

    /*
     * public static void main(String[] args) { PriceComparator pc = new
     * PriceComparator(true); TradeOrder to1 = new TradeOrder(null, "GGGL",
     * true, false, 123, 20); TradeOrder to2 = new TradeOrder(null, "GGGL",
     * false, false, 123, 10); System.out.println(pc.compare(to1, to2)); }
     */
}
