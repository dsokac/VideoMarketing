package hr.videomarketing.Utils;

import hr.videomarketing.Utils.TMobilePackage.TMobile;
import hr.videomarketing.Utils.VipPackage.Vip;


/**
 * Created by bagy on 24.11.2016..
 */

public class ProviderProducer {
    public static ProviderFactory getProvider(String operator){
        switch (operator){
            case TMobile.id:
                return new TMobile();
            /*case TMobile.id:
                return new TMobile();*/
            default:
                return new Vip();
        }
    }
}
