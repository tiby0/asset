package z.t.assetmanagement.style

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer

import kotlinx.android.synthetic.main.dialog_start.*

import z.t.assetmanagement.R
import android.view.View





class TimeDialog// 继承dialog重写构造方法
    (context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_start)
        // 设置是否可以关闭当前控件
        //setCancelable(false)
        // 找到tv_time控件
        yes.isEnabled = false
        DownTimer().start()
        initEvent()
    }
    /**
     * 初始化界面的确定和取消监听器
     */
     fun initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                onClickBottomListener?.onPositiveClick()
                dismiss()
            }
        })
        //设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                onClickBottomListener?.onNegtiveClick()
                dismiss()
            }
        })
    }
    /**
     * 设置确定取消按钮的回调
     */
    var onClickBottomListener: OnClickBottomListener? = null

    fun setOnClickBottomListener(onClickBottomListener: OnClickBottomListener): TimeDialog {
        this.onClickBottomListener = onClickBottomListener
        return this
    }

    interface OnClickBottomListener {
        /**
         * 点击确定按钮事件
         */
        fun onPositiveClick()

        /**
         * 点击取消按钮事件
         */
        fun onNegtiveClick()
    }

    // 继承CountDownTimer类
    internal inner class DownTimer : CountDownTimer(2000, 1000) {
        // 重写CountDownTimer的两个方法
        override fun onTick(millisUntilFinished: Long) {
            yes.text = (millisUntilFinished / 1000 + 1).toString() + "s"
        }

        override fun onFinish() {
//            StartDialog.this.dismiss();
            yes.text = "确定"
            yes.isEnabled = true
        }

    }// 设置时间4秒
}
