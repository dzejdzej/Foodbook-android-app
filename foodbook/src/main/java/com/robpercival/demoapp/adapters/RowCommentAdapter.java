package com.robpercival.demoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.robpercival.demoapp.R;
import com.robpercival.demoapp.rest.dto.CommentDto;

import java.util.ArrayList;
import java.util.List;

public class RowCommentAdapter extends BaseAdapter {

    private Context singleRestaurantActivity;
    private List<CommentDto> data;
    private static LayoutInflater inflater = null;

    public RowCommentAdapter(Context singleRestaurantActivity, List<CommentDto> data) {
        this.singleRestaurantActivity = singleRestaurantActivity;
        if(data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
        inflater = (LayoutInflater) singleRestaurantActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row_comment, null);

        final CommentDto dto = data.get(position);

        TextView commentOwnerName = vi.findViewById(R.id.commentOwnerName);
        commentOwnerName.setText(dto.getUser());

        TextView commentText = vi.findViewById(R.id.commentText);
        commentText.setText(dto.getText());

        //TextView commentDate = vi.findViewById(R.id.commentDate);
        //commentDate.setText(dto.getDate().toString());

        return vi;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
