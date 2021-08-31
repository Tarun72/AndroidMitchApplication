package com.mine.mvvmmitch.di

import android.app.Application
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mine.mvvmmitch.R
import com.mine.mvvmmitch.openapi.persistance.AccountPersistenceDao
import com.mine.mvvmmitch.openapi.persistance.AppDatabase
import com.mine.mvvmmitch.openapi.persistance.AppDatabase.Companion.DATABASE_NAME
import com.mine.mvvmmitch.openapi.persistance.AuthTokenDao
import com.mine.mvvmmitch.utill.Constants
import com.mine.mvvmmitch.utill.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *  It defines a configuration point for your object graph(or application object we require),
 *  where you declare which objects you want to be available for injection and their scopes.
 *
 *
 *
 *  @Module annotated class defines a class that contributes to the dagger object graph.
 *
 *  https://codingwithmitch.com/courses/dagger22-android/component-modules-and-static-provides/
 * */

@Module
class AppModule {
    /**
     * For these cases where @Inject is insufficient or awkward,
     * use an @Provides-annotated method to satisfy a dependency.
     * The method’s return type defines which dependency it satisfies.
     *
     * */
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }

    @Singleton // single instance of Object
    @Provides
    fun provideAppDb(app: Application): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration() // get correct db version if schema changed
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthTokenDao(db: AppDatabase): AuthTokenDao {
        return db.getAuthToken()
    }

    @Singleton
    @Provides
    fun provideAccountPropertiesDao(db: AppDatabase): AccountPersistenceDao {
        return db.getAccountProperties()
    }

    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.drawable.default_image)
            .error(R.drawable.default_image)
    }

    @Singleton
    @Provides
    fun provideGlideInstance(
        application: Application,
        requestOptions: RequestOptions
    ): RequestManager {
        return Glide.with(application)
            .setDefaultRequestOptions(requestOptions)
    }

}

/**
 *
 * So the execution flow in a dagger app would be:
 * The android app starts and the MyApplication class builds the graph,
 * parsing the @Module annotated classes and keeping an instance of it.
 * Then, the classes declared in the module can access its objects just injecting themselves in the object graph.
 * Gradle then will evaluate their @Inject annotations and perform the dependency injections.
 *
 * */