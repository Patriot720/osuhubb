package example.cerki.osuhub.List;

import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import example.cerki.osuhub.R;
import example.cerki.osuhub.TestHelper;
import example.cerki.osuhub.Util;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static example.cerki.osuhub.TestHelper.getFromResources;
import static example.cerki.osuhub.TestHelper.isImageEqualToRes;
import static org.junit.Assert.*;

/**
 * Created by cerki on 02-Dec-17.
 */
@RunWith(AndroidJUnit4.class)
public class ViewHelperTest {
    private ImageView arrow;
    private TextView textView;

    @Before
    public void setUp() throws Exception {
        arrow = new ImageView(getTargetContext());
        textView = new TextView(getTargetContext());
    }
    @Test
    public void testJsonObjectConstructor() throws Exception {
        StringBuilder builder = getFromResources("json.txt"); // TODO move to it's own test class
        JSONArray jsonArray = new JSONArray(builder.toString());
        Player player = new Player(jsonArray.getJSONObject(0));
        assertEquals(player.getString("pp_rank"),"4");
        assertEquals(2831793,player.getId());
    }

    @Test
    public void testJsonObjectEmpty() throws Exception {
        Player player = new Player(new JSONObject());
    }

    @Test
    public void setDiffNormalNegativeFloatValue(){
        Double value = -0.5;
        Util.showPlayerDifference(textView,arrow,value);
        assertDown("0.50", isImageEqualToRes(arrow, R.drawable.arrow_down));
    }

    private void assertDown(String actual, boolean imageEqualToRes) {
        assertEquals(textView.getText(), actual);
        assertTrue(imageEqualToRes);
    }

    @Test
    public void setDiffNormalPositiveFloat() throws Exception {
        Double value = 0.342342;
        Util.showPlayerDifference(textView,arrow,value);
        assertUp("0.34");
    }

    private void assertUp(String actual) {
        assertDown(actual, arrow.getDrawable() == null);
    }

    @Test
    public void testHugeNullValue() throws Exception {
        Double value = 0.000000000023;
        Util.showPlayerDifference(textView,arrow,value);
        assertInvisible();
    }

    private void assertInvisible() {
        assertEquals(textView.getVisibility() , View.INVISIBLE);
        assertEquals(textView.getVisibility(),View.INVISIBLE);
    }

    @Test
    public void testNullValue(){
        Double value = 0d;
        Util.showPlayerDifference(textView,arrow,value);
        assertInvisible();
    }
    @Test
    public void testHugeNegativeNullValue(){
        Double value = -0.0000000004343;
        Util.showPlayerDifference(textView,arrow,value);
        assertInvisible();
    }
}