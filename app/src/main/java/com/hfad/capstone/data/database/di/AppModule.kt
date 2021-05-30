package com.hfad.capstone.data.database.di

import android.app.Application
import androidx.room.Room
import com.hfad.capstone.api.Api
import com.hfad.capstone.api.AuthInterceptor
import com.hfad.capstone.data.database.db.*
import com.hfad.capstone.helper.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideClient(app: Application):OkHttpClient =
        OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(AuthInterceptor(app))
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api =
        retrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun provideTransactionDatabase(app: Application) : TransactionDatabase =
        Room.databaseBuilder(app, TransactionDatabase::class.java, "transaction_database")
            .build()

    @Provides
    @Singleton
    fun provideProductDatabase(app: Application) : ProductDatabase =
        Room.databaseBuilder(app, ProductDatabase::class.java, "product_database")
            .build()

    @Provides
    @Singleton
    fun provideCompositionDatabase(app: Application) : CompositionDatabase =
        Room.databaseBuilder(app, CompositionDatabase::class.java, "composition_database")
                .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideUserDatabase(app: Application) : UserDatabase =
            Room.databaseBuilder(app, UserDatabase::class.java, "user_database")
                    .build()

    @Provides
    @Singleton
    fun provideStoreDatabase(app: Application) : StoreDatabase =
            Room.databaseBuilder(app, StoreDatabase::class.java, "store_database")
                    .build()

    @Provides
    @Singleton
    fun provideReviewDatabase(app: Application) : ReviewResponseDatabase =
        Room.databaseBuilder(app, ReviewResponseDatabase::class.java, "review_database")
            .build()

    @Provides
    @Singleton
    fun provideCompositionDetailDatabase(app: Application) : CompositionDetailDatabase =
            Room.databaseBuilder(app, CompositionDetailDatabase::class.java, "composition_detail_database")
                    .build()

}