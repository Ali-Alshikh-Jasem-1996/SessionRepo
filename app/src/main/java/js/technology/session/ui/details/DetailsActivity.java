package js.technology.session.ui.details;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import dagger.android.AndroidInjection;
import js.technology.session.R;
import js.technology.session.data.model.db.Invitees;
import js.technology.session.databinding.ActivityDetailsBinding;
import js.technology.session.ui.base.BaseActivity;
import js.technology.session.utils.AppConstants;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
    }

    public ObservableList<Invitees> getInviteesObservableList() {
        return inviteesObservableList;
    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
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