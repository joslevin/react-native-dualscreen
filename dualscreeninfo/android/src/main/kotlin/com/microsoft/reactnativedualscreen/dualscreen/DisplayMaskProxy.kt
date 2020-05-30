package com.microsoft.reactnativedualscreen.dualscreen

import android.app.Activity
import android.graphics.Rect
import kotlin.reflect.full.staticFunctions

class DisplayMaskProxy(currentActivity: Activity?) {
 
    private val mDisplayMask: Any?

    init {
        var displayMask: Any? = null;
        try {
            if (currentActivity != null) {
                val displayMaskClass = Class.forName("com.microsoft.device.display.DisplayMask", true, this.javaClass.classLoader)
                displayMask = displayMaskClass.kotlin.staticFunctions.find { m -> m.name == "fromResourcesRect" }!!.call(currentActivity)
            }
        } catch (e: ClassNotFoundException) {
        }
        mDisplayMask = displayMask
    }

    fun getBoundingRectsForRotation(rotation: Int): MutableList<Rect> {
        return if (mDisplayMask!= null) mDisplayMask::class.members.find { m -> m.name == "getBoundingRectsForRotation" }?.call(mDisplayMask, rotation) as MutableList<Rect>
            else mutableListOf()
    }


}