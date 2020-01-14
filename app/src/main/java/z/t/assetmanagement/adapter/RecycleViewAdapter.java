package z.t.assetmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import z.t.assetmanagement.R;
import z.t.assetmanagement.dataBase.CapitalRecord;
import z.t.assetmanagement.dataBase.TotalCapitalRecord;


import java.text.SimpleDateFormat;
import java.util.List;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    private Context mContext;
    private List<CapitalRecord> data;
    private TotalCapitalRecord totalCapitalRecord;


    public RecycleViewAdapter(Context context, List<CapitalRecord> brings, TotalCapitalRecord totalCapitalRecord2) {
        mContext = context;
        data = brings;
        totalCapitalRecord = totalCapitalRecord2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //负责创建视图
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }


    //定义接口
    //长按时间
    public interface OnReItemLongClickLisenter {
        void OnReItemLongClick(View view, int position);
    }

    //点击事件
    public interface OnReItemOnclickLisenter {
        void OnReItemOnclick(View view, int position);

    }

    //声明长按接口
    private OnReItemLongClickLisenter longClickLisenter;
    //声明点击接口
    private OnReItemOnclickLisenter clickLisenter;

    //长按时间
    public void setOnReItemLongClickLisenter(OnReItemLongClickLisenter longClickLisenter) {

        this.longClickLisenter = longClickLisenter;
    }

    //点击事件


    public void setOnReItemOnclickLisenter(OnReItemOnclickLisenter clickLisenter) {
        this.clickLisenter = clickLisenter;
    }


    public void notifyDataSetChanged(List<CapitalRecord> dataList) {
        this.data = dataList;
        super.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //相当于listview的adapter中的getview方法
        //负责将数据绑定到视图上

        holder.amount.setText(String.valueOf(data.get(position).getAmount()));
        holder.type.setText(data.get(position).getName());
        holder.change.setText(String.valueOf(data.get(position).getChange()));
        double change = data.get(position).getAmount();
        if (totalCapitalRecord != null) {
            for (CapitalRecord c : totalCapitalRecord.getCapitalRecordlist()) {
                if (data.get(position).getName().equals(c.getName())) {
                    change = data.get(position).getAmount()-c.getAmount();
                    break;
                }
            }
        }
        if (change > 0) {
            holder.change.setText("+" + change);
            holder.change.setTextColor(ContextCompat.getColor(mContext, R.color.text_red));
        } else {
            holder.change.setText("" + change);
            holder.change.setTextColor(ContextCompat.getColor(mContext, R.color.text_green));
        }

        if (data.get(position).getRemark() != null && !"".equals(data.get(position).getRemark())) {
            holder.remark.setVisibility(View.VISIBLE);
            holder.remark.setText(data.get(position).getRemark());
        } else {
            holder.remark.setVisibility(View.GONE);
        }

        ///////////////////////////////////////////////////////////////////////////////////
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int layoutPosition = holder.getLayoutPosition();
                View itemView = holder.itemView;
                if (clickLisenter != null) {
                    clickLisenter.OnReItemOnclick(itemView, layoutPosition);

                }

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                View itemView = holder.itemView;
                if (longClickLisenter != null) {
                    longClickLisenter.OnReItemLongClick(itemView, adapterPosition);

                }
//                ToastUtil.showSnackbarS(v,"ccccc");
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder {
        //@BindView(R.id.name)
        TextView remark = itemView.findViewById(R.id.remark);
        //@BindView(R.id.type)
        TextView type = itemView.findViewById(R.id.typeName);
        //@BindView(R.id.amount)
        TextView amount = itemView.findViewById(R.id.amount);
        //@BindView(R.id.change)
        TextView change = itemView.findViewById(R.id.change);

        //@BindView(R.id.time)
//        TextView time = itemView.findViewById(R.id.time);
        //@BindView(R.id.phase)
//        TextView phase = itemView.findViewById(R.id.phase);

        ViewHolder(View view) {
            super(view);
//            ButterKnife.bind(this, view);
        }
    }
}
