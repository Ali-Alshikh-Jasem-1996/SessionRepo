package com.example.nargisshoppapp10.view.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.nargisshoppapp10.util.Util.checkNotNull;

final class RecyclerViewItemHolder extends RecyclerView.ViewHolder {
  private ListItemViewHolder viewItemHolder;
  private final ListItemViewHolder.OnClickListener onListItemClickListener;

  @SuppressWarnings("unchecked") RecyclerViewItemHolder(@NonNull final View itemView, @NonNull final OnClickListener onClickListener) {
    super(itemView);
    checkNotNull(onClickListener, "onClickListener == null");
    onListItemClickListener = onClickListener::onClick;
  }

  @SuppressWarnings("unchecked") void bindModel(@NonNull final ListItemViewModel listItem, final int position) {
    if (viewItemHolder == null) {
      viewItemHolder = listItem.createViewHolder(onListItemClickListener);
      viewItemHolder.bindView(itemView);
    }
    listItem.bindView(viewItemHolder, position);
  }

  public ListItemViewHolder viewItemHolder() {
    return viewItemHolder;
  }

  interface OnClickListener<T, MODEL extends ListItemViewModel<T>> {
    void onClick(@NonNull MODEL itemModel);
  }
}
