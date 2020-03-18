package z.t.assetmanagement

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main2.*
import org.litepal.LitePal
import org.litepal.extension.findAll
import org.litepal.extension.findFirst
import z.t.assetmanagement.adapter.TypeAdapter
import z.t.assetmanagement.dataBase.CapitalRecordType
import z.t.assetmanagement.helpClass.ProcessUtil
import z.t.assetmanagement.helpClass.ToastUtil
import z.t.assetmanagement.helpClass.UUIDGenerator
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class Main2Activity : AppCompatActivity() {

    lateinit var capitalRecordTypeList: List<CapitalRecordType>
    private lateinit var recycleView: RecyclerView
    lateinit var mAdapter: TypeAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        select()
        fab.setOnClickListener {
            // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //.setAction("Action", null).show()
            showAdd(this)
        }
    }

    private fun select() {
        capitalRecordTypeList = LitePal.findAll()
        pollToRefreshListView()

    }

    private fun pollToRefreshListView() {
        
        recycleView = findViewById(R.id.recyclerview)
        recycleView.setHasFixedSize(true)//设置固定大小
        recycleView.itemAnimator = DefaultItemAnimator()//设置默认动画
        val mLayoutManage = LinearLayoutManager(this)
        //mLayoutManage.setOrientation(OrientationHelper.HORIZONTAL)//设置滚动方向，横向滚动
        recycleView.layoutManager = mLayoutManage
        mAdapter = TypeAdapter(this, capitalRecordTypeList)
        recycleView.adapter = mAdapter

        //调用适配器里的方法
        mAdapter.setOnReItemOnclickLisenter(object : TypeAdapter.OnReItemOnclickLisenter {
            override fun OnReItemOnclick(view: View, position: Int) {
                ToastUtil.showSnackbarS(view, "1S")

            }
        })

        mAdapter.setOnReItemLongClickLisenter(object : TypeAdapter.OnReItemLongClickLisenter {
            override fun OnReItemLongClick(view: View, position: Int) {
                AlertDialog.Builder(this@Main2Activity).setTitle("系统提示").setMessage("删除此条记录？")
                    .setNegativeButton("取消") { dialogInterface: DialogInterface, i: Int ->
                    }
                    .setPositiveButton("确定") { dialogInterface: DialogInterface, i: Int ->
                        capitalRecordTypeList[position].delete()
                        capitalRecordTypeList = capitalRecordTypeList - capitalRecordTypeList[position]
                        mAdapter.notifyDataSetChanged(capitalRecordTypeList)
                    }
                    .show()
            }
        })

    }

    private fun showAdd(activity: AppCompatActivity): AlertDialog {

        val builder = AlertDialog.Builder(activity)//修改这里

        val view = View.inflate(activity, R.layout.type_add, null)

        val name: EditText = view.findViewById(R.id.name)
        val radio1: RadioButton = view.findViewById(R.id.radio1)
        val radio2: RadioButton = view.findViewById(R.id.radio2)

        val yes: Button = view.findViewById(R.id.yes)
        val no: Button = view.findViewById(R.id.no)

        builder.setView(view)
        val dialog = builder.create()

        no.setOnClickListener { dialog.dismiss() }
        yes.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val nowdate2 = Date()
                Log.d(this.toString(), "onClick: $nowdate2")
                var nowdate = Date()

                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val currentDate = dateFormat.format(Date())
                try {
                    nowdate = ProcessUtil.ConverToDateTime(currentDate)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                val capitalRecordType = CapitalRecordType()
                capitalRecordType.unid = UUIDGenerator.getUUID()
                capitalRecordType.createdate = nowdate


                when {
                    TextUtils.isEmpty(name.text.toString()) -> {
                        ToastUtil.showSnackbarS(view, "名称不能为空")
                        return
                    }
                    !(radio1.isChecked||radio2.isChecked)-> {
                        ToastUtil.showSnackbarS(view, "类型必须选择")
                        return
                    }

                    else -> {

                        if(radio1.isChecked) {
                            val capitalisBeing =
                                LitePal.where("name = '${name.text}'")
                                    .findFirst<CapitalRecordType>()
                            if (capitalisBeing != null) {
                                ToastUtil.showSnackbarS(view, "该记录重复")
                                return
                            }

                            capitalRecordType.name = name.text.toString()

                            val s = capitalRecordType.save()

                            if (s) {
                                capitalRecordTypeList = capitalRecordTypeList + capitalRecordType
                                ToastUtil.showSnackbarS(view, "添加成功")
                            } else {
                                ToastUtil.showSnackbarS(view, "添加失败")
                            }
                        }else if (radio2.isChecked){
                            val capitalisBeing =
                                LitePal.where("twoType = '${name.text}'")
                                    .findFirst<CapitalRecordType>()
                            if (capitalisBeing != null) {
                                ToastUtil.showSnackbarS(view, "该记录重复")
                                return
                            }

                            capitalRecordType.twoType = name.text.toString()

                            val s = capitalRecordType.save()

                            if (s) {
                                capitalRecordTypeList = capitalRecordTypeList + capitalRecordType
                                ToastUtil.showSnackbarS(view, "添加成功")
                            } else {
                                ToastUtil.showSnackbarS(view, "添加失败")
                            }
                        }
                     mAdapter.notifyDataSetChanged(capitalRecordTypeList)
                    }
                }

            }
        })
        dialog.show()

        return dialog
    }
}
