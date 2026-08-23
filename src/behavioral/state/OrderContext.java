package behavioral.state;

public class OrderContext {


    private OrderState state = new PlacedState();
    public void setState(OrderState state) { this.state = state; }
    public void next() { state.nextStatus(this); }
    public void cancel() { state.cancel(this); }
    public String getStatus() { return state.getStatus(); }
}
 