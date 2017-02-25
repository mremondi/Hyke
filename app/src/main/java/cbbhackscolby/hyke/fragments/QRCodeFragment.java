package cbbhackscolby.hyke.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cbbhackscolby.hyke.R;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;

import java.util.List;

/**
 * Created by mremondi on 2/24/17.
 */

public class QRCodeFragment extends Fragment{

    public static final String TITLE = "QR Reader";
    private String lastText;
    private View rootView;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if(result.getText() == null || result.getText().equals(lastText)) {
                // Prevent duplicate scans
                return;
            }

            lastText = result.getText();

            //Added preview of scanned barcode
            ImageView imageView = (ImageView) rootView.findViewById(R.id.barcodePreview);
            imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.qr_fragment, null, false);


        TextView tvCodeInfo = (TextView) rootView.findViewById(R.id.code_info);
        tvCodeInfo.setText(lastText);

        return rootView;
    }
}
