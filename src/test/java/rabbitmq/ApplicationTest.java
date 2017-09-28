package rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rabbitmq.manager.MessageCounterManager;
import rabbitmq.model.Message;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by fotarik on 26/09/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Value(value = "${rabbitmq.queue.name}")
    private String queueName;
    @Value(value = "${rabbitmq.topic.exchange}")
    private String topicExchange;
    private static final String WRONG_QUEUE = "rabbitmq-wrong-example";
    private CountDownLatch lock = new CountDownLatch(1);

    @Autowired
    RabbitTemplate rabbitTemplate;

    private final String ENGLISH_PHONE_NUMBER = "+441111119020";


    @Test
    public void testSpringBootApplicationRun() throws InterruptedException {
        Application.main(new String[]{
                "--spring.main.web-environment=false",
                "--spring.autoconfigure.exclude=blahblahblah",
        });
    }

    @Test
    public void produceMessageAndSendToQueue() throws Exception {
        //send a message to the correct queue
        Message event = Message.builder().id("1").phoneNumber(ENGLISH_PHONE_NUMBER).build();
        rabbitTemplate.convertAndSend(topicExchange, queueName, event);
        lock.await(2000, TimeUnit.MILLISECONDS);
        assertEquals(MessageCounterManager.getInstance().getCounter().longValue(), 1L);
        //send second message to the correct queue
        event = Message.builder().id("2").phoneNumber(ENGLISH_PHONE_NUMBER).build();
        rabbitTemplate.convertAndSend(topicExchange, queueName, event);
        lock.await(2000, TimeUnit.MILLISECONDS);
        assertEquals(MessageCounterManager.getInstance().getCounter().longValue(), 2L);
        //send a message to the wrong queue
        event = Message.builder().id("3").phoneNumber(ENGLISH_PHONE_NUMBER).build();
        rabbitTemplate.convertAndSend(topicExchange, WRONG_QUEUE, event);
        lock.await(2000, TimeUnit.MILLISECONDS);
        assertEquals(MessageCounterManager.getInstance().getCounter().longValue(), 2L);
    }

}
