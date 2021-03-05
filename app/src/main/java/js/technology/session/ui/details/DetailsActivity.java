package js.technology.session.ui.details;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import js.technology.session.R;
import js.technology.session.data.model.db.Invitees;
import js.technology.session.databinding.ActivityDetailsBinding;
import js.technology.session.ui.base.BaseActivity;
import js.technology.session.utils.AppConstants;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends BaseActivity<ActivityDetailsBinding> implements InviteeClickEvent {

    private AdapterInvitees adapterInvitees;
    private ObservableList<Invitees> inviteesObservableList;
    private Long sessionId;
    private ActivityDetailsBinding activityDetailsBinding;

    @Override
    public int getLayoutId() {
        return R.layout.activity_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Init Objects
        adapterInvitees = new AdapterInvitees();
        inviteesObservableList = new ObservableArrayList<>();
        //get Intent Extra For Session Id
        sessionId = getIntent().getLongExtra(AppConstants.EXTRAS_SESSION_ID, -1);
        //Assign activity Binding From BaseActivity Which have defined and get it layout id
        performDataBinding();

        List<String> images = new ArrayList<>();
        images.add("https://cdn.shopify.com/s/files/1/0362/4920/6921/files/2_5465497702663980914_e312a09c-b482-4e9a-8053-051fe023fef5.jpg");
        images.add("https://cdn.shopify.com/s/files/1/0362/4920/6921/files/2_5465497702663980915_ea2fb087-a2a3-410c-a5f1-01e793b9b061.jpg?v=1591183464");
        images.add("https://cdn.shopify.com/s/files/1/0362/4920/6921/files/Lebensmittel_1_1080x.jpeg?v=1596297420");
    }

    public ObservableList<Invitees> getInviteesObservableList() {
        return inviteesObservableList;
    }

    private void performDataBinding() {
        //get binding activity view
        activityDetailsBinding = getViewDataBinding();
        //set variable activity
        activityDetailsBinding.setActivity(this);
        //set variable session listener
        adapterInvitees.setMyListener(this);
        activityDetailsBinding.recyclerViewInvitees.setAdapter(adapterInvitees);
        //Seed Invitees For The Same Session, by Session Id
        startSeedingInvitees();
    }

    private void startSeedingInvitees() {
        //Load Session by it's Id From Room Db and observe to activity session and it's invitees
        getCompositeDisposable().add(getDbHelper().loadSessionById(sessionId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(session -> {
                    activityDetailsBinding.setSession(session);
                    inviteesObservableList.addAll(session.invitees);
                    Log.d("TAG", "seedInvitees: " + session.invitees.size());
                }, throwable -> Log.d("TAG", "seedInvitees: " + throwable)));
    }

    @Override
    public void InviteeClick(View view, int position) {
        //call method From Base Activity
        showPopupMenu(view, adapterInvitees.getItemByPosition(position).phoneNumber
                , adapterInvitees.getItemByPosition(position).email);
    }
}