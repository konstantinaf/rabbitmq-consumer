package rabbitmq.services;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rabbitmq.model.GroupingModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by fotarik on 26/09/2017.
 */
@Service
@Slf4j
public class GroupingService {

    private List<GroupingModel> groupingModelList = new ArrayList<>();

    public Map<String, List<GroupingModel>> groupPhoneNumbersByCountryCode(String phoneNumber) {
        findCountryCodeByPhoneNumber(phoneNumber);
        return groupingModelList.stream().collect(Collectors.groupingBy(GroupingModel::getCountryCode));
    }

    private void findCountryCodeByPhoneNumber(String phoneNumber) {

        boolean isValid;
        Phonenumber.PhoneNumber number;
        PhoneNumberUtil util = PhoneNumberUtil.getInstance();

        for (String r : util.getSupportedRegions()) {
            try {
                // check if it's a possible number
                isValid = util.isPossibleNumber(phoneNumber, r);
                if (isValid) {
                    number = util.parse(phoneNumber, r);
                    // check if it's a valid number for the given region
                    isValid = util.isValidNumberForRegion(number, r);
                    if (isValid) {
                        GroupingModel groupingModel = new GroupingModel();
                        groupingModel.setCountryCode(r);
                        groupingModel.setPhoneNumber(phoneNumber);
                        groupingModelList.add(groupingModel);
                    }
                } else {
                    log.error("Phone number is wrong ");
                }
            } catch (NumberParseException e) {
                log.error("Number Parse Exception ", e);
            }
        }
    }
}
