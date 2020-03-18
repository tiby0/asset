package z.t.assetmanagement

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import z.t.assetmanagement.biometriclib.BiometricPromptManager
import z.t.assetmanagement.helpClass.ToastUtil
import z.t.assetmanagement.style.TimeToast
import z.t.assetmanagement.test.DataBaseTest
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppCompatActivity() {

    private var mTextView: TextView? = null
    private var mButton: Button? = null
    private var mManager: BiometricPromptManager? = null
    private var context: Context? = null
    var onerror = 0
    var change = 0
    var time: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mTextView = findViewById(R.id.text_view)
        mButton = findViewById(R.id.button)
        context = this@HomeActivity
        try {
            mManager = BiometricPromptManager.from(this)

            val stringBuilder = StringBuilder()
            stringBuilder.append("SDK version is " + Build.VERSION.SDK_INT)
            stringBuilder.append("\n")
            stringBuilder.append("isHardwareDetected : " + mManager!!.isHardwareDetected)
            stringBuilder.append("\n")
            stringBuilder.append("hasEnrolledFingerprints : " + mManager!!.hasEnrolledFingerprints())
            stringBuilder.append("\n")
            stringBuilder.append("isKeyguardSecure : " + mManager!!.isKeyguardSecure)
            stringBuilder.append("\n")

            mTextView!!.text = stringBuilder.toString()

            mButton!!.setOnClickListener { click() }
            click()
        } catch (re: Exception) {

        }
        short1.setOnClickListener { view ->
            Snackbar.make(view, "短", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()
            onerror += 1
            if (onerror == 10)
                startActivity(Intent(context, MainActivity().javaClass))
        }
        long1.setOnClickListener { view ->
            Snackbar.make(view, "长", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            onerror += 2
            if (onerror == 10)
                startActivity(Intent(context, MainActivity().javaClass))
        }
        `in`.setOnClickListener { view ->
            Snackbar.make(view, "不定", Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null).show()
            onerror += 3
            if (onerror == 10)
                startActivity(Intent(context, MainActivity().javaClass))
        }
        dialog.setOnClickListener { view ->
            TimeToast(this, "测试消息提示").show()
            onerror += 4
            if (onerror == 10)
                startActivity(Intent(context, MainActivity().javaClass))
        }

        changeButton.setOnClickListener {
            if (Date().time - time < 400) {

                change++
                if (change == 4) change = 0

                when (change) {
                    0 -> changeButton.setImageResource(R.mipmap.title)
                    1 -> changeButton.setImageResource(R.mipmap.btn_add_list)
                    2 -> changeButton.setImageResource(R.mipmap.ic_launcher)
                    3 -> changeButton.setImageResource(R.mipmap.mark)
                }
                val vib = this.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
                vib.vibrate(100)
            }
            time = Date().time

        }
    }

    var safe = false
    var safe2 = true
    private fun click() {
        if (mManager!!.isBiometricPromptEnable) {
            mManager!!.authenticate(object : BiometricPromptManager.OnBiometricIdentifyCallback {
                override fun onUsePassword() {
                    val date = Date()
                    val format = SimpleDateFormat("HHmm")
                    val time = format.format(date)
                    var editText = EditText(context)
                    editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                    Timer().schedule(object : TimerTask() {
                        override fun run() {
                            var inputManager =
                                this@HomeActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            inputManager.showSoftInput(editText, 0)
                        }
                    }, 300)
                    AlertDialog.Builder(context!!).setTitle("系统提示")
                        .setMessage("请输入密码")
                        .setView(editText)
                        .setNegativeButton("取消") { dialogInterface, i ->
                        }
                        .setPositiveButton("确定") { dialogInterface, i ->

                            if (editText.text.toString() == "${time}0")
                                startActivity(Intent(context, MainActivity().javaClass))
                            else
                                ToastUtil.showLong(context, "密码错误")
                        }
                        .show()
                }

                override fun onSucceeded() {

                    Toast.makeText(context, "onSucceeded", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailed() {
                    if (safe) {
                        Toast.makeText(context, "onFailed", Toast.LENGTH_SHORT).show()
                    } else {
                        safe = true
                    }
                }

                override fun onError(code: Int, reason: String) {
                    if (safe) {
                        Toast.makeText(context, "onError", Toast.LENGTH_SHORT).show()
                    } else {
                        safe = true
                    }
                }

                override fun onCancel() {
                    safe2 = false
                    Toast.makeText(context, "onCancel", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun dataBaseTest(view: View) {
        startActivity(Intent(this, DataBaseTest::class.java))
    }


}

