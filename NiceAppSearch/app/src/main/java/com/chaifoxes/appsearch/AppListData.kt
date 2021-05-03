package com.chaifoxes.appsearch

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable

class AppListData
{
	private var appName: String? = null
	private var appIcon: Drawable? = null
	val app: ApplicationInfo
	private val packageManager: PackageManager

	constructor(_app: ApplicationInfo, _pkg: PackageManager)
	{
		app = _app
		packageManager = _pkg
	}

	fun getAppName() : String
	{
		if (appName == null)
		{
			appName = packageManager.getApplicationLabel(app).toString()
		}
		return appName!!
	}

	public fun getAppIcon() : Drawable
	{
		if (appIcon == null)
		{
			appIcon = packageManager.getApplicationIcon(app.packageName)
		}
		return appIcon!!
	}
}