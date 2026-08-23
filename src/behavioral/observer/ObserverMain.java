package behavioral.observer;

/**
 * CLIENT / DRIVER.
 *
 * EXPECTED OUTPUT ONCE IMPLEMENTED (roughly):
 *   Channel CodeWithPiyush uploaded: Strategy Pattern in 10 min
 *   Hey Amit, new video uploaded: Strategy Pattern in 10 min
 *   Hey Neha, new video uploaded: Strategy Pattern in 10 min
 *   -- Amit unsubscribed --
 *   Channel CodeWithPiyush uploaded: Observer Pattern in 10 min
 *   Hey Neha, new video uploaded: Observer Pattern in 10 min
 */
public class ObserverMain {

    public static void main(String[] args) {


        YoutubeChannel youtubeChannel = new YoutubeChannel();
        youtubeChannel.addSubscriber(new EmailSubscriber("Piyush"));
        youtubeChannel.addSubscriber(new SmsSubscriber("Kanha"));
        youtubeChannel.addSubscriber(new PushSubscriber("Shivji"));
        youtubeChannel.addSubscriber(new EmailSubscriber("Shivji"));


        youtubeChannel.uploadVideo("Java Basics");



      
    
    }
}
