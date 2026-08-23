package behavioral.strategy;

public class ShopingCart {


    PaymentStrategy paymentStrategy;

    ShopingCart(PaymentStrategy paymentStrategy)
    {
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout(int amount)
    {
       paymentStrategy.pay(amount);
    }
    
}
