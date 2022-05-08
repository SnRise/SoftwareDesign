import dao.InMemoryAccountDao;
import http.ExchangeHttpClient;
import http.RxNettyAccountHttpServer;
import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

/**
 * @author Madiyar Nurgazin
 */
public class Main {
    public static void main(String[] args) {
        RxNettyAccountHttpServer server = new RxNettyAccountHttpServer(
                new InMemoryAccountDao(new ExchangeHttpClient())
        );

        HttpServer
                .newServer(8081)
                .start((req, resp) -> {
                    Observable<String> response = server.getResponse(req);
                    return resp.writeString(response.map(r -> r + System.lineSeparator()));
                })
                .awaitShutdown();
    }
}
