package behavioral.strategy;

public class CardPayment implements PaymentStrategy
{
    @Override
    public void pay(int amount)
    {
        System.out.println("Paid by CARD "+ amount);
    }

}