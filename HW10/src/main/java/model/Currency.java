package model;

/**
 * @author Madiyar Nurgazin
 */
public enum Currency {
    USD,
    EUR,
    RUB;

    public double convertPrice(double price, Currency other) {
        return price * getMultiplier() / other.getMultiplier();
    }

    private double getMultiplier() {
        return switch (this) {
            case USD -> 67;
            case EUR -> 70.15;
            case RUB -> 1;
        };
    }
}
