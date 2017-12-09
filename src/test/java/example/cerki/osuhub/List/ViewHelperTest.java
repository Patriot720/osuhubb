package example.cerki.osuhub.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import example.cerki.osuhub.R;
import example.cerki.osuhub.TestHelper;
import example.cerki.osuhub.Util;

import static example.cerki.osuhub.TestHelper.getFromResources;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
@RunWith(RobolectricTestRunner.class)
public class ViewHelperTest {
    private ImageView arrow;
    private TextView textView;
    private Context mContext;

    @Before
    public void setUp() throws Exception {
        mContext = RuntimeEnvironment.application;
        arrow = new ImageView(mContext);
        textView = new TextView(mContext);
    }
    @Test
    public void testJsonObjectConstructor() throws Exception {
        StringBuilder builder = getFromResources(mContext,"json.txt"); // TODO move to it's own test class
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
        assertDown("0.50", TestHelper.isImageEqualToRes(mContext,arrow, R.drawable.arrow_down));
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