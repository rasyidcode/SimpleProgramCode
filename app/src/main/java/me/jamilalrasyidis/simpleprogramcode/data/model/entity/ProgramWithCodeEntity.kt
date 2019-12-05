package me.jamilalrasyidis.simpleprogramcode.data.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ProgramWithCodeEntity(
    @Embedded val programEntity: ProgramEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "program_id"
    )
    val codeEntity: CodeEntity
)