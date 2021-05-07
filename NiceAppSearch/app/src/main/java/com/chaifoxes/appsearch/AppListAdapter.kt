package com.chaifoxes.appsearch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class AppListAdapter(private val context: Context, private val allItems: java.util.ArrayList<AppListData>) :
	BaseAdapter(), Filterable
{
	private lateinit var appIcon: ImageView
	private lateinit var appName: TextView

	private var filteredItems: ArrayList<AppListData> = arrayListOf()


	override fun getCount(): Int =
		filteredItems.size

	override fun getItem(position: Int): Any =
		filteredItems[position];


	override fun getItemId(position: Int): Long =
		position.toLong()


	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View?
	{
		var convertView = LayoutInflater.from(context).inflate(R.layout.app_list_item, parent, false)

		appIcon = convertView.findViewById(R.id.appIcon)
		appName = convertView.findViewById(R.id.appName)

		appIcon.setImageDrawable(filteredItems[position].getAppIcon())
		appName.text = filteredItems[position].getAppName()

		return convertView
	}


	override fun getFilter(): Filter
	{
		return object : Filter()
		{
			override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults)
			{
				if (filterResults.values !is ArrayList<*> || charSequence?.trim().isNullOrEmpty())
				{
					filteredItems = arrayListOf()
				}
				else
				{
					filteredItems = filterResults.values as ArrayList<AppListData>
				}
				notifyDataSetChanged()
			}



			override fun performFiltering(charSequence: CharSequence?): FilterResults
			{
				var queryString = charSequence?.toString()?.toLowerCase()?.trim()

				val filterResults = FilterResults()

				if (queryString==null || queryString.isEmpty())
				{
					filterResults.values = listOf<AppListData>()
					return filterResults
				}
				else
				{
					while(queryString?.isNotEmpty()!!)
					{
						val newFilteredItems = allItems.filter()
						{
							it.getAppName().toLowerCase().contains(queryString!!)
						}

						if (newFilteredItems.isNotEmpty())
						{
							filterResults.values = newFilteredItems
							return filterResults
						}
						queryString = queryString.dropLast(1)
					}
				}

				return filterResults
			}
		}
	}
}