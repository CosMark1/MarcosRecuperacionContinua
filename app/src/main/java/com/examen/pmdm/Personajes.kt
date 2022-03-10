package com.examen.pmdm

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

@Parcelize
data class Resultados (
    val results: List<Personaje>,
): Parcelable

@Parcelize
data class Personaje (
    val name: String,
    val url: String,
    val films: List<String>
):Parcelable
