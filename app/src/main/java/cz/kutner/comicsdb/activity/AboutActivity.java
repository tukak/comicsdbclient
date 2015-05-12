package cz.kutner.comicsdb.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

import cz.kutner.comicsdbclient.comicsdbclient.R;

public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView first = (TextView) this.findViewById(R.id.about_first);
        first.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        TextView donate = (TextView) this.findViewById(R.id.about_donate);
        donate.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
    }
}
