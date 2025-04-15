package com.example.netinfo.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import android.widget.RemoteViews
import com.example.netinfo.R

class NetInfoWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == "android.appwidget.action.APPWIDGET_UPDATE") {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(
                intent.component
            )
            onUpdate(context, appWidgetManager, appWidgetIds)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

private fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val views = RemoteViews(context.packageName, R.layout.netinfo_widget)
    
    // Set click listener for the entire widget
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    val pendingIntent = android.app.PendingIntent.getActivity(
        context,
        0,
        intent,
        android.app.PendingIntent.FLAG_IMMUTABLE
    )
    views.setOnClickPendingIntent(R.id.widget_root, pendingIntent)
    
    // Update widget appearance based on system theme
    val isDarkMode = context.resources.configuration.uiMode and 
        android.content.res.Configuration.UI_MODE_NIGHT_MASK == 
        android.content.res.Configuration.UI_MODE_NIGHT_YES
    
    if (isDarkMode) {
        views.setInt(R.id.widget_root, "setBackgroundResource", R.drawable.widget_background_dark)
    } else {
        views.setInt(R.id.widget_root, "setBackgroundResource", R.drawable.widget_background_light)
    }
    
    appWidgetManager.updateAppWidget(appWidgetId, views)
} 