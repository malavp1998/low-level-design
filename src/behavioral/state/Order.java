package behavioral.state;

class Order {
    private String status = "PLACED";

    public void next() {
        if (status.equals("PLACED")) {
            System.out.println("Shipping the order...");
            status = "SHIPPED";
        } else if (status.equals("SHIPPED")) {
            System.out.println("Delivering the order...");
            status = "DELIVERED";
        } else if (status.equals("DELIVERED")) {
            System.out.println("Already delivered, can't move further");
        } else if (status.equals("CANCELLED")) {
            System.out.println("Cannot proceed, order was cancelled");
        }
    }

    public void cancel() {
        if (status.equals("PLACED")) {
            System.out.println("Cancelling order...");
            status = "CANCELLED";
        } else if (status.equals("SHIPPED")) {
            System.out.println("Cannot cancel, already shipped");
        } else if (status.equals("DELIVERED")) {
            System.out.println("Cannot cancel, already delivered");
        }
        // Every action needs the same if-else across all states
        // Add a new state (RETURNED)? Update EVERY method.
    }
}