package fragmentor.app

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import fragmentor.TestActivity
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is a short description.
 *
 * @author Scott Smith 2018-03-23 15:11
 */
@RunWith(AndroidJUnit4::class)
class SupportActivityTest {
    @JvmField val rule = ActivityTestRule<TestActivity>(TestActivity::class.java)

    @Test
    fun pushToFragment() {
        val intent = Intent(InstrumentationRegistry.getContext(), TestActivity::class.java)
        rule.launchActivity(intent)
    }
}