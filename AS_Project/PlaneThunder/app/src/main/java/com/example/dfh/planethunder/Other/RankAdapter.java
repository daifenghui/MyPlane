package com.example.dfh.planethunder.Other;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dfh.planethunder.R;

import java.util.ArrayList;

/**
 * Created by dfh on 19-8-21.
 */

public class RankAdapter extends ArrayAdapter<Rank> {
    private int resourceId;

    public RankAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Rank> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Rank rank=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView pr=(TextView)view.findViewById(R.id.numbertxv);
        TextView pn=(TextView)view.findViewById(R.id.nametxv);
        TextView ps=(TextView)view.findViewById(R.id.scoretxv);
        pr.setText(""+rank.getRanknum());
        pn.setText(rank.getName());
        ps.setText(""+rank.getScore());
        return view;
    }
}
