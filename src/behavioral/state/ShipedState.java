package behavioral.state;


public class ShipedState implements OrderState {

   
    @Override
    public void nextStatus(OrderContext orderStateContext)
    {
       System.out.println("Delivering  the order...");
       orderStateContext.setState(new DeliveredState());  
    }
    @Override
    public void cancel(OrderContext orderStateContext)
    {
        System.out.println("Cannot cancel, already shipped");
        //orderStateContext.setState(new CancelledState());
    }

    @Override
    public String getStatus(){
       return "SHIPPED";
    }
    
    
}