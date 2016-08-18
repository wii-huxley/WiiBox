package com.huxley.wii.wiibox.page.knowledge.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiitools.base.BaseFragment;
import com.zzhoujay.richtext.RichText;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

public class KnowledgeDetailFragment extends BaseFragment implements KnowledgeDetailContract.View {

    private KnowledgeDetailContract.Presenter mKnowledgeDetailPresenter;
    private TextView tvContent;

    public static KnowledgeDetailFragment newInstance() {
        return new KnowledgeDetailFragment();
    }

    @Override
    public void setPresenter(@NonNull KnowledgeDetailContract.Presenter presenter) {
        mKnowledgeDetailPresenter = checkNotNull(presenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge_detail;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
        mKnowledgeDetailPresenter.start();
    }

    private void initView() {
        tvContent = $(R.id.tvContent);
    }

    @Override
    public void setContent(String content) {
        RichText.from(content) // 数据源
                .type(RichText.TYPE_MARKDOWN) // 数据格式,不设置默认是Html,使用fromMarkdown的默认是Markdown格式
//                .autoFix(true) // 是否自动修复，默认true
                .async(true) // 是否异步，默认false
//                .fix(imageFixCallback) // 设置自定义修复图片宽高
//                .noImage(true) // 不显示并且不加载图片
//                .imageClick(onImageClickListener) // 设置图片点击回调
//                .imageLongClick(onImageLongClickListener) // 设置图片长按回调
//                .urlClick(onURLClickListener) // 设置链接点击回调
//                .urlLongClick(onUrlLongClickListener) // 设置链接长按回调
//                .placeHolder(placeHolder) // 设置加载中显示的占位图
//                .error(errorImage) // 设置加载失败的错误图
                .into(tvContent); // 设置目标TextView
    }
}
