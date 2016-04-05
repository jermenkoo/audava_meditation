package com.lambroszannettos.themindmanifesto;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lambros on 19/03/16.
 */
public class ChooseMeditation extends BaseActivity {

    private ListView interventionList;
    private MyFunctions functions = MyFunctions.getUniqueInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Load the layout
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.choose_med_layout, contentFrameLayout);

        //The ListView to be populated
        interventionList = (ListView) findViewById(R.id.med_list);

        //Arraylist to hold all sets of titles and albums (i.e. categories
        //for audio files
        ArrayList<HashMap<String, String>> itemsList = new ArrayList<>();

        //Get the list of all m4a files in Raw folder
        ArrayList<File> files = functions.getAllFilesInAssetByExtension(this, AppConstant.INTERVENTION_FOLDER, ".m4a");

        //Add those files to the ListView
        for (File f : files) {
            String title = getAudioTitle(this, f, AppConstant.INTERVENTION_FOLDER);
            String album = getAudioAlbum(this, f, AppConstant.INTERVENTION_FOLDER);

            HashMap<String, String> itemsMap = new HashMap<>(2);

            itemsMap.put("title", title);
            itemsMap.put("album", album);
            itemsList.add(itemsMap);
        }

        //Adapter for converting file items into View items to populate the ListView
        SimpleAdapter adapter2 = new SimpleAdapter(this, itemsList,
                android.R.layout.simple_list_item_2,
                new String[]{"title", "album"},
                new int[]{android.R.id.text1,
                        android.R.id.text2});
        interventionList.setAdapter(adapter2);

        //Actions when item is selected
        interventionList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(ChooseMeditation.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Given an audio file, this returns the ID3 Title Field
    private String getAudioTitle(Context context, File file, String path) {
        AssetFileDescriptor afd;
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        try {
            afd = context.getAssets().openFd(path + file.getAbsolutePath());
            mmr.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String title =
                mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        return title;
    }

    private String getAudioAlbum(Context context, File file, String path) {
        AssetFileDescriptor afd;
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        try {
            afd = context.getAssets().openFd(path + file.getAbsolutePath());
            mmr.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String album =
                mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
        return album;
    }
}
