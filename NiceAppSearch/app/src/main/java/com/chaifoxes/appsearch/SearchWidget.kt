package com.chaifoxes.appsearch

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class SearchWidget : AppWidgetProvider()
{
	override fun onUpdate(
		context: Context,
		appWidgetManager: AppWidgetManager,
		appWidgetIds: IntArray
	)
	{
		// There may be multiple widgets active, so update all of them
		for (appWidgetId in appWidgetIds)
		{
			updateAppWidget(context, appWidgetManager, appWidgetId)
		}
	}

	override fun onEnabled(context: Context)
	{
		// Enter relevant functionality for when the first widget is created
	}

	override fun onDisabled(context: Context)
	{
		// Enter relevant functionality for when the last widget is disabled
	}
}

internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int)
{
	// Construct the RemoteViews object
	val views = RemoteViews(context.packageName, R.layout.search_widget)

	val intent = Intent(context, MainActivity::class.java)

	val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

	views.setOnClickPendingIntent(R.id.main_widget_layout, pendingIntent)

	// Instruct the widget manager to update the widget
	appWidgetManager.updateAppWidget(appWidgetId, views)
}