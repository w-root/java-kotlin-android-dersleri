package com.example.kotlinileri_artbookapp.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.kotlinileri_artbookapp.R
import com.example.kotlinileri_artbookapp.repository.ArtRepository
import com.example.kotlinileri_artbookapp.service.ArtDao
import com.example.kotlinileri_artbookapp.service.ArtDatabase
import com.example.kotlinileri_artbookapp.service.RetrofitAPI
import com.example.kotlinileri_artbookapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun injectRetrofitAPI() : RetrofitAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitAPI::class.java)
    }

    @Provides
    @Singleton
    fun injectArtRepository(api:RetrofitAPI,db:ArtDao) : ArtRepository {
        return ArtRepository(api,db)
    }

    @Singleton
    @Provides
    fun injectDatabase(@ApplicationContext context:Context): ArtDatabase {
        return Room.databaseBuilder(
            context,ArtDatabase::class.java,"artdb"
        ).build()

    }
    @Provides
    @Singleton
    fun injectDao(database: ArtDatabase) : ArtDao{
        return database.ArtDao()
    }

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context :Context): RequestManager {
        return Glide.with(context)
            .setDefaultRequestOptions(
                RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground))
    }

}