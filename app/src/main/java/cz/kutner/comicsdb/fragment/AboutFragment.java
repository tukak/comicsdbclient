package cz.kutner.comicsdb.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.kutner.comicsdb.R;

public class AboutFragment extends Fragment {

    @Bind(R.id.about_first)
    TextView aboutFirst;
    @Bind(R.id.about_donate)
    TextView aboutDonate;

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance() {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, view);
        aboutFirst.setMovementMethod(LinkMovementMethod.getInstance());
        aboutDonate.setMovementMethod(LinkMovementMethod.getInstance());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void onStart() {
        super.onStart();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("O aplikaci");
    }
}
