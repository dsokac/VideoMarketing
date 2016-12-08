package hr.videomarketing.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hr.videomarketing.R;

import static hr.videomarketing.VideoMarketingApp.PROVIDER;

/**
 * Created by bagy on 6.12.2016..
 */

public class VideoMarketingAppDialog extends DialogFragment {
    private CheckBox checkBox;
    private TextView text;
    private TextView tvTitle;
    private RelativeLayout header;
    private RelativeLayout content;
    private ImageView headerIcon;
    private int visibility;
    private String title;
    private String textToShow;


    private String postivieButtonText="";
    private String negativeButtonText = "";

    DialogInterface.OnClickListener positiveButton=null;
    DialogInterface.OnClickListener negativeButton=null;

    public VideoMarketingAppDialog(){
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_alert_dialog_for_video_seek,null);
        header = (RelativeLayout) view.findViewById(R.id.dialogHeader);
        header.setBackgroundColor(getResources().getColor(PROVIDER.getColors().getDarkToolbarColor()));
        headerIcon = (ImageView)view.findViewById(R.id.ivAppLogoDialogHeader);
        headerIcon.setImageResource(PROVIDER.getLogos().getAppLogoResId());
        tvTitle = (TextView)view.findViewById(R.id.tvLabelHeader);
        tvTitle.setText(title);
        text = (TextView)view.findViewById(R.id.tvContentDialog);
        text.setText(textToShow);
        checkBox = (CheckBox) view.findViewById(R.id.cbDoNotShowDialog);
        if(visibility != 0)checkBox.setVisibility(visibility);
        if(positiveButton != null)builder.setPositiveButton(postivieButtonText,positiveButton);
        if(negativeButton != null)builder.setNegativeButton(negativeButtonText,negativeButton);

        builder.setView(view);
        return builder.create();
    }

    public void setText(String s){
        this.textToShow = s;
    }

    public void setNegativeButton(DialogInterface.OnClickListener negativeButton) {
        this.negativeButton = negativeButton;
    }


    public void setPostivieButtonText(String postivieButtonText) {
        this.postivieButtonText = postivieButtonText;
    }

    public void setNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
    }
    public void setPositiveButton(DialogInterface.OnClickListener positiveButton) {
        this.positiveButton = positiveButton;
    }
    public void setCheckBoxVisibility(int visibility){
        this.visibility = visibility;
    }
    public void setTitle(String s){
        this.title = s;
    }
    public boolean getCheckedStatus(){
        return checkBox != null?checkBox.isChecked():false;
    }

    public boolean isCheckBoxVisible() {
        return visibility == View.VISIBLE?true:false;
    }
    public void cancel(){
        this.dismiss();
    }
}
