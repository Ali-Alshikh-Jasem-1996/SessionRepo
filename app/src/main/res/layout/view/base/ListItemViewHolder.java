package com.example.nargisshoppapp10.view.base;

import android.view.View;

import com.example.nargisshoppapp10.util.Util;

import androidx.annotation.NonNull;

public abstract class ListItemViewHolder<T, MODEL extends ListItemViewModel<T>> {
  private final OnClickListener onClickListener;
  private MODEL itemModel;

  public ListItemViewHolder(@NonNull final OnClickListener onClickListener) {
    this.onClickListener = Util.checkNotNull(onClickListener, "clickListener == null");
  }

  protected void bindView(@NonNull final View view) {
    //////////////////////////////////////////////
  }

  public void bindModel(@NonNull final MODEL listViewItemModel, final int position) {
    itemModel = listViewItemModel;
  }

  @SuppressWarnings("WeakerAccess") public MODEL itemModel() {
    return itemModel;
  }

  @NonNull protected OnClickListener onClickListener() {
    return onClickListener;
  }

  protected void notifyOnClickListener() {
    onClickListener.onClick(itemModel);
  }

  public interface OnClickListener<T, MODEL extends ListItemViewModel<T>> {
    void onClick(@NonNull MODEL itemModel);
  }
}
