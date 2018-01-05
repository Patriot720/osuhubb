package example.cerki.osuhub.ui.Dialogs;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.zagum.switchicon.SwitchIconView;

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
    GridLayout modsView;
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
        ButterKnife.bind(this,dialogView);

        clearButton.setOnClickListener(l -> {
            clearMods();
            clearUsername();
        });
    }

    public void show(){
        dialog.show();
    }

    private void clearMods() {
        for (int i = 0; i < modsView.getChildCount(); i++) {
            SwitchIconView icon = (SwitchIconView) modsView.getChildAt(i);
            icon.setIconEnabled(false);
        }
    }

    public String getUsername(){
        return username.getText().toString();
    }

    public boolean isEmpty(){
        return getModsIntegerValue() == 0 && getUsername().isEmpty();
    }
    public boolean isUsernameEmpty(){
        return getUsername().isEmpty();
    }


    public  int getModsIntegerValue(){
        int childCount = modsView.getChildCount();
        int mods = 0;
        for (int i = 0; i < childCount; i++) {
            SwitchIconView mod = (SwitchIconView) modsView.getChildAt(i);
            if(mod.isIconEnabled()) {
                int flag = Integer.parseInt((String) mod.getTag());
                mods += flag;
            }
        }
        return mods;
    }
    private void clearUsername() {
        username.setText("");
    }
}
