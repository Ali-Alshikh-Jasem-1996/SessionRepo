package js.technology.session.ui.session;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import io.reactivex.Observable;
import js.technology.session.R;
import js.technology.session.data.model.db.InviteesList;
import js.technology.session.data.model.db.Session;
import js.technology.session.data.model.db.SessionList;
import js.technology.session.databinding.ActivitySessionsBinding;
import js.technology.session.ui.base.BaseActivity;
import js.technology.session.utils.AppConstants;
import js.technology.session.utils.CommonUtils;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SessionsActivity extends BaseActivity<ActivitySessionsBinding> implements SessionClickEvent {

    private AdapterSession adapterSession;
    private ObservableList<Session> sessionsObservableList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sessions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init objects
        adapterSession = new AdapterSession();
        //this Observable List for observe list sessions to sessions recycler view
        sessionsObservableList = new ObservableArrayList<>();
        //Assign activity Binding From BaseActivity Which have defined and get it layout id
        performDataBinding();
    }

    private void performDataBinding() {
        //get binding activity view
        ActivitySessionsBinding activitySessionsBinding = getViewDataBinding();
        //set variable activity
        activitySessionsBinding.setActivity(this);
        //set variable session listener
        adapterSession.setMyListener(this);
        //set recycler view sessions adapter
        activitySessionsBinding.recyclerViewSessions.setAdapter(adapterSession);
        //Seed All Invitees From Invitees Gson assert And Stored to Room DB
        startSeedingInvitees();
        //Seed All Sessions From Session Gson assert And Stored to Room DB
        //and Observe to recycler view For View Session
        startSeedingSessions();
    }

    @Override
    public void SessionClick(int position) {
        showDetails(adapterSession.getItemByPosition(position).SessionId);
    }

    @Override
    public void IconClick(View view, int position) {
        //when click on owner icon
        showPopupMenu(view, adapterSession.getItemByPosition(position).OwnerContactNumber,
                adapterSession.getItemByPosition(position).OwnerEmail);
    }

    public ObservableList<Session> getSessionsObservableList() {
        return sessionsObservableList;
    }

    private void startSeedingSessions() {
        //seed all sessions
        getCompositeDisposable().add(seedSessions()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(aBoolean -> {
                    getAllSessions();
                    Log.d("TAG", "seedSessions: " + aBoolean);
                }, throwable -> Log.d("TAG", "seedSessions: " + throwable)));
    }

    private Observable<Boolean> seedSessions() {
        return getDbHelper().isSessionEmpty()
                .concatMap(isEmpty -> {
                    if (isEmpty) {
                        //convert Session.Json to list sessions
                        SessionList sessionList = getGson().fromJson(CommonUtils
                                .loadJSONFromAsset(this, AppConstants.SEED_SESSION), SessionList.class);
                        //save list sessions into room Db
                        return getDbHelper().saveSessionList(sessionList.sessions);
                    }
                    return Observable.just(false);
                });
    }

    private void getAllSessions() {
        //After convert Json to list sessions and store into Room Db
        //get All Sessions from Room Db
        getCompositeDisposable().add(getDbHelper().getAllSessions()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(sessions -> sessionsObservableList.addAll(sessions),
                        throwable -> Log.d("TAG", "seedSessions: " + throwable)));
    }

    private void startSeedingInvitees() {
        //seed all sessions
        getCompositeDisposable().add(seedInvitees()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(isSuccess -> {
                    //getAllInvitees();
                    Log.d("TAG", "seedInvitees: " + isSuccess);
                }, throwable -> Log.d("TAG", "seedInvitees: " + throwable)));
    }

    private Observable<Boolean> seedInvitees() {
        return getDbHelper().isInviteesEmpty()
                .concatMap(isEmpty -> {
                    if (isEmpty) {
                        //convert Invitees.Json to list invitees
                        InviteesList inviteesList = getGson().fromJson(CommonUtils
                                .loadJSONFromAsset(this, AppConstants.SEED_INVITEES), InviteesList.class);
                        //save list invitees into room Db
                        return getDbHelper().saveInviteesList(inviteesList.invitees);
                    }
                    return Observable.just(false);
                });
    }

    /*private void getAllInvitees() {
        mCompositeDisposable.add(dbHelper.getAllInvitees()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(invitees -> {
                    inviteesObservableList.addAll(invitees);
                    Log.d("TAG", "seedInvitees: " + invitees.size());
                }, throwable -> {
                    Log.d("TAG", "seedInvitees: " + throwable);
                }));
    }*/
}