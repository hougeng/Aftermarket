package com.aftermarket.android.fgment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aftermarket.android.MainActivity;
import com.aftermarket.android.R;
import com.aftermarket.android.util.DialogLogout;
import com.aftermarket.android.util.PictureTypeEntity;
import com.mylhyl.circledialog.BaseCircleDialog;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.callback.ConfigSubTitle;
import com.mylhyl.circledialog.callback.ConfigText;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.params.SubTitleParams;
import com.mylhyl.circledialog.params.TextParams;
import com.mylhyl.circledialog.res.values.CircleDimen;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG="MineFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private CircleDialog.Builder builder;
    private BaseCircleDialog dialogFragment;
    static {
        CircleDimen.DIALOG_RADIUS = 8;
        //CircleColor.
    }

    public MineFragment() {
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
        if (getActivity()== null )
            return;
        Button btn_logout = (Button) getActivity().findViewById(R.id.logout);
        Button btn_about = (Button) getActivity().findViewById(R.id.btn_about);
        Button btn_picture = (Button) getActivity().findViewById(R.id.but_picture);
        Button btn_add_user_message = (Button) getActivity().findViewById(R.id.btn_add_user_message);
        Button btn_user_response = (Button) getActivity().findViewById(R.id.btn_user_response);

        btn_add_user_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CircleDialog.Builder builder = new CircleDialog.Builder();
                builder.setTitle("个人信息增改")
                        .setSubTitle("输入需要修改的内容，点击提交即可修改信息")
                        .configSubTitle(params -> params.isShowBottomDivider = true)
                        .setBodyView(R.layout.dialog_login, viewHolder -> {
                            EditText etUser = viewHolder.findViewById(R.id.login_et_user);
                            EditText etPwd = viewHolder.findViewById(R.id.login_et_pwd);

                            viewHolder.addTextChangedListener(etUser, (s, start, before, count) ->
                                    builder.setPositiveDisable(
                                            TextUtils.isEmpty(etUser.getText()) || TextUtils.isEmpty(etPwd.getText())
                                    ).refresh()
                            );
                            viewHolder.addTextChangedListener(etPwd, (s, start, before, count) -> {
                                // 测试长度为3自动关闭
                                if (s.length() == 20) {
                                    builder.dismiss();
                                } else {
                                    builder.setPositiveDisable(
                                            TextUtils.isEmpty(etUser.getText()) || TextUtils.isEmpty(etPwd.getText())
                                    ).refresh();
                                }
                            });
                        })
                        .setPositiveDisable(true)
                        .setPositiveBody("提交", viewHolder -> {
                            EditText etUser = viewHolder.findViewById(R.id.login_et_user);
                            EditText etPwd = viewHolder.findViewById(R.id.login_et_pwd);
                            TextView tvError = viewHolder.findViewById(R.id.login_tv_error);

                            if ("hougeng".equals(etUser.getText().toString()) &&
                                    "广东海洋大学".equals(etPwd.getText().toString())) {
                                Toast.makeText(getContext(),"修改成功",Toast.LENGTH_LONG).show();
                                return true;
                            }
                            tvError.setText("信息输入有误/连接不上服务器");
                            return false;
                        })
                        .setNegative("取消", null)
                        .show(getChildFragmentManager());
            }
        });

        btn_user_response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment = new CircleDialog.Builder()
                        //.setTypeface(typeface)
                        .setCanceledOnTouchOutside(false)
                        .setCancelable(true)
                        .setTitle("反馈信息")
                        .setInputHint("反馈或者投诉内容，请写上您的具体问题和电话号码，会有专人负责")
                        .setInputHeight(300)
                        .setInputShowKeyboard(true)
                        .setInputEmoji(true)
                        .setInputCounter(100)
//                        .setInputCounter(20, (maxLen, currentLen) -> maxLen - currentLen + "/" + maxLen)
                        .configInput(params -> {
                            params.styleText = Typeface.BOLD;
                        })
                        .setNegative("取消", null)
                        .setPositiveInput("确定", (text, v) -> {
                            if (TextUtils.isEmpty(text)) {
                                Toast.makeText(getContext(), "请输入内容", Toast.LENGTH_SHORT).show();
                                return false;
                            } else {
                                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        })
                        .show(getChildFragmentManager());
            }
        });

        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final List<PictureTypeEntity> items = new ArrayList<>();
                items.add(new PictureTypeEntity(1,"拍照"));
                items.add(new PictureTypeEntity(2,"从相册选择"));
                new CircleDialog.Builder()
                        .configDialog(new ConfigDialog() {
                            @Override
                            public void onConfig(DialogParams params) {
                                params.backgroundColorPress = Color.CYAN;
                                //增加弹出动画
                                params.animStyle = R.style.dialogWindowAnim;
                            }
                        })
                        .setSubTitle("请从以下中选择照片的方式进行提交")
                        .setItems(items, (parent, view1, position1, id) -> {
                            Toast.makeText(getContext(),"点击了"+items.get(position1),Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                        )
                        .setNegative("取消",null)
                        .show(getChildFragmentManager());
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: 退出登陆");
                DialogLogout.getInstance().show(getFragmentManager(), "DialogLogout");
            }
        });

        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: 关于我们");
                new CircleDialog.Builder()
                        .setMaxHeight(0.8f)
                        .setCanceledOnTouchOutside(true)
                        .setCancelable(false)
                        .configDialog(new ConfigDialog() {
                            @Override
                            public void onConfig(DialogParams params) {

                            }
                        })
                        .setTitle("关于我们")
                        .setSubTitle("2020-06-01")
                        .configSubTitle(new ConfigSubTitle() {
                            @Override
                            public void onConfig(SubTitleParams params) {
                                params.isShowBottomDivider = true;
                            }
                        })
                        .setText("  伴随着智能化技术的不断升级完善，越来越多传统服务业也加入智能产业市场的潮流中。售后服务，是新时期企业做大做强的关键点之一。\n" +
                                "  智能手机的出现和普及，为售后服务带来新的机遇和挑战。客户在享受线上购物的方便快捷的同时，也迫切需要线下的及时维护服务。而市场上的售后服务一般是电话联系修理或寻找客服再转专人来修理，既浪费时间，也容易与维护人员出现误会等。利用云计算平台，设计基于Android的云售后服务系统，可以让客户快速提交维修单，查看维修进度，让维修工能第一时间进行接单沟通，实现售后O2O，让客户真正体验线上售后的便利。\n" +
                                "  Android云售后服务系统的设计围绕着简洁、方便易用的原则，以Android技术为核心，充分利用其交互性能，尽可能优化用户体验，提高软件的流畅度，实现用户保修，维修人员定位签到，二维码的扫码维护，二维码的生成打印等功能。\n")
                        .configText(new ConfigText() {
                            @Override
                            public void onConfig(TextParams params) {
                                params.gravity = Gravity.LEFT |Gravity.TOP;
                            }
                        })
                        .setNegative("取消",null)
                        .setPositive("确定",this)
                        .configPositive(new ConfigButton() {
                            @Override
                            public void onConfig(ButtonParams params) {
//                                Toast.makeText(getContext(),"queding",Toast.LENGTH_SHORT).show();
                                params.backgroundColorPress = Color.RED;
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
        return inflater.inflate(R.layout.fragment_mine,container,false);
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
