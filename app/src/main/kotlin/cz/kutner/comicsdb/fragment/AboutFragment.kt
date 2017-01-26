package cz.kutner.comicsdb.fragment


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.danielstone.materialaboutlibrary.ConvenienceBuilder
import com.danielstone.materialaboutlibrary.MaterialAboutFragment
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic
import cz.kutner.comicsdb.R
import cz.kutner.comicsdb.utils.Utils


class AboutFragment : MaterialAboutFragment() {

    override fun getMaterialAboutList(context: Context?): MaterialAboutList {

        val appCardBuilder = MaterialAboutCard.Builder()
        appCardBuilder.addItem(ConvenienceBuilder.createAppTitleItem(context))

        appCardBuilder.addItem(ConvenienceBuilder.createVersionActionItem(context,
                IconicsDrawable(context).icon(MaterialDesignIconic.Icon.gmi_info_outline).sizeDp(18),
                "Verze",
                false))
        appCardBuilder.addItem(ConvenienceBuilder.createWebsiteActionItem(context,
                IconicsDrawable(this.context).icon(MaterialDesignIconic.Icon.gmi_time_restore).sizeDp(18),
                "Historie změn",
                false,
                Uri.parse("https://github.com/tukak/comicsdbclient/releases")))

        val authorCardBuilder = MaterialAboutCard.Builder()
        authorCardBuilder.title("Autor")

        authorCardBuilder.addItem(MaterialAboutActionItem.Builder()
                .text("Lukáš Kutner")
                .subText("tukak")
                .icon(IconicsDrawable(this.context).icon(MaterialDesignIconic.Icon.gmi_account).sizeDp(18))
                .setOnClickListener({
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse("http://comicsdb.cz/user.php?id=5953")
                    startActivity(i)
                })
                .build())

        authorCardBuilder.addItem(ConvenienceBuilder.createEmailItem(context,
                IconicsDrawable(this.context).icon(MaterialDesignIconic.Icon.gmi_email).sizeDp(18),
                "Napište své nápady nebo hlašte chyby",
                true,
                "lukas@kutner.cz",
                "Komentář k aplikaci ComicsDB")
        )

        authorCardBuilder.addItem(ConvenienceBuilder.createWebsiteActionItem(context,
                IconicsDrawable(this.context).icon(MaterialDesignIconic.Icon.gmi_twitter).sizeDp(18),
                "@tukak",
                true,
                Uri.parse("https://twitter.com/tukak")
                ))

        authorCardBuilder.addItem(ConvenienceBuilder.createWebsiteActionItem(context,
                IconicsDrawable(this.context).icon(MaterialDesignIconic.Icon.gmi_code).sizeDp(18),
                "Zdrojový kód",
                true,
                Uri.parse("https://github.com/tukak/comicsdbclient")
        ))


        val aboutCardBuilder = MaterialAboutCard.Builder()
        aboutCardBuilder.title(R.string.about)

        aboutCardBuilder.addItem((MaterialAboutActionItem.Builder()
                .text(R.string.about_first)
                .build())
        )
        aboutCardBuilder.addItem((MaterialAboutActionItem.Builder()
                .text(R.string.about_free)
                .build())
        )

        return MaterialAboutList(appCardBuilder.build(), aboutCardBuilder.build(), authorCardBuilder.build())
    }


    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.title = "O aplikaci"
        Utils.logVisit(contentName = "Zobrazení O aplikaci")
    }

    companion object {

        fun newInstance(): AboutFragment {
            val args = Bundle()
            val fragment = AboutFragment()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
