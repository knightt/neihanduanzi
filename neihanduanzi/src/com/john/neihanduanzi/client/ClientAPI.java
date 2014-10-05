package com.john.neihanduanzi.client;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response.ErrorListener;
/**
 * 所有与服务器接口对接的方法，全部在这个类里面
 * @author John
 *
 */
public class ClientAPI {

	/**
	 * 获取内涵段子列表内容
	 * 
	 * @param categoryType
	 *            要获取的参数类型
	 * @param itemCount
	 *            服务器一次传回来的条目数
	 * @see TestActivity#CATEGORY_TEXT
	 * @see TestActivity#CATEGORY_IMAGE
	 * 
	 * 下载用volley等于又开了一条线程 ，Response.Listener相当于回调  ，在主线程执行
	 */
	public static void getList(RequestQueue queue,int categoryType,int itemCount,Response.Listener<String> responseListener){
		//TODO 测试内涵段子列表接口，获取文本列表
		
		String CATEGORY_LIST_API = "http://ic.snssdk.com/2/essay/zone/category/data/";
		
		//分类参数。根据类型获取不同的数据
		String categoryParam = "category_id=" + categoryType;
	    String countParam = "count="+ itemCount;
	    String deviceTypeParam = "device_type=KFTT";
	    String openUDIDParam = "openudid=b90ca6a3a19a78d6";
	    
	    String url = CATEGORY_LIST_API
				+ "?"
				+ categoryParam
				+ "&"
				+ countParam
				+ "&"
				+ deviceTypeParam
				+ "&"
				+ openUDIDParam
				+ "&level=6&iid=2335870100&device_id=2718056496&ac=wifi&channel=wandoujia&aid=7&app_name=joke_essay&version_code=302&device_platform=android&os_api=17&os_version=4.2.2&uuid=359209028606337";
	     
	    queue.add(new StringRequest(Request.Method.GET, url, responseListener,new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				// TODO Auto-generated method stub
				
			}
		}));
	    
	    queue.start();
	
	
	}
}
