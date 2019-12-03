package me.jamilalrasyidis.simpleprogramcode.di

import me.jamilalrasyidis.simpleprogramcode.data.repository.CodeRepository
import me.jamilalrasyidis.simpleprogramcode.data.repository.ProgramRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { ProgramRepository(get()) }

    single { CodeRepository(get()) }
}