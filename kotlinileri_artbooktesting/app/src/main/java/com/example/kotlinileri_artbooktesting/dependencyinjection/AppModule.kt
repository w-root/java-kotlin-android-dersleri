package com.example.kotlinileri_artbooktesting.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.kotlinileri_artbooktesting.R
import com.example.kotlinileri_artbooktesting.api.RetrofitAPI
import com.example.kotlinileri_artbooktesting.repository.ArtRepository
import com.example.kotlinileri_artbooktesting.service.ArtDao
import com.example.kotlinileri_artbooktesting.service.ArtDatabase
import com.example.kotlinileri_artbooktesting.util.Constans
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context:Context): ArtDatabase {
        return Room.databaseBuilder(
            context,ArtDatabase::class.java,"artdb"
        ).build()

    }
    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase): ArtDao {
        return database.ArtDao()
    }

    @Singleton
    @Provides
    fun injectRetrofitAPI() : RetrofitAPI {
        return Retrofit.Builder()
            .baseUrl(Constans.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context :Context): RequestManager {
        return Glide.with(context)
            .setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground))
    }
    @Singleton
    @Provides
    fun injectRepo(dao: ArtDao,api: RetrofitAPI): ArtRepository {
        return ArtRepository(api,dao)
    }


}