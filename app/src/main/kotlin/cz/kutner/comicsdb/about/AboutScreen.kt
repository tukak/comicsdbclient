package cz.kutner.comicsdb.about

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import cz.kutner.comicsdb.BuildConfig

@Composable
fun AboutScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // App card
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "ComicsDB Client", style = MaterialTheme.typography.titleLarge)
                Text(
                    text = "Verze: ${BuildConfig.VERSION_NAME}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "Historie změn",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable {
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = "https://github.com/tukak/comicsdbclient/releases".toUri()
                            context.startActivity(i)
                        }
                )
            }
        }

        // About card
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "O aplikaci", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Neoficiální klient pro Android na prohlížení ComicsDB. Veškeré zobrazované údaje pocházejí z ComicsDB.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable {
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = "http://www.comicsdb.cz".toUri()
                            context.startActivity(i)
                        }
                )
                Text(
                    text = "Aplikace je a bude poskytována zdarma, bez reklam a bez nákupů v aplikaci. Je vytvářena ve volném čase a je poskytována bez jakýchkoliv záruk.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "Pokud chcete, můžete přispět na samotnou ComicsDB.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable {
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = "http://comicsdb.cz/donate.php".toUri()
                            context.startActivity(i)
                        }
                )
            }
        }

        // Author card
        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Autor", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Lukáš Kutner (tukak)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable {
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = "http://comicsdb.cz/user.php?id=5953".toUri()
                            context.startActivity(i)
                        }
                )
                Text(
                    text = "Hlašte chyby nebo pište nápady: lukas@kutner.cz",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "@tukak",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable {
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = "https://twitter.com/tukak".toUri()
                            context.startActivity(i)
                        }
                )
                Text(
                    text = "Zdrojový kód",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable {
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = "https://github.com/tukak/comicsdbclient".toUri()
                            context.startActivity(i)
                        }
                )
            }
        }
    }
}
