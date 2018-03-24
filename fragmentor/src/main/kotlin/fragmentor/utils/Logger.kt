package fragmentor.utils

import android.util.Log

/**
 * Logger
 *
 * @author Scott Smith 2018-03-23 09:51
 */
object Logger {
    private val TAG = "Fragmentor"

    fun d(msg: String) {
        Log.d(TAG, msg)
    }

    fun e(msg: String) {
        Log.e(TAG, msg)
    }
}