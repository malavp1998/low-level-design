package behavioral.state;

public interface OrderState {

    void nextStatus(OrderContext OrderStateContext);
    void cancel(OrderContext OrderStateContext);
    String getStatus(); 

    
}
