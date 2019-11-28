package me.jamilalrasyidis.simpleprogramcode.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import me.jamilalrasyidis.simpleprogramcode.data.model.entity.ProgramEntity
import me.jamilalrasyidis.simpleprogramcode.data.services.dao.ProgramDao
import me.jamilalrasyidis.simpleprogramcode.data.services.firebase.ProgramRef
import org.koin.core.KoinComponent

class ProgramRepository(
    private val programDao: ProgramDao
) : KoinComponent {

    private val database by lazy { FirebaseFirestore.getInstance() }

    private val programRef by lazy { database.collection("programs") }

    val programs by lazy { programDao.programs }

    suspend fun getPrograms() {
        val programList = mutableListOf<ProgramEntity>()

        programRef.get().addOnSuccessListener {
            for (document in it) {
                Log.d(TAG, "id: ${document.id}")
                programList.add(
                    ProgramEntity(
                        id = document.id,
                        title = document["title"].toString(),
                        desc = document["desc"].toString()
                    )
                )
            }

            runBlocking {
                programDao.insert(programList)
            }
        }
    }

    companion object {
        const val TAG = "ProgramRepository"
    }
}