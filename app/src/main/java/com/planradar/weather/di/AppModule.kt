package com.planradar.weather.di
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.planradar.weather.data.api.ApiConstants
import com.planradar.weather.data.api.WeatherApi
import com.planradar.weather.data.cache.Cache
import com.planradar.weather.data.cache.WeatherDatabase
import com.planradar.weather.data.cache.daos.WeatherDao
import com.planradar.weather.domain.repository.WeatherRepository
import com.planradar.weather.domain.repository.WeatherRepositoryImpl
import com.planradar.weather.utils.GeneralConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, WeatherDatabase::class.java, GeneralConstants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .setJournalMode(RoomDatabase.JournalMode.AUTOMATIC)
            .build()

    @Singleton
    @Provides
    fun provideRunDao(db: WeatherDatabase): WeatherDao = db.weatherDao()

    @Provides
    @Singleton
    fun provideRetrofitClient(): WeatherApi {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
//            .addInterceptor(OkHttpProfilerInterceptor())
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(newRequest)
            }
            /*.addNetworkInterceptor(CacheInterceptor())
            .addInterceptor(ForceCacheInterceptor(context))*/
            .build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(ApiConstants.BASE_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}