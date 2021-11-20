package ca.sfu.fluorine.parentapp.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropImageService {
	private final ActivityResultCaller resultCaller;

	public CropImageService(ActivityResultCaller resultCaller) { this.resultCaller = resultCaller; }

	private static final ActivityResultContract<Object, Bitmap> cropImageActivityContract
			= new ActivityResultContract<Object, Bitmap>() {
		@NonNull
		@Override
		public Intent createIntent(@NonNull Context context, Object input) {
			return CropImage.activity()
					.setAspectRatio(1, 1)
					.setCropShape(CropImageView.CropShape.OVAL)
					.setAllowFlipping(false)
					.setAllowRotation(false)
					.setGuidelines(CropImageView.Guidelines.ON)
					.getIntent(context);
		}

		@Override
		public Bitmap parseResult(int resultCode, @Nullable Intent intent) {
			CropImage.ActivityResult result = CropImage.getActivityResult(intent);
			if (result == null) return null;
			return result.getBitmap();
		}
	};

	public void launch(ActivityResultCallback<Bitmap> callback) {
		resultCaller.registerForActivityResult(
				cropImageActivityContract,
				callback).launch(null);
	}
}
