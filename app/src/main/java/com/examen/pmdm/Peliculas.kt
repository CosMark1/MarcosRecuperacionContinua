package com.examen.pmdm

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName


@Parcelize
data class ResultadosPeliculas (
    val results: List<Peliculas>,
): Parcelable

@Parcelize
data class Peliculas (
    val title: String,
    val characters: List<String>,
    val url: String
) : Parcelable
