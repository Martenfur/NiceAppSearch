package com.chaifoxes.appsearch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AppListAdapter(private val context: Context, private val arrayList: java.util.ArrayList<AppListData>) :
	BaseAdapter()
{
	private lateinit var appIcon: ImageView
	private lateinit var appName: TextView

	override fun getCount(): Int =
		arrayList.size


	override fun getItem(position: Int): Any =
		position


	override fun getItemId(position: Int): Long =
		position.toLong()


	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View?
	{
		var convertView = LayoutInflater.from(context).inflate(R.layout.app_list_item, parent, false)

		appIcon = convertView.findViewById(R.id.appIcon)
		appName = convertView.findViewById(R.id.appName)

		appIcon.setImageDrawable(arrayList[position].appIcon)
		appName.text = arrayList[position].appName

		return convertView
	}
}