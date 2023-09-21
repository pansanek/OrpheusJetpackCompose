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
        "Pranav",
        R.drawable.ic_launcher_foreground
    ),
    Person(
        2,
        "Ayesha",
        R.drawable.ic_launcher_foreground
    ),
    Person(
        3,
        "Roshini",
        R.drawable.ic_launcher_foreground
    ),
    Person(
        4,
        "Kaushik",
        R.drawable.ic_launcher_foreground
    ),
    Person(
        5,
        "Dad",
        R.drawable.ic_launcher_foreground
    ),
    Person(
        6,
        "Pranav",
        R.drawable.ic_launcher_foreground
    ),
    Person(
        7,
        "Ayesha",
        R.drawable.ic_launcher_foreground
    ),
    Person(
        8,
        "Roshini",
        R.drawable.ic_launcher_foreground
    ),
    Person(
        9,
        "Kaushik",
        R.drawable.ic_launcher_foreground
    ),
    Person(
        10,
        "Dad",
        R.drawable.ic_launcher_foreground
    ),
)