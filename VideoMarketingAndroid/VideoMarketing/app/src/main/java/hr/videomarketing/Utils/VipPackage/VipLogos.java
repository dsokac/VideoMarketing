package hr.videomarketing.Utils.VipPackage;

import hr.videomarketing.R;
import hr.videomarketing.Utils.ProviderLogo;

/**
 * Created by bagy on 24.11.2016..
 */

public class VipLogos implements ProviderLogo {
    @Override
    public int getAppLogoResId() {
        return R.drawable.ic_epp_vip_logotip;
    }

    @Override
    public int getProviderLogoResId() {
        return R.drawable.vip_logo;
    }

    @Override
    public int getButtonCheckLogoResId() {
        return R.drawable.btn_registration_circle_red;
    }
}
