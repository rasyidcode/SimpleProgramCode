package me.jamilalrasyidis.simpleprogramcode.di

import me.jamilalrasyidis.simpleprogramcode.ui.detail.DetailViewModel
import me.jamilalrasyidis.simpleprogramcode.ui.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { HomeViewModel(get()) }

    viewModel { DetailViewModel(get()) }

}