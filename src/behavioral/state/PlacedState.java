package behavioral.state;


public class PlacedState implements OrderState {

   
    @Override
    public void nextStatus(OrderContext orderStateContext)
    {
       System.out.println("Shipping the order...");
       orderStateContext.setState(new ShipedState());  
    }
    @Override
    public void cancel(OrderContext orderStateContext)
    {
        System.out.println("Cancelling order...");
        orderStateContext.setState(new CancelledState());
    }

    @Override
    public String getStatus(){
       return "PLACED";
    }
    
    
}