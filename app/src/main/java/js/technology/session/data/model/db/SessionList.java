package js.technology.session.data.model.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SessionList {
    @Expose
    @SerializedName("Sessions")
    public List<Session> sessions;
}
