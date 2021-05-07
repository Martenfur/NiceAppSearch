package com.chaifoxes.appsearch

import android.content.pm.PackageManager
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isVisible
import java.util.ArrayList

class RecentAppsView
{
	private var recentAppsView: LinearLayout
	private val activity: MainActivity

	private val recentAppsCount = 4

	private val appIconSize = 58.dpToPx

	constructor(_activity: MainActivity)
	{
		activity = _activity

		recentAppsView = activity.findViewById(R.id.recent_apps)

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

		for (i in 0 until recentAppsCount)
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
			icon.setImageDrawable(appList[i].getAppIcon())

			icon.setOnClickListener()
			{
				activity.openApp(appList[i].app.packageName)
			}

			recentAppsView.addView(icon)
		}

	}


	// TODO: Remove.
	private fun createAppList(): ArrayList<AppListData>
	{
		val apps = activity.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

		val appsData = ArrayList<AppListData>(apps.size)

		for (app in apps)
		{
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