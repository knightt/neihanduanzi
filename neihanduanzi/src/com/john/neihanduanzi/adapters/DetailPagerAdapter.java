package com.john.neihanduanzi.adapters;


import java.util.List;

import com.john.neihanduanzi.bean.TextEntity;
import com.john.neihanduanzi.fragments.DetailContentFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class DetailPagerAdapter extends FragmentPagerAdapter {

	private List<TextEntity> entities;
	public DetailPagerAdapter(List<TextEntity> entities,FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.entities= entities;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		DetailContentFragment fragment = new DetailContentFragment();
		
		Bundle arguements = new Bundle();
		
		TextEntity entity = entities.get(arg0);
		arguements.putSerializable("entity", entity);
		fragment.setArguments(arguements);
		return fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return entities.size();
	}

}
