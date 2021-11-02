package com.example.detectolibrary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DOLYTSearchFragment extends Fragment {

    String YTSURL;
    WebView webView;
    public DOLYTSearchFragment(String URL){
        YTSURL=URL;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_d_o_l_y_t_search, container, false);
        webView=rootView.findViewById(R.id.DOLYTsearch);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(YTSURL);

        return rootView;
    }
}