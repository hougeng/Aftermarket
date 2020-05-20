package com.aftermarket.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.aftermarket.android.fgment.AddressFragment;
import com.aftermarket.android.fgment.MineFragment;
import com.aftermarket.android.fgment.OrdersFragment;
import com.aftermarket.android.fgment.QrcodeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG="MainActivity";
    private ViewPager viewPager;
    private LinearLayout mTabMine;
    private LinearLayout mTabAddress;
    private LinearLayout mTabOrders;
    private LinearLayout mTabQrcode;
    private ImageButton btnAddress,btnOrders,btnQrcode,btnMine;
    private PagerAdapter mPagerAdapter;
    private List<View> mViews = new ArrayList<View>();

    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate: "+getClass().getSimpleName());
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); //隐藏标题栏
        /*子线程跳主线程刷新UI*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: 切换Fragment" );
                resetImg();
                btnAddress = (ImageButton) findViewById(R.id.tab_address_img);
                btnAddress.setImageResource(R.drawable.location_press);
                replaceAddressFragment(new AddressFragment());
            }
        }).start();
        initViews();
        initEvents();
    }

    /*滑动点击事件*/
    private void initEvents() {
        mTabAddress.setOnClickListener(this);
        mTabOrders.setOnClickListener(this);
        mTabQrcode.setOnClickListener(this);
        mTabMine.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            /*滑动点击事件*/
            @Override
            public void onPageSelected(int position) {
                int currentItem = viewPager.getCurrentItem();
                resetImg();
                switch (currentItem) {
                    case 0:
                        btnAddress.setImageResource(R.drawable.location_press);
                        replaceAddressFragment(new AddressFragment());
                        Log.e(TAG, "onPageSelected: 滑动地址（主页）");
                        break;
                    case 1:
                        btnOrders.setImageResource(R.drawable.orders_press);
                        replaceAddressFragment(new OrdersFragment());
                        Log.e(TAG, "onPageSelected: 滑动工单（主页）");
                        break;
                    case 2:
                        btnQrcode.setImageResource(R.drawable.qccode_press);
                        replaceAddressFragment(new QrcodeFragment());
                        Log.e(TAG, "onPageSelected: 滑动二维码（主页）");
                        break;
                    case 3:
                        btnMine.setImageResource(R.drawable.wo_press);
                        replaceAddressFragment(new MineFragment());
                        Log.e(TAG, "onPageSelected: 滑动工单（主页）");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.ViewPager);
        mTabMine = (LinearLayout) findViewById(R.id.tab_mine);
        mTabAddress = (LinearLayout) findViewById(R.id.tab_address);
        mTabOrders = (LinearLayout) findViewById(R.id.tab_orders);
        mTabQrcode = (LinearLayout) findViewById(R.id.tab_qrcode);

        btnAddress = (ImageButton) findViewById(R.id.tab_address_img);
        btnOrders = (ImageButton) findViewById(R.id.tab_orders_img);
        btnQrcode = (ImageButton) findViewById(R.id.tab_qrcode_img);
        btnMine = (ImageButton) findViewById(R.id.tab_mine_img);

        LayoutInflater inflater = LayoutInflater.from(this);

        View tabAddress = inflater.inflate(R.layout.fragment_address,null);
        View tabOrders = inflater.inflate(R.layout.fragment_orders,null);
        View tabQrcode = inflater.inflate(R.layout.fragment_qrcode,null);
        View tabMine = inflater.inflate(R.layout.fragment_mine,null);

        mViews.add(tabAddress);
        mViews.add(tabOrders);
        mViews.add(tabQrcode);
        mViews.add(tabMine);

        /*Fragment适配器*/
        mPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                Log.e(TAG, "instantiateItem: view" );
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(mViews.get(position));
            }
        };
        viewPager.setAdapter(mPagerAdapter);
    }

    /*点击翻页*/

    public void onClick(View view) {
        resetImg();
        switch (view.getId()){
            case R.id.tab_address:
                viewPager.setCurrentItem(0);
                btnAddress.setImageResource(R.drawable.location_press);
                replaceAddressFragment(new AddressFragment());
                Log.e(TAG, "onClick: 点击首页" );
                break;
            case R.id.tab_orders:
                viewPager.setCurrentItem(1);
                btnOrders.setImageResource(R.drawable.orders_press);
                replaceAddressFragment(new OrdersFragment());
                Log.e(TAG, "onClick: 点击工单" );
                break;
            case R.id.tab_qrcode:
                viewPager.setCurrentItem(2);
                btnQrcode.setImageResource(R.drawable.qccode_press);
                replaceAddressFragment(new QrcodeFragment());
                Log.e(TAG, "onClick: 点击二维码" );
                break;
            case R.id.tab_mine:
                viewPager.setCurrentItem(3);
                btnMine.setImageResource(R.drawable.wo_press);
                replaceAddressFragment(new MineFragment());
                Log.e(TAG, "onClick: 点击我的" );
                break;
            default:
                break;
        }
    }

    /*将图标重置为暗色*/

    private void resetImg() {
        btnAddress.setImageResource(R.drawable.location);
        btnMine.setImageResource(R.drawable.wo);
        btnQrcode.setImageResource(R.drawable.qccode);
        btnOrders.setImageResource(R.drawable.orders);
    }

    /*动态切换Fragment*/

    private void replaceAddressFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.orders_layout,fragment);
        transaction.commit();
    }

}
