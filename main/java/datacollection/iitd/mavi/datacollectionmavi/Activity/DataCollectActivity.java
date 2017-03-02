package datacollection.iitd.mavi.datacollectionmavi.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import datacollection.iitd.mavi.datacollectionmavi.Activity.LoginActivity;
import datacollection.iitd.mavi.datacollectionmavi.Fragment.DataFormFragment;
import datacollection.iitd.mavi.datacollectionmavi.Fragment.DataListFragment;
import datacollection.iitd.mavi.datacollectionmavi.Fragment.PopUpDialogFragment;
import datacollection.iitd.mavi.datacollectionmavi.Model.SignBoard;
import datacollection.iitd.mavi.datacollectionmavi.R;
import datacollection.iitd.mavi.datacollectionmavi.dummy.DummyContent;

public class DataCollectActivity extends AppCompatActivity implements DataFormFragment.OnFragmentInteractionListener, DataListFragment.OnListFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private  DataFormFragment mdataFormFragment;
    private DataListFragment mdataListFragment;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collect);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_collect, menu);
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
        if(id==R.id.logout)
        {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isLoggedIn", false); // Storing boolean - true/false
            editor.commit();

            startActivity( new Intent(this, LoginActivity.class));
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction() {
        mdataListFragment.reLoadListData();

    }

    @Override
    public void onListFragmentInteraction(SignBoard item) {

        PopUpDialogFragment popUpDialogFragment  = PopUpDialogFragment.newInstance(item);

                FragmentManager fragmentManager = getSupportFragmentManager();

        /** Starting a FragmentTransaction */
       FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        popUpDialogFragment.show(fragmentManager,"asd");


    }

    /**
     * A placeholder fragment containing a simple view.
     */


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            if(position==0)
            {
                mdataFormFragment=DataFormFragment.newInstance();
                return  mdataFormFragment;

            }
            else
            {
                mdataListFragment =  DataListFragment.newInstance();
                return  mdataListFragment;

            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Form";
                case 1:
                    return "Data Stored";
            }
            return null;
        }
    }
}
