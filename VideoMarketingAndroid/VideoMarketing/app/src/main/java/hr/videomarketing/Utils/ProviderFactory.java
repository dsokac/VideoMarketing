package hr.videomarketing.Utils;

/**
 * Created by bagy on 24.11.2016..
 */

public abstract class ProviderFactory {
    public abstract ProviderColors getColors();
    public abstract ProviderLogo getLogos();
    public abstract String getCode();
    public abstract int getButtonColors();
    public abstract String getLink();
    public abstract int backgroundPhotoLeft();
    public abstract int backgroundPhotoRight();
}
