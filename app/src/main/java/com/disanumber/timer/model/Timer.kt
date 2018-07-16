package com.disanumber.timer.model

import android.graphics.drawable.Drawable

data class Timer(val name: String, val description: String, val image: Drawable, val length: Int, var isSport: Boolean,
                 var isFood: Boolean)