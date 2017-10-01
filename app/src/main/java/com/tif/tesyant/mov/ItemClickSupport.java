package com.tif.tesyant.mov;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by tesyant on 9/30/17.
 */

public class ItemClickSupport {

//    private final RecyclerView mRecyclerView;
//    private AdapterView.OnItemClickListener mOnItemClickListener;
//    private AdapterView.OnItemLongClickListener mOnItemLongClickListener;
//
//    public ItemClickSupport(RecyclerView recyclerView) {
//        mRecyclerView = recyclerView;
//        mRecyclerView.setTag(R.id.item_click_support, this);
//        mRecyclerView.addOnChildAttachStateChangeListener(mAttchListener);
//    }
//
//    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            if (mOnItemClickListener != null) {
//                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(view);
//                mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), view);
//            }
//        }
//    };
//
//    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
//        @Override
//        public boolean onLongClick(View view) {
//            if (mOnItemLongClickListener != null) {
//                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(view);
//                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), view);
//            }
//            return false;
//        }
//    };
//
//    private RecyclerView.OnChildAttachStateChangeListener mAttchListener = new RecyclerView.OnChildAttachStateChangeListener() {
//        @Override
//        public void onChildViewAttachedToWindow(View view) {
//            if (mOnItemClickListener != null) {
//                view.setOnClickListener(mOnClickListener);
//            }
//            if (mOnItemLongClickListener != null) {
//                view.setOnLongClickListener(mOnLongClickListener);
//            }
//        }
//
//        @Override
//        public void onChildViewDetachedFromWindow(View view) {
//        }
//    };
//
//    public static ItemClickSupport addTo(RecyclerView view) {
//        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
//        if (support == null) {
//            support = new ItemClickSupport(view);
//        }
//        return support;
//    }
//
//    public static ItemClickSupport removeFrom(RecyclerView view) {
//        ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
//        if (support != null) {
//            support.detach(view);
//        }
//        return support;
//    }
//
//    public ItemClickSupport setOnItemClickListener(AdapterView.OnItemClickListener listener) {
//        mOnItemClickListener = listener;
//        return this;
//    }
//
//    public ItemClickSupport setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
//        mOnItemLongClickListener = listener;
//        return this;
//    }
//
//    private void detach(RecyclerView view) {
//        view.removeOnChildAttachStateChangeListener(mAttchListener);
//        view.setTag(R.id.item_click_support, null);
//    }
//
//    public interface OnItemClickListener {
//        void onItemClicked(RecyclerView recyclerView, int position, View view);
//    }
//
//    public interface OnItemLongClickListener {
//        boolean onItemLongClicked(RecyclerView recyclerView, int position, View view);
//    }
}
