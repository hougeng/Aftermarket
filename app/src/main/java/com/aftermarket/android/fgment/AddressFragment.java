package com.aftermarket.android.fgment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aftermarket.android.R;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddressFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private MapView mapview = null;
    private View mapLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public AddressFragment() {
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
    public static AddressFragment newInstance(String param1, String param2) {
        AddressFragment fragment = new AddressFragment();
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

    /*创建VIEW*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mapLayout=inflater.inflate(R.layout.fragment_address,container,false);
        mapview = (MapView) mapLayout.findViewById(R.id.mapview);
        TencentMap mTencentMap = mapview.getMap();
//第一次渲染成功的回调
        mTencentMap.setOnMapLoadedCallback(new TencentMap.OnMapLoadedCallback() {
            public void onMapLoaded() {
                //地图正常显示
            }
        });
        return  mapLayout;
    }


    @Override
    public void onDestroy() {
        mapview.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        mapview.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        mapview.onResume();
        super.onResume();
    }

    @Override
    public void onStop() {
        mapview.onStop();
        super.onStop();
    }

//    /**
//     * 方法必须重写
//     * map的生命周期方法
//     */
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        Log.i("sys", "mf onSaveInstanceState");
//        super.onSaveInstanceState(outState);
//        mapview.onSaveInstanceState(outState);
//    }
//

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
