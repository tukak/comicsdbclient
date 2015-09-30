package cz.kutner.comicsdb.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.ContentViewEvent
import com.google.android.gms.analytics.HitBuilders
import cz.kutner.comicsdb.ComicsDBApplication
import cz.kutner.comicsdb.R
import kotlinx.android.synthetic.fragment_about.about_donate
import kotlinx.android.synthetic.fragment_about.about_first

public class AboutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        about_first.movementMethod = LinkMovementMethod.getInstance()
        about_donate.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar!!.title = "O aplikaci"
        val tracker = ComicsDBApplication.getTracker()
        tracker.setScreenName("AboutFragment")
        tracker.send(HitBuilders.ScreenViewBuilder().build())
        Answers.getInstance().logContentView(ContentViewEvent().putContentName("Zobrazení O aplikaci"))
    }

    companion object {

        public fun newInstance(): AboutFragment {
            val args = Bundle()
            val fragment = AboutFragment()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
