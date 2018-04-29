package fragmentor.app

import android.animation.Animator
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.FrameLayout
import fragmentor.R
import fragmentor.animation.TransitionAnimator
import fragmentor.animation.TransitionAnimatorStatus
import fragmentor.animation.TransitionAnimatorType
import fragmentor.annotations.LaunchModeValue
import fragmentor.widget.FragmentorRootLayout
import kotlin.reflect.KClass

/**
 * Support fragment.
 *
 * @author Scott Smith 2018-03-22 21:04
 */
open class SupportFragment : Fragment(), FragmentControl {
    private var mActiveTransitionAnimator: TransitionAnimator? = null
    private var mPassiveTransitionAnimator: TransitionAnimator? = null
    private var mTransitionAnimatorStatus = TransitionAnimatorStatus()
    private var mParameters = HashMap<String, Any>()
    private var mDelegates = ArrayList<SupportFragmentDelegate>()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mDelegates.forEach { it.onAttach(context) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDelegates.forEach { it.onCreate(savedInstanceState) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDelegates.forEach { it.onViewCreated(view, savedInstanceState) }
    }

    final override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return null
    }

    final override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        var nextAnimator: Animator? = null
        var nextAnimatorType: TransitionAnimatorType? = null

        when (nextAnim) {
            R.animator.fragmentor_enter_mark -> {
                nextAnimator = mActiveTransitionAnimator?.enter
                nextAnimatorType = TransitionAnimatorType.ENTER
            }
            R.animator.fragmentor_exit_mark -> {
                nextAnimator = mActiveTransitionAnimator?.exit
                nextAnimatorType = TransitionAnimatorType.EXIT
            }
            R.animator.fragmentor_pop_enter_mark -> {
                nextAnimator = mPassiveTransitionAnimator?.enter
                nextAnimatorType = TransitionAnimatorType.POP_ENTER
            }
            R.animator.fragmentor_pop_exit_mark -> {
                nextAnimator = mPassiveTransitionAnimator?.exit
                nextAnimatorType = TransitionAnimatorType.POP_EXIT
            }
        }

