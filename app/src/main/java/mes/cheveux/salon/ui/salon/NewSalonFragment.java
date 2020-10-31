package mes.cheveux.salon.ui.salon;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mes.cheveux.salon.R;
import mes.cheveux.salon.common.Constants;
import mes.cheveux.salon.common.HelperMethods;
import mes.cheveux.salon.common.MesApp;
import mes.cheveux.salon.common.api.RetrofitClient;
import mes.cheveux.salon.common.data.MessageResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static mes.cheveux.salon.common.HelperMethods.createPartFromString;


public class NewSalonFragment extends Fragment {

    private EditText inputSalonName,inputPhone,inputWebsite,inputAddress;
    private ImageView salonImageView;
    private Button btnSelectImages,btnPostProduct;
    private RelativeLayout pleaseWaitLayout;
    private AlertDialog.Builder builder;


    private ImagePicker imagePicker;
    private int IMAGE_REQUEST_CODE = 10001;
    private String salonName,salonPhone,salonAddress,website,place_id,state,city,country;
    private Double latitude,longitude;
    private int owner_id = 1;
    private  Image firstImage;

    public NewSalonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_salon, container, false);

        inputSalonName = view.findViewById(R.id.input_salon_name);
        inputPhone = view.findViewById(R.id.input_phone);
        inputWebsite = view.findViewById(R.id.input_website);
        inputAddress = view.findViewById(R.id.input_address);


        salonImageView = view.findViewById(R.id.salonImageView);

        btnSelectImages = view.findViewById(R.id.btn_select_images);
        btnSelectImages.setOnClickListener(v -> this.pickImage());

        btnPostProduct = view.findViewById(R.id.btn_post_product);
        btnPostProduct.setOnClickListener(v -> this.postNewSalon());

        pleaseWaitLayout = view.findViewById(R.id.llProgressBar);

        return view;
    }

    @SuppressLint("CheckResult")
    private void postNewSalon(){
        if (validateSalonForm()){
            pleaseWaitLayout.setVisibility(View.VISIBLE);
            MultipartBody.Part logoImage = HelperMethods.filePart(firstImage.getPath(),"logoImage");
            HashMap<String, RequestBody> dataMap  =  this.prepareFormMap();

            RetrofitClient.getInstance()
                    .getApiService()
                    .postSalon(dataMap,logoImage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleSuccessResponse, throwable -> {
                        pleaseWaitLayout.setVisibility(View.GONE);
                        Toast.makeText(getContext(),
                                throwable.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    });
        }

    }

    private void handleSuccessResponse(MessageResponse messageResponse){
        pleaseWaitLayout.setVisibility(View.GONE);
        if (messageResponse.getCode() == Constants.SUCCESS_CODE) {
            builder.setMessage(messageResponse.getMessage())
                    .setCancelable(false)
                    .setPositiveButton("OK",(dialog,id)->{
                       // Navigation.createNavigateOnClickListener(R.id.nav_my_salon);
                    });
        } else {
            builder.setMessage(messageResponse.getMessage())
                    .setCancelable(false)
                    .setNegativeButton("OK",(dialog,id)->{
                        dialog.cancel();
                    });
        }

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Response");
        alert.show();
    }

    private void pickImage() {

        imagePicker = ImagePicker.create(NewSalonFragment.this);
        imagePicker.folderMode(true)
                .toolbarFolderTitle("Folder")
                .toolbarImageTitle("Tap to select")
                .toolbarDoneButtonText("DONE")
                .includeVideo(false)
                .limit(1)
                .enableLog(false)
                .start(IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {

        if (requestCode == IMAGE_REQUEST_CODE ) {
            // Get a list of picked images
            List<Image> images = ImagePicker.getImages(data);

            if (images != null) {
                firstImage = images.get(0);
                Picasso.get().load(firstImage.getPath()).into(salonImageView);
                salonImageView.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getActivity(), "No image selected!", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean validateSalonForm(){
        salonName = inputSalonName.getText().toString().trim();
        salonPhone = inputPhone.getText().toString().trim();
        website = inputWebsite.getText().toString().trim();
        salonAddress = inputAddress.getText().toString().trim();

        boolean formValid = true;

        if (salonName.isEmpty() || salonName.length() < 3) {
            inputSalonName.setError("Salon name must be greater than 3 characters!");
            formValid = false;
        }
        if (salonPhone.isEmpty()) {
            inputPhone.setError("Phone cannot be empty!");
            formValid = false;
        }
        if (salonAddress.isEmpty() || salonAddress.length() < 5) {
            inputAddress.setError("Address cannot be empty!");
            formValid = false;
        }

        return  formValid;
    }


    private HashMap<String, RequestBody> prepareFormMap() {
        // create a map of data to pass along
        RequestBody _salonName = createPartFromString(salonName);
        RequestBody _salonPhone = createPartFromString(salonPhone);
        RequestBody _salonAddress = createPartFromString(salonAddress);
        RequestBody _website = createPartFromString(website);
        RequestBody _place_id = createPartFromString(place_id);
        RequestBody _state = createPartFromString(state);
        RequestBody _city = createPartFromString(city);
        RequestBody _country = createPartFromString(country);
        RequestBody _latitude = createPartFromString(String.valueOf(latitude));
        RequestBody _longitude = createPartFromString(String.valueOf(longitude));
        RequestBody _owner_id = createPartFromString(String.valueOf(owner_id));


        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", _salonName);
        map.put("phone", _salonPhone);
        map.put("address", _salonAddress);
        map.put("website", _website);
        map.put("place_id", _place_id);
        map.put("state", _state);
        map.put("city", _city);
        map.put("country", _country);

        map.put("latitude", _latitude);
        map.put("longitude", _longitude);
        map.put("owner_id", _owner_id);

        return  map;
    }
}
