package ca.sfu.fluorine.parentapp.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

	public void saveImage(String fileName, Bitmap image) {
		FileOutputStream outputStream = null;
		try {
			outputStream = context.openFileOutput(fileName + FILE_EXT, Context.MODE_PRIVATE);
			image.compress(Bitmap.CompressFormat.PNG, 95, outputStream);
			outputStream.close();
		} catch (Exception e) {
			Toast.makeText(context, R.string.save_error, Toast.LENGTH_SHORT).show();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Nullable
	public Bitmap loadImage(@Nullable String fileName) {
		if (fileName == null || fileName.isEmpty()) return null;
		FileInputStream inputStream = null;
		Bitmap bitmap = null;
		try {
			inputStream = context.openFileInput(fileName + FILE_EXT);
			bitmap = BitmapFactory.decodeStream(inputStream);
		} catch (Exception e) {
			Toast.makeText(context, R.string.fetch_error, Toast.LENGTH_SHORT).show();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bitmap;
	}

	public void deleteImage(@Nullable String fileName) {
		if (fileName == null || fileName.isEmpty()) return;
		context.deleteFile(fileName);
	}
}
