package example.jbot.slack.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import me.ramswaroop.jbot.core.slack.Bot;
import me.ramswaroop.jbot.core.slack.SlackService;

/**
 * Test handlers discovery
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class HandlersTest {

    @MockBean
    private SlackService slackService;

    @Autowired
    private BotOne botOne;

    @Autowired
    private BotTwo botTwo;

    @Test
    public void test() {
        Assert.assertNotNull(botOne);
        Assert.assertNotNull(botTwo);

        Field field = ReflectionUtils.findField(Bot.class, "methodNameMap");
        field.setAccessible(true);
        Set botOneNames = ((Map) ReflectionUtils.getField(field, botOne))
            .keySet();
        Set botTwoNames = ((Map) ReflectionUtils.getField(field, botTwo))
            .keySet();

        assertEquals(3, botOneNames.size());
        assertEquals(3, botTwoNames.size());

        assertTrue(botOneNames.contains("oneMessage"));
        assertTrue(botOneNames.contains("threeMessage"));
        assertTrue(botOneNames.contains("fourMessage"));

        assertTrue(botTwoNames.contains("twoMessage"));
        assertTrue(botTwoNames.contains("threeMessage"));
        assertTrue(botTwoNames.contains("fourMessage"));
    }
}
