/**
 * A price comparator for trade orders.
 */
public class PriceComparator
    implements java.util.Comparator<TradeOrder>
{
    boolean asc;

    public PriceComparator()
    {
        asc = true;
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

        if (asc)
        {
            value = (order1.getPrice() - order2.getPrice());
        }
        else
        {
            value = (order2.getPrice() - order1.getPrice());
        }

        return (int)Math.round(value);

    }

    public static void main(String[] args) {
        System.out.println(Math.round(-0.1));
    }

}
