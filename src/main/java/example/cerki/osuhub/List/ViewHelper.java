package example.cerki.osuhub.List;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
}
