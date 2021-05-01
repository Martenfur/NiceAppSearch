package com.chaifoxes.appsearch

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity()
{
	private lateinit var listView: ListView

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)


		listView = findViewById(R.id.app_list_view)

		val adapter = AppListAdapter(this, createAppList())
		listView.adapter = adapter

		val search = findViewById<TextInputEditText>(R.id.app_search)
		search.doOnTextChanged { text, start, count, after ->  (listView.adapter as AppListAdapter).filter.filter(text)}

		showKeyboard()
	}


	fun showKeyboard()
	{
		findViewById<View>(android.R.id.content).rootView.requestFocus()
		val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
	}


	fun createAppList() : ArrayList<AppListData>
	{
		val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

		val appsData = ArrayList<AppListData>(apps.size)

		for (app in apps)
		{
			if (packageManager.getLaunchIntentForPackage(app.packageName) != null)
			{
				val data = AppListData(
					packageManager.getApplicationLabel(app).toString(),
					packageManager.getApplicationIcon(app.packageName)
				)

				appsData.add(data)
			}
		}

		return appsData
	}
}



