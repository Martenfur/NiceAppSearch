package com.chaifoxes.appsearch

import android.graphics.drawable.Drawable

class AppListData
{
	var appName: String
	var appIcon: Drawable

	constructor(_appName: String, _appIcon: Drawable)
	{
		appName = _appName
		appIcon = _appIcon
	}
}