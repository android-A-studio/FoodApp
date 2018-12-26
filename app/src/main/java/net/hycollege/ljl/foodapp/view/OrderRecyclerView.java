package net.hycollege.ljl.foodapp.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.hycollege.ljl.foodapp.R;

import java.util.ArrayList;

/**
 * 实现加载菜单列表的类
 */
class OrderRecyclerView extends RecyclerView.Adapter<OrderRecyclerView.Viewholder> {
    private final Context context;
    private ArrayList<String> notelist;

    public OrderRecyclerView(Context mcontext) {
        this.context = mcontext;
        notelist = new ArrayList<>();
        notelist.add("aa");
       /* notelist.add("cc");
        notelist.add("dd");
        notelist.add("bb");*/
    }

    /**
     * 负责承载每个子项的布局
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View iten_recycler = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderrecyclerview, parent, false);
        Viewholder viewHolder = new Viewholder(iten_recycler);
        return viewHolder;
    }

    /**
     * 负责将每个子项holder绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        setMenuListener(holder.recycler_image_menu, notelist.get(position));
        holder.recycler_image_menu.setImageResource(R.mipmap.chicken);
        switch (position) {
            case 0:

                break;
            case 1:
                //holder.recycler_image_menu.setImageResource(R.mipmap.beautiful2);
                break;
        }
    }

    //菜单列表监听
    private void setMenuListener(ImageView recycler_image_menu, final String s) {
        recycler_image_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "666" + s, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return notelist.size();
    }

    public static class Viewholder extends ViewHolder {
        ImageView recycler_image_notetype, recycler_image_menu;
        TextView recycler_text_note, recycler_text_time;

        public Viewholder(View itemView) {
            super(itemView);
            recycler_image_menu = (ImageView) itemView.findViewById(R.id.order_data_icon);
        }
    }
}
