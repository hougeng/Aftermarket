package com.aftermarket.android.fgment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.aftermarket.android.R;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.callback.ConfigSubTitle;
import com.mylhyl.circledialog.callback.ConfigText;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.SubTitleParams;
import com.mylhyl.circledialog.params.TextParams;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public OrdersFragment() {
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
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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
        CheckBox checkBox_getorders = (CheckBox)getActivity().findViewById(R.id.checkbox_getorders);

        checkBox_getorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CircleDialog.Builder()
                        .setMaxHeight(0.8f)
                        .setCanceledOnTouchOutside(true)
                        .setCancelable(false)
                        .configDialog(new ConfigDialog() {
                            @Override
                            public void onConfig(DialogParams params) {

                            }
                        })
                        .setTitle("确认信息！")
                        .setSubTitle("维修地址：广东省肇庆城东区")
                        .configSubTitle(new ConfigSubTitle() {
                            @Override
                            public void onConfig(SubTitleParams params) {
                                params.isShowBottomDivider = true;
                            }
                        })
                        .setText("\n\n  是否接受该订单？\n\n")
                        .configText(new ConfigText() {
                            @Override
                            public void onConfig(TextParams params) {
                                params.gravity = Gravity.CENTER |Gravity.CENTER;
                                params.textColor = Color.BLACK;
                                params.textSize = 30 ;
                            }
                        })
                        .setNegative("取消",v ->
                                checkBox_getorders.setChecked(false))
                        .setPositive("确定",v ->
                                Toast.makeText(getContext(),"你已接受订单，请及时回复客户进行服务",Toast.LENGTH_LONG).show())
                        .configNegative(new ConfigButton() {
                            @Override
                            public void onConfig(ButtonParams params) {
//                                Toast.makeText(getContext(),"queding",Toast.LENGTH_SHORT).show();
                                params.backgroundColor = Color.RED;
                            }
                        })
                        .show(getChildFragmentManager());
            }
        });
    }

    /*创建VIEW*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders,container,false);
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
