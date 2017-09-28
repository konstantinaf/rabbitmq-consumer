package rabbitmq.consumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import rabbitmq.model.Message;
import rabbitmq.services.GroupingService;

/**
 * Created by fotarik on 28/09/2017.
 */
@SpringBootTest
public class ConsumerTest {

    @InjectMocks
    Consumer consumer;

    @Mock
    GroupingService groupingService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testReceiveMessage(){
        consumer.receiveMessage(createMessage());
    }

    private Message createMessage() {
        return Message.builder().id("123").phoneNumber("1234").build();
    }
}
