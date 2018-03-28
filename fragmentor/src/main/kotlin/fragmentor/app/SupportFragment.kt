package fragmentor.app

import android.animation.Animator
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.FrameLayout
import fragmentor.R
import fragmentor.animation.TransitionAnimator
import fragmentor.animation.TransitionAnimatorType
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
        return nextAnimator
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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

    fun setActiveTransitionAnimator(activeTransitionAnimator: TransitionAnimator?) {
        mActiveTransitionAnimator = activeTransitionAnimator
    }

    fun setPassiveTransitionAnimator(passiveTransitionAnimator: TransitionAnimator?) {
        mPassiveTransitionAnimator = passiveTransitionAnimator
    }

    override fun push(fragmentCls: KClass<out SupportFragment>,
        vararg params: Pair<String, Any>,
        addToBackStack: Boolean,
        activeTransitionAnimator: TransitionAnimator?,
        passiveTransitionAnimator: TransitionAnimator?) {
        val activity = requireActivity()
        (activity as? FragmentorActivity)?.pushToFragment(fragmentCls = fragmentCls,
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
}