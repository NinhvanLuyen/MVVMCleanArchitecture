package nvl.com.mvvm.data.local.room_dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import nvl.com.mvvm.data.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity: User)

    @Query("DELETE FROM user")
    fun deleteAll()

    @Query("DELETE FROM user where `user_id ` = :user_id ")
    fun deleteUser(user_id: Int)

    @Query("SELECT * from user  ORDER BY `insert_time ` ASC")
    fun getAll(): List<User>

    @Query("SELECT * from user ORDER BY `insert_time ` ASC")
    fun getAllLiveData(): LiveData<List<User>>
}