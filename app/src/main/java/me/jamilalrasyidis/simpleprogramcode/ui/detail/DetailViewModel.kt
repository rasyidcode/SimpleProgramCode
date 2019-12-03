package me.jamilalrasyidis.simpleprogramcode.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.CodeEntity
import me.jamilalrasyidis.simpleprogramcode.data.repository.CodeRepository

class DetailViewModel(
    private val codeRepository: CodeRepository
) : ViewModel() {

    fun getCodeByProgramId(programId: String) : LiveData<List<CodeEntity>> {
        return codeRepository.getCodeByProgramId(programId)
    }

    fun updateFavorite(isFavored: Boolean, codeId: String) {
        viewModelScope.launch {
            codeRepository.updateFavorite(isFavored, codeId)
        }
    }

    init {
        viewModelScope.launch {
            try {
                codeRepository.getCodes()
            } catch (e: Exception) {
                Log.d(TAG, "ERROR : $e")
            }
        }
    }

    companion object {
        const val TAG = "DetailViewModel"
    }
}