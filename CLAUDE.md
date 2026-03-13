# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

ComicsDBClient is an Android app (Kotlin) for browsing the Czech comics database at comicsdb.cz. Single-module app under package `cz.kutner.comicsdb`.

## Build Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK (minified + shrunk)
./gradlew assembleRelease

# Full build (compile + lint + test)
./gradlew build

# Run unit tests
./gradlew test

# Run instrumented tests (requires device/emulator)
./gradlew connectedAndroidTest

# Check for dependency updates
./gradlew dependencyUpdates
```

## Build Details

- **Gradle**: Kotlin DSL, version catalog at `gradle/libs.versions.toml`
- **Compile/Target SDK**: 36, **Min SDK**: 24, **Java**: 17
- Version code/name derived from git (`git rev-list` count and `git describe --tags`)
- Data Binding and View Binding both enabled
- KAPT used (not KSP)
- R8/ProGuard enabled for release builds

## Architecture

**MVVM with LiveData** — no Jetpack Compose, no Navigation Component, no repository layer.

Data flow: Fragment/Activity → ViewModel → Retrofit Service → comicsdb.cz REST API

### Base classes in `abstracts/` package

- `AbstractFragment<Data: Item>` — list screen with RecyclerView, infinite scroll pagination, view state switching (content/empty/loading/error), DiffUtil updates
- `AbstractDetailActivity<Data: Item>` — detail screen with single item loading and deep link support
- `AbstractViewModel<Data: Item>` — single item loading
- `AbstractPagedViewModel<Data: Item>` — paginated list loading with page tracking
- `AbstractListAdapter` — `ListDelegationAdapter<List<Item>>` using AdapterDelegates4

### Code organization

Feature-per-package: each feature (e.g. `comicsList/`, `comicsDetail/`, `authorList/`) contains its Fragment/Activity, ViewModel, and adapter delegate. Shared code lives in `abstracts/`, `model/`, `network/`, `helpers/`, `utils/`.

### Key libraries

| Library | Purpose |
|---------|---------|
| Koin 4.x | DI — modules defined in `network/KoinModule.kt`, initialized in `ComicsDBApplication` |
| Retrofit 3.0 + GSON | REST client — services in `network/` package, base URL `https://comicsdb.cz` |
| OkHttp 5.x | HTTP client with 10MB cache, 120s timeouts |
| Coil 3.x | Image loading (including HTML image getter via `CoilImageGetter`) |
| AdapterDelegates4 | Multi-type RecyclerView adapters |
| Timber | Logging (debug builds only) |

### Navigation

- `MainActivity` with NavigationView drawer hosts list fragments
- Detail screens are separate Activities (Comics, Series, Author)
- Deep links: `https://comicsdb.cz/comics/{id}`, `/author/{id}`, `/serie/{id}`
- Search via `SearchActivity` with ViewPager tabs

### Models

All data models implement the `Item` marker interface. List models (e.g. `Comics`, `Series`) and detail models (e.g. `ComicsDetail`, `SeriesDetail`) are separate classes. Some models parse HTML content via `parseAsHtml()`.
