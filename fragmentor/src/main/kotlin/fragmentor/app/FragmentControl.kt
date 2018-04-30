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
     * @see [shouldInterceptBackPressed]
     */
    fun onBackPressed(activity: FragmentorActivity) = activity.onBackPressed()
}