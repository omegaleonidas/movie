package com.Sidiq.moviecataloc;

import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemClickSupport {
    private final RecyclerView mrecyclerView;

    private OnItemClickListener monItemClickListener;
    private OnItemLongClickListener monItemLongClickListener;

    public ItemClickSupport(RecyclerView recyclerView) {
        mrecyclerView = recyclerView;
        mrecyclerView.setTag(R.id.item_click_support, this);
        mrecyclerView.addOnChildAttachStateChangeListener(listener);
    }


    private View.OnClickListener monClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (monItemClickListener != null) {
                RecyclerView.ViewHolder holder = mrecyclerView.getChildViewHolder(v);
                monItemClickListener.onItemClicked(mrecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };
    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (monItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mrecyclerView.getChildViewHolder(v);
                return monItemLongClickListener.onItemLongClicked(mrecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener listener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(@NonNull View view) {
            if (monItemClickListener != null) {
                view.setOnClickListener(monClickListener);
            }
            if (monItemLongClickListener != null) {
                view.setOnLongClickListener(onLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(@NonNull View view) {

        }
    };


    public static ItemClickSupport addTo(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support == null) {
            support = new ItemClickSupport(view);
        }
        return support;
    }

    public static ItemClickSupport removeFrom(RecyclerView view) {
        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(listener);
        view.setTag(R.id.item_click_support, null);
    }

    public ItemClickSupport setOnItemClickListener(OnItemClickListener listener) {
        monItemClickListener = listener;
        return this;
    }

    public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
        monItemLongClickListener = listener;
        return this;
    }


    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }

}
