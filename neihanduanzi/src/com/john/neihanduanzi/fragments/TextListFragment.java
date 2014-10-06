package com.john.neihanduanzi.fragments;

import java.util.LinkedList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.john.neihanduanzi.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TextListFragment extends Fragment implements OnClickListener,
		OnScrollListener ,OnRefreshListener2<ListView>{

	private View quickTools;
	private TextView textNotify;

	public TextListFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_textlist, container,
				false);
		
		// 获取标题控件，增加点击，进行 新消息悬浮框显示的功能
				View titleView = view.findViewById(R.id.textlist_title);
				titleView.setOnClickListener(this);
				
				
				
		// TODO 获取listview并且设置数据（以后需要用PullToRefresh进行完善）

		PullToRefreshListView refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.textlist_listview);
		
		//设置上拉与下拉的事件监听
		refreshListView.setOnRefreshListener(this);
		refreshListView.setMode(Mode.BOTH);
		ListView listView = refreshListView.getRefreshableView();
		
		
		List<String> strings = new LinkedList<String>();
		for (int i = 0; i < 100; i++) {
			strings.add("java" +i);
		}
		// TODO 添加列表上面的快速工具条（Header）
		header = inflater.inflate(R.layout.textlist_header_tools, listView,
				false);
		listView.addHeaderView(header);

		View quickPublish = header.findViewById(R.id.quick_tools_publish);
		quickPublish.setOnClickListener(this);

		View quickReview = header.findViewById(R.id.quick_tools_review);
		quickReview.setOnClickListener(this);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, strings);

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
    ///////////////////////////////////////////////////////////////////////////
	//列表下拉刷新与上啦加载
	
	/*
	 * 从上向下拉动列表，那么就要加载新数据操作
	 */
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * 从下向上拉动列表，那么就要加载考虑是否要加载旧的数据
	 */
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		
	}

}
