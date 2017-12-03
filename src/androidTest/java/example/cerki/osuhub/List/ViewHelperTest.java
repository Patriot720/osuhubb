package example.cerki.osuhub.List;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import example.cerki.osuhub.R;
import example.cerki.osuhub.TestHelper;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static example.cerki.osuhub.TestHelper.isImageEqualToRes;
import static org.junit.Assert.*;

/**
 * Created by cerki on 02-Dec-17.
 */
// TODO MOVE TO LOCAL TEST
@RunWith(AndroidJUnit4.class)
public class ViewHelperTest {
    private ImageView arrow;
    private TextView textView;
    private ImageView arrow_down;
    private ImageView arrow_up;

    @Before
    public void setUp() throws Exception {
        arrow = new ImageView(getTargetContext());
        textView = new TextView(getTargetContext());
        arrow_down = new ImageView(getTargetContext());
        arrow_down.setImageResource(R.drawable.arrow_down);
        arrow_up = new ImageView(getTargetContext());
        arrow_up.setImageResource(R.drawable.arrow_up);
    }

    @Test
    public void setDifference() throws Exception {
        String value = "-23";
        ViewHelper.setDifference(textView,arrow,value);
        assertEquals("23",textView.getText());
        assertTrue(arrow.getVisibility() == View.VISIBLE);
    }

    @Test
    public void setDifferenceEmptyValues() throws Exception {
        String value = "";
        ViewHelper.setDifference(textView,arrow,value);
        assertInvisible();
    }

    private void assertInvisible() {
        assertTrue(arrow.getVisibility() == View.INVISIBLE);
        assertTrue(textView.getVisibility() == View.INVISIBLE);
    }

    @Test
    public void setDifferenceMinusOnly() throws Exception {
        ViewHelper.setDifference(textView,arrow,"-");
        assertInvisible();
    }

    @Test
    public void setDifferenceMinusNull() throws Exception {
        ViewHelper.setDifference(textView,arrow,"-0");
        assertInvisible();
    }

    @Test
    public void setDifference0Value() throws Exception {
        String value = "0";
        String floatVal = "0.00";
        ViewHelper.setDifference(textView,arrow,value);
        assertTrue(arrow.getVisibility() == View.INVISIBLE);
        ViewHelper.setDifference(textView,arrow,floatVal);
        assertTrue(arrow.getVisibility() == View.INVISIBLE);
    }
    @Test
    public void setDifferenceEmptyValueWithMinus(){
        String value = "-0.00";
        ViewHelper.setDifference(textView,arrow,value);
        assertTrue(arrow.getVisibility() == View.INVISIBLE);
    }
    @Test
    public void setDiffNormalNegativeFloatValue(){
        Double value = -0.5;
        ViewHelper.setDiff(textView,arrow,value);
        assertEquals(textView.getText(),"0.50");
        assertTrue(isImageEqualToRes(arrow,R.drawable.arrow_up));
    }

    @Test
    public void setDiffNormalPositiveFloat() throws Exception {
        Double value = 0.342342;
        ViewHelper.setDiff(textView,arrow,value);
        assertEquals(textView.getText(),"0.34");
        assertTrue(arrow.getDrawable() == null);
    }

    @Test
    public void testHugeNullValue() throws Exception {
        Double value = 0.000000000023;
        ViewHelper.setDiff(textView,arrow,value);
        assertEquals(textView.getVisibility() , View.INVISIBLE);
        assertEquals(textView.getVisibility(),View.INVISIBLE);
    }
}