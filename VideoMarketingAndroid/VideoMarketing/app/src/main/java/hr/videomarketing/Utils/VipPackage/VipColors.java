package hr.videomarketing.Utils.VipPackage;

import hr.videomarketing.R;
import hr.videomarketing.Utils.ProviderColors;

/**
 * Created by bagy on 24.11.2016..
 */

public class VipColors implements ProviderColors {

    @Override
    public int getToolbarColor() {
        return R.color.color_vip_toolbar;
    }

    @Override
    public int getBottomBarColor() {
        return R.color.color_vip_bottom;
    }

    @Override
    public int getDarkToolbarColor() {
        return  R.color.color_vip_dark_red;
    }

    @Override
    public int getLinesColor() {
        return R.color.color_vip_bottom;
    }
}
