package com.example.administrator.t;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


/**
 * Created by Administrator on 2015/7/21.
 */
public class mainPage_fragment extends android.support.v4.app.Fragment{
    View rootview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_main, container, false);

        final ListView listView = (ListView)rootview.findViewById(R.id.achievement_list);
        int[] imageId = new int[]{R.drawable.alarmsmall,R.drawable.sentencesmall,R.drawable.coinsmall,R.drawable.alarmsmall,R.drawable.sentencesmall};
        String[] message = new String[]{getString(R.string.message_review),getString(R.string.message_correct),getString(R.string.message_earning),getString(R.string.message_review),getString(R.string.message_correct)   };
        List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>();

        for (int i=0; i<imageId.length; i++){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("message",message[i]);
            map.put("image",imageId[i]);
            mapList.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this.getActivity(),mapList, R.layout.messages_main,new String[]{"image","message"},new int[]{R.id.image,R.id.message});
        listView.setAdapter(adapter);
        return rootview;
    }
}
