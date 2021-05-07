package com.chaifoxes.appsearch

import android.content.pm.PackageManager
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import java.util.*


class AppList
{
	private var listView: ListView

	private var timer: Timer? = null

	private val activity: MainActivity

	constructor(_activity: MainActivity, recentAppsView: RecentAppsView)
	{
		activity = _activity

		listView = activity.findViewById(R.id.app_list_view)

		disable()

		val search = activity.findViewById<TextInputEditText>(R.id.app_search)
		initListView()

		search.doOnTextChanged(
			fun(text: CharSequence?, start: Int, count: Int, after: Int)
			{
				if (timer == null)
				{
					timer = Timer()
				}
				else
				{
					timer?.cancel()
					timer = Timer()
				}

				timer?.schedule(
					object : TimerTask()
					{
						override fun run()
						{
							activity.runOnUiThread()
							{
								(listView.adapter as AppListAdapter).filter.filter(text)
								if (text.isNullOrBlank())
								{
									recentAppsView.enable()
									disable()
								}
								else
								{
									recentAppsView.disable()
									enable()
								}
							}
						}
					},
					100
				)

			}
		)
	}

	fun enable()
	{
		listView.isEnabled = true
		listView.isVisible = true
	}


	fun disable()
	{
		listView.isEnabled = false
		listView.isVisible = false
	}


	var initialized = false

	private fun initListView()
	{
		if (initialized)
		{
			return
		}
		initialized = true
		enable()

		val appList = createAppList()
		val adapter = AppListAdapter(activity, appList)
		listView.adapter = adapter
		listView.setOnItemClickListener(
			object : AdapterView.OnItemClickListener
			{
				override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
				{
					val item = parent?.getItemAtPosition(position) as AppListData
					activity.openApp(item.app.packageName)
				}
			}
		)
	}


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