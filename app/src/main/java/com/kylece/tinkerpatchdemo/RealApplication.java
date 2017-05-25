package com.kylece.tinkerpatchdemo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;

/**
 * Created by Kyle on 25/05/2017
 */

public class RealApplication extends Application {
    public static final String TAG = RealApplication.class.getSimpleName();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
    }

    /**
     * @see <a href="https://github.com/TinkerPatch/tinkerpatch-easy-sample/blob/master/app/src/main/java/com/tinkerpatch/easy_sample/SampleApplication.java">application sample</a>
     */
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.TINKER_ENABLE) {
            ApplicationLike tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
            TinkerPatch.init(tinkerApplicationLike)
                    .reflectPatchLibrary()
                    .setPatchRollbackOnScreenOff(true)
                    .setPatchRestartOnSrceenOff(true)
                    .setFetchPatchIntervalByHours(3);
            TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
            Log.d(TAG, "Current patch version is " + TinkerPatch.with().getPatchVersion());
        }
    }
}
