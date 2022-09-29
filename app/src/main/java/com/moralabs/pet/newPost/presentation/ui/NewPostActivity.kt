package com.moralabs.pet.newPost.presentation.ui

 import android.app.Activity
 import android.content.Context
 import android.content.Intent
 import android.os.Parcel
 import android.os.Parcelable
 import androidx.activity.result.contract.ActivityResultContract
 import com.moralabs.pet.R
 import com.moralabs.pet.core.presentation.toolbar.PetToolbarListener
import com.moralabs.pet.core.presentation.ui.BaseActivity
import com.moralabs.pet.databinding.ActivityNewPostBinding
 import com.moralabs.pet.onboarding.presentation.ui.login.LoginActivity
 import dagger.hilt.android.AndroidEntryPoint

typealias LocationAction = (() -> Unit)

@AndroidEntryPoint
class NewPostActivity : BaseActivity<ActivityNewPostBinding>(),
    PetToolbarListener {

    companion object {
        var BUNDLE_CHOOSE_TYPE = "type"
        var LOCATION = "location"
        var LOCATION_ID = "location_id"
    }
    override fun getLayoutId() = R.layout.activity_new_post
}

class LocationResultContract : ActivityResultContract<LocationAction, LocationResult>() {

    private var action: LocationAction? = null

    override fun createIntent(context: Context, action: LocationAction): Intent {
        this.action = action

        return Intent(context, LoginActivity::class.java)
    }

    override fun parseResult(resultCode: Int, result: Intent?): LocationResult {
        if (resultCode != Activity.RESULT_OK) {
            return LocationResult.LOCATION_CANCELED
        }

        val loginResult = result?.getParcelableExtra<LocationResult>(LoginActivity.RESULT_LOGIN)

        if (loginResult == LocationResult.LOCATION_OK) {
            this.action?.invoke()
        }

        return loginResult ?: LocationResult.LOCATION_CANCELED
    }
}

enum class LocationResult(val id: Int) : Parcelable {

    LOCATION_OK(0),
    LOCATION_CANCELED(2);

    constructor(parcel: Parcel) : this(parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocationResult> {
        override fun createFromParcel(parcel: Parcel): LocationResult {
            return values()[parcel.readInt()]
        }

        override fun newArray(size: Int): Array<LocationResult?> {
            return arrayOfNulls(size)
        }
    }
}