package com.frame.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.frame.library.R;
import com.just.agentweb.AgentWeb;

public abstract class WebViewActivity extends BaseActivity {
    protected String url;
    private AgentWeb mAgentWeb;

    @Override
    protected void initView(Bundle savedInstanceState) {
        url = getIntent().getStringExtra(Intent.EXTRA_ORIGINATING_URI);
        if (url != null) {
            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent(getWebContentView(), new LinearLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator()
                    .setMainFrameErrorView(R.layout.error_view, -1)
                    .createAgentWeb()
                    .ready()
                    .go(url);
        } else {
            finish();
        }
    }

    protected abstract ViewGroup getWebContentView();

    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
