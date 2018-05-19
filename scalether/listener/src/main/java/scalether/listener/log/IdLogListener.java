package scalether.listener.log;

import scalether.domain.request.LogFilter;
import scalether.domain.response.Log;

public interface IdLogListener {
    boolean isEnabled();
    LogFilter createFilter(String fromBlock, String toBlock);
    void onLog(Log log, int confirmations, boolean confirmed);
}
