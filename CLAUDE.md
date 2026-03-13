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

# Check for dependency updates (note: currently broken with Gradle 9.4)
./gradlew dependencyUpdates --no-parallel
```

## Build Details

- **Gradle**: Kotlin DSL, version catalog at `gradle/libs.versions.toml`
- **AGP**: 9.x with built-in Kotlin support (no separate `kotlin.android` plugin)
- **Compile/Target SDK**: 36, **Min SDK**: 24, **Java**: 17
- Version code/name derived from git (`git rev-list` count and `git describe --tags`)
- Compose enabled via `kotlin.compose` plugin
- No KAPT, no data binding, no view binding
- R8/ProGuard enabled for release builds

## Architecture

**MVVM with StateFlow + Jetpack Compose** — no Navigation Component, no repository layer.

Data flow: Composable Screen → ViewModel (StateFlow) → Retrofit Service → comicsdb.cz REST API

### ViewState pattern

All screens use `ViewState<T>` sealed class (`ui/components/ViewState.kt`) with Loading, Content, Error, Empty states. `ViewStateContainer` composable switches on the state to show the appropriate UI.

### Base classes in `abstracts/` package

- `AbstractViewModel<Data: Item>` — single item loading via `MutableStateFlow<ViewState<Data>>`
- `AbstractPagedViewModel<Data: Item>` — paginated list loading with page tracking, infinite scroll support, `isLoading` guard against duplicate loads

### Code organization

Feature-per-package: each feature (e.g. `comicsList/`, `comicsDetail/`, `authorList/`) contains its Activity (if detail screen), ViewModel, and `*Screen.kt` composable. Shared code lives in `abstracts/`, `model/`, `network/`, `ui/`, `utils/`.

- `ui/theme/` — `ComicsDBTheme`, `Color.kt` (primary=#C00000)
- `ui/components/` — `ViewState`, `ViewStateContainer`, `LoadingView`, `ErrorView`, `EmptyView`, `CoilImage`, `HtmlText`
- `main/` — `MainActivity` + `MainScreen` with `ModalNavigationDrawer` and screen switching

### Key libraries

| Library | Purpose |
|---------|---------|
| Compose BOM + Material 3 | UI framework and design system |
| Koin 4.x | DI — modules in `di/`, initialized in `ComicsDBApplication`, `koinViewModel` in Compose |
| Retrofit 3.0 + GSON | REST client — services in `network/` package, base URL `https://comicsdb.cz` |
| OkHttp 5.x | HTTP client with 10MB cache, 120s timeouts |
| Coil 3.x + coil-compose | Image loading in Compose (`AsyncImage`) and HTML (`CoilImageGetter`) |
| Telephoto | Pinch-to-zoom image viewing (`ZoomableAsyncImage`) |
| Timber | Logging (debug builds only) |

### Navigation

- `MainActivity` with Compose `ModalNavigationDrawer` hosts list screens (Comics, News, Series, Authors, Classified, Forum, About)
- Detail screens are separate Activities with Compose `setContent` (Comics, Series, Author)
- All Activities extend `ComponentActivity`
- Deep links: `https://comicsdb.cz/comics/{id}`, `/author/{id}`, `/serie/{id}`
- Search via `SearchActivity` with `HorizontalPager` tabs
- Image viewer via `ImageViewSliderActivity` with `HorizontalPager` + `ZoomableAsyncImage`

### HTML rendering

`HtmlText` composable (`ui/components/SharedComponents.kt`) uses `AndroidView` wrapping `TextView` with `CoilImageGetter` for rendering HTML content with inline images. Used in news, forum, classified, comments, and author bio screens.

### Models

All data models implement the `Item` marker interface. List models (e.g. `Comics`, `Series`) and detail models (e.g. `ComicsDetail`, `SeriesDetail`) are separate classes. Some models parse HTML content via `parseAsHtml()`.
