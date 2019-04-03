package test.bazar.com.cafebazar.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.bazar.com.cafebazar.R;
import test.bazar.com.cafebazar.adapters.BannerAdapter;
import test.bazar.com.cafebazar.adapters.NewAppAdapter;
import test.bazar.com.cafebazar.models.App;
import test.bazar.com.cafebazar.models.Banner;
import test.bazar.com.cafebazar.models.Slider;
import test.bazar.com.cafebazar.retrofit.ApiClient;
import test.bazar.com.cafebazar.retrofit.ApiService;


public class FragmentHome extends Fragment implements BaseSliderView.OnSliderClickListener {
    private SliderLayout slider;
    //اینم لیستی هست که قراره لیست اسلایدهای گرفته شده از سرور داخل این لیست ریخته بشه
    private ArrayList<String> sliderArray;
    private ArrayList<String> bannerArray;
    // اطلاعاتی که از سرور میاد داخل این لیست میریزیم
    private List<Slider> sliders;
    private List<Banner> banners;
    private RecyclerView bannersRecyclerView;
    private RecyclerView recyclerNewApp;
    private RecyclerView recyclerUpdaterApp;
    List<App> newApps;
    List<App> updateApp;
    ImageView imgProfile;
    String phoneNumber = "";
    SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setupViews(view);

