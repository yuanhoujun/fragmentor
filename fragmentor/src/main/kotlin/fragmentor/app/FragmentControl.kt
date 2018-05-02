package fragmentor.app

import fragmentor.animation.TransitionAnimator
import fragmentor.annotations.LaunchModeValue
import kotlin.reflect.KClass

/**
 * Control fragment interface.
 *
 * @author Scott Smith 2018-03-22 21:24
 */
interface FragmentControl {
    fun push(fragmentCls: KClass<out SupportFragment>,
        vararg params: Pair<String, Any>,
        launchMode: LaunchModeValue = LaunchModeValue.REUSE,
        addToBackStack: Boolean = true,
        activeTransitionAnimator: TransitionAnimator? = null,
        passiveTransitionAnimator: TransitionAnimator? = null)

    fun push(fragment: SupportFragment, vararg params: Pair<String, Any>)

    fun pop()
    fun popToFragment(fragmentCls: KClass<in SupportFragment>)

    fun remove(fragment: SupportFragment)

    fun <T: SupportFragment> remove(fragmentCls: KClass<T>)

    /**
     * Intercept back operation, [onBackPressed] will be called if this method return true.
     *
     * @see [onBackPressed]
     */
    fun shouldInterceptBackPressed() = false

    /**
     * It will be called when back pressed if you intercept back event.
     *
     * @param activity the host activity.
     *
     * @see [shouldInterceptBackPressed]
     */
    fun onBackPressed(activity: FragmentorActivity) = activity.onBackPressed()

    /**
     * Set result and pop to last fragment, the method annotated by [fragmentor.annotations.ForResult] will be invoked.
     * It is a better implementation like [android.app.Activity.startActivityForResult] in SupportFragment.
     *
     * @param data corresponds to the parameters of the callback method
     * @param popToLast true: pop to the last fragment, false: just callback
     */
    fun setResultAndComplete(vararg data: Any, popToLast: Boolean = true)
}