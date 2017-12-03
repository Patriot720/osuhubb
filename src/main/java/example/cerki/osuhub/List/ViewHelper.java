package example.cerki.osuhub.List;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import example.cerki.osuhub.R;

/**
 * Created by cerki on 02-Dec-17.
 */

public class ViewHelper {
    public static void setDifference(TextView textView, ImageView arrow, String value) {
        if(value.equals("") || value.equals("0") || value.contains("0.00")
                || value.equals("-0") || value.equals("-")){ // TODO change to regexp
            textView.setVisibility(View.INVISIBLE);
            arrow.setVisibility(View.INVISIBLE);
            return;
        }
        if(value.charAt(0) == '-') {
            value = value.substring(1);
            arrow.setImageResource(R.drawable.arrow_down);
        }
        textView.setText(value);
        textView.setVisibility(View.VISIBLE);
        arrow.setVisibility(View.VISIBLE);

    }

    public static String convertToString(Double value){

        if(value % 1 == 0) {
            int val = value.intValue();
            return String.valueOf(val);
        }
        return String.format(Locale.getDefault(),"%.2f",value);
    }
    @SuppressLint("SetTextI18n")
    public static void setDiff(TextView textView, ImageView arrow, Double value) {
        if(value < 0.01 && value > 0){
            textView.setVisibility(View.INVISIBLE);
            arrow.setVisibility(View.INVISIBLE);
            return;
        }
        if(value < 0){
            arrow.setImageResource(R.drawable.arrow_down);
            value = value * -1;
        }
        textView.setText(convertToString(value));
        arrow.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
    }
}
