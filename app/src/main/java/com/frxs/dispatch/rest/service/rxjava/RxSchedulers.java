package com.frxs.dispatch.rest.service.rxjava;

import com.frxs.core.base.BaseActivity;
import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *     author : ewu
 *     e-mail : xxx@xx
 *     time   : 2017/07/24
 *     desc   : xxxx描述
 *     version: 1.0
 * </pre>
 */
public class RxSchedulers {

    public static <T> ObservableTransformer<T, T> compose() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> ObservableTransformer<T, T> compose(final LifecycleTransformer<T> lifecycleTransformer) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(lifecycleTransformer);
            }
        };
    }

    public static <T> ObservableTransformer<T, T> compose(@NonNull final BaseActivity context, @NonNull final boolean showProgress) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                if (showProgress) {
                                    context.showProgressDialog();
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnTerminate(new Action() {
                            @Override
                            public void run() throws Exception {
                                if (showProgress) {
                                    context.dismissProgressDialog();
                                }
                            }
                        });
            }
        };
    }

    public static <T> ObservableTransformer<T, T> compose(@NonNull final BaseActivity context, final LifecycleTransformer<T> lifecycleTransformer, @NonNull final boolean showProgress) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                if (showProgress) {
                                    context.showProgressDialog();
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(lifecycleTransformer)
                        .doOnTerminate(new Action() {
                            @Override
                            public void run() throws Exception {
                                if (showProgress) {
                                    context.dismissProgressDialog();
                                }
                            }
                        });
            }
        };
    }
}
