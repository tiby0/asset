package z.t.assetmanagement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import z.t.assetmanagement.R
import z.t.assetmanagement.dataBase.CapitalRecordType


class TypeAdapter(private val mContext: Context, private var data: List<CapitalRecordType>?) :
    RecyclerView.Adapter<TypeAdapter.ViewHolder>() {

    //声明长按接口
    private var longClickLisenter: OnReItemLongClickLisenter? = null
    //声明点击接口
    private var clickLisenter: OnReItemOnclickLisenter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //负责创建视图
        val view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false)
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


    fun notifyDataSetChanged(dataList: List<CapitalRecordType>) {
        this.data = dataList
        super.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //相当于listview的adapter中的getview方法
        //负责将数据绑定到视图上

        holder.typeName.text = data!![position].name
        holder.amount.text = data!![position].twoType
        holder.change.text = ""

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

        var change: TextView = itemView.findViewById(R.id.change)
        var amount: TextView = itemView.findViewById(R.id.amount)
        var typeName: TextView = itemView.findViewById(R.id.typeName)

    }
}
