package com.sidert.sidertmovil.v2.bussinesmodel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import com.sidert.sidertmovil.Home;
import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.v2.R;
import com.sidert.sidertmovil.v2.SidertMovilApplication;
import com.sidert.sidertmovil.v2.domain.daos.LoginReportDao;
import com.sidert.sidertmovil.v2.domain.entities.LoginReport;
import com.sidert.sidertmovil.v2.ui.LoginActivity;
import com.sidert.sidertmovil.v2.utils.ExecutorUtil;
import com.sidert.sidertmovil.v2.viewmodels.SplashSidertViewModel;

import java.net.NetworkInterface;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.appcompat.app.AppCompatActivity;
import timber.log.Timber;

public class SplashSidertBussinesModel extends BaseBussinesModel {

    private static final String TAG = SplashSidertViewModel.class.getSimpleName();
    private final LoginReportDao loginReportDao;
    private final String androidServiceUrl;
    private final String androidApplicationId;

    @Inject
    public SplashSidertBussinesModel(SidertMovilApplication sidertMovilApplication,
                                     ExecutorUtil executorUtil,
                                     SessionManager sessionManager,
                                     LoginReportDao loginReportDao,
                                     @Named("baseUrl") String androidServiceUrl,
                                     @Named("androidApplicationId") String androidApplicationId) {
        super(sidertMovilApplication, executorUtil, sessionManager);
        this.androidServiceUrl = androidServiceUrl;
        this.loginReportDao = loginReportDao;
        this.androidApplicationId = androidApplicationId;
    }

    @SuppressLint("HardwareIds")
    public void saveMacInSession() {
        try {
            Timber.tag(TAG).e(Build.MODEL);
            String deviceModel = Build.MODEL.trim();
            Resources resources = this.sidertMovilApplication.getResources();
            String[] modelos = resources.getStringArray(R.array.device_models);
            Set<String> models = new HashSet<>(Arrays.asList(modelos));

            String contentResolver = null;
            if (models.contains(deviceModel)) {
                contentResolver = androidApplicationId;
            } else {
                List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());

                for (NetworkInterface nif : all) {
                    if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                    byte[] macBytes = nif.getHardwareAddress();

                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(Integer.toHexString(b & 0xFF)).append(":");
                    }

                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }

                    String[] mac = res1.toString().toUpperCase().split(":");

                    for (int i = 0; i < mac.length; i++) {
                        if (mac[i].length() == 1)
                            mac[i] = "0" + mac[i];
                    }

                    String newMacAddress = mac[0] + ":" + mac[1] + ":" + mac[2] + ":" + mac[3] + ":" + mac[4] + ":" + mac[5];
                    Timber.tag("MAC NIF ADDRESS").e(newMacAddress);

                    //Se guarda la MacAddress en variable de sesion
                    contentResolver = newMacAddress.toUpperCase();
                }
            }
            Timber.tag(TAG).e(contentResolver);
            sessionManager.setAddress(contentResolver);

        } catch (Exception ex) {
            Timber.tag(TAG).e(ex);
        }
    }

    public void removeUserIfSessionHasMoreThanOneDay(AppCompatActivity activity) {
        sessionManager.setDominio(androidServiceUrl);
        Timber.tag(TAG).i("Nueva URL: %s", androidServiceUrl);
        try {
            Intent intent = new Intent(sidertMovilApplication, LoginActivity.class);
            Optional<LoginReport> optionalLoginReport = this.executorUtil.runTaskInThread(loginReportDao::findOneOrderByCreateAtDesc);

            if (optionalLoginReport.isPresent()) {
                LoginReport loginReport = optionalLoginReport.get();

                String loginTime = loginReport.getLoginTimestamp();
                LocalDateTime lastLoginWithTime = LocalDateTime.parse(loginTime);
                Timber.tag(TAG).i("Last login timestamp, %s", loginTime);

                LocalDate now = LocalDate.now();
                LocalDate lastLogin = lastLoginWithTime.toLocalDate();
                long daysDiff = ChronoUnit.DAYS.between(lastLogin, now);

                Timber.tag(TAG).i("Tiempo sin conexion: %d", daysDiff);

                if (daysDiff > 0) {
                    sessionManager.deleteUser();
                }
                if (sessionManager.getUser().get(0) != null && sessionManager.getUser().get(6).equals("true")) {
                    intent.setClass(sidertMovilApplication, Home.class);
                }
            }

            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> {
                if (activity != null) {
                    activity.startActivity(intent);
                    activity.finish();
                }
            }, 100);

        } catch (TimeoutException | ExecutionException | InterruptedException e) {
            Timber.tag(TAG).e(e);
        }
    }


}
