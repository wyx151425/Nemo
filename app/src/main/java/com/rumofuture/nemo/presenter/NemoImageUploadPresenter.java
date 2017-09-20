package com.rumofuture.nemo.presenter;

import android.content.Intent;

import com.rumofuture.nemo.app.NemoPresenter;
import com.smile.filechoose.api.ChosenImage;

/**
 * Created by WangZhenqi on 2017/4/14.
 */

public interface NemoImageUploadPresenter extends NemoPresenter {
    void chooseImage();
    void submitChoice(int requestCode, Intent data);
    void setChosenImage(ChosenImage chosenImage);
    void releaseImageChooseManager();
}
