package model;

import org.bson.Document;

/**
 * @author Madiyar Nurgazin
 */
public record Product(long id, String name, Currency currency, double price) {

    public Product convertCurrency(Currency otherCurrency) {
        return new Product(id, name, otherCurrency, currency.convertPrice(price, otherCurrency));
    }

    public Document toDocument() {
        return new Document()
                .append("id", id)
                .append("name", name)
                .append("currency", currency.toString())
                .append("price", price);
    }

    public static Product fromDocument(Document document) {
        return new Product(
                document.getLong("id"),
                document.getString("name"),
                Currency.valueOf(document.getString("currency")),
                document.getDouble("price")
        );
    }
}
