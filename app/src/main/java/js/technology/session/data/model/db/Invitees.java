package js.technology.session.data.model.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "invitees")
public class Invitees {
    @Expose
    @SerializedName("id")
    @PrimaryKey
    public int id;

    @Expose
    @SerializedName("name")
    @ColumnInfo(name = "name")
    public String name;

    @Expose
    @SerializedName("email")
    @ColumnInfo(name = "email")
    public String email;

    @Expose
    @SerializedName("phoneNumber")
    @ColumnInfo(name = "phoneNumber")
    public String phoneNumber;

    @Expose
    @SerializedName("jobTitle")
    @ColumnInfo(name = "jobTitle")
    public String jobTitle;
}
