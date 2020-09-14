package com.hly.learn.fragments;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hly.learn.R;
import com.hly.learn.util.RetrofitApi;
import com.hly.learn.util.Translation;
import com.hly.learn.util.Translation1;
import com.hly.learn.util.Translation2;
import com.hly.learn.util.WeatherInfo;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxJavaFragment extends BaseFragment {

    private TextView clickTxt;
    private TextView dspTxt;
    private TextView rrTxt;
    private TextView merTxt;
    private TextView zipTxt;
    private Button clickBtn;
    private EditText editTxt;
    private TextView resultTxt;

    String s = "你好";
    String result = "数据源来自 = ";

    @Override
    public int getLayoutId() {
        return R.layout.rxjava_layout;
    }

    @Override
    public void initData(View view) {
        clickTxt = view.findViewById(R.id.click_txt);
        clickTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeObserve();
                executeTranslation();
                mergeOperation();
                zipOperation();
                retryOperation();
            }
        });
        dspTxt = view.findViewById(R.id.display_txt);
        rrTxt = view.findViewById(R.id.retrofit_txt);
        merTxt = view.findViewById(R.id.merge_txt);
        zipTxt = view.findViewById(R.id.zip_txt);
        clickBtn = view.findViewById(R.id.click_btn);
        throttleOperation();
        editTxt = view.findViewById(R.id.edit_txt);
        resultTxt = view.findViewById(R.id.result_txt);
        debounceOperation();
    }

    private void setDisplayTxt(String txt) {
        dspTxt.setText(txt);
    }

    private void setRetrofitTxt(String txt) {
        rrTxt.setText(txt);
    }

    private void setMergeTxt(String txt) {
        merTxt.setText(txt);
    }

    private void setZipTxt(String txt) {
        zipTxt.setText(txt);
    }

    private void executeObserve() {
        //以下两种方式可以代替e.onNext("你好");e.onNext("RxJava");e.onNext("今天学习一下RxJava");
        //1.Observable observable = Observable.just("你好","RxJava","今天学习一下RxJava");
        //String[] words = {"你好","RxJava","今天学习一下RxJava"};
        //2.Observable observable = Observable.fromArray(words);
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("你好");
                Thread.sleep(500);
                e.onNext("RxJava");
                Thread.sleep(500);
                e.onNext("今天学习一下RxJava");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //Observable只是生产事件，真正的发送事件是在它被订阅的时候，即当 subscribe() 方法执行时
                .subscribe(
                        new Observer<String>() {
                            private Disposable mDisposable;

                            @Override
                            public void onSubscribe(Disposable d) {
                                // 该方法最先调用
                                Log.e("Seven", "-----onSubscribe()------");
                                mDisposable = d;
                            }

                            @Override
                            public void onNext(String value) {
                                Log.e("Seven", "-----onNext(): " + value);
                                //1.dispose()后，observer就不再接收后面的消息，即"今天学习一下RxJava"接收不到了
                                //if (value.equals("RxJava")) {
                                //    mDisposable.dispose();
                                //}
                                //2.在收到onComplete()或onError()之后，就不会回调该方法了
                                setDisplayTxt(value);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                Log.e("Seven", "-----onComplete()");
                            }
                        });
        /**
         * 直到有观察者(Observer)订阅时，才动态创建被观察者对象（Observable） & 发送事件
         */
        Observable<String> observable = Observable.defer(
                new Callable<ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<? extends String> call() throws Exception {
                        return Observable.just(s);
                    }
                });
        s = "你好,学习RxJava";
        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Log.e("Seven", "value is: " + value);
                //输出 value is: 你好,学习RxJava
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * Retrofit+RxJava结合使用
     */
    private void executeTranslation() {
        //1.创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置网络请求Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(可将得到的Json串转换为与其结构相符的对应Translation类)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();
        //2.创建网络请求接口的实例
        RetrofitApi request = retrofit.create(RetrofitApi.class);
        //3.采用Observable<...>形式对网络请求进行封装
        //Observable<Translation> observable = request.getTranslation();
        Observable<Translation> observable = request.getTranslation2("fy", "auto", "auto",
                "Hello, My name is Westbrook");
        //4.通过线程切换发送网络请求
        observable.subscribeOn(Schedulers.io())//切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())//切换回到主线程 处理请求结果
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Translation result) {
                        //5.接收服务器返回的数据
                        setRetrofitTxt(result.show());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Seven", "请求失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 使用Merge操作符，合并两个Observable
     * 如果两个Observable<T>,T是相同的类型，比如String， 那么在subscribe里面的new Observer<String>和 onNext(String)
     * 如果两个Observable<T>,T不是相同的类型，如下： 那么在subscribe里面的new Observer<Object>和 onNext(Object)即可
     */
    private void mergeOperation() {
        //设置第1个Observable：通过网络获取数据,此处仅作网络请求的模拟
        Observable<String> network = Observable.just("获取字符串");

        //设置第2个Observable：通过本地文件获取数据,此处仅作本地文件请求的模拟
        Observable<Integer> file = Observable.just(1000);

        //通过merge（）合并事件 & 同时发送事件
        Observable.merge(network, file)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        if (value instanceof String) {
                            result += value;
                        } else {
                            Log.d("Seven", "数据为： " + (Integer)value);
                        }
                        //Log.d("Seven", "数据源有： " + value);
                        //result += value + " ";
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Seven", "对Error事件作出响应");
                    }

                    // 接收合并事件后，统一展示
                    @Override
                    public void onComplete() {
                        Log.d("Seven", "获取数据完成");
                        setMergeTxt(result);
                        Log.d("Seven",  "result is: " + result);
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void zipOperation() {
        //1:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") //设置网络请求Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(自动转换为Translation类)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        //2：创建网络请求接口实例
        RetrofitApi request = retrofit.create(RetrofitApi.class);

        //3：采用Observable<...>形式 对 2个网络请求 进行封装
        //即2个网络请求异步 & 同时发送
        Observable<Translation> observable = request.getTranslation().subscribeOn(
                Schedulers.io()); // 新开线程进行网络请求1
        Observable<Translation1> observable1 = request.getTranslationTwo().subscribeOn(
                Schedulers.io());// 新开线程进行网络请求2

        // 步骤4：通过使用Zip（）对两个网络请求进行合并再发送
        Observable.zip(observable, observable1,
                new BiFunction<Translation, Translation1, String>() {
                    // 注：创建BiFunction对象传入的第3个参数 = 合并后数据的数据类型
                    @Override
                    public String apply(Translation translation,
                            Translation1 translation1) throws Exception {
                        return translation.show() + " & " + translation1.show();
                    }
                }).observeOn(AndroidSchedulers.mainThread()) // 在主线程接收 & 处理数据
                .subscribe(new Consumer<String>() {
                    // 成功返回数据时调用, Consumer<T>, T对应apply的返回值，apply对应BiFunction
                    // 最后的参数类型
                    @Override
                    public void accept(String combine_info) throws Exception {
                        // 结合显示2个网络请求的数据结果
                        Log.d("Seven", "最终接收到的数据是：" + combine_info);
                        setZipTxt(combine_info);
                    }
                }, new Consumer<Throwable>() {
                    // 网络请求错误时调用
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("Seven", "请求失败： " + throwable.toString() );
                    }
                });
        // 或者使用new Observer
        /*.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Log.d("Seven", "最终接收到的数据是：" + value);
                setZipTxt(value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });*/
    }

    private void retryOperation() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new Exception("发生错误了"));
                e.onNext(3);
            }
        }).retry(3) // 设置重试次数 = 3次
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d("Seven", "接收到了事件" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Seven", "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Seven", "对Complete事件作出响应");
                    }
                });
        //输出结果如下：
        /*
        09-10 09:30:45.004 D/Seven   (19596): 接收到了事件1
        09-10 09:30:45.004 D/Seven   (19596): 接收到了事件2
        09-10 09:30:45.004 D/Seven   (19596): 接收到了事件1
        09-10 09:30:45.004 D/Seven   (19596): 接收到了事件2
        09-10 09:30:45.004 D/Seven   (19596): 接收到了事件1
        09-10 09:30:45.004 D/Seven   (19596): 接收到了事件2
        09-10 09:30:45.004 D/Seven   (19596): 接收到了事件1
        09-10 09:30:45.004 D/Seven   (19596): 接收到了事件2
        09-10 09:30:45.005 D/Seven   (19596): 对Error事件作出响应*/
    }

    //功能防抖，在指定时间内多次点击，只响应第一次
    //不执行onComplete()
    private void throttleOperation() {
        RxView.clicks(clickBtn).throttleFirst(1, TimeUnit.SECONDS).observeOn(
                AndroidSchedulers.mainThread()).subscribe(
                new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object value) {
                        Log.e("Seven", "执行点击事件");
                        //getWeatherInfo();
                        //getWeatherInfoThird();
                        postTranslationThird();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.e("Seven", "对throttle Complete事件作出响应");
                    }
                });
    }

    //联想搜索优化，根据指定时间过滤事件的过滤操作符
    //比如在搜索框输入文字时，在指定时间内不再有文字输入时，才会发送请求，否则不发送
    //若在这段时间内，输入框有文字输入或变化，则继续等待该段时间，循环上述过程
    //不执行onComplete()
    private void debounceOperation() {
        RxTextView.textChanges(editTxt)
                .debounce(1, TimeUnit.SECONDS).skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharSequence>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(CharSequence charSequence) {
                        resultTxt.setText("发送给服务器的字符 = " + charSequence.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Seven", "对Error事件作出响应" );

                    }

                    @Override
                    public void onComplete() {
                        Log.d("Seven", "对Complete事件作出响应");
                    }
                });
    }

    /*private void backpressOperation() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d("Seven", "发送事件 1");
                emitter.onNext(1);
                Log.d("Seven", "发送事件 2");
                emitter.onNext(2);
                Log.d("Seven", "发送事件 3");
                emitter.onNext(3);
                Log.d("Seven", "发送完成");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    // 步骤2：创建观察者 =  Subscriber & 建立订阅关系

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d("Seven", "onSubscribe");
                        s.request(3);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("Seven", "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w("Seven", "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Seven", "onComplete");
                    }
                });
    }*/

    private void getWeatherInfo() {
        //1.创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://wthrcdn.etouch.cn/") // 设置网络请求Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(可将得到的Json串转换为对应的Translation类)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();
        //2.创建网络请求接口的实例
        final RetrofitApi request = retrofit.create(RetrofitApi.class);
        //3.采用Observable<...>形式对网络请求进行封装
        Observable<WeatherInfo> observable = request.getWeatherInfo("青岛");
        //4.通过线程切换发送网络请求
        observable.subscribeOn(Schedulers.io())//切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())//切换回到主线程 处理请求结果
                .subscribe(new Observer<WeatherInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(WeatherInfo result) {
                        //5.接收服务器返回的数据
                        Log.e("Seven", "Weather info is: " + result.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Seven", "请求失败 : " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getWeatherInfoThird() {
        //1.创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://wthrcdn.etouch.cn/") // 设置网络请求Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(可将得到的Json串转换为对应的Translation类)
                .build();
        //2.创建网络请求接口的实例
        final RetrofitApi request = retrofit.create(RetrofitApi.class);
        Call<WeatherInfo> call = request.getWeatherInfoThird("北京");
        call.enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                Log.e("Seven", "use call to get weather info is: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {

            }
        });
    }

    private void postTranslationThird() {
        //1.创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com//") // 设置网络请求Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(可将得到的Json串转换为对应的Translation类)
                .build();
        //2.创建网络请求接口的实例
        final RetrofitApi request = retrofit.create(RetrofitApi.class);
        Call<Translation2> call = request.getTranslationThird("Welcome to beijing");
        call.enqueue(new Callback<Translation2>() {
            @Override
            public void onResponse(Call<Translation2> call, Response<Translation2> response) {
                Log.e("Seven", "use call to post translation is: " + response.body().getTranslateResult().get(0).get(0).getTgt());
            }

            @Override
            public void onFailure(Call<Translation2> call, Throwable t) {

            }
        });
    }
}
