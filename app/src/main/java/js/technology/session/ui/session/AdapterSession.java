package js.technology.session.ui.session;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import js.technology.session.R;
import js.technology.session.data.model.db.Session;
import js.technology.session.databinding.ItemSessionBinding;
import js.technology.session.utils.AppConstants;

public class AdapterSession extends RecyclerView.Adapter<AdapterSession.SessionViewHolder> {

    private List<Session> sessionList = new ArrayList<>();
    private List<Integer> sessionListOngoing;
    private boolean isThereAnySessionsOngoing;
    private SessionClickEvent sessionClickEvent;

    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSessionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_session, parent, false);
        return new AdapterSession.SessionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    Session getItemByPosition(int position) {
        return sessionList.get(position);
    }

    public void setSessionList(List<Session> sessions) {
        sessionList = sessions;
        sessionListOngoing = new ArrayList<>();
        int ongoingSession;
        for (Session session : sessionList) {
            ongoingSession = isOngoing(session.ActivityStartDate, session.ActivityEndDate);
            sessionListOngoing.add(ongoingSession);
            isThereAnySessionsOngoing |= ongoingSession == AppConstants.DATE_ONGOING;
        }
        notifyDataSetChanged();
    }

    class SessionViewHolder extends RecyclerView.ViewHolder {

        private ItemSessionBinding mItemSessionBinding;

        SessionViewHolder(ItemSessionBinding binding) {
            super(binding.getRoot());
            this.mItemSessionBinding = binding;
        }

        public void onBind(int position) {
            final Session session = sessionList.get(position);
            mItemSessionBinding.setSession(session);
            mItemSessionBinding.setOngoing(sessionListOngoing.get(position));
            mItemSessionBinding.setIsThereAnySessionsOngoing(isThereAnySessionsOngoing);
            mItemSessionBinding.setClickItem(sessionClickEvent);
            mItemSessionBinding.setPosition(position);
            mItemSessionBinding.executePendingBindings();
        }
    }

    public void setMyListener(SessionClickEvent listener) {
        this.sessionClickEvent = listener;
    }

    private int isOngoing(String startTimeString, String endTimeString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        try {
            Date startDate = format.parse(startTimeString);
            Date endDate = format.parse(endTimeString);
            Date currentDate = Calendar.getInstance().getTime();
            Log.d("TAG", "Ali setTextIsOngoingTime: " + (currentDate.after(startDate) && currentDate.before(endDate)));
            if (currentDate.after(startDate) && currentDate.before(endDate))
                return AppConstants.DATE_ONGOING;
            else if (currentDate.before(startDate))
                return AppConstants.DATE_UPCOMING;
            else return AppConstants.DATE_PAST;
        } catch (ParseException e) {
            e.printStackTrace();
            return AppConstants.DATE_PAST;
        }
    }
}
