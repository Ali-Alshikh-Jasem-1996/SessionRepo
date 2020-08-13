package js.technology.session.ui.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;

import com.google.gson.Gson;

import javax.inject.Inject;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import js.technology.session.R;
import js.technology.session.data.local.db.AppDatabase;
import js.technology.session.data.local.db.AppDbHelper;
import js.technology.session.rx.AppSchedulerProvider;
import js.technology.session.rx.SchedulerProvider;
import js.technology.session.ui.details.DetailsActivity;
import js.technology.session.utils.AppConstants;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    private T mViewDataBinding;
    private AppDbHelper mDbHelper;
    private CompositeDisposable mCompositeDisposable;
    private SchedulerProvider schedulerProvider;
    @Inject
    Gson mGson;
    @Inject
    AppDatabase appDatabase;

    public abstract
    @LayoutRes
    int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Inject Activity
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        //Define DbHelper to control Room Db
        mDbHelper = new AppDbHelper(appDatabase);
        //Define Composite Disposable to dispose RxJava Observe when Destroy Activity
        mCompositeDisposable = new CompositeDisposable();
        //Define SchedulerProvider For subscribe on io thread and observe on ui thread for RxJava Observe
        schedulerProvider = new AppSchedulerProvider();
        //binding the activity
        performDataBinding();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public AppDbHelper getDbHelper() {
        return mDbHelper;
    }

    public Gson getGson() {
        return mGson;
    }

    public SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mViewDataBinding.executePendingBindings();
    }

    public void showPopupMenu(View view, String contactNumber, String emailAddress) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.item_call)
                callNumber(contactNumber);
            else if (menuItem.getItemId() == R.id.item_send_sms)
                sendSMS(contactNumber);
            else sendEmail(emailAddress);
            return true;
        });
        popupMenu.show();
    }

    protected void sendSMS(String contactNumber) {
        Uri uri = Uri.parse("sms:" + contactNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", "JS Technology Group");
        startActivity(intent);
    }

    protected void callNumber(String contactNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + contactNumber));
        startActivity(intent);
    }

    protected void sendEmail(String emailAddress) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailAddress));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        intent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(intent, "send Email.."));
    }

    protected void showDetails(Long sessionId) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(AppConstants.EXTRAS_SESSION_ID, sessionId);
        startActivity(intent);
    }

}

