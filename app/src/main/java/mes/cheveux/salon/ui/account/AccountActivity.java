package mes.cheveux.salon.ui.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import mes.cheveux.salon.R;
import mes.cheveux.salon.ui.CommonPagerAdapter;

import static androidx.fragment.app.FragmentPagerAdapter.*;

public class AccountActivity extends AppCompatActivity {

    TextView loginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ViewPager viewPager = findViewById(R.id.accountVpPager);
        CommonPagerAdapter commonPagerAdapter = new CommonPagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        commonPagerAdapter.addFragment(new LoginFragment());
        commonPagerAdapter.addFragment(new RegisterFragment());
        viewPager.setAdapter(commonPagerAdapter);

    }
}
