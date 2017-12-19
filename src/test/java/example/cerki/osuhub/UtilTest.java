package example.cerki.osuhub;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by cerki on 04-Dec-17.
 */
public class UtilTest {
    @Test
    public void testPattern() throws Exception {
        Pattern pattern = Pattern.compile("/(\\d+)/");
        String s = "https://assets.ppy.sh//beatmaps/480963/covers/card.jpg?1512657210";
        Matcher matcher = pattern.matcher(s);
        matcher.find();
        assertEquals(matcher.group(1),"480963");
    }
}