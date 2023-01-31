package com.stas1270.githubapi.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QueryEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "queryRequest") val name: String
) {
    constructor(name: String) : this(0, name)
}
