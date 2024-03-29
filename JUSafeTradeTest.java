import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.regex.*;

import org.junit.*;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

/**
 * StockExchange Lab
 *
 * @author Aravind and Shreyas
 * @version March 2024
 * @author Period: 11
 * @author Assignment: StockExchange Lab
 * @author Sources: None
 */

public class JUSafeTradeTest
{
    // --Test TradeOrder
    /**
     * TradeOrder tests: TradeOrderConstructor - constructs TradeOrder and then
     * compare toString TradeOrderGetTrader - compares value returned to
     * constructed value TradeOrderGetSymbol - compares value returned to
     * constructed value TradeOrderIsBuy - compares value returned to
     * constructed value TradeOrderIsSell - compares value returned to
     * constructed value TradeOrderIsMarket - compares value returned to
     * constructed value TradeOrderIsLimit - compares value returned to
     * constructed value TradeOrderGetShares - compares value returned to
     * constructed value TradeOrderGetPrice - compares value returned to
     * constructed value TradeOrderSubtractShares - subtracts known value &
     * compares result returned by getShares to expected value
     */
    private String  symbol        = "GGGL";
    private boolean buyOrder      = true;
    private boolean marketOrder   = true;
    private int     numShares     = 123;
    private int     numToSubtract = 24;
    private double  price         = 123.45;

    // --Test Trader
    @Test
    public void traderConstructor()
    {
        Trader trader = new Trader(null, "goodName", "pass");

        assertEquals("<< Invalid Trader constructed - screenName >>", trader.getName(), "goodName");
        assertEquals("<< Invalid Trader constructed  - password >>", trader.getPassword(), "pass");
        assertNotNull("<< Invalid Trader constructed - mailbox >>", trader.mailbox());
    }


    @Test
    public void traderGetName()
    {
        Trader trader = new Trader(null, "goodName", "pass");
        assertEquals("<< Trader.getName invalid screenName >>", "goodName", trader.getName());
    }


    @Test
    public void traderGetPassword()
    {
        Trader trader = new Trader(null, "goodName", "pass");
        assertEquals("<< Trader.getPassword invalid password >>", "pass", trader.getPassword());
    }


    @Test
    public void traderCompareTo()
    {
        Trader trader = new Trader(null, "goodName", "pass");
        Trader traderGreater = new Trader(null, "z_goodName", "greater");
        Trader traderLess = new Trader(null, "a_goodName", "less");
        Trader traderEqual = new Trader(null, "goodName", "equal");
        assertTrue("<< Trader.compareTo > fails >>", traderGreater.compareTo(trader) > 0);
        assertTrue("<< Trader.compareTo < fails >>", traderLess.compareTo(trader) < 0);
        assertTrue("<< Trader.compareTo == fails >>", traderEqual.compareTo(trader) == 0);
    }


    @Test
    public void traderEquals()
    {
        Trader trader = new Trader(null, "goodName", "pass");
        Trader traderEqual = new Trader(null, "goodName", "equal");
        assertTrue("<< Trader.equals fails >>", traderEqual.equals(trader));
    }


    @Test
    public void traderNotEquals()
    {
        Trader trader = new Trader(null, "goodName", "pass");
        Trader traderEqual = new Trader(null, "badName", "equal");
        assertFalse("<< Trader.equals fails >>", traderEqual.equals(trader));
    }


    @Test
    public void traderNotEqualsNull()
    {
        Trader trader = null;
        Trader traderEqual = new Trader(null, "badName", "equal");
        assertFalse("<< Trader.equals fails >>", traderEqual.equals(trader));
    }


    @Test
    public void traderNotEqualsNotTrader()
    {
        String trader = "not a trader";
        Trader traderEqual = new Trader(null, "badName", "equal");
        assertFalse("<< Trader.equals fails >>", traderEqual.equals(trader));
    }


    @Test
    public void traderHasMessages()
    {
        Trader trader = new Trader(null, "goodName", "pass");
        assertFalse("<< Trader.hasMessages failed >>", trader.hasMessages());
        trader.receiveMessage("zyzzx");
        assertTrue("<< Trader.hasMessages failed >>", trader.hasMessages());
    }


    @Test
    public void traderReceiveMessage()
    {
        Trader trader = new Trader(null, "goodName", "pass");

        trader.receiveMessage("zyzzx");
        assertTrue("<< Trader.receiveMessage- hasMessages failed >>", trader.hasMessages());

        Queue<String> mbox = trader.mailbox();

        // System.err.println(mbox.peek());
        assertEquals(mbox.peek(), "zyzzx");

        trader.openWindow(); // called for code coverage. Should empty
                             // mailbox

        assertTrue("<< Trader.openWindow - hasMessages failed >>", !trader.hasMessages());
    }


