package mes.cheveux.salon.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HelperMethods {

    private static Gson gson;

    public static Gson getGsonParser() {
        if(null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }

    @NonNull
    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    @SuppressLint("CheckResult")
    public static MultipartBody.Part filePart(String filePath, String key){

        File mainImage = new File(filePath);

        try {
            mainImage =  new Compressor(MesApp.getInstance().context)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .compressToFile(mainImage);
        } catch (IOException e) {
            Toast.makeText(MesApp.getInstance().context,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        }

        RequestBody requestBodyMainFile = RequestBody.create(
                MediaType.parse("image/*")
                , mainImage);

        return   MultipartBody.Part
                .createFormData(key, mainImage.getName(), requestBodyMainFile);
    }


    public static Double formatCurrency(Double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(value));
    }

}
