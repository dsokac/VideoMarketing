package hr.videomarketing.Utils.TMobilePackage;

import hr.videomarketing.R;
import hr.videomarketing.Utils.ProviderLogo;

/**
 * Created by bagy on 24.11.2016..
 */

public class TMobileLogos implements ProviderLogo {
    @Override
    public int getAppLogoResId() {
        return R.drawable.ic_epp_tmobile_logotip;
    }

    @Override
    public int getProviderLogoResId() {
        return R.drawable.ic_logo_t_rgb;
    }
    @Override
    public int getButtonCheckLogoResId() {
        return R.drawable.btn_registration_cicrle_blue;
    }
}
