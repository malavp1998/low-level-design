package behavioral.observer;

public class EmailSubscriber implements Subscriber{

    String name;

    EmailSubscriber(String name)
    {
        this.name = name;
    }
 
    @Override
    public void update(String title)
    {
        System.out.println(name + " got email: New video -> " + title);
    }

}