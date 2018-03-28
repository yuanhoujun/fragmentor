package fragmentor.animation

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.support.annotation.AnimatorRes

/**
 * This is a short description.
 *
 * @author Scott Smith 2018-03-25 13:19
 */
class TransitionAnimator private constructor(val enter: Animator, val exit: Animator) {

    companion object {
        fun createBy(context: Context, @AnimatorRes enter: Int, @AnimatorRes exit: Int): TransitionAnimator {
            return createBy(enter = AnimatorInflater.loadAnimator(context, enter),
                            exit = AnimatorInflater.loadAnimator(context, exit))
        }

        fun createBy(enter: Animator, exit: Animator): TransitionAnimator {
            return TransitionAnimator(enter, exit)
        }
    }
}

enum class TransitionAnimatorType {
    ENTER, EXIT, POP_ENTER, POP_EXIT
}