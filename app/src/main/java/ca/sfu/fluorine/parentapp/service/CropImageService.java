package ca.sfu.fluorine.parentapp.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import ca.sfu.fluorine.parentapp.R;
import ca.sfu.fluorine.parentapp.view.children.AddChildActivity;

public class CropImageService {
	private final AppCompatActivity activity;
	public CropImageService(AppCompatActivity activity) {
		this.activity = activity;
	}

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
		activity.registerForActivityResult(
				cropImageActivityContract,
				callback).launch(null);
	}
}
