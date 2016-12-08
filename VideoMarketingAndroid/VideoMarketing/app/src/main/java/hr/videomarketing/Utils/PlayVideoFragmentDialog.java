package hr.videomarketing.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import hr.videomarketing.R;

import static hr.videomarketing.VideoMarketingApp.PROVIDER;

/**
 * Created by bagy on 5.12.2016..
 */

public class PlayVideoFragmentDialog extends DialogFragment implements DialogInterface.OnClickListener{
    public interface PlayVideoFragmentDialogInterface{
        void onOKClick();
    }

    CheckBox checkBox;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_alert_dialog_for_video_seek,null);
        RelativeLayout header = (RelativeLayout) view.findViewById(R.id.dialogHeader);
        header.setBackgroundColor(getResources().getColor(PROVIDER.getColors().getDarkToolbarColor()));
        ImageView icon = (ImageView)view.findViewById(R.id.ivAppLogoDialogHeader);
        icon.setImageResource(PROVIDER.getLogos().getAppLogoResId());
        checkBox = (CheckBox) view.findViewById(R.id.cbDoNotShowDialog);
        builder.setView(view);

        builder.setPositiveButton(getResources().getString(R.string.alert_dialog_ok),this);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if(checkBox.isChecked()){
            DialogStateAdapter.saveStatus(getActivity(),false);
        }else{
            DialogStateAdapter.saveStatus(getActivity(),true);
        }
        if(isVisible()){
            dismiss();
        }
    }
}
