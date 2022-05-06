package http;

import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

/**
 * @author Madiyar Nurgazin
 */
public interface HttpController {
    <T> Observable<String> getResponse(HttpServerRequest<T> request);
}
