package fragmentor.app

import android.util.Pair
import kotlin.reflect.KClass

/**
 * Control fragment interface.
 *
 * @author Scott Smith 2018-03-22 21:24
 */
interface FragmentControl {
    fun push(fragmentCls: KClass<in SupportFragment>, vararg params: Pair<String, Any>)
    fun push(fragment: SupportFragment, vararg params: Pair<String, Any>)

    fun pop()
    fun popToFragment(fragmentCls: KClass<in SupportFragment>)
}