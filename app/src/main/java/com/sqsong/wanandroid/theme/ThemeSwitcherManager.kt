package com.sqsong.wanandroid.theme

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.annotation.StyleRes
import com.sqsong.wanandroid.util.Constants
import com.sqsong.wanandroid.util.PreferenceHelper.get
import com.sqsong.wanandroid.util.PreferenceHelper.set
import javax.inject.Inject

class ThemeSwitcherManager {

    private var mProvider: ThemeResourceProvider

    private var mPreferences: SharedPreferences

    private var mActivityList: MutableList<Activity?>

    private val mContext: Context
    private var mThemeColorIndex: Int = 0
    private val mThemeColorList = mutableListOf<ColorPalette>()

    @Inject
    constructor(context: Context, sourceProvider: ThemeResourceProvider,
                preferences: SharedPreferences, activityList: MutableList<Activity?>) {
        this.mContext = context
        this.mActivityList = activityList
        this.mProvider = sourceProvider
        this.mPreferences = preferences

        var index = mPreferences[Constants.THEMEOVERLAY_INDEX, 0]
        if (index == null || index < 0) index = 0
        mThemeColorIndex = index
    }

    @SuppressLint("ResourceType")
    fun getThemeOverlayList(): MutableList<ColorPalette> {
        if (!mThemeColorList.isEmpty()) return mThemeColorList
        mThemeColorList.clear()
        val colorTypedArray = mContext.resources.obtainTypedArray(mProvider.getThemeColors())
        val descTypedArray = mContext.resources.obtainTypedArray(mProvider.getThemeColorDesc())
        if (colorTypedArray.length() != descTypedArray.length()) {
            throw IllegalArgumentException("Color array length not match description array length.")
        }
        for (i in 0 until colorTypedArray.length()) {
            @StyleRes val paletteOverlay = colorTypedArray.getResourceId(i, 0)
            val typedArray = mContext.obtainStyledAttributes(paletteOverlay, ThemeSwitcherDialog.THEME_OVERLAY_ATTRS)
            val primaryColor = typedArray?.getColor(0, Color.TRANSPARENT)
            val primaryDarkColor = typedArray?.getColor(1, Color.TRANSPARENT)
            val secondaryColor = typedArray?.getColor(2, Color.TRANSPARENT)

            val colorPalette = ColorPalette(paletteOverlay, primaryColor,
                    primaryDarkColor, secondaryColor, descTypedArray.getString(i), i == mThemeColorIndex)
            mThemeColorList.add(colorPalette)
            typedArray?.recycle()
        }
        return mThemeColorList
    }

    fun getThemeColorIndex(): Int {
        return mThemeColorIndex
    }

    fun setThemeOverlayRes(themeColorIndex: Int) {
        if (mThemeColorIndex == themeColorIndex) return
        mPreferences[Constants.THEMEOVERLAY_INDEX] = themeColorIndex
        for (activity in mActivityList) {
            activity?.recreate()
        }
        mThemeColorIndex = themeColorIndex
    }

    fun applyThemeOverlay(activity: Activity?) {
        val overlayList = getThemeOverlayList()
        if (mThemeColorIndex >= 0 && mThemeColorIndex < overlayList.size) {
            val palette = overlayList[mThemeColorIndex]
            activity?.setTheme(palette.themeOverlay)
        }
    }

}