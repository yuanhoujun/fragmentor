package com.youngfeng.fragmentorsample.main

import android.os.Bundle
import android.util.Log
import com.youngfeng.fragmentorsample.fragments.TheFirstFragment
import com.youngfeng.fragmentorsample.ui.BaseActivity

/**
 * This is a short description.
 *
 * @author Scott Smith 2018-03-26 20:50
 */
class MainActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pushToFragment(fragmentCls = TheFirstFragment::class, addToBackStack = false)
    }

    override fun onStart() {
        super.onStart()
        Log.e(">>>>>", "MainActivity onStart@@@@@@@@")
    }
}