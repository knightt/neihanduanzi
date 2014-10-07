package com.john.neihanduanzi.activities;

import java.util.List;

import com.john.neihanduanzi.R;
import com.john.neihanduanzi.adapters.DetailPagerAdapter;
import com.john.neihanduanzi.bean.DataStore;
import com.john.neihanduanzi.bean.TextEntity;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class EssayDeatilActivity extends FragmentActivity {

	private ViewPager pager;
    private DetailPagerAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_essay_deatil);
		
		pager = (ViewPager) this.findViewById(R.id.detail_pager_content);
		
		//设置 FragmentPagerAdapter
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		int category = 1;
		
		int currentEssayPosition = 0;
		if(extras !=null){
			category = extras.getInt("category",1);
			currentEssayPosition = extras.getInt("currentEssayPosition",0);
		}
		
		DataStore dataStore = DataStore.getInstance();
		List<TextEntity> entities = null;
		if (category == 1) {
			entities = dataStore.getTextEntity();
		}else if(category == 2){
			entities = dataStore.getImageEntity();
		}
		adapter = new DetailPagerAdapter(entities, getSupportFragmentManager());
		pager.setAdapter(adapter);
		
		if(currentEssayPosition > 0){
			pager.setCurrentItem(currentEssayPosition);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.essay_deatil, menu);
		return true;
	}

}
