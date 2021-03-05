package com.example.nargisshoppapp10.view.image;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.example.nargisshoppapp10.R;
import com.example.nargisshoppapp10.view.base.ListItemViewHolder;
import com.example.nargisshoppapp10.view.base.ListItemViewModel;
import com.example.nargisshoppapp10.view.base.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.nargisshoppapp10.util.Util.checkNotNull;

public final class ImageGalleryView extends FrameLayout implements RecyclerViewAdapter.OnItemClickListener {
  private RecyclerView pagerView;
  private RecyclerView pagerIndicatorView;
  private View pagerIndicatorFrameView;

  private final RecyclerViewAdapter pagerAdapter = new RecyclerViewAdapter();
  private final RecyclerViewAdapter pagerIndicatorAdapter = new RecyclerViewAdapter(this);
  private final RecyclerViewAdapter.ItemComparator itemComparator = new RecyclerViewAdapter.ItemComparator() {
    @Override
    public boolean equalsById(final ListItemViewModel oldItem, final ListItemViewModel newItem) {
      return oldItem.equals(newItem);
    }

    @Override
    public boolean equalsByContent(final ListItemViewModel oldItem, final ListItemViewModel newItem) {
      return oldItem.equals(newItem);
    }
  };

  public ImageGalleryView(final Context context) {
    super(context);
  }

  public ImageGalleryView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
  }

  public ImageGalleryView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void renderImages(@NonNull final List<String> images) {
    checkNotNull(images, "images == null");

    List<ListItemViewModel> items = new ArrayList<>();
    for (String image : images) {
      items.add(new PagerListItemModel(image));
    }
    pagerAdapter.swapItemsAndNotify(items, itemComparator);

    items = new ArrayList<>();
    for (String image : images) {
      items.add(new PagerIndicatorListItemModel(image));
    }
    pagerIndicatorAdapter.swapItemsAndNotify(items, itemComparator);
  }

  @Override
  public void onItemClick(@NonNull final ListItemViewModel itemViewModel) {
    pagerView.smoothScrollToPosition(itemViewModel.position());
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    initPagerView();
    initPagerIndicator();
  }

  private void initPagerView() {
    pagerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    pagerView.setHasFixedSize(true);
    pagerView.setAdapter(pagerAdapter);
    new PagerSnapHelper().attachToRecyclerView(pagerView);

    pagerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
        int index = ((LinearLayoutManager) pagerView.getLayoutManager()).findFirstVisibleItemPosition();
        View pagerItemView = ImageGalleryView.this.pagerView.getLayoutManager().findViewByPosition(index);

        index = ((LinearLayoutManager) pagerIndicatorView.getLayoutManager()).findFirstVisibleItemPosition();
        View pagerIndicatorItemView = pagerIndicatorView.getLayoutManager().findViewByPosition(index);

        float offset = 1f * dx / (pagerView.getAdapter().getItemCount() * pagerItemView.getWidth());
        pagerIndicatorFrameView.setTranslationX(pagerIndicatorFrameView.getTranslationX()
          + offset * pagerIndicatorItemView.getWidth() * pagerIndicatorView.getAdapter().getItemCount());
      }
    });
  }

  private void initPagerIndicator() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    pagerIndicatorView.setLayoutManager(layoutManager);
    pagerIndicatorView.setHasFixedSize(true);
    pagerIndicatorView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        pagerIndicatorFrameView.setTranslationX(pagerIndicatorFrameView.getTranslationX() - dx);
      }
    });

    int padding = getResources().getDimensionPixelOffset(R.dimen.default_padding_half) / 2;
    pagerIndicatorView.addItemDecoration(new RecyclerView.ItemDecoration() {
      @Override
      public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent, final RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position == RecyclerView.NO_POSITION) {
          return;
        }
        outRect.left = position == 0 ? padding : 0;
        outRect.right = parent.getAdapter().getItemCount() > 1 && position == parent.getAdapter().getItemCount() - 1 ? padding : 0;
      }
    });
    pagerIndicatorFrameView.setTranslationX(padding);
    pagerIndicatorView.setAdapter(pagerIndicatorAdapter);
  }

  @SuppressWarnings("WeakerAccess")
  static final class PagerListItemModel extends ListItemViewModel<String> {

    PagerListItemModel(@NonNull final String image) {
      super(image, R.layout.image_gallery_pager_item);
      checkNotNull(image, "image == null");
    }

    @Override
    public ListItemViewHolder<String, ListItemViewModel<String>> createViewHolder(
      final ListItemViewHolder.OnClickListener onClickListener) {
      return new PagerListItemModel.ItemViewHolder(onClickListener);
    }

    static final class ItemViewHolder extends ListItemViewHolder<String, ListItemViewModel<String>> {
      private ShopifyDraweeView imageView;

      ItemViewHolder(@NonNull final OnClickListener onClickListener) {
        super(onClickListener);
      }

      @Override
      public void bindModel(@NonNull final ListItemViewModel<String> listViewItemModel, final int position) {
        super.bindModel(listViewItemModel, position);
        imageView.loadShopifyImage(listViewItemModel.payload());
      }

      void onImageClick() {
        onClickListener().onClick(itemModel());
      }
    }
  }

  static final class PagerIndicatorListItemModel extends ListItemViewModel<String> {

    PagerIndicatorListItemModel(@NonNull final String image) {
      super(image, R.layout.image_gallery_pager_indicator_item);
      checkNotNull(image, "image == null");
    }

    @Override
    public ListItemViewHolder<String, ListItemViewModel<String>> createViewHolder(
      final ListItemViewHolder.OnClickListener onClickListener) {
      return new PagerListItemModel.ItemViewHolder(onClickListener);
    }

    static final class ItemViewHolder extends ListItemViewHolder<String, ListItemViewModel<String>> {
      private ShopifyDraweeView imageView;

      ItemViewHolder(@NonNull final OnClickListener onClickListener) {
        super(onClickListener);
      }

      @Override
      public void bindModel(@NonNull final ListItemViewModel<String> listViewItemModel, final int position) {
        super.bindModel(listViewItemModel, position);
        imageView.loadShopifyImage(listViewItemModel.payload());
      }

      void onImageClick() {
        onClickListener().onClick(itemModel());
      }
    }
  }
}
