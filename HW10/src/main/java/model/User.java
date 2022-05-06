package model;

import org.bson.Document;

/**
 * @author Madiyar Nurgazin
 */
public record User(long id, String login, Currency preferredCurrency) {

    public Document toDocument() {
        return new Document()
                .append("id", id)
                .append("login", login)
                .append("preferredCurrency", preferredCurrency.toString());
    }

    public static User fromDocument(Document document) {
        return new User(
                document.getLong("id"),
                document.getString("login"),
                Currency.valueOf(document.getString("preferredCurrency"))
        );
    }
}
