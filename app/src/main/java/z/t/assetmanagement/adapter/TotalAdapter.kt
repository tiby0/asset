package z.t.assetmanagement.adapter

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import z.t.assetmanagement.R
import z.t.assetmanagement.dataBase.TotalCapitalRecord
import z.t.assetmanagement.helpClass.ToastUtil
import java.text.SimpleDateFormat


class TotalAdapter(private val mContext: Context, private var data: List<TotalCapitalRecord>?) :
    RecyclerView.Adapter<TotalAdapter.ViewHolder>() {

    //声明长按接口
    private var longClickLisenter: OnReItemLongClickLisenter? = null
    //声明点击接口
    private var clickLisenter: OnReItemOnclickLisenter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //负责创建视图
        val view = LayoutInflater.from(mContext).inflate(R.layout.itemlist, parent, false)
        return ViewHolder(view)

    }


    //定义接口
    //长按时间
    interface OnReItemLongClickLisenter {
        fun OnReItemLongClick(view: View, position: Int)
    }

    //点击事件
    interface OnReItemOnclickLisenter {
        fun OnReItemOnclick(view: View, position: Int)

    }

    //长按时间
    fun setOnReItemLongClickLisenter(longClickLisenter: OnReItemLongClickLisenter) {

        this.longClickLisenter = longClickLisenter
    }

    //点击事件
    fun setOnReItemOnclickLisenter(clickLisenter: OnReItemOnclickLisenter) {
        this.clickLisenter = clickLisenter
    }


    fun notifyDataSetChanged(dataList: List<TotalCapitalRecord>) {
        this.data = dataList
        super.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //相当于listview的adapter中的getview方法
        //负责将数据绑定到视图上

        holder.total.text = data!![position].totalamount.toString()
        val format = SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E")
        val time = format.format(data!![position].createdate)
        holder.createDate.text = time
        holder.phase.text = "第 ${data!![position].phase} 期"
        var totalCapitalRecord: TotalCapitalRecord? = null
        var change = 0.0
        if (position == data!!.size - 1) {
            change = data!![position].totalamount
            totalCapitalRecord = null
        } else {
            change = data!![position].totalamount - data!![position + 1].totalamount
            totalCapitalRecord = data!![position + 1]
        }
        if (change > 0) {
            holder.totalChange.text = "+$change"
            holder.totalChange.setTextColor(ContextCompat.getColor(mContext, R.color.text_red))
        } else {
            holder.totalChange.text = "$change"
            holder.totalChange.setTextColor(ContextCompat.getColor(mContext, R.color.text_green))
        }
        holder.total.setOnClickListener {
            if (holder.recyclerview.isVisible) holder.recyclerview.visibility = View.GONE
            else holder.recyclerview.visibility = View.VISIBLE
        }
        holder.recyclerview.setHasFixedSize(true)//设置固定大小
        holder.recyclerview.itemAnimator = DefaultItemAnimator()//设置默认动画
        val mLayoutManage = LinearLayoutManager(mContext)
        holder.recyclerview.isNestedScrollingEnabled = false//禁止滑动
        //mLayoutManage.setOrientation(OrientationHelper.HORIZONTAL)//设置滚动方向，横向滚动
        holder.recyclerview.layoutManager = mLayoutManage
        val mAdapter = RecycleViewAdapter(mContext, data!![position].capitalRecordlist,totalCapitalRecord)
        holder.recyclerview.adapter = mAdapter

        //调用适配器里的方法
        mAdapter.setOnReItemOnclickLisenter { view, position2 ->
            ToastUtil.showSnackbarS(view, "2S")

//            val builder = AlertDialog.Builder(mContext)//修改这里
//            val view = View.inflate(mContext, R.layout.lnui_total, null)
//            val name: EditText = view.findViewById(R.id.name)
//            val amount: EditText = view.findViewById(R.id.amount)
//            val phase: EditText = view.findViewById(R.id.phase)
//            val remark: EditText = view.findViewById(R.id.remark)
//            val yes: Button = view.findViewById(R.id.yes)
//            val no: Button = view.findViewById(R.id.no)
//            amount.setText(data!![position].capitalRecordlist[position2].amount.toString())
//            name.setText(data!![position].capitalRecordlist[position2].tpyeName)
//            val format = SimpleDateFormat("yyyy年MM月dd日 HH:mm E")
//            val time = format.format(data!![position].capitalRecordlist[position2].createdate)
//            remark.setText(time)
//            builder.setView(view)
//            val dialog = builder.create()
//            no.setOnClickListener { dialog.dismiss() }
//            yes.setOnClickListener {
//                data!![position].capitalRecordlist[position2].phase = phase.text.toString().toInt()
//                data!![position].capitalRecordlist[position2].save()
////                mAdapter.notifyDataSetChanged(data!![position].capitalRecordlist)
////                notifyDataSetChanged()
//            }
//            dialog.show()
        }

        mAdapter.setOnReItemLongClickLisenter { view, position2 ->
            //ToastUtil.showSnackbar(view,"2L")

            AlertDialog.Builder(mContext).setTitle("系统提示").setMessage("删除此条记录？")
                .setNegativeButton("取消") { dialogInterface: DialogInterface, i: Int ->
                }
                .setPositiveButton("确定") { dialogInterface: DialogInterface, i: Int ->
                    data!![position].capitalRecordlist[position2].delete()
                    data!![position].totalamount -= data!![position].capitalRecordlist[position2].amount
                    data!![position].capitalRecordlist -= data!![position].capitalRecordlist[position2]

                    mAdapter.notifyDataSetChanged(data!![position].capitalRecordlist)
                    notifyDataSetChanged()
                }
                .show()
        }


        ///////////////////////////////////////////////////////////////////////////////////
        holder.itemView.setOnClickListener {
            val layoutPosition = holder.layoutPosition
            val itemView = holder.itemView
            if (clickLisenter != null) {
                clickLisenter!!.OnReItemOnclick(itemView, layoutPosition)

            }
        }

        holder.itemView.setOnLongClickListener {
            val adapterPosition = holder.adapterPosition
            val itemView = holder.itemView
            if (longClickLisenter != null) {
                longClickLisenter!!.OnReItemLongClick(itemView, adapterPosition)

            }
            true
        }


    }

    override fun getItemCount(): Int {
        return if (data == null) 0 else data!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var recyclerview: RecyclerView = itemView.findViewById(R.id.recyclerview)
        var totalChange: TextView = itemView.findViewById(R.id.totalChange)
        var total: TextView = itemView.findViewById(R.id.total)
        var phase: TextView = itemView.findViewById(R.id.phase)
        var createDate: TextView = itemView.findViewById(R.id.createDate)
    }
}
