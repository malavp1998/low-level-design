package behavioral.observer;

public class SmsSubscriber implements Subscriber{

    String name;

    SmsSubscriber(String name)
    {
        this.name = name;
    }
 
    @Override
    public void update(String title)
    {
        System.out.println(name + " got SMS: New video -> " + title);
    }

}