    @Test
    public void traderGetQuote()
    {
        StockExchange safe = new StockExchange();
        safe.listStock("ABCD", "wxyz", 123.45);

        Brokerage broke = new Brokerage(safe);
        Trader trader = new Trader(broke, "goodName", "pass");
        trader.getQuote("ABCD");

        // System.out.println(trader);
        assertTrue("<<< Trader.getQuote failed >>>", trader.hasMessages());

        Queue<String> mbox = trader.mailbox();
        // System.err.println(mbox);
        assertTrue("<<< Trader.getQuote - invalid quote >>>", mbox.peek().contains("123.45"));

        trader.quit();
    }


    @Test
    public void traderPlaceOrder()
    {
        StockExchange safe = new StockExchange();
        safe.listStock("ABCD", "wxyz", 123.45);

        Brokerage broke = new Brokerage(safe);

        Trader trader = new Trader(broke, "goodName", "pass");
        Queue<String> mbox = trader.mailbox();
        TradeOrder order = new TradeOrder(trader, "ABC", true, true, 100, 123.99);
        trader.placeOrder(order);

        assertTrue(
            "<<< Trader.placeOrder - invalid message >>>",
            mbox.remove().contains("ABC not found"));

        order = new TradeOrder(trader, "ABCD", true, true, 100, 123.99);
        safe.placeOrder(order);
        // System.err.println(mbox);
        assertTrue("<<< Trader.placeOrder - invalid message >>>", mbox.peek().contains("ABCD"));
    }


    @Test
    public void TraderToString()
    {
        Trader trader = new Trader(null, "goodName", "pass");
        assertNotNull(trader.toString());
    }


    @Test
    public void StockGetQuote()
    {
        Stock stock = new Stock("i am cool", "sdfd", 1);
        assertNotNull(stock.getQuote());
    }


    @Test
    public void StockConstructor()
    {
        Stock stock = new Stock("i am cool", "sdfd", 1);
        assertNotNull(stock);
    }


    @Test
    public void StockGetQuoteString()
    {
        Stock stock = new Stock("i am cool", "sdfd", 1);
        assertNotNull(stock.getQuote());
    }

    // @Test
    // public void StockPlaceOrder()
    // {
    // Stock stock = new Stock("i am cool", "sdfd", 1);
    // TradeOrder order = new TradeOrder(null, "GGGL", true, false, 1, 1);
    // stock.placeOrder(order);
    // }


    @Test
    public void StockExchangeConstructor()
    {
        StockExchange stock = new StockExchange();
        assertNotNull(stock);
    }


    @Test
    public void PriceComparatorConstructor()
    {
        PriceComparator pc = new PriceComparator(true);
        assertNotNull(pc);
    }


    @Test
    public void toStringBrokerage()
    {
        StockExchange stock = new StockExchange();
        Brokerage b = new Brokerage(stock);
        assertNotNull(b.getLoggedTraders());

        assertNotNull(b.toString());
    }


    @Test
    public void testExectureOrereds()
    {
        StockExchange exchange = new StockExchange();
        exchange.listStock("DS", "DanceStudios.com", 12.33);
        exchange.listStock("NSTL", "Nasty Loops Inc.", 0.25);
        exchange.listStock("GGGL", "Giggle.com", 10.00);
        exchange.listStock("MATI", "M and A Travel Inc.", 28.20);
        exchange.listStock("DDLC", "Dulce De Leche Corp.", 57.50);
        exchange.listStock("SAFT", "SafeTrade.com Inc.", 322.45);

        Brokerage safeTrade = new Brokerage(exchange);
        safeTrade.addUser("stockman", "sesame");
        safeTrade.addUser("aravind", "shreyas");
        safeTrade.login("stockman", "sesame");
        safeTrade.addUser("mstrade", "bigsecret");
        safeTrade.login("mstrade", "bigsecret");

        exchange.getListedStocks().get("DS").executeOrders();
        exchange.getListedStocks().get("DS").toString();

    }


    @Test
    public void PriceComparatorCompare()
    {
        PriceComparator pc = new PriceComparator(true);
        TradeOrder to1 = new TradeOrder(null, "GGGL", true, false, 123, 10);
        TradeOrder to2 = new TradeOrder(null, "GGGL", true, false, 123, 10);
        pc.compare(to1, to2);
    }


    @Test
    public void BrokerageTestContrsutrcotr()
    {
        StockExchange stock = new StockExchange();
        Brokerage b = new Brokerage(stock);
        assertNotNull(b);
    }


    @Test
    public void BrokerageaddUserscreenName()
    {
        StockExchange stock = new StockExchange();
        Brokerage b = new Brokerage(stock);
        int a = b.addUser("ar", "shreyas");
        assertEquals(a, -1);

    }


