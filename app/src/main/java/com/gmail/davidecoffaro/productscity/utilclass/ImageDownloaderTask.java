package com.gmail.davidecoffaro.productscity.utilclass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
    WeakReference<ImageView> imageViewWeakReference;

    public ImageDownloaderTask(ImageView imageView){
        imageViewWeakReference = new WeakReference<ImageView>(imageView);
    }

    protected Bitmap doInBackground(String... params){
        return downloadBitmap(params[0]);
    }

    protected void onPostExecute(Bitmap b){
        if(isCancelled()){
            b = null;
        }

        if(imageViewWeakReference!=null){
            ImageView imageView = imageViewWeakReference.get();
            if(imageView!=null){
                //TODO aggiustare per rendere l'immagine del prodotto visibile con la giusta dimensione
                //imageView.setImageBitmap(Bitmap.createScaledBitmap(b, 120, 120, false));
                imageView.setImageBitmap(b);
                //imageView.setMinimumHeight(b.getHeight());
                //imageView.setMinimumWidth(b.getWidth());

            }
        }
    }

    protected Bitmap downloadBitmap(String stringImage){
        try {
            InputStream is = new URL(stringImage).openStream();
            Bitmap b = BitmapFactory.decodeStream(is);
            return b;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}
