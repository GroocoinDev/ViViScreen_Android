package com.groo.viviscreen.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.groo.viviscreen.R;

import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.saeid.fabloading.LoadingView;

public class WebViewActivity extends AppCompatActivity {
    @BindView(R.id.activity_webview_layout) RelativeLayout activityLayout;
    @BindView(R.id.mainWebView) WebView mMainWebView;
    @BindView(R.id.loading_layout)  RelativeLayout loadingLayout;
    @BindView(R.id.fab_loading) LoadingView fabLoading;

    private final Handler handler = new Handler();
    private boolean isWebViewCalled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        // URL 정보 얻기
        Intent intent = getIntent();
        String url = "";
        if(intent.getExtras() != null) {
            url = intent.getExtras().getString("url");
        }

        // 메인 로딩
        mainWebViewSetting();

        // 페이지 로드
        mMainWebView.postUrl(url, null);

        // 포인트 적립
        //showPopupGroopoint();

        // Fab-Loading
        fabLoading.addAnimation(Color.parseColor("#2F5DA9"), R.drawable.vivi_logo_white, LoadingView.FROM_LEFT);
        fabLoading.addAnimation(Color.parseColor("#FFD200"), R.drawable.vivi_logo_white, LoadingView.FROM_RIGHT);
        fabLoading.addAnimation(Color.parseColor("#FF4218"), R.drawable.vivi_logo_white, LoadingView.FROM_LEFT);
        fabLoading.addAnimation(Color.parseColor("#C7E7FB"), R.drawable.vivi_logo_white, LoadingView.FROM_RIGHT);
    }

    @Override
    public void onBackPressed() {
        // 로딩 View
        if (loadingLayout.getVisibility() == View.VISIBLE) {
            loadingLayout.setVisibility(View.GONE);
            return;
        }

        // History 존재시
        if (mMainWebView.canGoBack()) {
            mMainWebView.goBack();
            return;
        }

        super.onBackPressed();
    }

    public void mainWebViewSetting() {
        WebSettings webSetting = mMainWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);          // Javascript 사용하기
        webSetting.setLoadWithOverviewMode(true);       // html 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
        webSetting.setUseWideViewPort(true);            // viewport 메타 태그를 지원
        webSetting.setSupportZoom(false);               // 사용자 제스처를 통한 줌 기능 활성화 여부
        webSetting.setDisplayZoomControls(false);       // 줌 컨트롤 사용 여부
        webSetting.setBuiltInZoomControls(true);        // WebView 내장 줌 사용여부
        webSetting.setDefaultTextEncodingName("UTF-8"); // TextEncoding 이름 정의
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);// 캐쉬 사용 방법을 정의
        webSetting.setTextZoom(100);
        //webSetting.setSupportMultipleWindows(true);

        // 크로스도메인 설정
        webSetting.setAllowUniversalAccessFromFileURLs(true);
        webSetting.setAllowFileAccessFromFileURLs(true);
        webSetting.setDomStorageEnabled(true);              // HTML5 의 LocalStorage 기능을 사용
        webSetting.setAllowFileAccess(true);              // 웹 뷰 내에서 파일 액세스 활성화 여부
        webSetting.setAllowContentAccess(true);
        webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(mMainWebView, true);

        String version = null;
        try {
            PackageInfo i = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = i.versionName;
        } catch(PackageManager.NameNotFoundException e) { }
        String userAgent = webSetting.getUserAgentString();
        webSetting.setUserAgentString(userAgent  + " AndroidAPP " + version.substring(0, 5));

        mMainWebView.setWebViewClient(new  WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loadingLayout.setVisibility(View.VISIBLE);
                fabLoading.startAnimation();

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(!isWebViewCalled) {
                    if (url != null && !url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {
                        if (url.startsWith("intent:")) {
                            try {
                                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                                Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                                if (existPackage != null) {
                                    startActivity(intent);
                                } else {
                                    Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                                    marketIntent.setData(Uri.parse("market://details?id=" + intent.getPackage()));
                                    startActivity(marketIntent);
                                }
                                return true;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (url.startsWith("market:")) {
                            try {
                                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                                if (intent != null) {
                                    startActivity(intent);
                                }
                                return true;
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        } else if (url.startsWith("ispmobile:")) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                            return true;
                        } else if (url.startsWith("vguardend:")) {
                            return true;
                        } else if (url.startsWith("tel:")) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                            startActivity(intent);
                            return true;
                        } else if (url.startsWith("mailto:")) {
                            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
                            startActivity(intent);
                            return true;
                        } else {
                            return true;
                        }
                    }

//                    if (!isSameWithIndexHost(url, indexHost)) {
//                        if(url.contains("instagram.com") || url.contains("facebook.com") || url.contains("pf.kakao.com/_WxmnHd")) {
//                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                            startActivity(intent);
//                            return true;
//                        }
//                        return false;
//                    }

                    //isWebViewCalled = true;
                    //view.loadUrl(url);
                    return false;
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if(!isWebViewCalled) {
                    Uri uri = request.getUrl();
                    String url = uri.toString();
                    if (url != null && !url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {
                        if (url.startsWith("intent:")) {
                            try {
                                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                                Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                                if (existPackage != null) {
                                    startActivity(intent);
                                } else {
                                    Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                                    marketIntent.setData(Uri.parse("market://details?id=" + intent.getPackage()));
                                    startActivity(marketIntent);
                                }
                                return true;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (url.startsWith("market:")) {
                            try {
                                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                                if (intent != null) {
                                    startActivity(intent);
                                }
                                return true;
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        } else if (url.startsWith("ispmobile:")) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                            return true;
                        } else if (url.startsWith("vguardend:")) {
                            return true;
                        } else if (url.startsWith("tel:")) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                            startActivity(intent);
                            return true;
                        } else if (url.startsWith("mailto:")) {
                            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
                            startActivity(intent);
                            return true;
                        } else {
                            return true;
                        }
                    }

//                    if (!isSameWithIndexHost(url, indexHost)) {
//                        if(url.contains("instagram.com") || url.contains("facebook.com") || url.contains("pf.kakao.com/_WxmnHd")) {
//                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                            startActivity(intent);
//                            return true;
//                        }
//                        return false;
//                    }

                    //isWebViewCalled = true;
                    //view.loadUrl(url);
                    return false;
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingLayout.setVisibility(View.GONE);
                isWebViewCalled = false;
            }
        });
        mMainWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle(R.string.app_name)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle(R.string.app_name)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.yes,
                                new AlertDialog.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                })
                        .setNegativeButton(android.R.string.no,
                                new AlertDialog.OnClickListener(){
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.cancel();
                                    }
                                })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }

        });
    }

    private void showPopupGroopoint() {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int width = RelativeLayout.LayoutParams.MATCH_PARENT;
            int height = RelativeLayout.LayoutParams.MATCH_PARENT;
            int delay_sec = 1500;

            final View popupView = inflater.inflate(R.layout.popup_add_point, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, false);
            popupView.setAnimation(AnimationUtils.loadAnimation(WebViewActivity.this, R.anim.anim_fade_in));
            popupWindow.setAnimationStyle(-1);
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation anim = AnimationUtils.loadAnimation(WebViewActivity.this, R.anim.anim_fade_out);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            popupWindow.dismiss();
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                    popupView.startAnimation(anim);
                }
            }, delay_sec);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_stay,R.anim.anim_slide_out_bottom);
    }
}
