# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keepattributes Signature
-keepattributes *Annotation*
-renamesourcefileattribute Historias
-keepattributes SourceFile,LineNumberTable
-keepattributes EnclosingMethod
-dontwarn **$$Lambda$*
-dontwarn okio.**
-dontwarn okhttp3.**
-dontwarn org.xmlpull.v1.**
-dontwarn sun.misc.Unsafe

-keep class android.support.annotation.CallSuper
-keep class android.support.annotation.UiThread
-keep class android.support.annotation.Keep
-keep class android.support.annotation.Nullable
-keep class android.support.annotation.NonNull
-keep class android.support.v4.app.Fragment
-keep class android.support.v4.app.FragmentManager
-keep class android.support.v4.app.ActivityCompat
-keep class android.support.v4.content.ContextCompat
-keep class android.support.v4.app.ActivityCompat
##
-keep class rx.** { *; }
-keep class rx.Subscriber #sin esto falla rx
-keep class rx.exceptions
-keep class rx.Notification

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

-dontnote rx.internal.util.PlatformDependent
