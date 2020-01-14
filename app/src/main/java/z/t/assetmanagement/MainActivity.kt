package z.t.assetmanagement

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*

import org.litepal.LitePal
import org.litepal.extension.find
import org.litepal.extension.findFirst

import z.t.assetmanagement.adapter.TotalAdapter
import z.t.assetmanagement.dataBase.CapitalRecord
import z.t.assetmanagement.dataBase.TotalCapitalRecord
import z.t.assetmanagement.enumpack.LevelEnum
import z.t.assetmanagement.enumpack.StatusEnum
import z.t.assetmanagement.enumpack.TypeEnum
import z.t.assetmanagement.helpClass.ProcessUtil
import z.t.assetmanagement.helpClass.SpringUtil
import z.t.assetmanagement.helpClass.ToastUtil
import z.t.assetmanagement.helpClass.UUIDGenerator
import z.t.assetmanagement.mpchartexample.LineChartActivity2
import z.t.assetmanagement.style.TimeDialog

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var mAdapter: TotalAdapter
    private lateinit var recycleView: RecyclerView
    lateinit var totalCapitalRecordList: List<TotalCapitalRecord>
    private lateinit var totalCapitalRecordAddList: List<TotalCapitalRecord>
    private var start: Int = 0
    private lateinit var refreshLayout: SmartRefreshLayout
    val typelist = ArrayList<String>()
    var namelist = ArrayList<String>()
    val levellist = ArrayList<String>()
    val statuslist = ArrayList<String>()
    lateinit var mPopup: ListPopupWindow
    lateinit var lastCaplist: List<CapitalRecord>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        select()
        fab.setOnClickListener {
            // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //.setAction("Action", null).show()
            showAdd(this)
        }
//        fab2.setOnClickListener {
//            // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//            //.setAction("Action", null).show()
//            var totalCapitalRecordListppp = LitePal.findAll(TotalCapitalRecord::class.java)
//            for (t in totalCapitalRecordListppp) {
//                t.phase = 0
//                t.save()
//            }
//            var capitalRecordListppp = LitePal.findAll(CapitalRecord::class.java)
//            for (t in capitalRecordListppp) {
//                t.phase = 0
//                t.save()
//            }
//        }
//        fab2.visibility = View.GONE
        typelist.clear()
        for (t in TypeEnum.values()) {
            typelist.add(t.name)
        }
        levellist.clear()
        for (t in LevelEnum.values()) {
            levellist.add(t.name)
        }
        statuslist.clear()
        for (t in StatusEnum.values()) {
            statuslist.add(t.name)
        }
        val capPhase = LitePal.order("phase desc").findFirst<CapitalRecord>()
        lastCaplist = LitePal.where("phase = ${capPhase?.phase ?: 0}").find<CapitalRecord>()
        for (c in lastCaplist) {
            this.namelist.add(c.name)
        }
        mPopup = ListPopupWindow(this)
    }

    private fun showAdd(activity: AppCompatActivity): AlertDialog {
        val builder = AlertDialog.Builder(activity)//修改这里

        val view = View.inflate(activity, R.layout.add, null)
        val name: EditText = view.findViewById(R.id.name)
        val amount: EditText = view.findViewById(R.id.amount)
        val phase: EditText = view.findViewById(R.id.phase)
        val remark: EditText = view.findViewById(R.id.remark)
        val yes: Button = view.findViewById(R.id.yes)
        val no: Button = view.findViewById(R.id.no)
        val type: Spinner = view.findViewById(R.id.type)
        val level: Spinner = view.findViewById(R.id.level)
        val levelL: LinearLayout = view.findViewById(R.id.levelL)
        val status: Spinner = view.findViewById(R.id.status)
        val statusL: LinearLayout = view.findViewById(R.id.statusL)
        val more: LinearLayout = view.findViewById(R.id.more_name)

        pop(name, type)
        SpringUtil.adapterDataString(typelist, type, this)
        SpringUtil.adapterDataString(levellist, level, this)
        SpringUtil.adapterDataString(statuslist, status, this)
        val capPhase = LitePal.order("phase desc").findFirst<CapitalRecord>()
        if (capPhase != null) {
            phase.hint = capPhase.phase.toString() + "   " + ProcessUtil.ConverToString(capPhase.createdate)
        }
//        phase.isFocusable = true
//        phase.isFocusableInTouchMode = true
//        phase.requestFocus()
//        var timer =  Timer()
//        timer.schedule(object :TimerTask()
//        {
//            override fun run()
//            {
//                var inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                inputManager.showSoftInput(phase, 0)
//            }
//        },550)


        type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            //当选中某一个数据项时触发该方法
            /*
             * parent接收的是被选择的数据项所属的 Spinner对象，
             * view参数接收的是显示被选择的数据项的TextView对象
             * position接收的是被选择的数据项在适配器中的位置
             * id被选择的数据项的行号
             */
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                amount.setText("")
                remark.setText("")
                if (type.selectedItem == "其他") {
                    levelL.visibility = View.VISIBLE
                    statusL.visibility = View.VISIBLE
                } else {
                    levelL.visibility = View.GONE
                    statusL.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // TODO Auto-generated method stub
            }
        }
        amount.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                c: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                var s = c
                //删除.后面超过两位的数字
                if (s.toString().contains(".")) {
                    if (s.length - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(
                            0,
                            s.toString().indexOf(".") + 3
                        )
                        amount.setText(s)
                        amount.setSelection(s.length)
                    }
                }
                //如果.在起始位置,则起始位置自动补0
                if (s.toString().trim { it <= ' ' }.substring(0) == ".") {
                    s = "0$s"
                    amount.setText(s)
                    amount.setSelection(2)
                }
                //如果起始位置为0并且第二位跟的不是".",则无法后续输入
                if (s.toString().startsWith("0") && s.toString().trim { it <= ' ' }.length > 1) {
                    if (s.toString().substring(1, 2) != ".") {
                        amount.setText(s.subSequence(0, 1))
                        amount.setSelection(1)
                        return
                    }
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })

        builder.setView(view)
