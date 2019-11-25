package patryk.json.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import patryk.json.R;
import patryk.json.fragments.InsuranceFragment;
import patryk.json.fragments.ServicesFragment;


public class CustomPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES =
            new int[]{R.string.insurance, R.string.car_service};
    private final Context mContext;

    public CustomPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                return InsuranceFragment.newInstance("InsuranceFragment, 1");
            case 1:
                return ServicesFragment.newInstance("ServicesFragment, 2");
            default:
                return InsuranceFragment.newInstance("InsuranceFragment, Default");
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}