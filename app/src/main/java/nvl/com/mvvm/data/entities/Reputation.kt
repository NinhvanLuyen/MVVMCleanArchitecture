package nvl.com.mvvm.data.entities

data class Reputation(
        var reputation_history_type: String,
        var reputation_change: Int,
        var post_id: Int,
        var user_id: Int,
        var creation_date: Long)