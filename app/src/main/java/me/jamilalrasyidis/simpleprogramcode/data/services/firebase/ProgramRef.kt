package me.jamilalrasyidis.simpleprogramcode.data.services.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class ProgramRef {

    suspend fun getPrograms() : QuerySnapshot? = FirebaseFirestore.getInstance().collection("programs").get().result
}