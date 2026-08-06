# Kotlinx Serialization consumer rules
# Attributes are handled in the app's proguard rules for better optimization control

-keepnames class kotlinx.serialization.json.JsonClassDiscriminator

# Keep all @Serializable classes
-keepclassmembers class * {
    @kotlinx.serialization.Serializable *;
}

# Keep the serializer field and the serializer() method
-keepclassmembers class * {
    public static ** Companion;
}
-keep class **$$serializer { *; }
-keepclassmembers class * {
    ** serializer(...);
}
