package example.cerki.osuhub.ui;

import android.support.annotation.Nullable;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;


    public class FlexibleAdapterExtension<T extends IFlexible> extends FlexibleAdapter<T> {
        private OnDataUpdatedListener onDataUpdatedListener;
        public FlexibleAdapterExtension(@Nullable List<T> items) {
            super(items);
        }
        public interface OnDataUpdatedListener {
            void onDataUpdated();
        }

        public FlexibleAdapterExtension(@Nullable List<T> items, @Nullable Object listeners) {
            super(items, listeners);
        }

        @Override
        public void updateDataSet(@Nullable List<T> items) {
            if(items != null && items.size() != 0)
                super.updateDataSet(items);
            onDataUpdated();
        }

        public void setOnDataUpdatedListener(OnDataUpdatedListener onDataUpdatedListener) {
            this.onDataUpdatedListener = onDataUpdatedListener;
        }

        private void onDataUpdated() {
            onDataUpdatedListener.onDataUpdated();
        }
    }
