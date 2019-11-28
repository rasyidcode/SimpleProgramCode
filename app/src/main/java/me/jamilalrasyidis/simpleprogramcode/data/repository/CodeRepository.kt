package me.jamilalrasyidis.simpleprogramcode.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.CodeEntity
import me.jamilalrasyidis.simpleprogramcode.data.services.dao.CodeDao
import org.koin.core.KoinComponent

class CodeRepository(
    private val codeDao: CodeDao
) : KoinComponent {

    private val database by lazy { FirebaseFirestore.getInstance() }

    private val codeRefs by lazy { database.collection("codes") }

    fun getNames(programId: String) : LiveData<List<String>> {
        return codeDao.getNames(programId)
    }

    fun getCodes(programId: String, name: String) : LiveData<String> {
        return codeDao.getCodes(programId, name)
    }

    fun getCodeByProgramId(programId: String) : LiveData<List<CodeEntity>> {
        return codeDao.getCodeByProgramId(programId)
    }

    suspend fun getCodes() {
        val codes = mutableListOf<CodeEntity>()

        codeRefs.get().addOnSuccessListener {
            for (document in it) {
                codes.add(
                    CodeEntity(
                        id = document.id,
                        name = document["name"].toString(),
                        codes = document["codes"].toString(),
                        programId = document["program_id"].toString()
                    )
                )
            }

            runBlocking {
                codeDao.insert(codes)
            }
        }
    }

    companion object {
        const val TAG = "CodeRepository"
    }

}