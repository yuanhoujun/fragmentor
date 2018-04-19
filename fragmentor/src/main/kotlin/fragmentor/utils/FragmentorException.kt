package fragmentor.utils

import android.util.AndroidRuntimeException

/**
 * All exception of Fragmentor.
 *
 * @author Scott Smith 2018-04-19 10:09
 */
class FragmentorException constructor(msg: String?): AndroidRuntimeException(msg) {
}