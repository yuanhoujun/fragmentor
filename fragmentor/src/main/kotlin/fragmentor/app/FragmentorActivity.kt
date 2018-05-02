package fragmentor.app

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import fragmentor.R
import fragmentor.animation.TransitionAnimator
import fragmentor.annotations.LaunchModeValue
import fragmentor.utils.FragmentorException
import kotlin.reflect.KClass

/**
 * Support activity.
 *
 * @author Scott Smith 2018-03-22 21:06
 */
open class FragmentorActivity : AppCompatActivity() {
    private var mCurrentFragmentTag: String? = null
    private val KEY_CURRENT_FRAGMENT_TAG = "android:fragmentor:currentFragmentTag"
    private val mFragmentStack = mutableListOf<SupportFragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindRootView()
        backStackChanged()
    }

    private fun bindRootView() {
        val rootView = FrameLayout(this)
        rootView.id = R.id.fragmentor_container
        rootView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        super.setContentView(rootView)
    }

    private fun backStackChanged() {
        supportFragmentManager.addOnBackStackChangedListener {
            val fragments = supportFragmentManager.fragments
            for (fragment: Fragment in fragments) {
                if (fragment.isVisible) {
                    mCurrentFragmentTag = fragment.javaClass.canonicalName
                    break
                }
            }
        }
    }

    override fun setContentView(view: View?) {
        setContentView(view, view?.layoutParams)
    }

    override fun setContentView(layoutResID: Int) {
        val layout = layoutInflater.inflate(layoutResID, findViewById(R.id.fragmentor_container), false)
        setContentView(layout)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        val lp = params ?: FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        super.setContentView(view, lp)
    }

    fun pushToFragment(fragmentCls: KClass<out SupportFragment>,
        vararg params: Pair<String, Any>,
        launchMode: LaunchModeValue = LaunchModeValue.REUSE,
        addToBackStack: Boolean = true,
        activeTransitionAnimator: TransitionAnimator? = null,
        passiveTransitionAnimator: TransitionAnimator? = null) {
        if (fragmentCls.qualifiedName == mCurrentFragmentTag) return

        try {
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.animator.fragmentor_enter_mark, R.animator.fragmentor_exit_mark,
                R.animator.fragmentor_pop_enter_mark, R.animator.fragmentor_pop_exit_mark)
            if (!TextUtils.isEmpty(mCurrentFragmentTag)) {
                val currentFragment = fragmentManager.findFragmentByTag(mCurrentFragmentTag) as? SupportFragment

                if (null != currentFragment) {
                    transaction.hide(currentFragment)
                }
            }

            var targetFragment = fragmentManager.findFragmentByTag(fragmentCls.qualifiedName) as? SupportFragment
            val needNew = launchMode === LaunchModeValue.ALWAYS_NEW
            var isInstantiate: Boolean

            if (null != targetFragment && !targetFragment.isDestoryed() && !needNew) {
                isInstantiate = false
            } else {
                targetFragment = Fragment.instantiate(this, fragmentCls.qualifiedName) as? SupportFragment
                isInstantiate = true
            }

            if (null == targetFragment) {
                throw FragmentorException("${fragmentCls.qualifiedName} instantiate fail.")
            }

            targetFragment.isAddedToBackStack = addToBackStack
            targetFragment.setActiveTransitionAnimator(activeTransitionAnimator)
            targetFragment.setPassiveTransitionAnimator(passiveTransitionAnimator)
            // Put params to target fragment
            params.forEach {
                targetFragment.putParameter(it.first, it.second)
            }
            if (addToBackStack) {
                transaction.addToBackStack(fragmentCls.qualifiedName)
            }

            if (isInstantiate) {
                transaction.add(fragmentContainerId(), targetFragment, fragmentCls.qualifiedName)
            } else {
                transaction.show(targetFragment)
            }

            transaction.commitAllowingStateLoss()

            // Just for testing
            mCurrentFragmentTag = fragmentCls.qualifiedName
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    fun popToFragment(fragmentCls: KClass<in SupportFragment>) = supportFragmentManager.popBackStackImmediate(fragmentCls.qualifiedName,
        FragmentManager.POP_BACK_STACK_INCLUSIVE)

    fun popToLastFragment(): Boolean {
        return try {
            supportFragmentManager.popBackStackImmediate()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            false
        }
    }

    fun remove(fragment: SupportFragment): Boolean {
        return try {
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.remove(fragment)
            transaction.commitAllowingStateLoss()
            true
        } catch (e: IllegalStateException) {
            false
        }
    }

    fun <T: SupportFragment> remove(fragmentCls: KClass<T>): Boolean {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(fragmentCls.qualifiedName) as? SupportFragment ?: return false
        return remove(fragment)
    }

    fun addFragmentToStack(fragment: SupportFragment) {
        if (!mFragmentStack.contains(fragment)) {
            mFragmentStack.add(0, fragment)
        }
    }

    fun removeFragmentFromStack(fragment: SupportFragment) {
        mFragmentStack.remove(fragment)
    }

    fun getLastFragment(): SupportFragment? {
        var targetFragment: SupportFragment? = null

        if (mFragmentStack.size > 1) {
            var index = 1
            while (index < mFragmentStack.size) {
                if (mFragmentStack[index].isAddedToBackStack) {
                    targetFragment = mFragmentStack[index]
                    break
                }
                index ++
            }

            if (null == targetFragment && mFragmentStack.size > 1) {
                targetFragment = mFragmentStack[1]
            }
        }

        return targetFragment
    }

    open fun fragmentContainerId() = R.id.fragmentor_container

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(mCurrentFragmentTag) as? SupportFragment
        val intercepted = fragment?.shouldInterceptBackPressed() ?: false
        if (intercepted) {
            fragment?.onBackPressed(this)
        } else {
            super.onBackPressed()
        }
    }
}
