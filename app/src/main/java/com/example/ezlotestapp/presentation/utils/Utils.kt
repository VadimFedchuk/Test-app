package com.example.ezlotestapp.presentation.utils

import android.content.Context

fun Context.getImageResourceIdByName(name: String):Int {
    return resources.getIdentifier(
        name,
        "drawable",
        this.packageName
    )
}