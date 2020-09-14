package com.hly.learn;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;

public class ViewModel {

    private Context mContext;

    public ViewModel(Context context) {
        step.set(1);
        mContext = context;
        updateImageInfo(step.get());
    }

    public ObservableInt step = new ObservableInt();
    public ObservableField<Drawable> rightImageRes = new ObservableField<>();
    public ObservableField<String> rightImageName = new ObservableField<>();
    public ObservableField<String> rightImageDescription = new ObservableField<>();
    public ObservableField<String> textValue = new ObservableField<>();

    /**
     * new Step
     */
    public void nextStep(View view) {
        step.set(step.get() + 1);
        updateImageInfo(step.get());
    }

    /**
     * up step
     */
    public void upStep(View view) {
        step.set(step.get() - 1);
        updateImageInfo(step.get());
    }

    private void updateImageInfo(int step) {
        rightImageRes.set(ModalData.getDrawable(mContext, step));
        rightImageName.set(ModalData.getImageName(mContext, step));
        rightImageDescription.set(ModalData.getImageDes(mContext, step));
        textValue.set(ModalData.getImageName(mContext, step) + "挺好吃的啊");
    }
}
