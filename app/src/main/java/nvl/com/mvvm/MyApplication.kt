package nvl.com.mvvm

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import nvl.com.mvvm.di.appModule
import nvl.com.mvvm.di.repoModule
import nvl.com.mvvm.utils.Configs
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class MyApplication : Application() {
    companion object {
        lateinit var app: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        initializeTimber()
        Fresco.initialize(this)
        startKoin(this, listOf(appModule, repoModule))
    }
    private fun initializeTimber() {
        if (Configs.IS_DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}