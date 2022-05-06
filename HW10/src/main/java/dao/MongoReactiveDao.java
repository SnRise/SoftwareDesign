package dao;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoCollection;
import model.Currency;
import model.Product;
import model.User;
import org.bson.Document;
import rx.Observable;

/**
 * @author Madiyar Nurgazin
 */
public class MongoReactiveDao implements ReactiveDao {
    private final MongoCollection<Document> users;
    private final MongoCollection<Document> products;

    public MongoReactiveDao(MongoCollection<Document> users, MongoCollection<Document> products) {
        this.users = users;
        this.products = products;
    }

    @Override
    public Observable<Boolean> insertUser(User user) {
        return insert(user.toDocument(), users, "login");
    }

    @Override
    public Observable<User> findAllUsers() {
        return users
                .find()
                .toObservable()
                .map(User::fromDocument);
    }

    @Override
    public Observable<Boolean> insertProduct(Product product) {
        return insert(product.toDocument(), products, "name");
    }

    @Override
    public Observable<Product> findProductsByUserId(long userId) {
        return users
                .find(Filters.eq("id", userId))
                .toObservable()
                .map(doc -> Currency.valueOf(doc.getString("preferredCurrency")))
                .flatMap(userCurrency -> products.find()
                        .toObservable()
                        .map(doc -> Product.fromDocument(doc).convertCurrency(userCurrency))
                );
    }

    private Observable<Boolean> insert(Document document, MongoCollection<Document> collection, String uniqueField) {
        return collection
                .find(Filters.or(
                        Filters.eq(uniqueField, document.getString(uniqueField)),
                        Filters.eq("id", document.getLong("id"))
                ))
                .toObservable()
                .singleOrDefault(null)
                .flatMap(foundDoc -> {
                    if (foundDoc == null) {
                        return collection.insertOne(document)
                                .asObservable()
                                .isEmpty()
                                .map(f -> !f);
                    } else {
                        return Observable.just(false);
                    }
                });
    }
}
