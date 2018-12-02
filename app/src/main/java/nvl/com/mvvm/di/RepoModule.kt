package nvl.com.mvvm.di

import nvl.com.mvvm.MyApplication
import nvl.com.mvvm.data.local.room_repository.UserRepo
import nvl.com.mvvm.data.remote.api.ApiService
import nvl.com.mvvm.data.remote.api.ApiServiceImp
import nvl.com.mvvm.data.remote.api.HttpRepository
import nvl.com.mvvm.domain.UserUseCase
import nvl.com.mvvm.utils.Configs
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val repoModule = module {
    single { createOkHttpClient() }
    single { createWebService<HttpRepository>(get(), Configs.SERVER_URL) }
    single { createApiService(get()) }
    single { createUserRepo() }
    single { createUsercase(get(), get()) }

}
fun createApiService(httpRepository: HttpRepository): ApiService = ApiServiceImp(httpRepository)
fun createUserRepo(): UserRepo = UserRepo(MyApplication.app)

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor).build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}

fun createUsercase(apiService: ApiService, userRepo: UserRepo) = UserUseCase(apiService, userRepo)
