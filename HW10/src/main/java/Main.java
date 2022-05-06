import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import dao.MongoReactiveDao;
import dao.ReactiveDao;
import http.HttpController;
import http.RxNettyHttpController;
import io.reactivex.netty.protocol.http.server.HttpServer;
import org.bson.Document;

/**
 * @author Madiyar Nurgazin
 */
public class Main {
    public static void main(String[] args) {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = client.getDatabase("catalog");

        MongoCollection<Document> users = database.getCollection("users");
        MongoCollection<Document> products = database.getCollection("products");

        ReactiveDao reactiveDao = new MongoReactiveDao(users, products);
        HttpController controller = new RxNettyHttpController(reactiveDao);

        HttpServer
                .newServer(8081)
                .start((request, response) -> response.writeString(controller.getResponse(request)))
                .awaitShutdown();
    }
}
