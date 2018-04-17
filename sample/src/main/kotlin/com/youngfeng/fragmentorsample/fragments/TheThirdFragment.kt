package com.youngfeng.fragmentorsample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.youngfeng.fragmentorsample.R
import fragmentor.app.SupportFragment

/**
 * This is a short description.
 *
 * @author Scott Smith 2018-03-26 20:51
 */
class TheThirdFragment: SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_the_third, container, false)
    }
}