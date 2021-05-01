package com.chaifoxes.appsearch

import android.graphics.drawable.Drawable

class AppListData
{
	var appName: String
	var appPackageName : String
	var appIcon: Drawable

	constructor(_appName: String, _appPackageName: String, _appIcon: Drawable)
	{
		appName = _appName
		appPackageName = _appPackageName
		appIcon = _appIcon
	}
}