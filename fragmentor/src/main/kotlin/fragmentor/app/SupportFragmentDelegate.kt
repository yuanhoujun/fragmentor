package fragmentor.app

import android.content.Context
import android.os.Bundle
import android.view.View

/**
 * This interface represents a delegate of [SupportFragment], which you can use to do something instead of extending [SupportFragment].
 *
 * When using this delegate class, you should call the following method instead of [SupportFragment] method of the same name:
 * * [onAttach]
 * * [onCreate]
 * * [onViewCreated]
 * * [onActivityCreated]
 * * [onViewStateRestored]
 * * [onStart]
 * * [onResume]
 * * [onPause]
 * * [onSaveInstanceState]
 * * [onStop]
 * * [onDestroyView]
 * * [onDestroy]
 * * [onDetach]
 *
 * In a [SupportFragment] instance, you can use [SupportFragment.addDelegate] method to bind this delegate interface, or
 * use [SupportFragment]'s extension method of the same name with prefix do.
 *
 * @author Scott Smith 2018-03-30 09:31
 */
interface SupportFragmentDelegate {
    /**
     * Should be called from [SupportFragment.onAttach] method.
     */
    fun onAttach(context: Context?) {}

    /**
     * Should be called from [SupportFragment.onCreate] method.
     */
    fun onCreate(savedInstanceState: Bundle?) {}

    /**
     * Should be called from [SupportFragment.onViewCreated] method.
     */
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {}

    /**
     * Should be called from [SupportFragment.onActivityCreated] method.
     */
    fun onActivityCreated(savedInstanceState: Bundle?) {}

    /**
     * Should be called from [SupportFragment.onViewStateRestored] method.
     */
    fun onViewStateRestored(savedInstanceState: Bundle?) {}

    /**
     * Should be called from [SupportFragment.onStart] method.
     */
    fun onStart() {}

    /**
     * Should be called from [SupportFragment.onResume] method.
     */
    fun onResume() {}

    /**
     * Should be called from [SupportFragment.onPause] method.
     */
    fun onPause() {}

    /**
     * Should be called from [SupportFragment.onSaveInstanceState] method.
     */
    fun onSaveInstanceState(outState: Bundle) {}

    /**
     * Should be called from [SupportFragment.onStop] method.
     */
    fun onStop() {}

    /**
     * Should be called from [SupportFragment.onDestroyView] method.
     */
    fun onDestroyView() {}

    /**
     * Should be called from [SupportFragment.onDestroy] method.
     */
    fun onDestroy() {}

    /**
     * Should be called from [SupportFragment.onDetach] method.
     */
    fun onDetach() {}
}