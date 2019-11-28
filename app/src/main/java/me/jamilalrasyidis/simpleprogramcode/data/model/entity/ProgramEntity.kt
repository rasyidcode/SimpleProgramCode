package me.jamilalrasyidis.simpleprogramcode.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProgramEntity(
    @PrimaryKey val id: String,
    val title: String,
    val desc: String,
    @ColumnInfo(name = "is_favored")
    var isFavored: Boolean = false,
    @ColumnInfo(name = "last_seen")
    var lastSeen: String = ""
) {
    override fun toString(): String {
        return "$id, $title, $desc, $isFavored, $lastSeen"
    }
}