package behavioral.state;


public class CancelledState implements OrderState {

   
   
    @Override
    public void nextStatus(OrderContext orderStateContext)
    {
        System.out.println("Cannot proceed, cancelled");
     //  orderStateContext.setState(new DeliveredState());  
    }
    @Override
    public void cancel(OrderContext orderStateContext)
    {
        System.out.println("Already cancelled");
        //orderStateContext.setState(new CancelledState());
    }

    @Override
    public String getStatus(){
       return "CANCELLED";
    }
    
}