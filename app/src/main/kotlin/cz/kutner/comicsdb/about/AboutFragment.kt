package cz.kutner.comicsdb.about


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.danielstone.materialaboutlibrary.ConvenienceBuilder
import com.danielstone.materialaboutlibrary.MaterialAboutFragment
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.sizeDp
import com.mikepenz.iconics.typeface.library.materialdesigniconic.MaterialDesignIconic
import cz.kutner.comicsdb.R

class AboutFragment : MaterialAboutFragment() {

    override fun getMaterialAboutList(context: Context): MaterialAboutList {
        val appCardBuilder = MaterialAboutCard.Builder()
        appCardBuilder.addItem(ConvenienceBuilder.createAppTitleItem(context))

        appCardBuilder.addItem(
            ConvenienceBuilder.createVersionActionItem(
                context,
                IconicsDrawable(context).icon(MaterialDesignIconic.Icon.gmi_info_outline).sizeDp(18),
                "Verze",
                false
            )
        )
        appCardBuilder.addItem(
            ConvenienceBuilder.createWebsiteActionItem(
                context,
                IconicsDrawable(context).icon(MaterialDesignIconic.Icon.gmi_time_restore).sizeDp(
                    18
                ),
                "Historie změn",
                false,
                Uri.parse("https://github.com/tukak/comicsdbclient/releases")
            )
        )

        val authorCardBuilder = MaterialAboutCard.Builder()
        authorCardBuilder.title("Autor")

        authorCardBuilder.addItem(
            MaterialAboutActionItem.Builder()
                .text("Lukáš Kutner")
                .subText("tukak")
                .icon(
                    IconicsDrawable(context).icon(MaterialDesignIconic.Icon.gmi_account).sizeDp(
                        18
                    )
                )
                .setOnClickAction {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse("http://comicsdb.cz/user.php?id=5953")
                    startActivity(i)
                }
                .build()
        )

        authorCardBuilder.addItem(
            ConvenienceBuilder.createEmailItem(
                context,
                IconicsDrawable(context).icon(MaterialDesignIconic.Icon.gmi_email).sizeDp(18),
                "Hlašte chyby nebo pište nápady",
                true,
                "lukas@kutner.cz",
                "Komentář k aplikaci ComicsDB"
            )
        )

        authorCardBuilder.addItem(
            ConvenienceBuilder.createWebsiteActionItem(
                context,
                IconicsDrawable(context).icon(MaterialDesignIconic.Icon.gmi_twitter).sizeDp(18),
                "@tukak",
                false,
                Uri.parse("https://twitter.com/tukak")
            )
        )

        authorCardBuilder.addItem(
            ConvenienceBuilder.createWebsiteActionItem(
                context,
                IconicsDrawable(context).icon(MaterialDesignIconic.Icon.gmi_github).sizeDp(18),
                "Zdrojový kód",
                true,
                Uri.parse("https://github.com/tukak/comicsdbclient")
            )
        )


        val aboutCardBuilder = MaterialAboutCard.Builder()
        aboutCardBuilder.title(R.string.about)

        aboutCardBuilder.addItem(
            (MaterialAboutActionItem.Builder()
                .text(R.string.about_first)
                .showIcon(false)
                .setOnClickAction(
                    ConvenienceBuilder.createWebsiteOnClickAction(
                        context,
                        Uri.parse("http://www.comicsdb.cz")
                    )
                )
                .build())
        )
        aboutCardBuilder.addItem(
            (MaterialAboutActionItem.Builder()
                .text(R.string.about_free)
                .icon(
                    IconicsDrawable(context).icon(MaterialDesignIconic.Icon.gmi_money_off).sizeDp(
                        18
                    )
                )
                .build())
        )

        aboutCardBuilder.addItem(
            (MaterialAboutActionItem.Builder()
                .text(R.string.about_donate)
                .icon(
                    IconicsDrawable(context).icon(MaterialDesignIconic.Icon.gmi_money_box).sizeDp(
                        18
                    )
                )
                .setOnClickAction(
                    ConvenienceBuilder.createWebsiteOnClickAction(
                        context,
                        Uri.parse("http://comicsdb.cz/donate.php")
                    )
                )
                .build())
        )

        return MaterialAboutList(
            appCardBuilder.build(),
            aboutCardBuilder.build(),
            authorCardBuilder.build()
        )
    }


    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.title = "O aplikaci"
    }

    companion object {

        fun newInstance(): AboutFragment {
            val args = Bundle()
            val fragment = AboutFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