//        builder.setCancelable(false)
        val dialog = builder.create()
        more.setOnClickListener {

            if (!mPopup.isShowing && namelist.size > 0) { // Log.i(TAG, "切换为角向上图标");
//                more1.setImageResource(R.mipmap.login_more_down) // 切换图标
                mPopup.show()// 显示弹出窗口
            }
        }

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

                val capitalRecord = CapitalRecord()
                capitalRecord.unid = UUIDGenerator.getUUID()
                capitalRecord.createdate = nowdate
                capitalRecord.versiondate = nowdate
                capitalRecord.tpye = type.selectedItem.toString()
                capitalRecord.tpyeName = type.selectedItem.toString()
                if (type.selectedItem == "其他") {
                    capitalRecord.level = level.selectedItem.toString()
                    capitalRecord.status = status.selectedItem.toString()
                } else if (type.selectedItem == "金融产品" || type.selectedItem == "股票") {
                    capitalRecord.level = "中"
                    capitalRecord.status = "T1"
                } else {
                    capitalRecord.level = "低"
                    capitalRecord.status = "随时"
                }
                when {
                    TextUtils.isEmpty(phase.text.toString()) -> {
                        ToastUtil.showSnackbarS(view, "周期不能为空")
                        return
                    }
                    TextUtils.isEmpty(name.text.toString()) -> {
                        ToastUtil.showSnackbarS(view, "名称不能为空")
                        return
                    }
                    TextUtils.isEmpty(amount.text.toString()) -> {
                        ToastUtil.showSnackbarS(view, "数额不能为空")
                        return
                    }
                    else -> {
                        val capitalisBeing =
                            LitePal.where("phase = '${phase.text}' and name = '${name.text}'")
                                .findFirst<CapitalRecord>()
                        if (capitalisBeing != null) {
                            ToastUtil.showSnackbarS(view, "该记录重复")
                            return
                        }
//                        val capitalRecordold = LitePal.where("phase = '${phase.text.toString().toInt() - 1}' and name = '${name.text}'").findFirst<CapitalRecord>()

                        capitalRecord.phase = phase.text.toString().toInt()
                        capitalRecord.name = name.text.toString()
                        capitalRecord.amount = amount.text.toString().toDouble()
                        capitalRecord.remark = remark.text.toString()
//                        capitalRecord.change = capitalRecord.amount - (capitalRecordold?.amount ?: 0.0)

                        val totalCapitalRecord = LitePal.where("phase = '${capitalRecord.phase}'")
                            .findFirst<TotalCapitalRecord>()
//                        val totalCapitalRecordold = LitePal.where("phase = '${capitalRecord.phase.toInt() - 1}'").findFirst<TotalCapitalRecord>()

                        if (totalCapitalRecord != null) {
                            totalCapitalRecord.versiondate = capitalRecord.versiondate
                            totalCapitalRecord.totalamount += capitalRecord.amount
//                            totalCapitalRecord.totalchange = totalCapitalRecord.totalamount - (totalCapitalRecordold?.totalamount ?: 0.0)
                            totalCapitalRecord.save()
                        } else {
                            val totalCapital = TotalCapitalRecord()
                            totalCapital.createdate = capitalRecord.createdate
                            totalCapital.totalamount = capitalRecord.amount
//                            totalCapital.totalchange = totalCapital.totalamount - (totalCapitalRecordold?.totalamount?:0.0)
                            totalCapital.phase = capitalRecord.phase
                            totalCapital.save()
                        }
                        val s = capitalRecord.save()
                        totalCapitalRecordList = LitePal.order("phase desc").find()
                        for (t in totalCapitalRecordList) {
                            t.capitalRecordlist = t.getList()
                        }
                        mAdapter.notifyDataSetChanged(totalCapitalRecordList)

                        if (s) {
                            ToastUtil.showSnackbarS(view, "添加成功")
                        } else {
                            ToastUtil.showSnackbarS(view, "添加失败")
                        }
                    }
                }

            }
        })
        dialog.show()

        return dialog
    }

    private fun select() {
        totalCapitalRecordList = LitePal.order("phase desc").limit(15).find()
        for (t in totalCapitalRecordList) {
            t.capitalRecordlist = t.getList()
        }
        pollToRefreshListView()

    }

    private fun selectAdd(i: Int) {
        totalCapitalRecordAddList = LitePal.order("phase desc")
            .limit(15).offset(i * 15).find()
        if (totalCapitalRecordAddList.isNotEmpty()) {
            for (t in totalCapitalRecordAddList) {
                t.capitalRecordlist = t.getList()
            }
            totalCapitalRecordList += totalCapitalRecordAddList
            mAdapter.notifyDataSetChanged(totalCapitalRecordList)
        } else {
            ToastUtil.showLong(this, "没有更多数据了")
            start -= 1
        }
    }

    private fun pop(et: EditText, sp: Spinner) {

        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, namelist)
        mPopup.setAdapter(adapter)
        mPopup.width = ViewGroup.LayoutParams.WRAP_CONTENT
        mPopup.height = ViewGroup.LayoutParams.WRAP_CONTENT
        mPopup.isModal = true
        mPopup.anchorView = et
        mPopup.setOnItemClickListener { parent, view, position, id ->
            et.setText(lastCaplist[position].name)
            SpringUtil.adapterDataString2(sp, lastCaplist[position].tpyeName)
            mPopup.dismiss()
        }
    }

    private fun pollToRefreshListView() {
        refreshLayout = findViewById(R.id.refreshLayout)
        refreshLayout.setOnRefreshListener { refreshlayout ->
            select()
            refreshlayout.finishRefresh()
            //refreshlayout.finishRefresh(2000/*,false*/)//传入false表示刷新失败
        }
        refreshLayout.setOnLoadMoreListener { refreshlayout ->
            start += 1
            selectAdd(start)
            refreshlayout.finishLoadMore()
            //refreshlayout.finishLoadMore(2000/*,false*/)//传入false表示加载失败
        }

        recycleView = findViewById(R.id.recyclerview)
        recycleView.setHasFixedSize(true)//设置固定大小
        recycleView.itemAnimator = DefaultItemAnimator()//设置默认动画
        val mLayoutManage = LinearLayoutManager(this)
        //mLayoutManage.setOrientation(OrientationHelper.HORIZONTAL)//设置滚动方向，横向滚动
        recycleView.layoutManager = mLayoutManage
        mAdapter = TotalAdapter(this, totalCapitalRecordList)
        recycleView.adapter = mAdapter

        //调用适配器里的方法
        mAdapter.setOnReItemOnclickLisenter(object : TotalAdapter.OnReItemOnclickLisenter {
            override fun OnReItemOnclick(view: View, position: Int) {
                ToastUtil.showSnackbarS(view, "1S")

//                val builder = AlertDialog.Builder(this@MainActivity)//修改这里
//                val view = View.inflate(this@MainActivity, R.layout.lnui_total, null)
//                val name: EditText = view.findViewById(R.id.name)
//                val amount: EditText = view.findViewById(R.id.amount)
//                val phase: EditText = view.findViewById(R.id.phase)
//                val remark: EditText = view.findViewById(R.id.remark)
//                val yes Button = view.findViewById(R.id.yes)
//                val no: Button = view.findViewById(R.id.no)
//                amount.setText(totalCapitalRecordList[position].totalamount.toString())
//                val format = SimpleDateFormat("yyyy年MM月dd日 HH:mm E")
//                val time = format.format(totalCapitalRecordList[position].createdate)
//                remark.setText(time)
//                builder.setView(view)
//                val dialog = builder.create()
//
//                no.setOnClickListener { dialog.dismiss() }
//                yes.setOnClickListener {
//                    totalCapitalRecordList[position].phase = phase.text.toString().toInt()
//                    totalCapitalRecordList[position].save()
//
////                    mAdapter.notifyDataSetChanged(totalCapitalRecordList)
//                }
//                dialog.show()
            }
        })

        mAdapter.setOnReItemLongClickLisenter(object : TotalAdapter.OnReItemLongClickLisenter {
            override fun OnReItemLongClick(view: View, position: Int) {
                TimeDialog(this@MainActivity).setOnClickBottomListener(object : TimeDialog.OnClickBottomListener {

                    override fun onPositiveClick() {
                        for (c in totalCapitalRecordList[position].capitalRecordlist) {
                            c.delete()
                        }
                        totalCapitalRecordList[position].delete()
                        totalCapitalRecordList -= totalCapitalRecordList[position]
                        mAdapter.notifyDataSetChanged(totalCapitalRecordList)
                    }

                    override fun onNegtiveClick() {

                    }
                }).show()

            }
        })

    }

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    fun showInput(et: EditText) {
        et.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * 隐藏键盘
     */
    fun hideInput() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val v = window.peekDecorView()
        if (null != v) {
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    override fun onResume() {
        super.onResume()
        select()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.统计 -> {
                val gson = Gson()
                var m = 15
                if (15 > totalCapitalRecordList.size) m = totalCapitalRecordList.size
                val list10 = totalCapitalRecordList.subList(0, m)
                Collections.sort(list10, SortByPhase()) //排序
                val ss = gson.toJson(list10)
                val intent = Intent(this, LineChartActivity2().javaClass)
                intent.putExtra("list", ss)
                startActivity(intent)
            }
            R.id.action_settings -> ToastUtil.showLong(this, "测试")
        }
        return true
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
    }
    class SortByPhase : Comparator<TotalCapitalRecord>{
        override fun compare(u1:TotalCapitalRecord, u2:TotalCapitalRecord):Int {
            if(u1.phase>u2.phase){
                return 1
            }
            return -1
        }

    }
}
