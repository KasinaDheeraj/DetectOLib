package com.example.detectolibrary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DOLGSearchFragment extends Fragment {

    String GSURL;
    WebView webView;
    public DOLGSearchFragment(String URL){
        GSURL=URL;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_d_o_l_g_search, container, false);
        webView=rootView.findViewById(R.id.DOLGsearch);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(GSURL);

        return rootView;
    }
}