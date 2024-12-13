import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class PersonalInfo(
    val name: String,
    val nickname: String,
    val hometown: String,
    val age: String,
    val gender: String,
    val color: String,
    val food: String,
    val comfort: String,
    val hobbies: String,
    val sports: String,
    val dateAdded: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(nickname)
        parcel.writeString(hometown)
        parcel.writeString(age)
        parcel.writeString(gender)
        parcel.writeString(color)
        parcel.writeString(food)
        parcel.writeString(comfort)
        parcel.writeString(hobbies)
        parcel.writeString(sports)
        parcel.writeString(dateAdded)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<PersonalInfo> {
        override fun createFromParcel(parcel: Parcel): PersonalInfo {
            return PersonalInfo(parcel)
        }

        override fun newArray(size: Int): Array<PersonalInfo?> {
            return arrayOfNulls(size)
        }
    }
}
