package behavioral.observer;

import java.util.ArrayList;
import java.util.List;

public class YoutubeChannel {

    List<Subscriber> subscribers = new ArrayList<>();

    public void addSubscriber(Subscriber subscriber)
    {
        subscribers.add(subscriber);
    }
    public void removeSubscriber(Subscriber subscriber)
    {
        subscribers.remove(subscriber);
    }


    public void uploadVideo(String title)
    {
        System.out.println("New vide uploaded "+ title);

        for (Subscriber subscriber : subscribers)
        {
            subscriber.update(title);
        }
    }
    
}
