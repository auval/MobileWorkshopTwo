# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/amir/Android/Sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile



# http://greenrobot.org/eventbus/documentation/proguard/
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#firebase real time dataase
# Add this global rule
-keepattributes Signature

# this is relevant only if using
# DatabaseReference.setValue(myEvent) and  DataSnapshot.getValue(MyEvent.class)
-keepclassmembers class ac.shenkar.workshoptwo.MyEvent {
  *;
}

# nice trick to simplify ProGuard preserving methods when shrinking & obfuscating
# use @Keep annotation anywhere I want to keep a method
-keep class android.support.annotation.Keep { *; }
-keepclassmembers class * {
     @android.support.annotation.Keep *;
}
