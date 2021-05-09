package com.karthik.blissv2alpha10.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "todo_table")
class Todo(
    @ColumnInfo(name = "todo")
    val todoTitle: String,
    @ColumnInfo(name = "priority")
    var priority: String,
    @ColumnInfo(name = "isCompleted")
    var isCompleted: Boolean
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}
