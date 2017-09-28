package rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rabbitmq.manager.MessageCounterManager;
import rabbitmq.model.GroupingModel;
import rabbitmq.model.Message;
import rabbitmq.services.GroupingService;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by fotarik on 26/09/2017.
 */
@Slf4j
@Component
public class Consumer {

    @Autowired
    private GroupingService groupingService;

    public void receiveMessage(Message message) {
        increaseMessagesCountrer();
        groupAndLogNumbersByCountry(message);
        logCounter();
    }

    private void increaseMessagesCountrer() {
        MessageCounterManager.getInstance().getCounter().incrementAndGet();
    }

    private void groupAndLogNumbersByCountry(Message message) {
        Map<String, List<GroupingModel>> groupingModelList = groupingService.groupPhoneNumbersByCountryCode(message.getPhoneNumber());
        logGrouping(groupingModelList);
    }

    private void logCounter() {
        log.info("Messages Count= " + MessageCounterManager.getInstance().getCounter());
    }

    private void logGrouping(Map<String, List<GroupingModel>> groupingModelList) {
        Iterator<Map.Entry<String, List<GroupingModel>>> it = groupingModelList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<GroupingModel>> pair = it.next();
            log.info("Country " + pair.getKey() + " has " + pair.getValue().size() + " number(s).");
            for (GroupingModel groupingModel : pair.getValue()) {
                log.info("Number " + groupingModel.getPhoneNumber());
            }

        }
    }

}
