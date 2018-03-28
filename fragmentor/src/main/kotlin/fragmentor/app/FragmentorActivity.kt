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
import kotlin.reflect.KClass

/**
 * Support activity.
 *
 * @author Scott Smith 2018-03-22 21:06
 */
open class FragmentorActivity : AppCompatActivity() {
    private var mCurrentFragmentTag: String? = null

    private val KEY_CURRENT_FRAGMENT_TAG = "android:fragmentor:currentFragmentTag"

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
            mCurrentFragmentTag = supportFragmentManager.findFragmentById(fragmentContainerId())?.tag
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
        TODO("Implement later")
    }

    fun pushToFragment(fragmentCls: KClass<out SupportFragment>,
        vararg params: Pair<String, Any>,
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
                val currentFragment = fragmentManager.findFragmentByTag(mCurrentFragmentTag)

                if (null != currentFragment) {
                    transaction.hide(currentFragment)
                }
            }

            var targetFragment = fragmentManager.findFragmentByTag(fragmentCls.qualifiedName) as? SupportFragment
            // TODO it must add more judgement whether the fragment is active
            if (null != targetFragment) {
                transaction.show(targetFragment)
            } else {
                targetFragment = Fragment.instantiate(this, fragmentCls.qualifiedName) as? SupportFragment

                if (null != targetFragment) {
                    targetFragment.setActiveTransitionAnimator(activeTransitionAnimator)
                    targetFragment.setPassiveTransitionAnimator(passiveTransitionAnimator)
                    transaction.add(fragmentContainerId(), targetFragment, fragmentCls.qualifiedName)
                    if (addToBackStack) {
                        transaction.addToBackStack(fragmentCls.qualifiedName)
                    }
                }
            }
            transaction.commitAllowingStateLoss()

            // Just for testing
            mCurrentFragmentTag = fragmentCls.qualifiedName
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    fun popToFragment(fragmentCls: KClass<in SupportFragment>) {
    }

    fun popToLastFragment(): Boolean {
        return try {
            supportFragmentManager.popBackStackImmediate()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            false
        }
    }

    override fun getSupportFragmentManager(): FragmentManager {
        return super.getSupportFragmentManager()
    }

    open fun fragmentContainerId() = R.id.fragmentor_container
}