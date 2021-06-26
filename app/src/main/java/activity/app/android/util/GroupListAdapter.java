package activity.app.android.util;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import activity.app.android.R;
import activity.app.android.model.Group;

public class GroupListAdapter implements ListAdapter {
    ArrayList<Group> groupList;
    Context context;
    public GroupListAdapter(Context context, ArrayList<Group> groupList) {
        this.groupList = groupList;
        this.context=context;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return groupList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }@Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return groupList.size();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Group group = groupList.get(position);
        if(convertView==null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            TextView title = convertView.findViewById(R.id.group_title);
            ImageView img = convertView.findViewById(R.id.group_image);
            title.setText(group.getGroupName());

            Glide.with(context)
                    .load(group.getCoverURL())
                    .into(img);
        }
        return convertView;
    }
}
