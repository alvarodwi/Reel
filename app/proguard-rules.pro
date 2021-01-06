##---------------Begin: proguard configuration for ViewBinding ----------
# ViewBindingDelegate uses Reflection.
-keepclassmembers class ** implements androidx.viewbinding.ViewBinding {
    public static ** bind(android.view.View);
}