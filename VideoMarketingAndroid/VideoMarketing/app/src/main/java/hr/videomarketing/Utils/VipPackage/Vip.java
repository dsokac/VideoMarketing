package hr.videomarketing.Utils.VipPackage;

import hr.videomarketing.Utils.ProviderColors;
import hr.videomarketing.Utils.ProviderFactory;
import hr.videomarketing.Utils.ProviderLogo;

/**
 * Created by bagy on 24.11.2016..
 */

public class Vip extends ProviderFactory {
    public final static String id = "21910";

    @Override
    public String getCode() {
        return id;
    }

    @Override
    public ProviderColors getColors() {
        return new VipColors();
    }

    @Override
    public ProviderLogo getLogos() {
        return new VipLogos();
    }

    @Override
    public int getButtonColors() {
        return new VipColors().getBottomBarColor();
    }
}
