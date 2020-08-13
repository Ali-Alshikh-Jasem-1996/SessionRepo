package js.technology.session.data.model.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class DataInviteesConverter implements Serializable {

    @TypeConverter
    public String fromInviteesList(List<Invitees> inviteesList) {
        if (inviteesList == null)
            return null;
        Gson gson = new Gson();
        Type type = new TypeToken<List<Invitees>>() {
        }.getType();
        return gson.toJson(inviteesList, type);
    }

    @TypeConverter
    public List<Invitees> toInviteesList(String inviteesListString) {
        if (inviteesListString == null)
            return null;
        Gson gson = new Gson();
        Type type = new TypeToken<List<Invitees>>() {
        }.getType();
        return gson.fromJson(inviteesListString, type);
    }

}
