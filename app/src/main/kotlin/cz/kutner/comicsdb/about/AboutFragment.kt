package cz.kutner.comicsdb.about


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.net.toUri
import androidx.appcompat.app.AppCompatActivity
import com.danielstone.materialaboutlibrary.ConvenienceBuilder
import com.danielstone.materialaboutlibrary.MaterialAboutFragment
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.materialdesigniconic.MaterialDesignIconic
import com.mikepenz.iconics.utils.sizeDp
import cz.kutner.comicsdb.R

class AboutFragment : MaterialAboutFragment() {

    override fun getMaterialAboutList(context: Context): MaterialAboutList {
        val appCardBuilder = MaterialAboutCard.Builder()
        appCardBuilder.addItem(ConvenienceBuilder.createAppTitleItem(context))

        appCardBuilder.addItem(
                ConvenienceBuilder.createVersionActionItem(
                        context,
                        IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_info_outline).apply {
                            sizeDp = 18
                        }, "Verze",
                        false
                )
        )
        appCardBuilder.addItem(
                ConvenienceBuilder.createWebsiteActionItem(
                        context,
                        IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_time_restore).apply {
                            sizeDp = 18
                        },
                        "Historie změn",
                        false,
                        "https://github.com/tukak/comicsdbclient/releases".toUri()
                )
        )

        val authorCardBuilder = MaterialAboutCard.Builder()
        authorCardBuilder.title("Autor")

        authorCardBuilder.addItem(
                MaterialAboutActionItem.Builder()
                        .text("Lukáš Kutner")
                        .subText("tukak")
                        .icon(
                                IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_account).apply {
                                    sizeDp = 18
                                }
                        )
                        .setOnClickAction {
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = "http://comicsdb.cz/user.php?id=5953".toUri()
                            startActivity(i)
                        }
                        .build()
        )

        authorCardBuilder.addItem(
                ConvenienceBuilder.createEmailItem(
                        context,
                        IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_email).apply {
                            sizeDp = 18
                        },
                        "Hlašte chyby nebo pište nápady",
                        true,
                        "lukas@kutner.cz",
                        "Komentář k aplikaci ComicsDB"
                )
        )

        authorCardBuilder.addItem(
                ConvenienceBuilder.createWebsiteActionItem(
                        context,
                        IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_twitter).apply {
                            sizeDp = 18
                        },
                        "@tukak",
                        false,
                        "https://twitter.com/tukak".toUri()
                )
        )

        authorCardBuilder.addItem(
                ConvenienceBuilder.createWebsiteActionItem(
                        context,
                        IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_github).apply {
                            sizeDp = 18
                        },
                        "Zdrojový kód",
                        true,
                        "https://github.com/tukak/comicsdbclient".toUri()
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
                                        "http://www.comicsdb.cz".toUri()
                                )
                        )
                        .build())
        )
        aboutCardBuilder.addItem(
                (MaterialAboutActionItem.Builder()
                        .text(R.string.about_free)
                        .icon(
                                IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_money_off).apply {
                                    sizeDp = 18
                                }
                        )
                        .build())
        )

        aboutCardBuilder.addItem(
                (MaterialAboutActionItem.Builder()
                        .text(R.string.about_donate)
                        .icon(
                                IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_money_box).apply {
                                    sizeDp = 18
                                }
                        )
                        .setOnClickAction(
                                ConvenienceBuilder.createWebsiteOnClickAction(
                                        context,
                                        "http://comicsdb.cz/donate.php".toUri()
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