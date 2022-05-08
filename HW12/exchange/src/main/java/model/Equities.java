package model;

import org.bson.Document;

/**
 * @author Madiyar Nurgazin
 */
public class Equities {
    private final String companyName;
    private final int count;
    private final int price;

    public Equities(Document document) {
        this(document.getString("companyName"), document.getInteger("count"), document.getInteger("price"));
    }

    public Equities(String companyName, int count, int price) {
        this.companyName = companyName;
        this.count = count;
        this.price = price;
    }

    public Document toDocument() {
        return new Document()
                .append("companyName", companyName)
                .append("count", count)
                .append("price", price);
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
    }

    public Equities changePrice(int newEquitiesPrice) {
        return new Equities(companyName, count, newEquitiesPrice);
    }

    public Equities add(int equitiesCount) {
        return new Equities(companyName, count + equitiesCount, price);
    }

    public Equities minus(int equitiesCount) {
        if (count < equitiesCount) {
            throw new IllegalArgumentException("Not enough equities on the exchange");
        }
        return new Equities(companyName, count - equitiesCount, price);
    }

    @Override
    public String toString() {
        return "Equities {\n" +
                "  companyName : " + companyName + ",\n" +
                "  count : " + count + ",\n" +
                "  price : " + price + "\n" +
                "}";
    }
}
