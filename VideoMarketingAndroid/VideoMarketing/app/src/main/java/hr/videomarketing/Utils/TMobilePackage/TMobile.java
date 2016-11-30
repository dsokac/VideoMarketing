package hr.videomarketing.Utils.TMobilePackage;

import hr.videomarketing.Utils.ProviderColors;
import hr.videomarketing.Utils.ProviderFactory;
import hr.videomarketing.Utils.ProviderLogo;

/**
 * Created by bagy on 24.11.2016..
 */

public class TMobile extends ProviderFactory {
    public final static String id = "21901";

    @Override
    public String getCode() {
        return id;
    }

    @Override
    public ProviderColors getColors() {
        return new TMobileColors();
    }

    @Override
    public ProviderLogo getLogos() {
        return new TMobileLogos();
    }

    @Override
    public int getButtonColors() {
        return new TMobileColors().getToolbarColor();
    }
}
