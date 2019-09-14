/*
 * MIT License
 *
 * Copyright (c) 2018 Soojeong Shin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.android.newsfeed;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * EmptyRecyclerView is RecyclerView subclass that provides empty view support for RecyclerView
 * to show or hide an empty view based on whether the adapter provided to the RecyclerView has
 * data or not.
 */

public class EmptyRecyclerView extends RecyclerView {

    private View mEmptyView;

    /**
     * The AdapterDataObserver calls checkIfEmpty() method every time, and it observes
     * an event that changes the content of the adapter
     */
    final private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs,
                             int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Checks if both mEmptyView and adapter are not null.
     * Hide or show mEmptyView depending on the size of the data(item count) in the adapter.
     */
    private void checkIfEmpty() {
        // If the item count provided by the adapter is equal to zero, make the empty View visible
        // and hide the EmptyRecyclerView.
        // Otherwise, hide the empty View and make the EmptyRecyclerView visible.
        if (mEmptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible =
                    getAdapter().getItemCount() == 0;
            mEmptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }

        checkIfEmpty();
    }

    /**
     * Set an empty view on the EmptyRecyclerView
     * @param emptyView refers to the empty state of the view
     */
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        checkIfEmpty();
    }
}
