package rabbitmq.model;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * Created by fotarik on 26/09/2017.
 */
@Getter
@Builder
public class Message implements Serializable {

    private String id;
    private String phoneNumber;
}

