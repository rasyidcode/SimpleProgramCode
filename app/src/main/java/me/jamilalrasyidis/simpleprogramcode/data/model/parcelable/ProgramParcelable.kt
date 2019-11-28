package me.jamilalrasyidis.simpleprogramcode.data.model.parcelable

import android.os.Parcel
import android.os.Parcelable

data class ProgramParcelable(
    val id: String?,
    val title: String?,
    val languages: String?,
    val codes: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(languages)
        parcel.writeString(codes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProgramParcelable> {
        override fun createFromParcel(parcel: Parcel): ProgramParcelable {
            return ProgramParcelable(parcel)
        }

        override fun newArray(size: Int): Array<ProgramParcelable?> {
            return arrayOfNulls(size)
        }
    }

}

data class Code(
    val name: String,
    val code: String
)