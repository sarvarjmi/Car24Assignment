# Production-Ready ProGuard/R8 Rules

# 1. General Project Settings
-keepattributes SourceFile,LineNumberTable,Signature,InnerClasses,EnclosingMethod,*Annotation*
-renamesourcefileattribute SourceFile

# 2. Kotlinx Serialization
# Keep all Serializable classes and their serializers
-keepnames class kotlinx.serialization.json.JsonClassDiscriminator
-keepclassmembers class * {
    @kotlinx.serialization.Serializable *;
}
-keepclassmembers class * {
    public static ** Companion;
}
-keep class **$$serializer { *; }
-keepclassmembers class * {
    ** serializer(...);
}

# Keep our specific models that are serialized
-keep class com.noorheroes.car24assignment.core.model.json.** { *; }

# 3. Timber
-keep class timber.log.Timber { *; }
-keep class timber.log.Timber$Tree { *; }

# 4. Hilt / Dagger
# Most is handled by the plugin, but keep the generated entry points
-keep class * extends android.app.Application
-keep class * extends android.app.Activity
-keep class * extends androidx.fragment.app.Fragment
-keep class * extends androidx.viewmodel.ViewModel
-keep class * extends androidx.lifecycle.ViewModel

# 5. Room
# Usually handled by consumer rules in core-database, but added here for safety
-keep class * extends androidx.room.RoomDatabase
-keep class * extends androidx.room.Entity
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }

# 6. Compose
# R8 handles Compose well, but keep names for @Preview if needed (optional for production)
#-keepclassmembers class * {
#    @androidx.compose.runtime.Composable *;
#}
