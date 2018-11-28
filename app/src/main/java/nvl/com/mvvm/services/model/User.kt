package nvl.com.mvvm.services.model

data class User(
        var user_id: Int,
        var account_id: Int,
        var last_modified_date: Long,
        var last_access_date: Long,
        var creation_date: Long,
        var reputation_change_year: Int,
        var reputation_change_quarter: Int,
        var reputation_change_month: Int,
        var reputation_change_week: Int,
        var reputation_change_day: Int,
        var reputation: Int,
        var insert_time: Long,
        var isBookmark: Boolean,
        var accept_rate: Int,
        var user_type: String,
        var location: String,
        var website_url: String,
        var link: String,
        var profile_image: String,
        var display_name: String)