package com.example.hometask3

// import android.os.Bundle
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    SDK_INT >= TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}


//inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
//    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
//    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
//}
//
//inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
//    SDK_INT >= TIRAMISU -> getSerializableExtra(key, T::class.java)
//    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
//}



