package ca.sfu.fluorine.parentapp.service;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import ca.sfu.fluorine.parentapp.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class CropImageService {
	private final FragmentActivity activity;

	public CropImageService(FragmentActivity activity) { this.activity = activity; }

	private final ActivityResultContract<Object, Bitmap> cropImageActivityContract
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

			// Convert URI -> Bitmap
			Uri imageUri = result.getUri();
			Bitmap image = null;
			try {
				ContentResolver cr = activity.getApplicationContext().getContentResolver();
				image = MediaStore.Images.Media.getBitmap(cr, imageUri);
			} catch (Exception e) {
				Toast.makeText(activity, R.string.fetch_error, Toast.LENGTH_SHORT).show();
			}
			return image;
		}
	};

	public ActivityResultLauncher<?> getServiceLauncher(ActivityResultCallback<Bitmap> callback) {
		return activity.registerForActivityResult(
				cropImageActivityContract,
				callback);
	}
}
