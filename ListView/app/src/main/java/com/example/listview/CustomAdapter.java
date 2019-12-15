package com.example.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    ArrayList<CustomItem> listViewItemList = new ArrayList<>();


    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public CustomItem getItem(int i) {
        return listViewItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Context mContext = viewGroup.getContext();
        // 추가한 xml 의 위젯들을 연결해주는 역할을 하는 메소드
        ViewHolder holder;

        if(view == null)
        {
            holder = new ViewHolder();

            // 레이아웃 위에 신규 레이아웃 덮어씌우는 서비스
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout, null);

            // 아래 코드에는 위에서 생성한 xml의 위젯을 findViewById 로 연결해줌.
            holder.tv_id = view.findViewById(R.id.tv_id);
            holder.tv_pass = view.findViewById(R.id.tv_pass);
            holder.tv_desc = view.findViewById(R.id.tv_desc);
            holder.tv_etc = view.findViewById(R.id.tv_etc);

            // 마지막으로 각 뷰에 태그를 부여해줌. 각 리스트 뷰 아이템의 변별력을 위해서 해줌.
            view.setTag(holder);

        }
        else
            holder = (ViewHolder)view.getTag();
        //아래에는  커스텀한 XML에 Parse 한 데이터베이스 정보들을 설정해주는 코드를 작성함.
        CustomItem mData = getItem(i);
        // 위젯에 각 아이템에 맞는 데이터를 설정해줌.
        holder.tv_id.setText(mData.getId());
        holder.tv_pass.setText(mData.getPass());
        holder.tv_desc.setText(mData.getDesc());
        holder.tv_etc.setText(mData.getEtc());


        return view;
    }
    class ViewHolder{
        public TextView tv_id;
        public TextView tv_pass;
        public TextView tv_desc;
        public TextView tv_etc;

    }
    public void addItem(String _id, String _pass, String _desc, String _etc)
    {
        //Parse 후 전달받은 데이터들을 CustomItem 객체로 만들어주고
        // 어레이리스트에 추가해주는 코드.
        CustomItem ci = new CustomItem(_id, _pass, _desc, _etc);
        listViewItemList.add(ci);
    }

}
