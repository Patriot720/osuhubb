package example.cerki.osuhub;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;


public class GenericRecyclerBuilder<T extends AbstractFlexibleItem> {
    private int layoutResource;
    private GenericRecyclerFragment.OnClickListener<T> listener;
    private FlexibleAdapter.EndlessScrollListener endlessScroll;
    private GenericRecyclerFragment.WorkerInterface worker;
    private int pageSize;

    private GenericRecyclerBuilder(int layoutResource) {
        this.layoutResource = layoutResource;
    }

    public static <T extends AbstractFlexibleItem> GenericRecyclerBuilder<T> using(int layoutResource){
        return new GenericRecyclerBuilder<>(layoutResource);
    }
    public GenericRecyclerBuilder onClickListener(GenericRecyclerFragment.OnClickListener<T> listener){
        this.listener = listener;
        return this;
    }
    public GenericRecyclerBuilder endlessScrollListener(FlexibleAdapter.EndlessScrollListener listener){
        this.endlessScroll = listener;
        return this;
    }
    public GenericRecyclerBuilder withPageSize(int size){
        this.pageSize = size;
        return this;
    }
    public GenericRecyclerBuilder workerFunctions(GenericRecyclerFragment.WorkerInterface worker){
        this.worker = worker;
        return this;
    }
    public GenericRecyclerFragment<T> build(){
        return new GenericRecyclerFragment<>(layoutResource, listener, endlessScroll, worker, pageSize);
    }

}
