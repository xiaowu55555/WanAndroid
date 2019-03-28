package com.frame.library.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

public  abstract class BaseSwipeBackActivity<T extends BaseViewModel> extends BaseActivity<T> implements SwipeBackActivityBase {
    SwipeBackActivityHelper mHelper;

    @Override
    protected void setSwipeBack() {
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        setSwipeBackEnable(true);
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {

    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
