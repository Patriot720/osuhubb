package example.cerki.osuhub.ui;

import android.view.View;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;
import example.cerki.osuhub.R;

/**
 * Created by cerki on 27.12.2017.
 */

class ScoreBoardHeader extends AbstractFlexibleItem<ScoreBoardHeader.ViewHolder> {

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.scoreboard_header;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter adapter) {
        return new ViewHolder(view,adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ViewHolder holder, int position, List payloads) {

    }

    public class ViewHolder extends FlexibleViewHolder{
        public ViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
        }
    }
}
