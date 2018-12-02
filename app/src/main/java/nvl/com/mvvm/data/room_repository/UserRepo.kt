package nvl.com.mvvm.data.room_repository

import android.app.Application
import android.content.res.Resources
import nvl.com.mvvm.data.MyRoomDB
import nvl.com.mvvm.data.entities.User
import nvl.com.mvvm.data.room_dao.UserDao
import io.reactivex.Single
import nvl.com.mvvm.utils.Utils

class UserRepo(application: Application) {
    protected var userDao: UserDao

    init {
        var myRoomDataBase = MyRoomDB.getInstance(application)
        userDao = myRoomDataBase!!.getUserDao()
    }

    fun insert(user: User): Single<Boolean> {
        return Single.create {
            user.isBookmark = true
            user.insert_time = Utils.getTime()
            userDao.insert(user)
            it.onSuccess(true)
        }
    }


    fun deleteAll(): Single<Boolean> {
        return Single.create {
            userDao.deleteAll()
            it.onSuccess(true)
        }
    }

    fun deleteUser(user: User): Single<Boolean> {
        return Single.create {
            userDao.deleteUser(user.user_id)
            it.onSuccess(true)
        }
    }


    fun getAll(): Single<MutableList<User>> {
        return Single.create {
            it.onSuccess(userDao.getAll().toMutableList())
        }
    }

    fun getAllLiveData() = userDao.getAllLiveData()
}