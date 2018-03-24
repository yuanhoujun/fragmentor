package fragmentor.app

import android.support.v4.app.Fragment
import android.util.Pair
import kotlin.reflect.KClass

/**
 * Support fragment.
 *
 * @author Scott Smith 2018-03-22 21:04
 */
open class SupportFragment: Fragment(), FragmentControl {

    override fun push(fragmentCls: KClass<in SupportFragment>, vararg params: Pair<String, Any>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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