        setSlideForSlider();
        getBanners();
        getNewApps();
        getUpdatedApp();
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getContext().getSharedPreferences("home", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                if (username.equals("")) {
                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
                    View bottomSheetView = getLayoutInflater().inflate(R.layout.buttom_sheet_profile, null);
                    bottomSheetDialog.setContentView(bottomSheetView);
                    ///////// تغییر اندازه باتم شیت
                    BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
                    bottomSheetBehavior.setPeekHeight(

                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, getResources().getDisplayMetrics())

                            //////////////////////////////////

                    );
                    final EditText edtPhoneNumber = (EditText) bottomSheetView.findViewById(R.id.editTextNumber);
                    final ProgressBar progressBar = (ProgressBar) bottomSheetView.findViewById(R.id.spin_kit);
                    Sprite doubleBounce = new DoubleBounce();
                    progressBar.setIndeterminateDrawable(doubleBounce);
                    Button btnRegister = (Button) bottomSheetView.findViewById(R.id.buttonRegister);
                    btnRegister.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final ApiService service = ApiClient.getClient().create(ApiService.class);
                            if (edtPhoneNumber.getText().toString().equals("")) {
                                edtPhoneNumber.setError("لطفا شماره همراه خود را وارد کنید");

                            } else {
                                phoneNumber = edtPhoneNumber.getText().toString();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("phoneNumber", phoneNumber);
                                editor.apply();
                                progressBar.setVisibility(View.VISIBLE);
                                Call<ResponseBody> call = service.sendNumber(edtPhoneNumber.getText().toString());
                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(final Call<ResponseBody> call, Response<ResponseBody> response) {
                                        try {

                                            Log.i("LOG", "onResponse: " + response.body().string());
                                            bottomSheetDialog.dismiss();
                                            final BottomSheetDialog bottomSheetValisation = new BottomSheetDialog(getContext());
                                            final View validationView = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_validation_code, null);
                                            bottomSheetValisation.setContentView(validationView);
                                            bottomSheetValisation.show();

                                            final EditText edtvalidatin = validationView.findViewById(R.id.edt_bottomSheetValidation_code);
                                            Button btnvalidation = validationView.findViewById(R.id.btn_bottomSheetValidation_validation);
                                            btnvalidation.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    // Toast.makeText(getContext(), "click shod", Toast.LENGTH_SHORT).show();
                                                    ApiService service1 = ApiClient.getClient().create(ApiService.class);
                                                    Call<ResponseBody> call1 = service1.sendvalidationCode(edtvalidatin.getText().toString(), phoneNumber);
                                                    call1.enqueue(new Callback<ResponseBody>() {
                                                        @Override
                                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                            try {
                                                                // Log.i("VALIDATION", "onResponse is : " + response.body().string());
                                                                String userId = response.body().string();
                                                                //این یعنی هر گونه فاصله داره بگیر این فاصله هارو
                                                                userId = userId.trim();
                                                                if (!userId.equals("validation faild")) {
                                                                    Toast.makeText(getContext(), "به بازار خوش آمدید", Toast.LENGTH_SHORT).show();
                                                                    sharedPreferences = getContext().getSharedPreferences("home", Context.MODE_PRIVATE);
                                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                                    editor.putString("username", phoneNumber);
                                                                    editor.putString("userId", userId);
                                                                    editor.apply();

                                                                    bottomSheetValisation.dismiss();
                                                                } else {
                                                                    Toast.makeText(getContext(), "کد فعالسازی اشتباه است دوباره سعی کنید", Toast.LENGTH_SHORT).show();

                                                                }
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                                        }
                                                    });

                                                }
                                            });

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                                    }
                                });
                            }
                        }
                    });
                    bottomSheetDialog.show();

                } else {
                    Bundle bundle = new Bundle();
                    String sharedPhoneNumber = sharedPreferences.getString("phoneNumber", phoneNumber);
                    bundle.putString("username", sharedPhoneNumber);
                    FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = manager.beginTransaction();
                    Fragment_accuont fragment_accuont = new Fragment_accuont();
                    fragment_accuont.setArguments(bundle);
                    fragmentTransaction.replace(R.id.rel_parent_allView, fragment_accuont);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }

            }
        });
        return view;
    }


    private void setupViews(View view) {
        imgProfile = (ImageView) view.findViewById(R.id.iv_toolbar_profile);
        slider = view.findViewById(R.id.slider);
        sliderArray = new ArrayList<>();
        bannerArray = new ArrayList<>();
      /*  sliderArray.add("https://dkstatics-public.digikala.com/digikala-adservice-banners/3563.jpg");
        sliderArray.add("https://dkstatics-public.digikala.com/digikala-adservice-banners/3581.jpg");
        sliderArray.add("https://dkstatics-public.digikala.com/digikala-adservice-banners/3543.jpg");
        sliderArray.add("https://dkstatics-public.digikala.com/digikala-adservice-banners/3557.jpg");*/
        slider.setPresetTransformer(SliderLayout.Transformer.Default);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        bannersRecyclerView = view.findViewById(R.id.rv_fragmentHome_banners);
        recyclerNewApp = view.findViewById(R.id.recycle_fragHome_newApp);
        recyclerUpdaterApp = view.findViewById(R.id.rv_fragmentHome_updatedApp);
        bannersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerNewApp.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerUpdaterApp.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getContext(), " کلیک شد", Toast.LENGTH_SHORT).show();

    }

    private void setSlideForSlider() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Slider>> call = service.getSliders();
        call.enqueue(new Callback<List<Slider>>() {
            @Override
            public void onResponse(Call<List<Slider>> call, Response<List<Slider>> response) {
                // کل اطلاعات رسیده از سمت سرور داخل این لیست ریخته میشه
                sliders = response.body();
                for (int i = 0; i < sliders.size(); i++) {
                    // Toast.makeText(getContext(), sliders.get(i).getUrl() + "", Toast.LENGTH_SHORT).show();
                    // اسلایدهای رسیده از سمت سرور داخل این آرایه ریخته میشه
                    sliderArray.add(sliders.get(i).getUrl());
                }
                for (int i = 0; i < sliderArray.size(); i++) {
                    DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());
                    defaultSliderView
                            .image(sliderArray.get(i))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(FragmentHome.this);
                    defaultSliderView.bundle(new Bundle());
                    slider.addSlider(defaultSliderView);
                }

            }

            @Override
            public void onFailure(Call<List<Slider>> call, Throwable t) {
                Toast.makeText(getContext(), t.toString() + "", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getBanners() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Banner>> call = service.getBanners();
        call.enqueue(new Callback<List<Banner>>() {
            @Override
            public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                banners = response.body();
              /*  for (int i = 0; i < banners.size(); i++) {
                    Log.i("BANNERS", "onResponse: " + banners.get(i).getUrl());
                }*/
                bannersRecyclerView.setAdapter(new BannerAdapter(banners, getActivity()));


            }

            @Override
            public void onFailure(Call<List<Banner>> call, Throwable t) {
                Toast.makeText(getContext(), t.toString() + "", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getNewApps() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<App>> call = service.getNewApps();
        call.enqueue(new Callback<List<App>>() {
            @Override
            public void onResponse(Call<List<App>> call, Response<List<App>> response) {
                newApps = response.body();
                /*for (int i = 0; i < newApps.size(); i++) {
                   Log.i("NEWAPSS", "onResponse: " + newApps.get(i).getName());
                }*/
                recyclerNewApp.setAdapter(new NewAppAdapter(getActivity(), newApps));

            }

            @Override
            public void onFailure(Call<List<App>> call, Throwable t) {

            }
        });

    }

    private void getUpdatedApp() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<App>> call = service.getUpdatedApp();
        call.enqueue(new Callback<List<App>>() {
            @Override
            public void onResponse(Call<List<App>> call, Response<List<App>> response) {
                updateApp = response.body();
                recyclerUpdaterApp.setAdapter(new NewAppAdapter(getActivity(), updateApp));

            }

            @Override
            public void onFailure(Call<List<App>> call, Throwable t) {

            }
        });
    }
}
