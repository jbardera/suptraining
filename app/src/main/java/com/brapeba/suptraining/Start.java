
/* Icons from Google Material Design at https://github.com/google/material-design-icons/blob/master/README.md
licensed under CC-BY see http://creativecommons.org/licenses/by/4.0/
*/

package com.brapeba.suptraining;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.Time;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Start extends AppCompatActivity
{
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private final int NUM_TABS=4;
    private String[] TABS;
    private int[] imageResId;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.starta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState==null)
        {
            // ... was not rotated -> initializing vars (new Session)
            Activity myActivity=this;
            newSession(this);
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        TABS=new String[]{getString(R.string.tab1),getString(R.string.tab2),getString(R.string.tab3),getString(R.string.tab4)};
        imageResId = new  int[] {
                R.drawable.ic_create_white_36dp,
                R.drawable.ic_storage_white_36dp,
                R.drawable.ic_equalizer_white_36dp,
                R.drawable.ic_settings_white_36dp
        };
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position)
            {
                case 0: // Fragment #0
                    return StartF.init(position);
                case 1: // Fragment #1
                    return SessionsListF.init(position);
                //return SavedF.init(position);
                case 2: // Fragment #2
                    return PlaceholderFragment.newInstance(position + 1);
                case 3:
                    return ConfigF.init(position);
                default: // should never happen!
                    return EmptyF.init(position);
            }

        }

        @Override
        public int getCount()
        {
            // Show 3 total pages.
            return NUM_TABS;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
                Drawable image = getResources().getDrawable(imageResId[position]);
                image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
                SpannableString sb = new SpannableString(" ");
                ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
                sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                return sb;
                //return TABS[position];
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
    {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber)
        {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.genericf, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    @Override public void onDestroy()
    {
        super.onDestroy();
    }

    static public void newSession(Activity activity)
    {
        Constants.table=new ArrayList<Element>();
        Constants.table.add(new Element(activity.getString(R.string.element1), 1)); //"1"=code for this new element
        Constants.listElementsData.put(1, new ElementsData(activity.getString(R.string.units), 50)); //setting "1" as code to match the element added above
        Constants.table.add(new Element(activity.getString(R.string.element2), 2));
        Constants.listElementsData.put(2, new ElementsData(activity.getString(R.string.units), 100));
        Constants.table.add(new Element(activity.getString(R.string.element3), 3));
        Constants.listElementsData.put(3, new ElementsData(activity.getString(R.string.units), 120));
        Constants.session = new Session(activity.getString(R.string.string2), Constants.table);
        //reading custom elements
    }
}
