package com.chaifoxes.appsearch

import java.io.File
import java.util.*

class AppHistory
{
	private val activity: MainActivity

	private val appHistoryFileName: String

	var appHistory: Queue<String> = LinkedList<String>()
	val maxAppHistorySize = 50

	constructor(_activity: MainActivity)
	{
		activity = _activity

		appHistoryFileName = activity.applicationContext.filesDir.path.toString() + "/apps.txt"

	}


	fun add(appPackage: String)
	{
		appHistory.add(appPackage)

		while (appHistory.size > maxAppHistorySize)
		{
			appHistory.remove()
		}
	}


	fun loadAppHistory()
	{
		val file = File(appHistoryFileName)
		if (!file.exists())
		{
			return
		}
		for(line in file.readLines())
		{
			appHistory.add(line)
		}
	}


	fun saveAppHistory()
	{
		val file = File(appHistoryFileName)

		file.writeText("")
		for(entry in appHistory)
		{
			file.appendText(entry + "\n")
		}
	}

	fun getFrequencyMap(): List<Pair<String, Int>>
	{
		val freqMap = emptyMap<String, Int>().toMutableMap()
		for(app in appHistory)
		{
			freqMap[app] = freqMap.getOrElse(app, {0}) + 1
		}

		val sortedMap = freqMap.toList().sortedByDescending { (_, value) -> value }

		return sortedMap
	}
}