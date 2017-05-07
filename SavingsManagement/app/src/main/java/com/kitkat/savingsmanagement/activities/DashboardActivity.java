package com.kitkat.savingsmanagement.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kitkat.savingsmanagement.R;
import com.kitkat.savingsmanagement.data.SavingsBean;
import com.kitkat.savingsmanagement.data.SavingsContentProvider;
import com.kitkat.savingsmanagement.utils.Utils;

import java.util.ArrayList;
import java.util.Date;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView mLv_savings;
    private ListViewAdpter mLvAdapter;
    private ArrayList<SavingsBean> mSavingBeanList = new ArrayList<>();
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddSavingsItemScrren();
            }
        });

        FloatingActionButton listBtn = (FloatingActionButton) findViewById(R.id.list);
        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListActivity();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Start add savings item screen
     */
    private void startAddSavingsItemScrren() {
        Intent intent = new Intent(this, AddSavingsItemActivity.class);
        startActivity(intent);
    }

    private void startListActivity() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    private void initViews(){
        mLv_savings = (ListView) findViewById(R.id.lv_savings);
        mProgressBar = (ProgressBar) findViewById(R.id.pb);
        mLv_savings.setEmptyView(mProgressBar);
        mLvAdapter = new ListViewAdpter();
    }

    private void fetchData() {

        if (mSavingBeanList != null){
            mSavingBeanList.clear();
        }

        Cursor cursor = getContentResolver().query(SavingsContentProvider.CONTENT_URI, null, null, null, "_id asc", null);
        while (cursor.moveToNext()){
            SavingsBean savingsBean = new SavingsBean();
            savingsBean.setBankName(cursor.getString(cursor.getColumnIndex("bank_name")));
            savingsBean.setAmount(cursor.getString(cursor.getColumnIndex("amount")));
            savingsBean.setStartDate(cursor.getString(cursor.getColumnIndex("start_date")));
            savingsBean.setEndDate(cursor.getString(cursor.getColumnIndex("end_date")));
            savingsBean.setYield(cursor.getString(cursor.getColumnIndex("yield")));
            savingsBean.setInterest(cursor.getString(cursor.getColumnIndex("interest")));
            mSavingBeanList.add(savingsBean);
        }

        mLv_savings.setAdapter(mLvAdapter);
    }

    class ListViewAdpter extends BaseAdapter{

        @Override
        public int getCount() {
            return mSavingBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return mSavingBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;

            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.item_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.bankName = (TextView) convertView.findViewById(R.id.bank_name);
                viewHolder.amount = (TextView) convertView.findViewById(R.id.amount);
                viewHolder.startTime = (TextView) convertView.findViewById(R.id.start_date);
                viewHolder.endTime = (TextView) convertView.findViewById(R.id.end_date);
                viewHolder.yield = (TextView) convertView.findViewById(R.id.yield);
                viewHolder.interest = (TextView) convertView.findViewById(R.id.interest);
                convertView.setTag(viewHolder);
            } else {
               viewHolder = (ViewHolder) convertView.getTag();
            }

            SavingsBean savingsBean = mSavingBeanList.get(position);
            viewHolder.bankName.setText(savingsBean.getBankName());
            viewHolder.startTime.setText(Utils.formatDate(new Date(Long.parseLong(savingsBean.getStartDate())), "yyyy-MM-DD"));
            viewHolder.endTime.setText(Utils.formatDate(new Date(Long.parseLong(savingsBean.getEndDate())), "yyyy-MM-DD"));
            viewHolder.amount.setText(savingsBean.getAmount());
            viewHolder.yield.setText(savingsBean.getYield());
            viewHolder.interest.setText(savingsBean.getInterest());
            return convertView;
        }

        class ViewHolder{
            TextView bankName;
            TextView amount;
            TextView startTime;
            TextView endTime;
            TextView yield;
            TextView interest;
        }
    }
}
