package fragmentor.app

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import fragmentor.R
import fragmentor.TestActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is a short description.
 *
 * @author Scott Smith 2018-03-23 15:11
 */
@RunWith(AndroidJUnit4::class)
class FragmentorActivityTest {
    @Rule
    @JvmField val rule = ActivityTestRule<TestActivity>(TestActivity::class.java)

    @Test
    fun pushToFragment() {
        val intent = Intent(InstrumentationRegistry.getContext(), TestActivity::class.java)
        rule.launchActivity(intent)
    }

    @Test
    fun pushToSecondFragment() {
        onView(allOf(withId(R.id.btn_to_second_fragment), withText("Go to second Fragment")))
            .perform(click())
        Thread.sleep(3000)
    }
}