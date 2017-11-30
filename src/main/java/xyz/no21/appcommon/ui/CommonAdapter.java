package xyz.no21.appcommon.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lin on 2017/4/29.
 * Email:L437943145@gmail.com
 * <p>
 * desc:
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonAdapter.CommonViewHolder> {

    protected List<T> items;
    protected ItemClickListener<T> mItemClickListener;
    protected Context mContext;

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    protected View inflaterView(ViewGroup parent, int id) {
        return LayoutInflater.from(parent.getContext()).inflate(id, parent, false);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mContext =  recyclerView.getContext();
    }

    @Override
    public abstract void onBindViewHolder(CommonViewHolder holder, int position);

    public void setItemClickListener(ItemClickListener<T> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItems(List<T> items) {
        int originCount = getItemCount();
        if (this.items == null) {
            this.items = items;
            notifyDataSetChanged();
        } else {
            this.items.addAll(items);
            notifyItemRangeInserted(originCount, items.size());
        }

    }

    public interface ItemClickListener<T> {
        void onItemClick(RecyclerView.ViewHolder holder, T data, int id);
    }

    public static class CommonViewHolder extends RecyclerView.ViewHolder {
        public int itemType;

        SparseArray<View> mViews = new SparseArray<>(7);

        public CommonViewHolder(final View itemView) {
            super(itemView);
        }

        public CommonViewHolder(View itemView, int itemType) {
            super(itemView);
            this.itemType = itemType;
        }

        public <V extends View> V findViewById(int id, Class<V> type) {
            View view = mViews.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                mViews.put(id, view);
            }
//            noinspection unchecked
            return (V) view;
        }

    }
}
