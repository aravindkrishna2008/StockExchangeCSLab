import static org.junit.Assert.assertEquals;

/**
 * A price comparator for trade orders.
 */
public class PriceComparator
    implements java.util.Comparator<TradeOrder>
{
    boolean asc;

    public PriceComparator()
    {
        this.asc = true;
    }


    public PriceComparator(boolean asc)
    {
        this.asc = asc;
    }


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
            value = Math.round(order1.getPrice() - order2.getPrice());
        }
        else
        {
            value = Math.round(order2.getPrice() - order1.getPrice());
        }

        return (int)(value);

    }


    public static void main(String[] args)
    {
        PriceComparator pc = new PriceComparator(true);
        TradeOrder to1 = new TradeOrder(null, "GGGL", true, false, 123, 20);
        TradeOrder to2 = new TradeOrder(null, "GGGL", false, false, 123, 10);
        System.out.println(pc.compare(to1, to2));
    }
}
