package me.jamilalrasyidis.simpleprogramcode.di

import androidx.room.Room
import me.jamilalrasyidis.simpleprogramcode.data.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<AppDatabase>().programDao
    }

    single {
        get<AppDatabase>().codeDao
    }

}