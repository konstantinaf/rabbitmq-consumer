package rabbitmq.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by fotarik on 28/09/2017.
 */
public class MessageTest {

    @Test
    public void testBuilderWithNoInfo(){
        Message message = Message.builder().build();
        assertNull(message.getId());
    }

    @Test
    public void testBuilderWithId(){
        Message message = Message.builder().id("123").build();
        assertEquals(message.getId(),"123");
    }

    @Test
    public void testBuilderWithPhoneNumber(){
        Message message = Message.builder().phoneNumber("6988190541").build();
        assertEquals(message.getPhoneNumber(),"6988190541");
    }

    @Test
    public void testBuilderWithIdAndPhoneNumber(){
        Message message = Message.builder().id("123").phoneNumber("6988190541").build();
        assertEquals(message.getId(),"123");
        assertEquals(message.getPhoneNumber(),"6988190541");
    }
}
