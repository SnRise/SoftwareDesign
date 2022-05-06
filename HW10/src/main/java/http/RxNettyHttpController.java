package http;

import dao.ReactiveDao;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import model.Currency;
import model.Product;
import model.User;
import rx.Observable;

/**
 * @author Madiyar Nurgazin
 */
public class RxNettyHttpController implements HttpController {
    private final ReactiveDao db;

    public RxNettyHttpController(ReactiveDao db) {
        this.db = db;
    }

    @Override
    public <T> Observable<String> getResponse(HttpServerRequest<T> request) {
        String path = request.getDecodedPath().substring(1);

        return switch (path) {
            case "register_user" -> registerUser(request);
            case "get_users" -> getUsers();
            case "add_product" -> addProduct(request);
            case "get_products" -> getProducts(request);
            default -> Observable.just("Not found: " + path);
        };
    }

    private <T> Observable<String> registerUser(HttpServerRequest<T> request) {
        long id = Long.parseLong(getQueryParam(request, "id"));
        String login = getQueryParam(request, "login");
        Currency currency = Currency.valueOf(getQueryParam(request, "currency").toUpperCase());

        User user = new User(id, login, currency);
        return db.insertUser(user).map(inserted -> inserted
                ? "Success"
                : String.format("User with login=%s or id=%d already exists", login, id)
        );
    }

    private Observable<String> getUsers() {
        return db.findAllUsers().map(user -> user + "\n");
    }

    private <T> Observable<String> addProduct(HttpServerRequest<T> request) {
        long id = Long.parseLong(getQueryParam(request, "id"));
        String name = getQueryParam(request, "name");
        Currency currency = Currency.valueOf(getQueryParam(request, "currency").toUpperCase());
        double price = Double.parseDouble(getQueryParam(request, "price"));

        Product product = new Product(id, name, currency, price);
        return db.insertProduct(product).map(inserted -> inserted
                ? "Success"
                : String.format("Product with name=%s or id=%d already exists", name, id)
        );
    }

    private <T> Observable<String> getProducts(HttpServerRequest<T> request) {
        long id = Long.parseLong(getQueryParam(request, "id"));
        return db.findProductsByUserId(id).map(product -> product + "\n");
    }

    private <T> String getQueryParam(HttpServerRequest<T> request, String param) {
        return request.getQueryParameters().get(param).get(0);
    }

}
