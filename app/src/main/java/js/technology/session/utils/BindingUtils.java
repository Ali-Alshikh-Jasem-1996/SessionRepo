package js.technology.session.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.android.material.textview.MaterialTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import js.technology.session.data.model.db.Invitees;
import js.technology.session.data.model.db.Session;
import js.technology.session.ui.details.AdapterInvitees;
import js.technology.session.ui.session.AdapterSession;

public final class BindingUtils {

    private static final String TAG = "BindingUtils";

    private BindingUtils() {
        // This class is not publicly instantiable
    }


    //Binding Adapter For set recyclerView Invitees of Session
    @BindingAdapter("adapter_invitees")
    public static void addInvitees(RecyclerView recyclerView, List<Invitees> invitees) {
        AdapterInvitees adapter = (AdapterInvitees) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setInviteesList(invitees);
        }
    }

    //Binding Adapter For set recyclerView Sessions
    @BindingAdapter("adapter")
    public static void addSessions(RecyclerView recyclerView, List<Session> sessions) {
        AdapterSession adapter = (AdapterSession) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setSessionList(sessions);
        }
    }

    //Binding Adapter For get First Letter Of Name And Nickname
    @SuppressLint("SetTextI18n")
    @BindingAdapter("text_name_shortcut")
    public static void setTextNameShortcut(MaterialTextView textView, String textName) {
        String[] firstLetters = textName.toUpperCase().split(" ");
        if (firstLetters.length > 1)
            textView.setText(firstLetters[0].substring(0, 1) + firstLetters[1].substring(0, 1));
        else if (firstLetters.length > 0)
            textView.setText(firstLetters[0].substring(0, 1));
    }

    //Binding Adapter For get Header Date Like This : Dec 24, 2018
    @BindingAdapter("text_date")
    public static void setTextDate(MaterialTextView textView, String textDateString) {
        //read Format of Session's Activity Start Date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-HH:mm", Locale.ENGLISH);
        //format result date to wanted Format Like : Dec 24, 2018
        SimpleDateFormat formatOut = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        try {
            Date date = format.parse(textDateString);
            if (date != null)
                textView.setText(formatOut.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter("text_start_time")
    public static void setTextStartTime(MaterialTextView textView, String textStartTimeString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        //format result START date to wanted Format Like : 6:00 AM
        SimpleDateFormat formatOut = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        try {
            Date date = format.parse(textStartTimeString);
            if (date != null)
                textView.setText(formatOut.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter({"text_start_date", "text_end_date"})
    public static void setTextStartToEndTime(MaterialTextView textView, String textStartTimeString, String textEndTimeString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        //format result START - END date to wanted Format Like : 3:00 PM - 5:00 PM
        SimpleDateFormat formatOut = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
        try {
            Date startDate = format.parse(textStartTimeString);
            Date endDate = format.parse(textEndTimeString);
            if (startDate != null && endDate != null)
                textView.setText(formatOut.format(startDate) + " - " + formatOut.format(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter("text_full_date")
    public static void setFullTextDate(MaterialTextView textView, String textDateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        //format result date to wanted Format Like : Friday, 21/12/2018, 07:00 AM
        SimpleDateFormat formatOut = new SimpleDateFormat("EEEE, dd/MM/yyyy, hh:mm a", Locale.ENGLISH);
        try {
            Log.d(TAG, "setFullTextDate: " + textDateString);
            if (textDateString != null) {
                Date date = format.parse(textDateString);
                if (date != null)
                    textView.setText(formatOut.format(date));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
