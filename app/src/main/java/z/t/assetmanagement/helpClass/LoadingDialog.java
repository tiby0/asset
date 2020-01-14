package z.t.assetmanagement.helpClass;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import z.t.assetmanagement.R;

/**
 * Created by chenrong on 2016/11/25.
 */
public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {

        private Context context;

        private String msg;

        private View contentView;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public LoadingDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LoadingDialog loadingDialog =  new LoadingDialog(context, R.style.style_loadingDialog);
            View layout = inflater.inflate(R.layout.widget_loading, null);
            loadingDialog.setContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if (!TextUtils.isEmpty(msg)){
                TextView textView = (TextView) layout.findViewById(R.id.tv_loading_msg);
                textView.setText(msg);
            }
            return loadingDialog;
        }

    }
}
