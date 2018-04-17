package com.youngfeng.fragmentorsample.fragments

import android.animation.Animator
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.youngfeng.fragmentorsample.R
import fragmentor.animation.TransitionAnimatorType
import fragmentor.app.SupportFragment
import kotlinx.android.synthetic.main.fragment_the_second.*

/**
 * This is a short description.
 *
 * @author Scott Smith 2018-03-26 20:51
 */
class TheSecondFragment: SupportFragment() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        printLog("onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        printLog("onCreate")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        printLog("onCreateView")
        return inflater.inflate(R.layout.fragment_the_second, container, false)
    }

    override fun onCreateAnimator(transit: Int, nextAnimatorType: TransitionAnimatorType?, nextAnimator: Animator?): Animator? {

        return super.onCreateAnimator(transit, nextAnimatorType, nextAnimator)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        printLog("onActivityCreated")

        btn_to_first_fragment.setOnClickListener {
            push(TheFirstFragment::class)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        printLog("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()

        printLog("onDestory")
    }

    override fun onDetach() {
        super.onDetach()

        printLog("onDetach")
    }

    private fun printLog(msg: String) {
        Log.d(javaClass.canonicalName, msg)
    }
}