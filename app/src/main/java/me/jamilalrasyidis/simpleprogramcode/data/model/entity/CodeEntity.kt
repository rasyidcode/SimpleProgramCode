package me.jamilalrasyidis.simpleprogramcode.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CodeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val codes: String,
    @ColumnInfo(name = "program_id")
    val programId: String
) {
    override fun toString(): String {
        return "$id \n $name \n $codes \n $programId"
    }
}