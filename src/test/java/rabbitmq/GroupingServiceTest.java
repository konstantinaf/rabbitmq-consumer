package rabbitmq;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rabbitmq.model.GroupingModel;
import rabbitmq.services.GroupingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by fotarik on 26/09/2017.
 */

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class GroupingServiceTest {

    @Autowired
    GroupingService groupingService;

    private final String ENGLISH_PHONE_NUMBER ="+441619149020";
    private final String ENGLISH_PHONE_NUMBER_2 ="+441619149627";
    private final String GREEK_PHONE_NUMBER="+306988190541";
    private final String WRONG__PHONE_NUMBER="+306988190541";
    private final String EN_COUNTRY_CODE = "GB";
    private final String GR_COUNTRY_CODE = "GR";

    private List<GroupingModel> groupingModels;
    GroupingModel englishCountryPhones;

    Map<String, List<GroupingModel>> groupedNumbersByCountry = new HashMap<>();
    @Before
    public void init() {
        groupingModels = new ArrayList<>();
        englishCountryPhones = new GroupingModel();
        englishCountryPhones.setPhoneNumber(ENGLISH_PHONE_NUMBER);
        englishCountryPhones.setCountryCode(EN_COUNTRY_CODE);
        groupingModels.add(englishCountryPhones);
    }

    @Test
    public void testValidPhoneNumber() throws Exception{
        groupedNumbersByCountry = groupingService.groupPhoneNumbersByCountryCode(ENGLISH_PHONE_NUMBER);
        assertNotNull(groupedNumbersByCountry);
        groupingModels = groupedNumbersByCountry.get(EN_COUNTRY_CODE);

        assertEquals(groupingModels.get(0).getPhoneNumber(), englishCountryPhones.getPhoneNumber());

        groupedNumbersByCountry = groupingService.groupPhoneNumbersByCountryCode(ENGLISH_PHONE_NUMBER_2);
        assertEquals(groupedNumbersByCountry.get(EN_COUNTRY_CODE).size(),2);

        groupedNumbersByCountry = groupingService.groupPhoneNumbersByCountryCode(GREEK_PHONE_NUMBER);
        assertEquals(groupedNumbersByCountry.get(EN_COUNTRY_CODE).size(),2);
        assertEquals(groupedNumbersByCountry.get(GR_COUNTRY_CODE).size(),1);
    }

    @Test
    public void testInvalidPhoneNumber() throws Exception{
         assertEquals(groupingService.groupPhoneNumbersByCountryCode(WRONG__PHONE_NUMBER).size(),2);
    }


}

