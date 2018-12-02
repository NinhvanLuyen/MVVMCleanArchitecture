package nvl.com.mvvm.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user")
data class User(
        @PrimaryKey
        @ColumnInfo(name = "user_id ") var user_id: Int,
        @ColumnInfo(name = "account_id ") var account_id: Int,
        @ColumnInfo(name = "last_modified_date ") var last_modified_date: Long,
        @ColumnInfo(name = "last_access_date ") var last_access_date: Long,
        @ColumnInfo(name = "creation_date ") var creation_date: Long,
        @ColumnInfo(name = "reputation_change_year ") var reputation_change_year: Int,
        @ColumnInfo(name = "reputation_change_quarter ") var reputation_change_quarter: Int,
        @ColumnInfo(name = "reputation_change_month ") var reputation_change_month: Int,
        @ColumnInfo(name = "reputation_change_week ") var reputation_change_week: Int,
        @ColumnInfo(name = "reputation_change_day ") var reputation_change_day: Int,
        @ColumnInfo(name = "reputation ") var reputation: Int,
        @ColumnInfo(name = "insert_time ") var insert_time: Long,
        @ColumnInfo(name = "isBookmark ") var isBookmark: Boolean,
        @ColumnInfo(name = "accept_rate ") var accept_rate: Int,
        @ColumnInfo(name = "user_type ") var user_type: String,
        @ColumnInfo(name = "location ") var location: String,
        @ColumnInfo(name = "website_url ") var website_url: String,
        @ColumnInfo(name = "link ") var link: String,
        @ColumnInfo(name = "profile_image ") var profile_image: String,
        @ColumnInfo(name = "display_name ") var display_name: String)
    : Parcelable {
    constructor() : this(0,
            0,
            0L,
            0L,
            0L,
            0,
            0,
            0,
            0,
            0,
            0,
            0L,
            false,
            0,
            "",
            "",
            "",
            "",
            "",
            "")
}