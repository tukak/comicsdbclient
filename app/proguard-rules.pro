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

#-dontwarn retrofit.appengine.UrlFetchClient

-dontwarn com.google.android.gms.**

-dontwarn java.lang.invoke.*

-dontwarn rx.internal.util.unsafe.*

#-dontwarn okhttp3.internal.huc.HttpURLConnectionImpl

-dontwarn kotlin.**
-dontwarn org.w3c.dom.events.*
-dontwarn org.jetbrains.annotations.NonNls

# Retrofit 2.X
## https://square.github.io/retrofit/ ##

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

## Square Picasso specific rules ##
## https://square.github.io/picasso/ ##

-dontwarn com.squareup.okhttp.**

# Okio
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**