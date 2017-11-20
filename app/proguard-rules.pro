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

#-dontobfuscate
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable

-keep class org.jetbrains.annotations.** {
    public protected *;
}

-keepattributes *Annotation*

#Crashlytics
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

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

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
-dontwarn javax.annotation.**

# OkHttp
-dontwarn okhttp3.**

# Okio
-dontwarn okio.**

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#asyncawait
-dontwarn rx.Observable
-dontwarn rx.observables.BlockingObservable

-keep class cz.kutner.comicsdb.model.** { *; }

-dontwarn cz.kutner.comicsdb.model.**