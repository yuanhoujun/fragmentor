package fragmentor.annotations

/**
 * Launch mode, like launch mode of the [android.app.Activity], but more simple.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class LaunchMode(val mode: LaunchModeValue = LaunchModeValue.REUSE)

enum class LaunchModeValue {
    /**
     * It will still create a Fragment instance if the back stack already have a instance of the same fragment type.
     */
    ALWAYS_NEW,
    /**
     * It will reuse the Fragment instance if the back stack already have a instance of the same fragment type.
     */
    REUSE
}