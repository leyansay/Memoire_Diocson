import android.os.Parcel
import android.os.Parcelable

data class PersonalInfo(
    val name: String,
    val nickname: String,
    val hometown: String,
    val age: String,
    val gender: String,
    val color: String,
    val food: String,
    val place: String,
    val hobbies: String,
    val sports: String,
    val dateAdded: String // Added field for date
) : Parcelable {
    // Constructor that takes a Parcel and reads the values
    constructor(parcel: Parcel) : this(
        name = parcel.readString() ?: "",
        nickname = parcel.readString() ?: "",
        hometown = parcel.readString() ?: "",
        age = parcel.readString() ?: "",
        gender = parcel.readString() ?: "",
        color = parcel.readString() ?: "",
        food = parcel.readString() ?: "",
        place = parcel.readString() ?: "",
        hobbies = parcel.readString() ?: "",
        sports = parcel.readString() ?: "",
        dateAdded = parcel.readString() ?: ""
    )

    // Write the values to a Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(nickname)
        parcel.writeString(hometown)
        parcel.writeString(age)
        parcel.writeString(gender)
        parcel.writeString(color)
        parcel.writeString(food)
        parcel.writeString(place)
        parcel.writeString(hobbies)
        parcel.writeString(sports)
        parcel.writeString(dateAdded)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PersonalInfo> {
        override fun createFromParcel(parcel: Parcel): PersonalInfo {
            return PersonalInfo(parcel)
        }

        override fun newArray(size: Int): Array<PersonalInfo?> {
            return arrayOfNulls(size)
        }
    }
}
