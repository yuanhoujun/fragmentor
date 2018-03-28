package fragmentor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import fragmentor.app.SupportFragment

/**
 * This is a short description.
 *
 * @author Scott Smith 2018-03-23 12:06
 */
class TheFirstFragment: SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_the_first, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view?.findViewById<Button>(R.id.btn_to_second_fragment)?.setOnClickListener {
            push(fragmentCls = TheSecondFragment::class)
        }
    }
}