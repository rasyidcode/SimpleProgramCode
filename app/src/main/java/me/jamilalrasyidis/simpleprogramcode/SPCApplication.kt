package me.jamilalrasyidis.simpleprogramcode

import android.app.Application
import me.jamilalrasyidis.simpleprogramcode.di.databaseModule
import me.jamilalrasyidis.simpleprogramcode.di.repositoryModule
import me.jamilalrasyidis.simpleprogramcode.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SPCApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.DEBUG)

            androidContext(this@SPCApplication)

            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    databaseModule
                )
            )
        }
    }
}