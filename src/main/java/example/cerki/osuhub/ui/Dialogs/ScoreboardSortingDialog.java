package example.cerki.osuhub.ui.Dialogs;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.zagum.switchicon.SwitchIconView;

import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.cerki.osuhub.R;

/**
 * Created by cerki on 02.01.2018.
 */

@SuppressWarnings("WeakerAccess")
public class ScoreboardSortingDialog {
    public interface OnPositiveListener{
        void onPositive();
    }
    private final MaterialDialog dialog;
    View dialogView;
    @BindView(R.id.clear_button)
    Button clearButton;
    @BindView(R.id.mods)
    GridLayout mods;
    @BindView(R.id.username)
    EditText username;

    public ScoreboardSortingDialog(Context context, OnPositiveListener onPositive) {
        dialog = new MaterialDialog.Builder(context)
                .title(R.string.sorting_dialog_title)
                .customView(R.layout.sorting_dialog_view, false)
                .positiveText(R.string.mods_accept)
                .onPositive((first,second)->onPositive.onPositive())
                .build();

        dialogView = dialog.getCustomView();
        if(dialogView == null)
            return;
        ButterKnife.bind(dialogView);

        clearButton.setOnClickListener(l -> {
            clearMods();
            clearUsername();
        });
    }

    public void show(){
        dialog.show();
    }

    private void clearMods() {
        for (int i = 0; i < mods.getChildCount(); i++) {
            SwitchIconView icon = (SwitchIconView) mods.getChildAt(i);
            icon.setIconEnabled(false);
        }
    }

    private void clearUsername() {
        username.setText("");
    }
}