        return onCreateAnimator(transit, nextAnimatorType, nextAnimator)
    }

    open fun onCreateAnimator(transit: Int, nextAnimatorType: TransitionAnimatorType?, nextAnimator: Animator?): Animator? {
        var nextAnimatorFinal = nextAnimator
        when (nextAnimatorType) {
            TransitionAnimatorType.ENTER -> {
                if (mTransitionAnimatorStatus.enterCanceled) {
                    nextAnimatorFinal = null
                }
            }
            TransitionAnimatorType.EXIT -> {
                if (mTransitionAnimatorStatus.exitCanceled) {
                    nextAnimatorFinal = null
                }
            }
            TransitionAnimatorType.POP_ENTER -> {
                if (mTransitionAnimatorStatus.popEnterCanceled) {
                    nextAnimatorFinal = null
                }
            }
            TransitionAnimatorType.POP_EXIT -> {
                if (mTransitionAnimatorStatus.popExitCanceled) {
                    nextAnimatorFinal = null
                }
            }
        }
        return nextAnimatorFinal
    }

    fun setActiveTransitionAnimator(activeTransitionAnimator: TransitionAnimator?) {
        mActiveTransitionAnimator = activeTransitionAnimator
    }

    fun setPassiveTransitionAnimator(passiveTransitionAnimator: TransitionAnimator?) {
        mPassiveTransitionAnimator = passiveTransitionAnimator
    }

    fun cancelTransitionAnimator(vararg types: TransitionAnimatorType) {
        types.forEach {
            when (it) {
                TransitionAnimatorType.ENTER -> mTransitionAnimatorStatus.enterCanceled = true
                TransitionAnimatorType.EXIT -> mTransitionAnimatorStatus.exitCanceled = true
                TransitionAnimatorType.POP_ENTER -> mTransitionAnimatorStatus.popEnterCanceled = true
                TransitionAnimatorType.POP_EXIT -> mTransitionAnimatorStatus.popExitCanceled = true
            }
        }
    }

    fun resumeTransitionAnimator(vararg types: TransitionAnimatorType) {
        types.forEach {
            when (it) {
                TransitionAnimatorType.ENTER -> mTransitionAnimatorStatus.enterCanceled = false
                TransitionAnimatorType.EXIT -> mTransitionAnimatorStatus.exitCanceled = false
                TransitionAnimatorType.POP_ENTER -> mTransitionAnimatorStatus.popEnterCanceled = false
                TransitionAnimatorType.POP_EXIT -> mTransitionAnimatorStatus.popExitCanceled = false
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mDelegates.forEach { it.onActivityCreated(savedInstanceState) }

        val rootView = FragmentorRootLayout(activity!!)
        rootView.id = R.id.fragmentor_root_view
        rootView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        replaceRootViewWith(rootView)
    }

    private fun replaceContentViewWith(newContentView: ViewGroup) {
        val rootView = view
        if (rootView is ViewGroup) {
            if (rootView.childCount > 0) {
                val realView = rootView.getChildAt(0)
                rootView.removeView(realView)
                newContentView.addView(realView)
                rootView.addView(newContentView)
            }
        }
    }

    private fun replaceRootViewWith(newRootView: ViewGroup) {
        val oldRootView = view
        if (null == oldRootView || oldRootView.parent !is ViewGroup) return

        try {
            val parent = oldRootView.parent as ViewGroup
            parent.removeView(oldRootView)
            newRootView.addView(oldRootView)
            parent.addView(newRootView)

            val fieldView = Fragment::class.java.getDeclaredField("mView")
            fieldView.isAccessible = true
            fieldView.set(this, newRootView)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        mDelegates.forEach { it.onViewStateRestored(savedInstanceState) }
    }

    /**
     * Bind a delegate instance of [SupportFragmentDelegate] to [SupportFragment].
     *
     * @param delegate The custom [SupportFragmentDelegate] instance.
     *
     * @see [SupportFragmentDelegate]
     */
    fun addDelegate(delegate: SupportFragmentDelegate) {
        mDelegates.add(delegate)
    }

    fun putParameter(key: String, value: Any) {
        mParameters[key] = value
    }

    fun <T> parameter(key: String): T? {
        return mParameters[key] as? T
    }

    override fun push(fragmentCls: KClass<out SupportFragment>,
        vararg params: Pair<String, Any>,
        launchMode: LaunchModeValue,
        addToBackStack: Boolean,
        activeTransitionAnimator: TransitionAnimator?,
        passiveTransitionAnimator: TransitionAnimator?) {
        val activity = requireActivity()
        (activity as? FragmentorActivity)?.pushToFragment(fragmentCls = fragmentCls,
            params = *params,
            launchMode = launchMode,
            addToBackStack = addToBackStack,
            activeTransitionAnimator = activeTransitionAnimator,
            passiveTransitionAnimator = passiveTransitionAnimator)
    }

    override fun push(fragment: SupportFragment, vararg params: Pair<String, Any>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun popToFragment(fragmentCls: KClass<in SupportFragment>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(fragment: SupportFragment) {
        val activity = requireActivity()
        (activity as? FragmentorActivity)?.remove(fragment)
    }

    override fun <T : SupportFragment> remove(fragmentCls: KClass<T>) {
        val activity = requireActivity()
        (activity as? FragmentorActivity)?.remove(fragmentCls)
    }

    fun isDestoryed(): Boolean {
        return isDetached || isRemoving || null == view || null == view?.parent || null == activity
    }

    override fun onStart() {
        super.onStart()
        mDelegates.forEach { it.onStart() }
    }


    override fun onResume() {
        super.onResume()
        mDelegates.forEach { it.onResume() }
    }

    override fun onPause() {
        super.onPause()
        mDelegates.forEach { it.onPause() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mDelegates.forEach { it.onSaveInstanceState(outState) }
    }

    override fun onStop() {
        super.onStop()
        mDelegates.forEach { it.onStop() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mDelegates.forEach { it.onDestroyView() }
    }

    override fun onDestroy() {
        super.onDestroy()
        mDelegates.forEach { it.onDestroy() }
    }

    override fun onDetach() {
        super.onDetach()
        mDelegates.forEach { it.onDetach() }
    }
}