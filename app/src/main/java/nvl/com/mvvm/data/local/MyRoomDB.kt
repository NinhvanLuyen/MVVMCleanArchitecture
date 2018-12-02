package nvl.com.mvvm.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import nvl.com.mvvm.data.entities.User
import nvl.com.mvvm.data.local.room_dao.UserDao

@Database(entities = [(User::class)], version = 1)
abstract class MyRoomDB : RoomDatabase() {
    abstract fun getUserDao(): UserDao

    companion object {
        private var INSTANCE: MyRoomDB? = null
        fun getInstance(context: Context): MyRoomDB? {
            if (INSTANCE == null) {
                synchronized(MyRoomDB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            MyRoomDB::class.java, "fossil_test.db")
//                            .addMigrations(FROM_1_TO_2)
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }


    }
}