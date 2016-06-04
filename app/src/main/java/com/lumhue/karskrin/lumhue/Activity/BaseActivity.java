package com.lumhue.karskrin.lumhue.Activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.lumhue.karskrin.lumhue.Drawer.DrawerItemClickListener;
import com.lumhue.karskrin.lumhue.Drawer.DrawerItemCustomAdapter;
import com.lumhue.karskrin.lumhue.Drawer.ObjectDrawerItem;
import com.lumhue.karskrin.lumhue.R;

public class BaseActivity extends AppCompatActivity {
    protected DrawerLayout mDrawerLayout;
    private String[] mNavigationDrawerItemTitles;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        //TODO butterknife
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[4];

        drawerItem[0] = new ObjectDrawerItem(R.drawable.ic_home, "Home");
        drawerItem[1] = new ObjectDrawerItem(R.drawable.ic_lights, "Lights");
        drawerItem[2] = new ObjectDrawerItem(R.drawable.ic_ambiances, "Ambiances");
        drawerItem[3] = new ObjectDrawerItem(R.drawable.ic_logout, "Logout");

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

    }
}
