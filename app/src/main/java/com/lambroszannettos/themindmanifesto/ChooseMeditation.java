package com.lambroszannettos.themindmanifesto;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lambros on 19/03/16.
 */
public class ChooseMeditation extends BaseActivity {

    private ListView interventionList;
    private MyFunctions functions = MyFunctions.getUniqueInstance();
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the layout
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.choose_med_layout, contentFrameLayout);

        // The ListView to be populated
        interventionList = (ListView) findViewById(R.id.med_list);
        title = (TextView) findViewById(R.id.txt_choose_med_title);

        // ArrayList to hold all sets of titles and albums (i.e. categories)
        // for audio files
        final ArrayList<HashMap<String, String>> itemsList = new ArrayList<>();

        title.setText("Browsing: " + interventionCategory);

        // LOG
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Interventions")
                .setAction("Browsing: " + interventionCategory)
                .build());

        // Add those files to the ListView
        for (File f : allAudioFiles) {
            String title = functions.getAudioTitle(this, f, AppConstant.INTERVENTION_FOLDER);
            String album = functions.getAudioAlbum(this, f, AppConstant.INTERVENTION_FOLDER);

            if (album.equals(interventionCategory) || interventionCategory.equals(AppConstant.BROWSE_ALL)) {
                HashMap<String, String> itemsMap = new HashMap<>(2);
                itemsMap.put("title", title);
                itemsMap.put("album", album);
                itemsList.add(itemsMap);
            }
        }

        // Adapter for converting file items into View items to populate the ListView
        SimpleAdapter adapter = new SimpleAdapter(this, itemsList,
                android.R.layout.simple_list_item_2,
                new String[]{"title", "album"},
                new int[]{android.R.id.text1,
                        android.R.id.text2});
        interventionList.setAdapter(adapter);

        // Actions when item is selected
        interventionList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Get selected item's title and album
                String tempTitle = itemsList.get(position).get("title");
                String tempAlbum = itemsList.get(position).get("album");

                //Call function which actually handles the switch of selected audio file
                selectIntervention(tempTitle, tempAlbum, true);
            }
        });
    }
}
