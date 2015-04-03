package comicsdb.kutner.cz;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import cz.kutner.comicsdbclient.comicsdbclient.R;

public class ComicsDetailActivity extends ActionBarActivity {
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.COMICS_URL);
        Fragment fragment = new ComicsDetailFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }
}
