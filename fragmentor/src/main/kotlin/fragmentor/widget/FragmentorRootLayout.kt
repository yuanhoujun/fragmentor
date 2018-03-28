package fragmentor.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.view.ViewTreeObserver

/**
 * This is a short description.
 *
 * @author Scott Smith 2018-03-27 11:57
 */
class FragmentorRootLayout(context: Context, attrs: AttributeSet?): FrameLayout(context, attrs) {
    private var fractionX = 0f
    private var mPreDrawListener: ViewTreeObserver.OnPreDrawListener? = null

    constructor(context: Context): this(context, null)

    fun setFractionX(fractionX: Float) {
        this.fractionX = fractionX

        if (null == mPreDrawListener) {
            mPreDrawListener = ViewTreeObserver.OnPreDrawListener {
                viewTreeObserver.removeOnPreDrawListener(mPreDrawListener)
                setTranslateX(fractionX)
                true
            }
            viewTreeObserver.addOnPreDrawListener(mPreDrawListener)
        }

        setTranslateX(fractionX)
    }

    private fun setTranslateX(fractionX: Float) {
        val width = width
        if (width <= 0f) return

        translationX = width * fractionX
    }

    fun getFractionX(): Float {
        return fractionX
    }
}