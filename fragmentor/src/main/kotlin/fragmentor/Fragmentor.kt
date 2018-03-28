package fragmentor

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object Fragmentor {
    private lateinit var applictionContext: Context
    var debug: Boolean = false
    private var isInitialized = false

    fun init(applictionContext: Context, debug: Boolean = false) {
        this.applictionContext = applictionContext
        this.debug = debug
        isInitialized = true
    }
}
