package com.john.test;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.john.neihanduanzi.R;
import com.john.neihanduanzi.bean.ImageEntity;
import com.john.neihanduanzi.client.ClientAPI;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.android.volley.Response;

/**
 * 这就是一个测试入口，所有的测试内容都可以在这里
 * 
 * @author John
 * 
 */
public class TestActivity extends Activity implements Response.Listener<String> {
	private RequestQueue queue;
	/**
	 * 分类ID, 1 代表文本
	 */

	private static final int CATEGORY_TEXT = 1;
	/**
	 * 分类ID, 2代表图片
	 */

	private static final int CATEGORY_IMAGE = 2;

	/*
	 * 数据
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		queue = Volley.newRequestQueue(this);
		// "http://ic.snssdk.com/2/essay/zone/category/data/?category_id=1&level=6&count=30&iid=2337593504&device_id=2757969807&ac=wifi&channel=wandoujia&aid=7&app_name=joke_essay&version_code=302&device_platform=android&device_type=KFTT&os_api=15&os_version=4.0.3&openudid=b90ca6a3a19a78d6";
		int itemCount = 1;

		ClientAPI.getList(queue, CATEGORY_IMAGE, itemCount, this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

	@Override
	public void onResponse(String arg0) {
		// TODO Auto-generated method stub
		try {
			JSONObject json = new JSONObject(arg0);
			JSONObject obj = json.getJSONObject("data");
			JSONArray jsonArray = obj.getJSONArray("data");
			int len = jsonArray.length();
			for (int i = 0; i < len; i++) {
				JSONObject item = jsonArray.getJSONObject(i);//??????????????????????????//
				//把imageEntity单独提成了一个类 ，进行解析
				ImageEntity imageEntity = new ImageEntity();
				imageEntity.parseJson(item);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
