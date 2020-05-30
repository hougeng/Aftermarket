package com.aftermarket.android.fgment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aftermarket.android.R;
import com.github.shenyuanqing.zxingsimplify.zxing.Activity.CaptureActivity;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitmapUtils;

import javax.security.auth.login.LoginException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QrcodeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG="QRCodeFragement";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private static final int REQUEST_SCAN = 0;

    public QrcodeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    /*在Fragment的newInstance方法中传递两个参数，并且通过fragment.setArgument保存在它自己身上*/
    public static QrcodeFragment newInstance(String param1, String param2) {
        QrcodeFragment fragment = new QrcodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    /*通过onCreate()调用的时候将这些参数取出来*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity()== null)
            return;
        Button btn_scanQRCode= (Button) getActivity().findViewById(R.id.btn_scan_barcode);
        Button btn_addQRCode= (Button) getActivity().findViewById(R.id.btn_add_qrcode);
        TextView resultTextView = (TextView) getActivity().findViewById(R.id.tv_scan_result);
        final ImageView mImage = (ImageView) getActivity().findViewById(R.id.iv_qr_image);
//        final EditText edit_addQRCode = (EditText) getActivity().findViewById(R.id.et_qr_string);
        final EditText edit_addQRCode1 = (EditText) getActivity().findViewById(R.id.edit_addQRCode1);
        final EditText edit_addQRCode2 = (EditText) getActivity().findViewById(R.id.edit_addQRCode2);
        final EditText edit_addQRCode3 = (EditText) getActivity().findViewById(R.id.edit_addQRCode3);
        final EditText edit_addQRCode4 = (EditText) getActivity().findViewById(R.id.edit_addQRCode4);

        btn_scanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: 点击扫描" );
//                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                 startActivityForResult(new Intent(getActivity(), CaptureActivity.class),REQUEST_SCAN);

            }
        });

        btn_addQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: 点击生成" );
                String edit_addQRCode = "设备名："+edit_addQRCode1.getText().toString()+"\n设备厂商:"+edit_addQRCode2.getText().toString()+"\n安装地址:"+edit_addQRCode3.getText().toString()+"\n电话号码:"+edit_addQRCode4.getText().toString();
                String content = edit_addQRCode.trim();
                Bitmap bitmap =null;
                try {
                    bitmap = BitmapUtils.create2DCode(content);
                    mImage.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

    }
/*接受返回值*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView resultTextView = (TextView) getActivity().findViewById(R.id.tv_scan_result);
        if(requestCode == REQUEST_SCAN && resultCode == RESULT_OK) {
            Toast.makeText(getContext(),data.getStringExtra("barCode"),Toast.LENGTH_LONG).show();
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("barCode");
            resultTextView.setText(scanResult);
        }
    }

    /*创建VIEW*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qrcode,container,false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*Fragment和Activity相关联时调用。可以通过该方法获取Activity引用，还可以通过getArguments()获取参数*/
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener) context;
        }else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void onDetach() {
        super.onDetach();
        mListener = null; //清楚监听器
    }


    /*一个活动加载多个Fragment，所以活动需要继承多个OnFragmentInteractionListener。继承之后需要重写onFragmentInteraction方法*/
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
