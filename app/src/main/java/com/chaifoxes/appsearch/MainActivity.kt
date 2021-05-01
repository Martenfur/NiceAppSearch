package com.chaifoxes.appsearch

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity()
{
	private lateinit var listView: ListView

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		listView = findViewById<ListView>(R.id.test_list_view)

		val pkgAppsList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

		val listItems = ArrayList<AppListData>(pkgAppsList.size)


		for (i in 0 until pkgAppsList.size)
		{
			val data = AppListData(
				packageManager.getApplicationLabel(pkgAppsList[i]).toString(),
				packageManager.getApplicationIcon(pkgAppsList[i].packageName)
			)

			listItems.add(data)
		}


		val adapter = AppListAdapter(this, listItems)
		listView.adapter = adapter
	}
}



