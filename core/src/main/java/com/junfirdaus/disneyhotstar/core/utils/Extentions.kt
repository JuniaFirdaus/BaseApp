package com.junfirdaus.disneyhotstar.core.utils

import android.content.Intent

fun Intent.getData(key: String): Int {
    return extras?.getInt(key) ?: 0
}
