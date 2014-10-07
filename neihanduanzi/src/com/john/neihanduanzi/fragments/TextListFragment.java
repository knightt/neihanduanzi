package com.john.neihanduanzi.fragments;

import java.util.List;

import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.john.neihanduanzi.R;
import com.john.neihanduanzi.activities.EssayDeatilActivity;
import com.john.neihanduanzi.adapters.EssayAdapter;
import com.john.neihanduanzi.bean.DataStore;
import com.john.neihanduanzi.bean.EntityList;
import com.john.neihanduanzi.bean.TextEntity;
import com.john.neihanduanzi.client.ClientAPI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 1.列表界面，第一次启动，并且数据为空的时候，自动加载数据 2.只要列表没有数据，进入这个界面的时候，就尝试加载数据 3.只要有数据，就不进行数据的加载
 * 4.进入这个界面，并且有数据的情况下，尝试检查新信息的个数
 * 
 * @author John
 * 
 */
public class TextListFragment extends Fragment implements OnClickListener,
		OnScrollListener, OnRefreshListener2<ListView>, OnItemClickListener {

	private View quickTools;
	private TextView textNotify;
	//ListView的数据源
	private EssayAdapter adapter;
	//private List<TextEntity> entities;
	
	//获取更新时间标示  就是minTime
	long lastTime;

	// 评论的参数
	private RequestQueue queue;
	/**
	 * 分类ID, 1 代表文本
	 */

	private static final int CATEGORY_TEXT = 1;
	/**
	 * 分类ID, 2代表图片
	 */

	private static final int CATEGORY_IMAGE = 2;

	// 请求分类ID
	private int requestCategory = CATEGORY_TEXT;

	public TextListFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		if (queue == null) {
			queue = Volley.newRequestQueue(getActivity());
		}
		

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			lastTime = savedInstanceState.getLong("lastTime");
			
		}
		View view = inflater.inflate(R.layout.fragment_textlist, container,
				false);

		// 获取标题控件，增加点击，进行 新消息悬浮框显示的功能
		View titleView = view.findViewById(R.id.textlist_title);
		titleView.setOnClickListener(this);

		// TODO 获取listview并且设置数据（以后需要用PullToRefresh进行完善）

		PullToRefreshListView refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.textlist_listview);
		// MARKMARKMARKMARKMARKMARKMARKMARKMARKMARKMARKMARKMARKMARKMARKMARKMARKMARK//
		// 设置上拉与下拉的事件监听
		refreshListView.setOnRefreshListener(this);
		refreshListView.setMode(Mode.BOTH);
		ListView listView = refreshListView.getRefreshableView();
		listView.setOnItemClickListener(this);
		

		// TODO 添加列表上面的快速工具条（Header）
		header = inflater.inflate(R.layout.textlist_header_tools, listView,
				false);
		listView.addHeaderView(header);

		View quickPublish = header.findViewById(R.id.quick_tools_publish);
		quickPublish.setOnClickListener(this);

		View quickReview = header.findViewById(R.id.quick_tools_review);
		quickReview.setOnClickListener(this);

		//?????????????????????????????????????????????????????????????????????
		List<TextEntity> entities = DataStore.getInstance().getTextEntity();
		/*if (entities == null) {
			entities = new LinkedList<TextEntity>();
			
		}*/
		//给adapter 数据
		adapter = new EssayAdapter(getActivity(), entities);
		listView.setOnScrollListener(this);
		listView.setAdapter(adapter);

		// TODO 获取 快速的工具条（发布和审核），用于列表滚动的显示和隐藏

		quickTools = view.findViewById(R.id.textlist_quick_tools);
		quickTools.setVisibility(View.INVISIBLE);

		// 设置 悬浮的工具条 两个命令事件

		quickPublish = quickTools.findViewById(R.id.quick_tools_publish);
		quickPublish.setOnClickListener(this);

		quickReview = quickTools.findViewById(R.id.quick_tools_review);
		quickPublish.setOnClickListener(this);

		// TODO 获取 新条目通知控件，每次有新数据要显示，过一段时间隐藏

		textNotify = (TextView) view.findViewById(R.id.textlist_new_notify);
		textNotify.setVisibility(View.INVISIBLE);

		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		//
		this.adapter = null;
		
		this.header = null;
		
		this.quickTools = null;
		
		this.textNotify = null;
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			int what = msg.what;
			if (what == 1) {
				// TODO what = 1 代表有新消息提醒
				textNotify.setVisibility(View.INVISIBLE);
			}
		}

	};
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putLong("lastTime", lastTime);
	};
	// ////////////////////////////////////////////////////////////////

	// 列表 滚动 ，显示 工具条

	private int lastIndex = 0;
	private View header;

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		int offset = lastIndex - firstVisibleItem;
		if (offset < 0 || firstVisibleItem == 0) {
			// 证明现在移动时向上移动
			if (quickTools != null) {
				quickTools.setVisibility(View.INVISIBLE);
			}
		} else if (offset > 0) {
			if (quickTools != null) {
				quickTools.setVisibility(View.VISIBLE);

			}
		}

		lastIndex = firstVisibleItem;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	// ////////////////////////////////////////////////////////////////

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.textlist_title:
			textNotify.setVisibility(View.VISIBLE);
			handler.sendEmptyMessageDelayed(1, 3000);
			break;

		default:
			break;
		}
	}

	// /////////////////////////////////////////////////////////////////////////
	// 列表下拉刷新与上啦加载

	/*
	 * 从上向下拉动列表，那么就要加载新数据操作
	 */

	// 数据列表回调方法 文字段子 图片段子都通过这个回调
	// 这个方法是在Volley联网响应返回的时候调用，他是在主线程执行的,因此可以直接更新UI
	public void listonResponse(String arg0) {
		// TODO Auto-generated method stub
		try {
			JSONObject json = new JSONObject(arg0);
			JSONObject obj = json.getJSONObject("data");

			// 解析段子列表完整数据
			EntityList entityList = new EntityList();
			// 这个方法是解析json的方法，包括的支持图片 文本 广告的解析
			entityList.parseJson(obj);// 相当于获取真实的数据

			// 代表还可以更新一次数据
			if (entityList.isHasMore()) {
				lastTime = entityList.getMinTime(); // 获取更新时间标识
			} else {
				// 没有更多的数据，休息一会儿
				String tip = entityList.getTip();
				Log.d("----", tip);
			}

			// 获取段子内容列表

			// TODO 把entityList 这个段子的数据集合体，传递给ListView之类的Adapter 即可显示

			List<TextEntity> ets = entityList.getEntities();
			// ?????????????????????????????????????????????
			if (ets != null) {
				if (!ets.isEmpty()) {

					// 把object添加到指定的location位置，原有的location以及以后的内容向后移动
					
					DataStore.getInstance().addTextEntities(ets);
					//entities.addAll(0, ets); // ets中内容按照迭代器的顺序添加

					/*
					 * int len = ets.size(); for(int index = len - 1;index >= 0;
					 * index--){ entities.add(0, ets.get(index)); }
					 */
					adapter.notifyDataSetChanged();
				} else {
					// TODO 没有更多数据，需要提示一下
				}

			} else {
				// TODO 没有网络或数据解析错误
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@Override
	public void onPullDownToRefresh(
			final PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		ClientAPI.getList(queue, requestCategory, 30, lastTime,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						// TODO 加载新数据
						refreshView.onRefreshComplete();
						// onResponse调用上面的listonResponse(arg0);方法
						// ，实际上上面的方法就是OnResponse
						//下载解析 更新UI
						listonResponse(arg0);

					}
				});

	}

	/*
	 * 从下向上拉动列表，那么就要加载考虑是否要加载旧的数据
	 */
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub

	}
////////////////////////////////////////////////////////////////////////////////////////
	@Override
	//跳转用的
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		position--;
		
		Intent intent = new Intent(getActivity(),EssayDeatilActivity.class);
		
		intent.putExtra("currentEssayPosition", position);
		
		intent.putExtra("category", requestCategory);
		
		startActivity(intent);
		
	}

}
