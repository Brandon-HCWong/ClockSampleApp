package com.brandonhc.clocksampleapp.data.di

import android.content.Context
import com.brandonhc.clocksampleapp.data.api.TimeApiService
import com.brandonhc.clocksampleapp.data.repository.TimesRepository
import com.brandonhc.clocksampleapp.data.room.AppDatabase
import com.brandonhc.clocksampleapp.ui.viewmodel.EditViewModel
import com.brandonhc.clocksampleapp.ui.viewmodel.SettingViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

object KoinModules {
    fun initKoin(applicationContext: Context) {
        startKoin {
            androidContext(applicationContext)
            modules(listOf(
                viewModelModule,
                apiServiceModule,
                repositoryModule,
                databaseModule
            ))
        }
    }

    /**
     *  ViewModel component.
     */
    private val viewModelModule = module {
        viewModel { SettingViewModel(get()) }
        viewModel { EditViewModel(get()) }
    }

    /**
     *  For retrofit service
     */
    private val apiServiceModule = module {
        single { TimeApiService.create() }
    }

    private val repositoryModule = module {
        single {
            TimesRepository(
                get(),
                get<AppDatabase>().ianaTimeZoneIdDao(),
                get<AppDatabase>().ianaTimeZoneInfoDao(),
                get<AppDatabase>().userTimeZoneInfoDao()
            )
        }
    }

    private val databaseModule = module {
        single { AppDatabase.build(androidContext()) }
    }
}