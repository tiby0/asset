package z.t.assetmanagement.helpClass;

import android.app.Activity;
import android.content.Context;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import z.t.assetmanagement.kotlinBean.SpinnerData;

import java.util.List;


/**
 * Created by Administrator on 2015/5/19.
 */
public class SpringUtil {
    public static void adapterData(Spinner spin, String name, String value) {

        SpinnerAdapter apsAdapter = spin.getAdapter();
        int k = apsAdapter.getCount();
        for (int i = 0; i < k; i++) {
            String Value = ((SpinnerData) spin.getItemAtPosition(i)).getValue();
            if (value != null && Value.equals(value)) {
                spin.setSelection(i, true);
                break;
            }
            String Text = ((SpinnerData) spin.getItemAtPosition(i)).getText();
            if (name != null && Text.equals(name)) {
                spin.setSelection(i, true);
                break;
            }
        }

    }

    public static void adapterData2(List<SpinnerData> list, Spinner spin, Activity mActivity) {

        ArrayAdapter<SpinnerData> adapter2 = new ArrayAdapter<SpinnerData>(mActivity,android.R.layout.simple_spinner_dropdown_item, list);
        //设置下拉列表的风格
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中

        spin.setAdapter(adapter2);


    }

    public static void adapterData3(List<SpinnerData> list, Spinner spin, Context mActivity) {

        ArrayAdapter<SpinnerData> adapter2 = new ArrayAdapter<SpinnerData>(mActivity, android.R.layout.simple_spinner_dropdown_item, list);
        //设置下拉列表的风格
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中

        spin.setAdapter(adapter2);

    }

    public static void adapterDataString(List<String> list, Spinner spin, Context mActivity) {

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_dropdown_item, list);
        //设置下拉列表的风格
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        spin.setAdapter(adapter2);

    }

    public static void adapterDataString2(Spinner spin, String name) {

        SpinnerAdapter apsAdapter = spin.getAdapter();
        int k = apsAdapter.getCount();
        for (int i = 0; i < k; i++) {
            String Text = spin.getItemAtPosition(i).toString();
            if (name != null && Text.equals(name)) {
                spin.setSelection(i, true);
                break;
            }
        }

    }

}
