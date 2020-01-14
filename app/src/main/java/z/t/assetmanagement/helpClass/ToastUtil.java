package z.t.assetmanagement.helpClass;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


/**
 * 消息提示工具
 * 功能：
 * 1.长时间显示的消息
 * 2.短时间显示的消息
 * 3.网络异常消息
 * 4.弹出等待提示框
 * 5.隐藏等待提示框
 */
public class ToastUtil {

    /**
     * Toast对象
     */
    private static Toast toast = null;

    /**
     * 请求等待提示框对象
     */
    private static LoadingDialog.Builder builder;
    private static LoadingDialog mLoadingDialog;

    /**
     * 长时间显示的消息
     *
     * @param context
     * @param msg
     */
    public static void showLong(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, null, Toast.LENGTH_LONG);
        }
        toast.setText(msg);
        toast.show();
    }

    /**
     * 短时间显示的消息
     *
     * @param context
     * @param msg
     */
    public static void showShort(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }

    /**
     * 网络异常消息
     *
     * @param context
     */
    public static void showInternetError(Context context) {
        if (toast == null) {
            toast = Toast.makeText(context,null, Toast.LENGTH_LONG);
        }
        toast.setText("链接服务器失败");
        toast.show();
    }
    /**
     * 当前无网络
     *
     * @param context
     */
    public static void hintNoNetwork(Context context) {
        if (toast == null) {
            toast = Toast.makeText(context,null, Toast.LENGTH_LONG);
        }
        toast.setText("当前无网络");
        toast.show();
    }

    /**
     * 再次点击退出应用
     * @param context
     */
    public static void hintExitApp(Context context){
        if (toast == null) {
            toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        }
        toast.setText("再次点击退出应用");
        toast.show();
    }

    /**
     * 弹出提示框
     * @param context
     * @param s
     */
    public static void showLoadingDialog(Context context, String s){
        if (builder == null) {
            builder = new LoadingDialog.Builder(context);
        }
        if (!TextUtils.isEmpty(s)){
            builder.setMsg(s);
        }
        if (mLoadingDialog == null) {
            mLoadingDialog = builder.create();
        }
        mLoadingDialog.show();
    }

    /**
     * 隐藏提示框
     */
    public static void hideLoadingDialog(){
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            builder = null;
            mLoadingDialog = null;
        }
    }

    /**
     * 没有权限提示
     */
    public static void showPermissionDenied(Context context) {
        if (toast == null) {
            toast = Toast.makeText(context,null, Toast.LENGTH_LONG);
        }
        toast.setText("您没有此权限");
        toast.show();
    }
    /**
     * 没有权限提示
     */
    public static void showNotOpen(Context context) {
        if (toast == null) {
            toast = Toast.makeText(context,null, Toast.LENGTH_SHORT);
        }
        toast.setText("正在努力开发中。。。");
        toast.show();
    }

    /**
     * 底部弹出式弹框
     */
    public static void showSnackbar(View view,String ss){
        Snackbar.make(view, ss, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
    /**
     * 底部弹出式弹框
     */
    public static void showSnackbarS(View view,String ss){
        Snackbar.make(view, ss, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }
}
