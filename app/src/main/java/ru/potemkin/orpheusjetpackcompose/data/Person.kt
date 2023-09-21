package ru.potemkin.orpheusjetpackcompose.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import ru.potemkin.orpheusjetpackcompose.R
import kotlinx.parcelize.Parcelize
@Parcelize
data class Person(
    val id: Int = 0,
    val name: String = "",
    @DrawableRes val icon: Int = R.drawable.ic_launcher_foreground
) : Parcelable

val personList = listOf(
    Person(
        1,
        "Patty",
        R.drawable.sample1
    ),
    Person(
        2,
        "Arina",
        R.drawable.sample2
    ),
    Person(
        3,
        "Masha",
        R.drawable.sample3
    ),
    Person(
        4,
        "Lera",
        R.drawable.sample4
    ),
    Person(
        5,
        "Wilya",
        R.drawable.sample4
    ),
    Person(
        6,
        "Mom",
        R.drawable.sample2
    ),
    Person(
        7,
        "Mommy",
        R.drawable.sample1
    ),
    Person(
        8,
        "Lean",
        R.drawable.sample1
    ),
    Person(
        9,
        "Larry",
        R.drawable.sample4
    ),
    Person(
        10,
        "Ariana",
        R.drawable.sample2
    ),
)