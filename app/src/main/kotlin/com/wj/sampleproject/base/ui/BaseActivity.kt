package com.wj.sampleproject.base.ui

import android.content.res.Resources
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import cn.wj.android.base.ui.activity.BaseBindingLibActivity
import com.google.android.material.snackbar.Snackbar
import com.wj.sampleproject.base.mvvm.BaseViewModel

/**
 * Activity 基类
 *
 * @author 王杰
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding>
    : BaseBindingLibActivity<VM, DB>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeData()
    }

    override fun getResources(): Resources? {
        // 禁止app字体大小跟随系统字体大小调节
        val resources = super.getResources()
        if (resources != null && resources.configuration.fontScale != 1.0f) {
            val configuration = resources.configuration
            configuration.fontScale = 1.0f
//            resources.updateConfiguration(configuration, resources.displayMetrics)
            createConfigurationContext(configuration)
        }
        return resources
    }

    override fun startAnim() {
    }

    override fun finishAnim() {
    }

    /**
     * 添加观察者
     */
    private fun observeData() {
        viewModel.snackbarData.observe(this, Observer {
            if (it.content.isNullOrBlank()) {
                return@Observer
            }
            val snackbar = Snackbar.make(mBinding.root, it.content.orEmpty(), it.duration)
            snackbar.setTextColor(it.contentColor)
            snackbar.setBackgroundTint(it.contentBgColor)
            if (it.actionText != null && it.onAction != null) {
                snackbar.setAction(it.actionText!!, it.onAction)
                snackbar.setActionTextColor(it.actionColor)
            }
            if (it.onCallback != null) {
                snackbar.addCallback(it.onCallback!!)
            }
            snackbar.show()
        })
        viewModel.uiCloseData.observe(this, Observer { close ->
            if (close) {
                // 关闭 Activity
                setResult(viewModel.resultCode, viewModel.resultData)
                finish()
            }
        })
    }
}