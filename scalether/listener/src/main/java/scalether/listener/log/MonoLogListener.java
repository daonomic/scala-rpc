package scalether.listener.log;

import reactor.core.publisher.Mono;
import scalether.domain.request.LogFilter;
import scalether.domain.response.Log;

public interface MonoLogListener {
    boolean isEnabled();
    Mono<LogFilter> createFilter(String fromBlock, String toBlock);
    Mono<Void> onLog(Log log, int confirmations, boolean confirmed);
}
