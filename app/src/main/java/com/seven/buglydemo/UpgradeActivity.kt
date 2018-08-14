package com.seven.buglydemo

import android.app.Activity
import android.os.Bundle
import android.view.Window

import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.download.DownloadListener
import com.tencent.bugly.beta.download.DownloadTask
import kotlinx.android.synthetic.main.activity_upgrade.*

class UpgradeActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_upgrade)

        updateBtn(Beta.getStrategyTask())
        tvProgress.text = tvProgress.text.toString() + Beta.getStrategyTask().savedLength
        tvTitle.text = tvTitle.text.toString() + Beta.getUpgradeInfo().title
        tvVersion.text = tvVersion.text.toString() + Beta.getUpgradeInfo().versionName
        tvSize.text = tvSize.text.toString() + Beta.getUpgradeInfo().fileSize
        tvTime.text = tvTime.text.toString() + Beta.getUpgradeInfo().publishTime
        tvContent.text = Beta.getUpgradeInfo().newFeature
        btnStart.setOnClickListener {
            val task = Beta.startDownload()
            updateBtn(task)
            if (task.status == DownloadTask.DOWNLOADING) {
                finish()
            }
        }

        btnCancel!!.setOnClickListener {
            Beta.cancelDownload()
            finish()
        }
        Beta.registerDownloadListener(object : DownloadListener {
            override fun onReceive(task: DownloadTask) {
                updateBtn(task)
                tvProgress.text = task.savedLength.toString()
            }

            override fun onCompleted(task: DownloadTask) {
                updateBtn(task)
                tvProgress.text = task.savedLength.toString()
            }

            override fun onFailed(task: DownloadTask, code: Int, extMsg: String) {
                updateBtn(task)
                tvProgress!!.text = "failed"

            }
        })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        Beta.unregisterDownloadListener()
    }


    fun updateBtn(task: DownloadTask) {
        when (task.status) {
            DownloadTask.INIT, DownloadTask.DELETED, DownloadTask.FAILED -> {
                btnStart!!.text = "开始下载"
            }
            DownloadTask.COMPLETE -> {
                btnStart!!.text = "安装"
            }
            DownloadTask.DOWNLOADING -> {
                btnStart!!.text = "暂停"
            }
            DownloadTask.PAUSED -> {
                btnStart!!.text = "继续下载"
            }
            else -> {
            }
        }
    }
}
