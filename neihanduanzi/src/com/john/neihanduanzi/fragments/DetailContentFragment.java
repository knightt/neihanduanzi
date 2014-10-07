package com.john.neihanduanzi.fragments;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import pl.droidsonroids.gif.GifImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.john.neihanduanzi.R;
import com.john.neihanduanzi.adapters.CommentAdapter;
import com.john.neihanduanzi.bean.Comment;
import com.john.neihanduanzi.bean.CommentList;
import com.john.neihanduanzi.bean.TextEntity;
import com.john.neihanduanzi.bean.UserEntity;
import com.john.neihanduanzi.client.ClientAPI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
//Viewpager的fragment
public class DetailContentFragment extends Fragment implements OnTouchListener, Listener<String> {

	private TextEntity entity;
	private ScrollView scrollView;
	private TextView txtHotCommentCount;
	private TextView recentCommentCount;
	private LinearLayout scrollContent;
	public DetailContentFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(entity == null){
			Bundle arguement = getArguments();
			Serializable serializable = arguement.getSerializable("essayItem");
			if (serializable instanceof TextEntity) {
				entity = (TextEntity) serializable;
			}
		}
		queue = Volley.newRequestQueue(getActivity());
	}
	
	private CommentAdapter hotAdapter;
	private CommentAdapter recentAdapter;
	
	private List<Comment> hotComments;//热门评论
	private List<Comment> recentComments;//最新评论
	//????????????????????????????????????????????????
	private int offset = 0;
	private RequestQueue queue;
	private boolean hasMore;
	private long groupId;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View ret = inflater.inflate(R.layout.fragment_detail_content, container, false);
		
		scrollView = (ScrollView) ret.findViewById(R.id.detail_scroll);
		
		scrollView.setOnTouchListener(this);
		
		//设置主体内容
		setEssayContent(ret);
		
		txtHotCommentCount = (TextView) ret.findViewById(R.id.txt_hot_comments_count);
		
		ListView hotCommentListView = (ListView) ret.findViewById(R.id.hot_comments_list);
		
		hotComments = new LinkedList<Comment>();
		
		hotAdapter = new CommentAdapter(getActivity(),hotComments);
		
		hotCommentListView.setAdapter(hotAdapter);
		/////////////////////////////////////////////////////////
		scrollContent = (LinearLayout) ret.findViewById(R.id.scroll_content);
		
		
		recentCommentCount = (TextView) ret.findViewById(R.id.txt_recent_comments_count);
		
		ListView recentCommentListView  = (ListView) ret.findViewById(R.id.recent_comments_list);
	    
		recentComments = new LinkedList<Comment>();
		
		recentAdapter = new CommentAdapter(getActivity(),recentComments);
		
		hotCommentListView.setAdapter(recentAdapter);
		
		groupId = entity.getGroupId();
		
		ClientAPI.getComments(queue, groupId, 0, this);
		
		
		/*if (entity != null) {
	
			UserEntity user= entity.getUserEntity();
			if(user != null){
				String avatarUrl = user.getAvatarUrl();
				String name = user.getName();
				
				
			}
			
		}*/
		return ret;
		
		
	}
	/**
	 * 设置段子主题内容（详情）
	 * @param ret
	 */

	private  void setEssayContent(View ret) {
		//1.先设置文本内容的数据
		
		TextView btnGifPlay = (TextView) ret.findViewById(R.id.btnGifPlay);
		ImageButton btnShare = (ImageButton) ret.findViewById(R.id.item_share);
		CheckBox chbBuryCount = (CheckBox) ret.findViewById(R.id.item_bury_count);
		CheckBox chbDiggCount = (CheckBox) ret.findViewById(R.id.item_digg_count);
		GifImageView gifImageView = (GifImageView) ret.findViewById(R.id.gridview);
		ImageView imgProfileImage = (ImageView) ret.findViewById(R.id.item_profile_image);
		ProgressBar pbDownloadProgress = (ProgressBar) ret.findViewById(R.id.item_image_download_progress);
		TextView txtCommentCount = (TextView) ret.findViewById(R.id.item_comments_count);
		TextView txtContent = (TextView) ret.findViewById(R.id.item_content);
		TextView txtProfileNick = (TextView) ret.findViewById(R.id.item_profile_nick);
		
		UserEntity user = entity.getUserEntity();
		String nick = "";
		
		if(user != null){
			nick = user.getName();
		}
		 txtProfileNick.setText(nick);
		
		String content = entity.getContent();
		 txtContent.setText(content);
		
		
		
		int diggCount = entity.getDiggCount();
		
		 chbDiggCount.setText(Integer.toString(diggCount));
		
		int userDigg = entity.getUserDigg();//当前用户是否赞过
		/*
		 * 如果diagg是1表示已经赞过，那么chbDiggCount必须禁用，所以用 != 1
		 */
		 chbDiggCount.setEnabled(userDigg != 1);
		
		
		
		
		int buryCount = entity.getBuryCount();
		
		 chbBuryCount.setText(Integer.toString(buryCount));
		
		int userBury = entity.getUserBury();
		/*
		 * 如果diagg是1表示已经踩过，那么chbBuryCount必须禁用，所以用 != 1
		 */
		 chbBuryCount.setEnabled(userBury != 1);
		
		
		int commentCount = entity.getCommentCount();
		 txtCommentCount.setText(Integer.toString(commentCount));
		
		//2.设置图片内容的数据
		
		//TODO 需要加载各种图片数据
	}
	
	/*
	 *  处理ScrollView  触摸事件，用于在ScrollView滚到最下面的时候，自动加载数据
	 */
	private boolean hasMove = false;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		boolean  bret = false;
		int action = event.getAction();
		if (action == MotionEvent.ACTION_DOWN) {
			bret = true;
			hasMove = false;
		}else if(action == MotionEvent.ACTION_MOVE){
			hasMove = true;
		}
		else if(action == MotionEvent.ACTION_UP){
			if(hasMove){
				//窗体上面的高度
				int sy = scrollView.getScrollY();
				//视窗的高度
				int measuredHeight = scrollView.getMeasuredHeight();
				//内容区的高度
				int ch = scrollContent.getHeight();
				
				if (sy + measuredHeight >= ch) {
					//TODO 进行评论分页加载
					
					ClientAPI.getComments(queue, groupId, offset, this);
					
				}
			}
		}
		return bret;
	}
