package com.chaifoxes.appsearch

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity()
{
	private lateinit var listView: ListView

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)


		listView = findViewById(R.id.app_list_view)

		listView.isEnabled = false


		val search = findViewById<TextInputEditText>(R.id.app_search)
		search.doOnTextChanged(fun(text: CharSequence?, start: Int, count: Int, after: Int)
		{
			initListView()
			(listView.adapter as AppListAdapter).filter.filter(text)
		})

		showKeyboard()
	}


	private fun initListView()
	{
		if (listView.isEnabled)
		{
			return
		}
		listView.isEnabled = true

		val adapter = AppListAdapter(this, createAppList())
		listView.adapter = adapter
		listView.setOnItemClickListener(object : AdapterView.OnItemClickListener
		{
			override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
			{
				val item = parent?.getItemAtPosition(position) as AppListData

				// TODO: move somewhere else.
				// Opening selected app.
				val intent: Intent? = packageManager.getLaunchIntentForPackage(item.appPackageName)
				intent?.addCategory(Intent.CATEGORY_LAUNCHER)

				applicationContext.startActivity(intent)

				// And closing our own.
				finishAndRemoveTask()
			}
		})

	}

	private fun showKeyboard()
	{
		findViewById<View>(android.R.id.content).rootView.requestFocus()
		val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
	}


	private fun createAppList(): ArrayList<AppListData>
	{
		val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

		val appsData = ArrayList<AppListData>(apps.size)

		for (app in apps)
		{
			if (packageManager.getLaunchIntentForPackage(app.packageName) != null)
			{
				val data = AppListData(
					packageManager.getApplicationLabel(app).toString(),
					app.packageName,
					packageManager.getApplicationIcon(app.packageName)
				)

				appsData.add(data)
			}
		}

		return appsData
	}
}



