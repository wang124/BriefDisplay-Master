package com.whut.glxiang.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.whut.glxiang.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
@SuppressLint("ValidFragment")
public class DocumentFragment extends Fragment implements OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener {
    private Context context;
    private Unbinder unbinder;
    private String fileName;
    @BindView(R.id.pdfView)
    PDFView pdfView;

    @SuppressLint("ValidFragment")
    public DocumentFragment(String s) {
        fileName = s;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pdfview, null);
        context = getActivity();
        unbinder = ButterKnife.bind(this,view);
        Integer pageNumber = 0;
        pdfView.fromAsset(fileName)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(context))
                .spacing(10) // in dp
                .onPageError( this)
                .load();
        return view;
    }

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageError(int page, Throwable t) {

    }
}
