package dao;

import model.Product;
import model.User;
import rx.Observable;

/**
 * @author Madiyar Nurgazin
 */
public interface ReactiveDao {

    Observable<Boolean> insertUser(User user);

    Observable<User> findAllUsers();

    Observable<Boolean> insertProduct(Product product);

    Observable<Product> findProductsByUserId(long userId);

}
