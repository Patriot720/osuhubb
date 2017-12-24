package example.cerki.osuhub.BeatmapActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.github.zagum.switchicon.SwitchIconView;

/**
 * Created by cerki on 21.12.2017.
 */

public class SwitchIconExtend extends SwitchIconView {
    public SwitchIconExtend(@NonNull Context context) {
        super(context);
        setOnClickListener(view -> switchState());
    }

    public SwitchIconExtend(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(view -> switchState());
    }

    public SwitchIconExtend(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(view -> switchState());
    }
}
