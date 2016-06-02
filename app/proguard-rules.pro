# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Program Files (x86)\Android\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keepattributes SourceFile,LineNumberTable

-keep class org.jsoup.** { *; }

-keep class org.jetbrains.annotations.** {
    public protected *;
}

-keepattributes *Annotation*
-keepclassmembers class * extends uk.co.ribot.easyadapter.ItemViewHolder {
    public <init>(...);
 }

-dontwarn okio.**
-dontwarn retrofit.appengine.UrlFetchClient

-dontwarn com.google.android.gms.**

-dontwarn java.lang.invoke.*

-dontwarn rx.internal.util.unsafe.*

-dontwarn okhttp3.internal.huc.HttpURLConnectionImpl

-dontwarn kotlin.**
-dontwarn org.w3c.dom.events.*
-dontwarn org.jetbrains.annotations.NonNls

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keep class com.squareup.okhttp.** { *; }

-keep interface com.squareup.okhttp.** { *; }

-dontwarn com.squareup.picasso.**