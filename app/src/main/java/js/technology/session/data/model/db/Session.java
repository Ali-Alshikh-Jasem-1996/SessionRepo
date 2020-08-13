package js.technology.session.data.model.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName = "session")
public class Session {

    @Expose
    @SerializedName("SessionId")
    @PrimaryKey
    public Long SessionId;

    @Expose
    @SerializedName("ActivityStartDate")
    @ColumnInfo(name = "ActivityStartDate")
    public String ActivityStartDate;

    @Expose
    @SerializedName("ActivityEndDate")
    @ColumnInfo(name = "ActivityEndDate")
    public String ActivityEndDate;

    @Expose
    @SerializedName("Subject")
    @ColumnInfo(name = "Subject")
    public String Subject;

    @Expose
    @SerializedName("ActivityType")
    @ColumnInfo(name = "ActivityType")
    public String ActivityType;

    @Expose
    @SerializedName("Location")
    @ColumnInfo(name = "Location")
    public String Location;

    @Expose
    @SerializedName("Owner")
    @ColumnInfo(name = "Owner")
    public String Owner;

    @Expose
    @SerializedName("OwnerEmail")
    @ColumnInfo(name = "OwnerEmail")
    public String OwnerEmail;

    @Expose
    @SerializedName("OwnerContactNumber")
    @ColumnInfo(name = "OwnerContactNumber")
    public String OwnerContactNumber;

    @Expose
    @SerializedName("PrimaryContactName")
    @ColumnInfo(name = "PrimaryContactName")
    public String PrimaryContactName;

    @Expose
    @SerializedName("LeadName")
    @ColumnInfo(name = "LeadName")
    public String LeadName;

    @Expose
    @SerializedName("AccountName")
    @ColumnInfo(name = "AccountName")
    public String AccountName;

    @Expose
    @SerializedName("OpportunityName")
    @ColumnInfo(name = "OpportunityName")
    public String OpportunityName;

    @Expose
    @SerializedName("Description")
    @ColumnInfo(name = "Description")
    public String Description;

    @Expose
    @SerializedName("invitees")
    @TypeConverters(DataInviteesConverter.class)
    @ColumnInfo(name = "invitees")
    public List<Invitees> invitees;

}
