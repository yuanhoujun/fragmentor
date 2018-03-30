package fragmentor.extension

import android.content.Context
import android.os.Bundle
import android.view.View
import fragmentor.app.SupportFragment
import fragmentor.app.SupportFragmentDelegate

/**
 * Add an action which will be invoked when [SupportFragment.onAttach] is called.
 */
fun SupportFragment.doOnAttach(action: (context: Context?) -> Unit) = addDelegate(onAttach = action)

/**
 * Add an action which will be invoked when [SupportFragment.onCreate] is called.
 */
fun SupportFragment.doOnCreate(action: (savedInstanceState: Bundle?) -> Unit) = addDelegate(onCreate = action)

/**
 * Add an action which will be invoked when [SupportFragment.onViewCreated] is called.
 */
fun SupportFragment.doOnViewCreated(action: (view: View, savedInstanceState: Bundle?) -> Unit) = addDelegate(onViewCreated = action)

/**
 * Add an action which will be invoked when [SupportFragment.onActivityCreated] is called.
 */
fun SupportFragment.doOnActivityCreated(action: (savedInstanceState: Bundle?) -> Unit) = addDelegate(onActivityCreated = action)

/**
 * Add an action which will be invoked when [SupportFragment.onViewStateRestored] is called.
 */
fun SupportFragment.doOnViewStateRestored(action: (savedInstanceState: Bundle?) -> Unit) = addDelegate(onViewStateRestored = action)

/**
 * Add an action which will be invoked when [SupportFragment.onStart] is called.
 */
fun SupportFragment.doOnStart(action: () -> Unit) = addDelegate(onStart = action)

/**
 * Add an action which will be invoked when [SupportFragment.onResume] is called.
 */
fun SupportFragment.doOnResume(action: () -> Unit) = addDelegate(onResume = action)

/**
 * Add an action which will be invoked when [SupportFragment.onPause] is called.
 */
fun SupportFragment.doOnPause(action: () -> Unit) = addDelegate(onPause = action)

/**
 * Add an action which will be invoked when [SupportFragment.onSaveInstanceState] is called.
 */
fun SupportFragment.doOnSaveInstanceState(action: (outState: Bundle) -> Unit) = addDelegate(onSaveInstanceState = action)

/**
 * Add an action which will be invoked when [SupportFragment.onStop] is called.
 */
fun SupportFragment.doOnStop(action: () -> Unit) = addDelegate(onStop = action)

/**
 * Add an action which will be invoked when [SupportFragment.onDestroyView] is called.
 */
fun SupportFragment.doOnDestroyView(action: () -> Unit) = addDelegate(onDestroyView = action)

/**
 * Add an action which will be invoked when [SupportFragment.onDestroy] is called.
 */
fun SupportFragment.doOnDestory(action: () -> Unit) = addDelegate(onDestroy = action)

/**
 * Add an action which will be invoked when [SupportFragment.onDetach] is called.
 */
fun SupportFragment.doOnDetach(action: () -> Unit) = addDelegate(onDetach = action)

/**
 * Add a delegate to [SupportFragment], split callback interface.
 */
fun SupportFragment.addDelegate(
    onAttach: ((context: Context?) -> Unit)? = null,
    onCreate: ((savedInstanceState: Bundle?) -> Unit)? = null,
    onViewCreated: ((view: View, savedInstanceState: Bundle?) -> Unit)? = null,
    onActivityCreated: ((savedInstanceState: Bundle?) -> Unit)? = null,
    onViewStateRestored: ((savedInstanceState: Bundle?) -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onResume: (() -> Unit)? = null,
    onPause: (() -> Unit)? = null,
    onSaveInstanceState: ((outState: Bundle) -> Unit)? = null,
    onStop: (() -> Unit)? = null,
    onDestroyView: (() -> Unit)? = null,
    onDestroy: (() -> Unit)? = null,
    onDetach: (() -> Unit)? = null
): SupportFragmentDelegate {
    val delegate = object: SupportFragmentDelegate {
        override fun onAttach(context: Context?) {
            super.onAttach(context)
            onAttach?.invoke(context)
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            onCreate?.invoke(savedInstanceState)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            onViewCreated?.invoke(view, savedInstanceState)
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            onActivityCreated?.invoke(savedInstanceState)
        }

        override fun onViewStateRestored(savedInstanceState: Bundle?) {
            super.onViewStateRestored(savedInstanceState)
            onViewStateRestored?.invoke(savedInstanceState)
        }

        override fun onStart() {
            super.onStart()
            onStart?.invoke()
        }

        override fun onResume() {
            super.onResume()
            onResume?.invoke()
        }

        override fun onPause() {
            super.onPause()
            onPause?.invoke()
        }

        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            onSaveInstanceState?.invoke(outState)
        }

        override fun onStop() {
            super.onStop()
            onStop?.invoke()
        }

        override fun onDestroyView() {
            super.onDestroyView()
            onDestroyView?.invoke()
        }

        override fun onDestroy() {
            super.onDestroy()
            onDestroy?.invoke()
        }

        override fun onDetach() {
            super.onDetach()
            onDetach?.invoke()
        }
    }
    addDelegate(delegate)

    return delegate
}