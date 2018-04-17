package com.youngfeng.fragmentorsample.fragments

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.youngfeng.fragmentorsample.R
import fragmentor.animation.TransitionAnimatorType
import fragmentor.app.SupportFragment
import fragmentor.extension.doOnStart

/**
 * This is a short description.
 *
 * @author Scott Smith 2018-03-26 20:51
 */
class TheFirstFragment: SupportFragment() {
    private var in1: TheFirstFragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_the_first, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<Button>(R.id.btn_to_second_fragment)?.setOnClickListener {
            push(fragmentCls = TheSecondFragment::class)
        }
    }

    override fun onCreateAnimator(transit: Int, nextAnimatorType: TransitionAnimatorType?, nextAnimator: Animator?): Animator? {
        Log.e("onCreateAnimator", "Transit = $transit, NextAnimatorType = $nextAnimatorType, NextAnimator = $nextAnimator")
        return super.onCreateAnimator(transit, nextAnimatorType, nextAnimator)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

    }
}