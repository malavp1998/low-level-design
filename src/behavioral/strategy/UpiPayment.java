package behavioral.strategy;

public class UpiPayment implements PaymentStrategy
{
    @Override
    public void pay(int amount)
    {
        System.out.println("Paid by UPI "+ amount);
    }

}