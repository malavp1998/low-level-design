package behavioral.strategy;

import javax.xml.crypto.dsig.spec.HMACParameterSpec;

/**
 * CLIENT / DRIVER.
 *
 * EXPECTED OUTPUT ONCE YOU IMPLEMENT IT (roughly):
 *   Cart total: 1250.0
 *   Paid 1250.0 using Credit Card ****3456
 *   --- same cart, different strategy ---
 *   Paid 1250.0 using UPI id piyush@okbank
 */
public class StrategyMain {

    public static void main(String[] args) {


        ShopingCart shopingCart  = new ShopingCart(new CardPayment());
        shopingCart.checkout(123);
        shopingCart  = new ShopingCart(new UpiPayment());
        shopingCart.checkout(400);


    }
}
