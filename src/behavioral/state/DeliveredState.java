package behavioral.state;


public class DeliveredState implements OrderState {

     
    @Override
    public void nextStatus(OrderContext orderStateContext)
    {
       System.out.println("Already Order deliverd");
    //   orderStateContext.setState(new DeliveredState());  
    }
    @Override
    public void cancel(OrderContext orderStateContext)
    {
        System.out.println("Cannot cancel, already delivered");
        //orderStateContext.setState(new CancelledState());
    }

    @Override
    public String getStatus(){
       return "DELIVERED";
    }
    
    
}