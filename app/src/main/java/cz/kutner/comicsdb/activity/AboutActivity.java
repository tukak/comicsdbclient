package cz.kutner.comicsdb.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.kutner.comicsdbclient.comicsdbclient.R;

public class AboutActivity extends AppCompatActivity {

    @Bind(R.id.about_first)
    TextView aboutFirst;
    @Bind(R.id.about_donate)
    TextView aboutDonate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        aboutFirst.setMovementMethod(LinkMovementMethod.getInstance());
        aboutDonate.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
