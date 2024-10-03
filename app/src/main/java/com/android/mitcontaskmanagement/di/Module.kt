package com.android.mitcontaskmanagement.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

import com.android.mitcontaskmanagement.data.local.dataSource.DataStorePreferencesDataSource
import com.android.mitcontaskmanagement.data.local.dataSource.PreferencesDataSource
import com.android.mitcontaskmanagement.data.local.dataSource.SharedPreferencesDataSource
import com.android.mitcontaskmanagement.data.local.room.TaskDAO
import com.android.mitcontaskmanagement.data.local.room.MitconDatabase
import com.android.mitcontaskmanagement.data.models.mappper.TaskMapper
import com.android.mitcontaskmanagement.data.models.mappper.UserMapper
import com.android.mitcontaskmanagement.data.remote.harperDb.Api
import com.android.mitcontaskmanagement.data.remote.harperDb.AuthInterceptor
import com.android.mitcontaskmanagement.util.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Module {

    @Provides
    @Singleton
    fun providesFirebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun providesSharedPref(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("TaskifySharedPref", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore

    @Provides
    @Singleton
    fun providesFirebaseStorage() = Firebase.storage.reference

    @Provides
    @Singleton
    @Named("sharedPref")
    fun providesSharedPreferencesDataSource(sharedPreferences: SharedPreferences): PreferencesDataSource =
        SharedPreferencesDataSource(sharedPreferences)

    @Provides
    @Singleton
    @Named("prefDataStore")
    fun providesDataStorePreferencesDataSource(dataStore: DataStore<Preferences>): PreferencesDataSource =
        DataStorePreferencesDataSource(dataStore)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor() = AuthInterceptor()

    @Provides
    @Singleton
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
    //    authInterceptor: AuthInterceptor
    ): Call.Factory {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .callTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        callFactory: Call.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://todo.riteshdayama.in/api/")
        .callFactory(callFactory)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun providesUserMapper(): UserMapper = UserMapper()

    @Provides
    @Singleton
    fun providesTaskMapper(): TaskMapper = TaskMapper()

    @Provides
    @Singleton
    fun providesTaskifyDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, MitconDatabase::class.java, "TaskifyDatabase"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providesTaskDao(taskifyDatabase: MitconDatabase): TaskDAO = taskifyDatabase.getTaskDao()

    @Provides
    fun providesContext(@ApplicationContext context: Context): Context = context
}
