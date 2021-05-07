package com.chaifoxes.appsearch

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class AppList
{
	private var listView: ListView

	private var timer: Timer? = null

	private val activity: MainActivity

	constructor(_activity: MainActivity)
	{
		activity = _activity

		listView = activity.findViewById(R.id.app_list_view)

		listView.isEnabled = false
		listView.isClickable = false

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
							}
						}
					},
					100
				)

			}
		)
	}


	private fun initListView()
	{
		if (listView.isEnabled)
		{
			return
		}
		listView.isEnabled = true

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

		val recentAppsView = activity.findViewById<LinearLayout>(R.id.recent_apps)

		for (i in 0..3)
		{
			var b = ImageButton(activity)

			var icon = ImageView(activity)

			val p = LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT
			)
			p.weight = 1f
			p.width = 58.px
			p.height = 58.px

			icon.layoutParams = p
			icon.setImageDrawable(appList[i].getAppIcon())

			icon.setOnClickListener()
			{
				activity.openApp(appList[i].app.packageName)
			}


			recentAppsView.addView(icon)
		}

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