package fragmentor.app

import fragmentor.animation.TransitionAnimator
import kotlin.reflect.KClass

/**
 * Control fragment interface.
 *
 * @author Scott Smith 2018-03-22 21:24
 */
interface FragmentControl {
    fun push(fragmentCls: KClass<out SupportFragment>,
        vararg params: Pair<String, Any>,
        addToBackStack: Boolean = true,
        activeTransitionAnimator: TransitionAnimator? = null,
        passiveTransitionAnimator: TransitionAnimator? = null)

    fun push(fragment: SupportFragment, vararg params: Pair<String, Any>)

    fun pop()
    fun popToFragment(fragmentCls: KClass<in SupportFragment>)
}