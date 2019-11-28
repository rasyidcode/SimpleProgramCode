package me.jamilalrasyidis.simpleprogramcode.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.ProgramEntity
import me.jamilalrasyidis.simpleprogramcode.data.repository.ProgramRepository

class HomeViewModel(
    private val programRepository: ProgramRepository
) : ViewModel() {

    val programs: LiveData<List<ProgramEntity>> = programRepository.programs

    init {
        viewModelScope.launch {
            programRepository.getPrograms()
        }
    }
}