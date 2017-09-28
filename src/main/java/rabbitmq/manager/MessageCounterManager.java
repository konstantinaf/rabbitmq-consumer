package rabbitmq.manager;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by fotarik on 26/09/2017.
 */
public enum MessageCounterManager {
    INSTANCE;

    @Getter
    private AtomicLong counter = new AtomicLong(0L);

    public static MessageCounterManager getInstance() {
        return MessageCounterManager.INSTANCE;
    }
}

