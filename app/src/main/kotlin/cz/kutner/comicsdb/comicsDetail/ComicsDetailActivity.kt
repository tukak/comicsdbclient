package cz.kutner.comicsdb.comicsDetail

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import cz.kutner.comicsdb.ui.theme.ComicsDBTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class ComicsDetailActivity : ComponentActivity() {
    private val model: ComicsDetailViewModel by viewModel()

    private val id: Int by lazy {
        if (Intent.ACTION_VIEW == intent.action) {
            intent.dataString?.toUri()?.pathSegments?.get(1)?.toInt() ?: 0
        } else {
            intent.getIntExtra(Intent.EXTRA_UID, 0)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.loadData(id)
        setContent {
            ComicsDBTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Detail comicsu") },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zpět")
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                ) { paddingValues ->
                    ComicsDetailScreen(viewModel = model, modifier = Modifier.padding(paddingValues))
                }
            }
        }
    }
}
