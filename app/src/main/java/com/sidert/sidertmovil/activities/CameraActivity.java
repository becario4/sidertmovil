package com.sidert.sidertmovil.activities;

import static com.sidert.sidertmovil.utils.Constants.PICTURE;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
/*import androidx.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;*/
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.sidert.sidertmovil.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CameraActivity extends AppCompatActivity {

    private TextureView textureView;
    private ImageButton ibCaptura;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private String cameraId;
    CameraDevice cameraDevice;
    CameraCaptureSession cameraCaptureSession;
    CaptureRequest captureRequest;
    CaptureRequest.Builder captureRequestBuiler;

    private Size imageDimensions;
    private ImageReader imageReader;
    private File file;
    Handler mBackgroundHandler;
    HandlerThread mBackgroundThread;

    private byte[] foto_byte = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        textureView = findViewById(R.id.mTexture);
        ibCaptura = findViewById(R.id.ibCaptura);
        assert textureView != null;
        textureView.setSurfaceTextureListener(textureListener);

        ibCaptura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    takePicture();
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101){
            if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                Toast.makeText(getApplicationContext(), "No cuenta con los permisos necesarios", Toast.LENGTH_SHORT).show();
            }
        }

    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            try {
                openCamera();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            cameraDevice = camera;
            try {
                createCameraPreview();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            cameraDevice.close();
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            cameraDevice.close();
            cameraDevice = null;
        }
    };

    private void createCameraPreview() throws CameraAccessException {
        SurfaceTexture texture = textureView.getSurfaceTexture();
        assert  texture != null;
        texture.setDefaultBufferSize(480,640);
        Surface surface = new Surface(texture);

        captureRequestBuiler = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        captureRequestBuiler.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_AE_MODE_ON);
        //captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH);
        captureRequestBuiler.set(CaptureRequest.JPEG_QUALITY, (byte) 90);
        captureRequestBuiler.addTarget(surface);
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        captureRequestBuiler.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));

        cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
            @Override
            public void onConfigured(CameraCaptureSession session) {
                if (cameraDevice == null){
                    return;
                }

                cameraCaptureSession = session;
                try {
                    updatePreview();
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onConfigureFailed(CameraCaptureSession session) {
                Toast.makeText(getApplicationContext(), "Configuracion cambiada", Toast.LENGTH_SHORT).show();
            }
        }, null);
    }

    private void updatePreview() throws CameraAccessException {
        if (cameraDevice == null){
            return;
        }

        captureRequestBuiler.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);

        cameraCaptureSession.setRepeatingRequest(captureRequestBuiler.build(), null, mBackgroundHandler);
    }

    private void openCamera() throws CameraAccessException {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        cameraId = manager.getCameraIdList()[0];
        CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
        StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        assert map != null;
        imageDimensions = map.getOutputSizes(SurfaceTexture.class)[0];

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            return;
        }

        manager.openCamera(cameraId, stateCallback, null);


    }

    private void takePicture () throws CameraAccessException {
        if (cameraDevice == null){
            return;
        }

        ImageReader reader = ImageReader.newInstance(640,480, ImageFormat.JPEG,1);

        /*CameraManager manager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
        CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraDevice.getId());
        Size[] jpegSizes = null;
        jpegSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);

        int width = 640;
        int height = 480;

        if (jpegSizes != null && jpegSizes.length > 0){
            width = jpegSizes[0].getWidth();
            height = jpegSizes[0].getHeight();
        }*/


        List<Surface> outputSurface = new ArrayList<>(2);

        outputSurface.add(reader.getSurface());

        outputSurface.add(new Surface(textureView.getSurfaceTexture()));

        final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

        captureBuilder.addTarget(reader.getSurface());
        captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_AE_MODE_ON);
        captureBuilder.set(CaptureRequest.JPEG_QUALITY, (byte) 90);
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));

        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        //file = new File(Environment.getExternalStorageDirectory()+"/"+ts+".jpg");

        Log.e("ts", ts);

        ImageReader.OnImageAvailableListener readerListener = new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
                Image image = null;

                image = reader.acquireLatestImage();

                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                byte[] bytes = new byte[buffer.capacity()];
                buffer.get(bytes);
                foto_byte = bytes;
                Log.e("size", foto_byte.length+" tamañoo");
               /* try {
                    save(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if (image != null){
                        image.close();
                    }
                }*/

            }
        };

        reader.setOnImageAvailableListener(readerListener, mBackgroundHandler);

        final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {
            @Override
            public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                super.onCaptureCompleted(session, request, result);
                /**envia la captura tomada al almacenamiento del dispositivo*/
              /*  Intent i_result = new Intent();
                i_result.putExtra(PICTURE, foto_byte);
                setResult(RESULT_OK, i_result);*/

                cameraDevice.close();
                finish();
                Toast.makeText(getApplicationContext(), "Save", Toast.LENGTH_SHORT).show();
                try {
                    createCameraPreview();
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        };

        cameraDevice.createCaptureSession(outputSurface, new CameraCaptureSession.StateCallback() {
            @Override
            public void onConfigured(CameraCaptureSession session) {
                try {
                    session.capture(captureRequestBuiler.build(), captureListener, mBackgroundHandler);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onConfigureFailed(CameraCaptureSession session) {

            }
        }, mBackgroundHandler);

    }

    private void save(byte[] bytes) throws IOException {
        OutputStream outputStream = null;
         outputStream = new FileOutputStream(file);
         outputStream.write(bytes);
         outputStream.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundThread();
        if (textureView.isAvailable()){
            try {
                openCamera();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        else{
            textureView.setSurfaceTextureListener(textureListener);
        }
    }

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    @Override
    protected void onPause() {

            cameraDevice.close();
            stopBackgroundThread();

        super.onPause();
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try{
            mBackgroundThread.join();
            mBackgroundThread= null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
