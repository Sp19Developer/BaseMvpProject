
package com.mvpbase.data;

import com.mvpbase.data.db.DbHelper;
import com.mvpbase.data.network.ApiHelper;
import com.mvpbase.data.prefs.PreferencesHelper;

public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {

    interface Callback<V> {
        void onSuccess(V baseResponse);

        void onFailed(int code, String message);
    }
    void logout();

}
