package fragmentor.animation

/**
 * This is a short description.
 *
 * @author Scott Smith 2018-03-28 16:15
 */
data class TransitionAnimatorStatus(
    var enterCanceled: Boolean = false,
    var exitCanceled: Boolean = false,
    var popEnterCanceled: Boolean = false,
    var popExitCanceled: Boolean = false
)