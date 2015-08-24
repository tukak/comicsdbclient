package cz.kutner.comicsdb.holder;

import android.view.View;
import android.widget.TextView;

import cz.kutner.comicsdb.R;
import cz.kutner.comicsdb.model.Author;
import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

@LayoutId(R.layout.list_item_authors)
public class AuthorViewHolder extends ItemViewHolder<Author> {
    @ViewId(R.id.authorName)
    TextView authorName;
    @ViewId(R.id.authorCountry)
    TextView authorCountry;

    public AuthorViewHolder(View view) {
        super(view);
    }

    @Override
    public void onSetValues(Author author, PositionInfo positionInfo) {
        authorName.setText(author.getName());
        authorCountry.setText(author.getCountry());
    }
}
