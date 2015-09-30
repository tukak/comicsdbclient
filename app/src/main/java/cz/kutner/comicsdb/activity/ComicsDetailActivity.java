package cz.kutner.comicsdb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;
import cz.kutner.comicsdb.R;
import cz.kutner.comicsdb.fragment.ComicsDetailFragment;

public class ComicsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics_detail);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Integer id = null;
        if (Intent.ACTION_VIEW.equals(intent.getAction())) { //volá nás někdo přes URL
            try {
                id = Integer.parseInt(intent.getDataString().replaceFirst("^.*\\D", ""));
            } catch (Exception e) {
            }
        } else {
            id = intent.getIntExtra(MainActivity.COMICS_ID, 0);
        }
        Fragment fragment = ComicsDetailFragment.Companion.newInstance();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
