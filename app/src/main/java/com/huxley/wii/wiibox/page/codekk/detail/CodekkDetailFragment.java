package com.huxley.wii.wiibox.page.codekk.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiitools.base.BaseFragment;
import com.huxley.wii.wiitools.common.helper.SnackbarHelper;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

public class CodekkDetailFragment extends BaseFragment implements CodekkDetailContract.View {

    private CodekkDetailContract.Presenter mCodekkDetailPresenter;
    private HtmlTextView                   tvContent;

    public static CodekkDetailFragment newInstance() {
        return new CodekkDetailFragment();
    }

    @Override
    public void setPresenter(@NonNull CodekkDetailContract.Presenter presenter) {
        mCodekkDetailPresenter = checkNotNull(presenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_codekk_detail;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
        mCodekkDetailPresenter.start();
    }

    private void initView() {
        tvContent = $(R.id.tvContent);
    }

    @Override
    public void showLoading() {
        isLoading(true);
    }

    @Override
    public void dismissLoading() {
        isLoading(false);
    }

    @Override
    public void showNotNet() {
        SnackbarHelper.showNoNetInfo(rootView);
    }

    @Override
    public void showError(Throwable e) {
        SnackbarHelper.showLoadErrorInfo(rootView, mCodekkDetailPresenter::start);
    }

    @Override
    public void showContent(String data, boolean isRefresh) {
//        RichText.from(data) // 数据源
//                .type(RichText.TYPE_HTML) // 数据格式,不设置默认是Html,使用fromMarkdown的默认是Markdown格式
//                .autoFix(true) // 是否自动修复，默认true
//                .async(true) // 是否异步，默认false
//                .fix(imageFixCallback) // 设置自定义修复图片宽高
//                .noImage(true) // 不显示并且不加载图片
//                .imageClick(onImageClickListener) // 设置图片点击回调
//                .imageLongClick(onImageLongClickListener) // 设置图片长按回调
//                .urlClick(onURLClickListener) // 设置链接点击回调
//                .urlLongClick(onUrlLongClickListener) // 设置链接长按回调
//                .placeHolder(placeHolder) // 设置加载中显示的占位图
//                .error(errorImage) // 设置加载失败的错误图
//                .into(tvContent); // 设置目标TextView
        tvContent.setHtml(data);
    }
}
