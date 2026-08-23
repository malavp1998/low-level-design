package behavioral.state;

/**
 * CLIENT / DRIVER.
 *
 * EXPECTED OUTPUT ONCE IMPLEMENTED (roughly):
 *   [machine state = IDLE, items left = 2]
 *   Insert a coin first.              <- invalid action, handled gracefully
 *   Coin accepted.
 *   [machine state = HAS_MONEY, items left = 2]
 *   Coin already inserted.
 *   Item selected.
 *   Item dispensed. Thank you!
 *   [machine state = IDLE, items left = 1]
 */
public class StateMain {

    public static void main(String[] args) {

        OrderContext order = new OrderContext(); 
        System.out.println(order.getStatus());   // PLACED
        order.next();                            // Shipping the order...
        order.cancel();                          // Cannot cancel, already shipped
        order.next();                            // Delivering the order...
        System.out.println(order.getStatus());   // DELIVERED
    
    }
}
