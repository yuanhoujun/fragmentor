package fragmentor

import android.os.Bundle
import fragmentor.app.SupportActivity

/**
 * TestActivity
 *
 * @author Scott Smith 2018-03-23 11:18
 */
class TestActivity: SupportActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pushToFragment(fragmentCls = TheFirstFragment::class)
    }
}