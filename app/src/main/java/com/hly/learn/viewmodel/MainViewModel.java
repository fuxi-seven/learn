package com.hly.learn.viewmodel;

import android.view.View;

import com.hly.learn.R;
import com.hly.learn.data.ImageDepository;
import com.hly.learn.util.ImageBean;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private ImageDepository mImageDepository;

    public MainViewModel() {
        mImageDepository = new ImageDepository();
        step.setValue(1);
        getImage(step.getValue());
    }

    public MutableLiveData<Integer> step = new MutableLiveData<>();
    public MutableLiveData<String> imageUrl = new MutableLiveData<>();
    public MutableLiveData<String> imageDescription = new MutableLiveData<>();

    /**
     * on click
     * @param view View
     */
    public void onClick(View view) {
        if (view.getId() == R.id.up) {
            step.setValue(step.getValue() - 1);
        } else if (view.getId() == R.id.down) {
            step.setValue(step.getValue() + 1);
        }
        getImage(step.getValue());
    }

    private void getImage(int step) {
        Observable<ImageBean> observable = mImageDepository.getImage("js", step, 1);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ImageBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ImageBean imageBean) {
                        List<ImageBean.ImagesBean> imagesBeans = imageBean.getImages();
                        ImageBean.ImagesBean imagesBean = imagesBeans.get(0);
                        String url = ImageBean.ImagesBean.BASE_URL + imagesBean.getUrl();
                        String des = imagesBean.getCopyright();
                        imageUrl.setValue(url);
                        imageDescription.setValue(des);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
