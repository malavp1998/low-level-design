package behavioral.observer;

public class PushSubscriber implements Subscriber{

    String name;

    PushSubscriber(String name)
    {
        this.name = name;
    }
 
    @Override
    public void update(String title)
    {
        System.out.println(name + " got PUSH notification: New video -> " + title);
    }

}