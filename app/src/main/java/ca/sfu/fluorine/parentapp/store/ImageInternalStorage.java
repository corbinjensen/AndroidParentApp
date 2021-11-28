package ca.sfu.fluorine.parentapp.store;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import ca.sfu.fluorine.parentapp.R;

public class ImageInternalStorage {
	private Context context;
	private static final String FILE_EXT = ".png";

	@SuppressLint("StaticFieldLeak")
	private static ImageInternalStorage INSTANCE;

	private ImageInternalStorage() {
	}

	public static ImageInternalStorage getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new ImageInternalStorage();
			INSTANCE.context = context.getApplicationContext();
		}
		return INSTANCE;
	}

	public void saveImage(@NonNull String fileName, @NonNull Bitmap image) {
		try (FileOutputStream outputStream =
					 context.openFileOutput(fileName + FILE_EXT, Context.MODE_PRIVATE)) {
			image.compress(Bitmap.CompressFormat.PNG, 95, outputStream);
		} catch (Exception e) {
			Toast.makeText(context, R.string.save_error, Toast.LENGTH_SHORT).show();
		}
	}

	@Nullable
	public Bitmap loadImage(@Nullable String fileName) {
		if (fileName == null || fileName.isEmpty()) return null;
		Bitmap bitmap = null;
		try (FileInputStream inputStream = context.openFileInput(fileName + FILE_EXT)) {
			bitmap = BitmapFactory.decodeStream(inputStream);
		} catch (Exception e) {
			Toast.makeText(context, R.string.fetch_error, Toast.LENGTH_SHORT).show();
		}
		return bitmap;
	}

	public void deleteImage(@Nullable String fileName) {
		if (fileName == null || fileName.isEmpty()) return;
		context.deleteFile(fileName);
	}
}
