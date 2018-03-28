package fragmentor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fragmentor.app.SupportFragment

/**
 * This is a short description.
 *
 * @author Scott Smith 2018-03-23 12:06
 */
class TheSecondFragment: SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_the_second, container, false)
    }
}