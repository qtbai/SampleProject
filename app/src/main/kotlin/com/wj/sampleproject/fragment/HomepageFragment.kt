package com.wj.sampleproject.fragment

import androidx.lifecycle.Observer
import cn.wj.android.recyclerview.layoutmanager.WrapContentLinearLayoutManager
import com.wj.sampleproject.R
import com.wj.sampleproject.adapter.HomepageArticleListRvAdapter
import com.wj.sampleproject.base.ui.BaseFragment
import com.wj.sampleproject.databinding.AppFragmentHomepageBinding
import com.wj.sampleproject.mvvm.HomepageViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * 主界面 - 首页
 */
class HomepageFragment
    : BaseFragment<HomepageViewModel, AppFragmentHomepageBinding>() {

    companion object {
        /**
         * 创建 Fragment
         *
         * @return 首页 Fragment
         */
        fun actionCreate(): HomepageFragment {
            return HomepageFragment()
        }
    }

    override val mViewModel: HomepageViewModel by viewModel()

    override val layoutResID: Int = R.layout.app_fragment_homepage

    /** 文章列表适配器 */
    private val mArticlesAdapter: HomepageArticleListRvAdapter by inject()

    override fun initView() {
        // 配置文章列表
        mBinding.rvArticles.layoutManager = WrapContentLinearLayoutManager()
        mBinding.rvArticles.adapter = mArticlesAdapter

        // 添加观察者
        mViewModel.articleListData.observe(this, Observer {
            mArticlesAdapter.loadData(it.list, it.refresh)
        })
    }
}