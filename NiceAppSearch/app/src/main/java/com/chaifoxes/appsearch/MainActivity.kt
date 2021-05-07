package com.chaifoxes.appsearch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity()
{

	private lateinit var appList: AppList

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)

		setContentView(R.layout.activity_main)

		showKeyboard()

		appList = AppList(this)

		findViewById<GridLayout>(R.id.main).setOnClickListener()
		{
			finishAndRemoveTask()
		}
	}


	fun openApp(packageName: String)
	{
		val intent: Intent? = packageManager.getLaunchIntentForPackage(packageName)
		intent?.addCategory(Intent.CATEGORY_LAUNCHER)

		hideKeyboard()

		// And closing our own.
		finishAndRemoveTask()

		// Should be exactly in this order. Otherwise, some apps may not let the window open.
		applicationContext.startActivity(intent)
	}


	private fun showKeyboard()
	{
		findViewById<View>(android.R.id.content).rootView.requestFocus()
		val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
	}


	private fun hideKeyboard()
	{
		findViewById<View>(android.R.id.content).rootView.requestFocus()
		val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
	}
}