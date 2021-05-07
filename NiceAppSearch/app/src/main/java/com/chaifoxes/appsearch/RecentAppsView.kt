package com.chaifoxes.appsearch

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isVisible
import java.util.*

class RecentAppsView
{
	private var recentAppsView: LinearLayout
	private val activity: MainActivity

	private val recentAppsCount = 4

	private val appIconSize = 55.dpToPx

	val appHistory: AppHistory

	constructor(_activity: MainActivity)
	{
		activity = _activity

		recentAppsView = activity.findViewById(R.id.recent_apps)

		appHistory = AppHistory(activity)

		appHistory.loadAppHistory()
		populateView()
	}


	fun enable()
	{
		recentAppsView.isEnabled = true
		recentAppsView.isVisible = true
	}


	fun disable()
	{
		recentAppsView.isEnabled = false
		recentAppsView.isVisible = false
	}


	fun populateView()
	{
		val appList = createAppList()

		for (app in appList)
		{
			var icon = ImageView(activity)

			val p = LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT
			)
			p.weight = 1f
			p.width = appIconSize
			p.height = appIconSize

			icon.layoutParams = p
			icon.setImageDrawable(app.getAppIcon())

			icon.setOnClickListener()
			{
				activity.openApp(app.app.packageName)
			}

			recentAppsView.addView(icon)
		}

	}


	// TODO: Remove.
	private fun createAppList(): ArrayList<AppListData>
	{
		val appsData = ArrayList<AppListData>()

		for (appPackage in appHistory.getFrequencyMap(recentAppsCount))
		{
			val app = activity.packageManager.getApplicationInfo(appPackage.first, 0)

			if (activity.packageManager.getLaunchIntentForPackage(app.packageName) != null)
			{
				val data = AppListData(
					app,
					activity.packageManager
				)

				appsData.add(data)
			}
		}

		return appsData
	}

}
