package com.john.neihanduanzi.bean;

import java.util.LinkedList;
import java.util.List;

public class DataStore {

	private static DataStore outInstance;
	
	public static  DataStore getInstance() {
		if(outInstance == null){
			outInstance = new DataStore();
		}
		return outInstance;
		
	}
	
	private List<TextEntity> textEntities;
	private List<TextEntity> imageEntities;
	private DataStore() {
		// TODO Auto-generated constructor stub
		textEntities = new LinkedList<TextEntity>();
		imageEntities = new LinkedList<TextEntity>();
	}
	/*
	 * 把获取到的文本段子列表放在最前边，这个方法针对的是下拉刷新的操作
	 */
	public void addTextEntities(List<TextEntity> entities){
		if(entities != null){
			textEntities.addAll(0, entities);
		}
		
	}
	/*
	 * 把获取到的文本段子列表放在最后边，这个方法针对的是上拉查看旧数据的操作
	 */
	public void appendTextEntities(List<TextEntity> entities){
		if(entities != null){
			textEntities.addAll(0, entities);
		}
		
	}
	
	/////////////////////////////////////////////////////////////
	/*
	 * 把获取到的文本段子列表放在最前边，这个方法针对的是下拉刷新的操作
	 */
	public void addImageEntities(List<TextEntity> entities){
		if(entities != null){
			imageEntities.addAll(0, entities);
		}
		
	}
	/*
	 * 把获取到的文本段子列表放在最后边，这个方法针对的是上拉查看旧数据的操作
	 */
	public void appendImageEntities(List<TextEntity> entities){
		if(entities != null){
			imageEntities.addAll(0, entities);
		}
		
	}
	//获取文本段子列表
	public List<TextEntity> getTextEntity(){
		return textEntities;
	}
	//获取图片段子列表
	public List<TextEntity> getImageEntity(){
		return imageEntities;
	}
}