///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void onResponse(String arg0) {
		// TODO Auto-generated method stub
		try {
			JSONObject json = new JSONObject(arg0);
			arg0 = json.toString(4);

		
			// Log.d("TestActiviy", "Comment List :"+arg0);
			//解析获取到的评论列表
			CommentList commentList = new CommentList();
			//评论列表包含两组数据，一个是热门评论，一个是新鲜评论
			//评论都有可能为空
			commentList.parseJson(json);

			//long groupId = commentList.getGroupId();
			
			hasMore = commentList.isHasMore();
			
		    int totalNumber = commentList.getTotalNumber();
			
			
			//表示评论列表是否还可以继续加载
			
			
			
			List<Comment> topComments = commentList.getTopComments();
			
			List<Comment> rtComments = commentList.getRecentComments();
			
			if(topComments != null){
				hotComments.addAll(topComments);
				hotAdapter.notifyDataSetChanged();
			}
			
			if(rtComments != null){
				recentComments.addAll(rtComments);
				recentAdapter.notifyDataSetChanged();
			}
			
			//TODO  直接把CommentList 提交给ListView 的Adapter  这样可以进行是否还有内容的判断
			//利用adapter更新数据
			
			
			
				//分页标识，要求服务器每次返回20条评论，通过hasMore判断时候还需要分页
				offset = offset + 20;	
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