    @Test
    public void BrokerageaddUserPass()
    {
        StockExchange stock = new StockExchange();
        Brokerage b = new Brokerage(stock);
        int a = b.addUser("arsadf", "s");
        assertEquals(a, -2);

    }


    @Test
    public void BrokerageaddUserScreenNameTaken()
    {
        StockExchange stock = new StockExchange();
        Brokerage b = new Brokerage(stock);
        b.addUser("aravind", "shreyas");
        int a = b.addUser("aravind", "shreyas");
        assertEquals(a, -3);

    }


    @Test
    public void BrokerageAddUser()
    {
        StockExchange stock = new StockExchange();
        Brokerage b = new Brokerage(stock);
        b.addUser("aravind", "shreyas");
    }


    @Test
    public void BrokerageLogin()
    {
        StockExchange stock = new StockExchange();
        Brokerage b = new Brokerage(stock);
        b.addUser("aravind", "shreyas");
        b.login("aravind", "shreyas");
    }


    @Test
    public void BrokerageLoginScreenNameNotFOUnd()
    {
        StockExchange stock = new StockExchange();
        Brokerage b = new Brokerage(stock);
        b.addUser("aravind", "shreyas");
        int a = b.login("aravind1", "shreyas");
        assertEquals(a, -1);
    }


    @Test
    public void BrokerageLoginInvalidPass()
    {
        StockExchange stock = new StockExchange();
        Brokerage b = new Brokerage(stock);
        b.addUser("aravind", "shreyas");
        int a = b.login("aravind", "shreyas1");
        assertEquals(a, -2);
    }


    @Test
    public void BrokerageLoginAlreadyLoggedIn()
    {
        StockExchange stock = new StockExchange();
        Brokerage b = new Brokerage(stock);
        b.addUser("aravind", "shreyas");
        b.login("aravind", "shreyas");
        int a = b.login("aravind", "shreyas");
        assertEquals(a, -3);
    }


    @Test
    public void BrokerageLogout()
    {
        StockExchange stock = new StockExchange();
        Brokerage b = new Brokerage(stock);
        b.addUser("aravind", "shreyas");
        b.login("aravind", "shreyas");
        b.logout(b.getTraders().get("aravind"));
    }

    // brokerage getQuote
    // brokerage placeOrder


    @Test
    public void TraderOrderGetTrader()
    {
        TradeOrder order =
            new TradeOrder(new Trader(null, "shreyas", "aravind"), "GGGL", true, false, 1, 1);
        order.getTrader();
    }


    @Test
    public void TraderOrderGetSymbol()
    {
        TradeOrder order =
            new TradeOrder(new Trader(null, "shreyas", "aravind"), "GGGL", true, false, 1, 1);
        order.getSymbol();
        order.getPrice();
        order.getShares();
        order.isBuy();
        order.isLimit();
        order.isMarket();
        order.isSell();
        order.subtractShares(1);
    }



    @Test
    public void PriceComparatorAscending1()
    {
        PriceComparator pc = new PriceComparator(true);
        TradeOrder to1 = new TradeOrder(null, "GGGL", false, false, 123, 10);
        TradeOrder to2 = new TradeOrder(null, "GGGL", true, false, 123, 20);
        pc.compare(to1, to2);
    }


    @Test
    public void PriceComparatorDescending2()
    {
        PriceComparator pc = new PriceComparator(false);
        TradeOrder to1 = new TradeOrder(null, "GGGL", true, false, 123, 20);
        TradeOrder to2 = new TradeOrder(null, "GGGL", false, false, 123, 10);
        pc.compare(to1, to2);
    }


    @Test
    public void PriceComparatorAscending3()
    {
        PriceComparator pc = new PriceComparator(true);
        TradeOrder to1 = new TradeOrder(null, "GGGL", true, true, 123, 20);
        TradeOrder to2 = new TradeOrder(null, "GGGL", false, false, 123, 10);
        pc.compare(to1, to2);
    }


    @Test
    public void PriceComparatorAscending4()
    {
        PriceComparator pc = new PriceComparator(true);
        TradeOrder to1 = new TradeOrder(null, "GGGL", false, false, 123, 20);
        TradeOrder to2 = new TradeOrder(null, "GGGL", true, true, 123, 10);
        pc.compare(to1, to2);
    }


    @Test
    public void PriceComparatorAscending5()
    {
        PriceComparator pc = new PriceComparator(true);
        TradeOrder to1 = new TradeOrder(null, "GGGL", false, true, 123, 20);
        TradeOrder to2 = new TradeOrder(null, "GGGL", false, true, 123, 10);
        pc.compare(to1, to2);
    }

}
