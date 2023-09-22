package ra.model.entity;

public enum ShippingType {
    ECONOMY(28000),
    FAST(38000),
    EXPRESS(60000);

    private double price;

    ShippingType(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
