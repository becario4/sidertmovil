package com.sidert.sidertmovil;

import com.sidert.sidertmovil.models.dto.BeneficiarioDto;
import com.sidert.sidertmovil.services.beneficiario.BeneficiarioService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class RetrofitTest {

    private Retrofit retrofit;
    private static final String TOKEN_JWT = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXRlcm5vIjoiQUNPU1RBIiwidXNlcl9uYW1lIjoiQVNFU09SMzg5Iiwic2VyaWVpZCI6IjM4OSIsIm5vbWJyZSI6IlZfSVNSQUVMIEFET05BSSIsImF1dGhvcml0aWVzIjpbIlJPTEVfQVNFU09SIl0sImNsaWVudF9pZCI6ImFuZHJvaWRhcHAiLCJtYXRlcm5vIjoiQUxFR1JJQSIsInN1Y3Vyc2FsZXMiOlt7ImlkIjo4LCJub21icmUiOiIxLjggVkVSQUNSVVogVVJCQU5PIiwibGF0aXR1ZCI6IjE5LjIwMjI5OTI3MTkzOTciLCJsb25naXR1ZCI6Ii05Ni4xNjE1Nzk5NTI0MTExNiIsInJlZ2lvbl9pZCI6MSwiY2VudHJvY29zdG9faWQiOjl9XSwibW9kdWxvcyI6W3siaWQiOjIsIm5vbWJyZSI6ImNhcnRlcmEiLCJzdWJtb2R1bG9faWQiOiIxIiwicGVybWlzb3MiOlt7ImlkIjoyLCJub21icmUiOiJFZGl0YXIiLCJtb2R1bG9faWQiOjJ9LHsiaWQiOjMsIm5vbWJyZSI6IkNhbmNlbGFyIiwibW9kdWxvX2lkIjoyfSx7ImlkIjoyMiwibm9tYnJlIjoiVmVyIiwibW9kdWxvX2lkIjoyfSx7ImlkIjoyMywibm9tYnJlIjoiVmVyIiwibW9kdWxvX2lkIjoyfV19LHsiaWQiOjQsIm5vbWJyZSI6ImltcHJlc2lvbiIsInN1Ym1vZHVsb19pZCI6IjMiLCJwZXJtaXNvcyI6W3siaWQiOjgsIm5vbWJyZSI6IlZlciIsIm1vZHVsb19pZCI6NH0seyJpZCI6OSwibm9tYnJlIjoiRWRpdGFyIiwibW9kdWxvX2lkIjo0fSx7ImlkIjoxMCwibm9tYnJlIjoiQ2FuY2VsYXIiLCJtb2R1bG9faWQiOjR9XX0seyJpZCI6Niwibm9tYnJlIjoiZ2VvbG9jYWxpemFyIiwic3VibW9kdWxvX2lkIjoiNCIsInBlcm1pc29zIjpbeyJpZCI6MTQsIm5vbWJyZSI6IlZlciIsIm1vZHVsb19pZCI6Nn0seyJpZCI6MTUsIm5vbWJyZSI6IkVkaXRhciIsIm1vZHVsb19pZCI6Nn0seyJpZCI6MTYsIm5vbWJyZSI6IkNhbmNlbGFyIiwibW9kdWxvX2lkIjo2fV19LHsiaWQiOjEwLCJub21icmUiOiJyZWN1cGVyYWNpb24gYWdmIiwic3VibW9kdWxvX2lkIjoiMTAiLCJwZXJtaXNvcyI6W3siaWQiOjI4LCJub21icmUiOiJWZXIiLCJtb2R1bG9faWQiOjEwfV19LHsiaWQiOjExLCJub21icmUiOiJyZWN1cGVyYWNpb24gY2MiLCJzdWJtb2R1bG9faWQiOiIxMSIsInBlcm1pc29zIjpbeyJpZCI6MjksIm5vbWJyZSI6IlZlciIsIm1vZHVsb19pZCI6MTF9XX0seyJpZCI6MjQsIm5vbWJyZSI6Ik9yaWdpbmFjaW9uIiwic3VibW9kdWxvX2lkIjoiMiIsInBlcm1pc29zIjpbeyJpZCI6MTIwLCJub21icmUiOiJBY2Nlc28iLCJtb2R1bG9faWQiOjI0fV19LHsiaWQiOjI1LCJub21icmUiOiJSZW5vdmFjaW9uIiwic3VibW9kdWxvX2lkIjoiMiIsInBlcm1pc29zIjpbeyJpZCI6MTIxLCJub21icmUiOiJBY2Nlc28iLCJtb2R1bG9faWQiOjI1fV19LHsiaWQiOjMyLCJub21icmUiOiJNZW51cyIsInN1Ym1vZHVsb19pZCI6IjMxIiwicGVybWlzb3MiOlt7ImlkIjoxMzgsIm5vbWJyZSI6IlBlcm1pc28gc3VwZXIgdXN1YXJpbyBzaWRlcnQgbcOzdmlsIiwibW9kdWxvX2lkIjozMn1dfV0sInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJpZCI6NTAsImV4cCI6MTY5NDgxNjE0MywianRpIjoiZDYyZDg4MDEtMjE2NS00ZDgxLTk4YTUtMDEzMjg1MzZkZjc2IiwiZW1haWwiOiJzaWRlcnQuYXNlc29yMzg5QGdtYWlsLmNvbSIsIm1hY19hZGRyZXNzZXMiOltdfQ.rHxOxgf4tj8ZMfkhK76va4G2cmSaVSzZq-hYWyvCbJJXrdD1LZcu371C-uK5vrpsk7nbrQcGzwiscvhFn9rnvYwdQ7E7Do-08wAvMTs1m7Xb-ODtd-4HMY4osP9fW_G1VWBkRiOXt_xulXtDrveJz6Y2Q_smmsN6wnK_Ku3PKltIv7aMqw-oRjN04ZLz38_FZ2GopGUfxskvqjS0MZCBgP_zmL0JodqEQdi_Th9hIweCF0rkVQlSTCINsIz5hCMUEsyOVbrNj_8oHfzu2DSuvn9TxdaurovSlyQo0ufijZjINYkYdlEhoc4YWMq6rA6nMgAbsI_4_i7_wbEbHL6aEQ";


    @Before
    public void setUp() throws Exception {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp[Old]").d(message));
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofMinutes(10))
                .readTimeout(Duration.ofMinutes(10))
                .writeTimeout(Duration.ofMinutes(10))
                .addInterceptor(httpLoggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://localhost:8085/")
                .build();
    }

    @Test
    public void sendBeneficiarioWithRetrofit() {
        BeneficiarioDto beneficiarioDto = new BeneficiarioDto(
                6511L,
                24687,
                1,
                "Wallace",
                "Wright",
                "Lopez",
                "VECINO",
                389
        );

        BeneficiarioService beneficiarioService = retrofit.create(BeneficiarioService.class);
        Call<Map<String, Object>> callingBeneficiario = beneficiarioService.senDataBeneficiario(TOKEN_JWT, beneficiarioDto);

        try {
            Response<Map<String, Object>> response = callingBeneficiario.execute();
            Assert.assertEquals(200, response.code());
            Map<String, Object> body = response.body();
            System.out.println(body);
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test
    public void sendBeneficiarioWithRetrofitBadRequest() {
        BeneficiarioDto beneficiarioDto = new BeneficiarioDto(
                9996511L,
                24687,
                1,
                "Wallace",
                "Wright",
                "Lopez",
                "VECINO",
                389
        );

        BeneficiarioService beneficiarioService = retrofit.create(BeneficiarioService.class);
        Call<Map<String, Object>> callingBeneficiario = beneficiarioService.senDataBeneficiario(TOKEN_JWT, beneficiarioDto);

        try {
            Response<Map<String, Object>> response = callingBeneficiario.execute();
            Assert.assertEquals(400, response.code());
            ResponseBody body = response.errorBody();
            System.out.println(body.string());
        } catch (IOException e) {
            Assert.fail();
        }
    }

}
