package com.example.job_seeker.presentation.extension

import android.content.Intent
import androidx.fragment.app.FragmentActivity

fun restartApp(activity: FragmentActivity) {
    val intent = activity.packageManager.getLaunchIntentForPackage(activity.packageName)
    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    activity.startActivity(intent)
    activity.finish()
}