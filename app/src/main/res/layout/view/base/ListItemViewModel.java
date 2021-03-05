package com.example.nargisshoppapp10.view.base;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public abstract class ListItemViewModel<T> {
  private final T payload;
  private final int viewType;
  private int position;

  public ListItemViewModel(final T payload, @LayoutRes final int viewType) {
    this.payload = payload;
    this.viewType = viewType;
  }

  public abstract ListItemViewHolder<T, ListItemViewModel<T>> createViewHolder(final ListItemViewHolder.OnClickListener onClickListener);

  public void bindView(final ListItemViewHolder<T, ListItemViewModel<T>> viewHolder, final int position) {
    this.position = position;
    viewHolder.bindModel(this, position);
  }

  public long itemId() {
    if (payload == null) {
      return 0;
    } else {
      return payload().hashCode();
    }
  }

  public T payload() {
    return payload;
  }

  @LayoutRes
  public int viewType() {
    return viewType;
  }

  public int position() {
    return position;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof ListItemViewModel)) return false;

    final ListItemViewModel that = (ListItemViewModel) o;

    if (viewType != that.viewType) return false;
    return payload != null ? payload.equals(that.payload) : that.payload == null;
  }

  @Override
  public int hashCode() {
    int result = payload != null ? payload.hashCode() : 0;
    result = 31 * result + viewType;
    return result;
  }

  public boolean equalsById(@NonNull final ListItemViewModel other) {
    return false;
  }

  public boolean equalsByContent(@NonNull final ListItemViewModel other) {
    return false;
  }
}
