package com.sidert.sidertmovil.v2.di.modules;

import com.sidert.sidertmovil.utils.SessionManager;
import com.sidert.sidertmovil.v2.remote.auth.LoginRemoteAuth;
import com.sidert.sidertmovil.v2.remote.datasource.CarteraRemoteDatasource;
import com.sidert.sidertmovil.v2.remote.datasource.CatalogosRemoteDatasource;
import com.sidert.sidertmovil.v2.remote.datasource.DocumentoRemoteDatasource;
import com.sidert.sidertmovil.v2.remote.datasource.EstadosRemoteDatasource;
import com.sidert.sidertmovil.v2.remote.datasource.ImpresionesRemoteDatasource;
import com.sidert.sidertmovil.v2.remote.datasource.PrestamoRemoteDatasource;
import com.sidert.sidertmovil.v2.remote.datasource.PrestamosPorRenovarRemoteDatasource;
import com.sidert.sidertmovil.v2.remote.datasource.ReciboAgfCcRemoteDatasource;
import com.sidert.sidertmovil.v2.remote.datasource.ReciboCcRemoteDatasource;
import com.sidert.sidertmovil.v2.remote.datasource.SolicitudesRemoteDatasource;
import com.sidert.sidertmovil.v2.remote.datasource.VerificacionDomiciliariaRemoteDatasource;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static okhttp3.logging.HttpLoggingInterceptor.Level;

@Module
public interface NetworkModule {

    @Provides
    @Singleton
    static Retrofit provideOkHttpClient(@Named("baseUrl") String baseUrl, SessionManager sessionManager) {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message));
        httpLoggingInterceptor.setLevel(Level.BASIC);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofMinutes(10))
                .readTimeout(Duration.ofMinutes(10))
                .writeTimeout(Duration.ofMinutes(10))
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(chain -> {
                    String token = sessionManager.getUser().get(7);
                    Request.Builder builder = chain.request().newBuilder();
                    if (token != null) {
                        builder.addHeader("Authorization", String.format("Bearer %s", token));
                    }
                    Request request = builder.build();
                    return chain.proceed(request);
                })
                .build();

        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    @Provides
    @Singleton
    static EstadosRemoteDatasource provideEstadosRemoteDatasource(Retrofit retrofit) {
        return retrofit.create(EstadosRemoteDatasource.class);
    }

    @Provides
    @Singleton
    static LoginRemoteAuth provideLoginRemoteAuth(Retrofit retrofit) {
        return retrofit.create(LoginRemoteAuth.class);
    }

    @Provides
    @Singleton
    static PrestamoRemoteDatasource providePrestamoService(Retrofit retrofit) {
        return retrofit.create(PrestamoRemoteDatasource.class);
    }

    @Provides
    @Singleton
    static PrestamosPorRenovarRemoteDatasource providePrestamosPorRenovarService(Retrofit retrofit) {
        return retrofit.create(PrestamosPorRenovarRemoteDatasource.class);
    }

    @Provides
    @Singleton
    static ReciboAgfCcRemoteDatasource provideReciboService(Retrofit retrofit) {
        return retrofit.create(ReciboAgfCcRemoteDatasource.class);
    }

    @Provides
    @Singleton
    static ReciboCcRemoteDatasource provideReciboCcService(Retrofit retrofit) {
        return retrofit.create(ReciboCcRemoteDatasource.class);
    }

    @Provides
    @Singleton
    static CatalogosRemoteDatasource provideCatalogosRemoteDatasource(Retrofit retrofit) {
        return retrofit.create(CatalogosRemoteDatasource.class);
    }

    @Provides
    @Singleton
    static SolicitudesRemoteDatasource provideSolicitudesRemoteDatasource(Retrofit retrofit) {
        return retrofit.create(SolicitudesRemoteDatasource.class);
    }

    @Provides
    @Singleton
    static ImpresionesRemoteDatasource provideImpresionesRemoteDatasource(Retrofit retrofit) {
        return retrofit.create(ImpresionesRemoteDatasource.class);
    }

    @Provides
    @Singleton
    static DocumentoRemoteDatasource provideDocumentoRemoteDatasource(Retrofit retrofit) {
        return retrofit.create(DocumentoRemoteDatasource.class);
    }

    @Provides
    @Singleton
    static VerificacionDomiciliariaRemoteDatasource provideVerificacionDomiciliariaRemoteDatasource(Retrofit retrofit) {
        return retrofit.create(VerificacionDomiciliariaRemoteDatasource.class);
    }

    @Provides
    @Singleton
    static CarteraRemoteDatasource provideCarteraRemoteDatasource(Retrofit retrofit) {
        return retrofit.create(CarteraRemoteDatasource.class);
    }

}
