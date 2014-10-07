package com.john.neihanduanzi.adapters;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

import com.john.neihanduanzi.R;
import com.john.neihanduanzi.bean.TextEntity;
import com.john.neihanduanzi.bean.UserEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class EssayAdapter extends BaseAdapter {

	private Context context;
	private List<TextEntity> entities;
	private LayoutInflater inflater;

	public EssayAdapter(Context context, List<TextEntity> entities) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.entities = entities;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return entities.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return entities.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View ret = convertView;

		if (convertView == null) {
			ret = inflater.inflate(R.layout.item_essay, parent, false);
		}

		if (ret != null) {
			ViewHolder holder = (ViewHolder) ret.getTag();

			if (holder == null) {
				holder = new ViewHolder();
				holder.btnGifPlay = (TextView) ret.findViewById(R.id.btnGifPlay);
				holder.btnShare = (ImageButton) ret.findViewById(R.id.item_share);
				holder.chbBuryCount = (CheckBox) ret.findViewById(R.id.item_bury_count);
				holder.chbDiggCount = (CheckBox) ret.findViewById(R.id.item_digg_count);
				holder.gifImageView = (GifImageView) ret.findViewById(R.id.gridview);
				holder.imgProfileImage = (ImageView) ret.findViewById(R.id.item_profile_image);
				holder.pbDownloadProgress = (ProgressBar) ret.findViewById(R.id.item_image_download_progress);
			    holder.txtCommentCount = (TextView) ret.findViewById(R.id.item_comments_count);
			    holder.txtContent = (TextView) ret.findViewById(R.id.item_content);
			    holder.txtProfileNick = (TextView) ret.findViewById(R.id.item_profile_nick);
			    ret.setTag(holder);
			}
			
			TextEntity entity = entities.get(position);
			
			//1.先设置文本内容的数据
			
			UserEntity user = entity.getUserEntity();
			String nick = "";
			
			if(user != null){
				nick = user.getName();
			}
			holder.txtProfileNick.setText(nick);
			
			String content = entity.getContent();
			holder.txtContent.setText(content);
			
			
			
			int diggCount = entity.getDiggCount();
			
			holder.chbDiggCount.setText(Integer.toString(diggCount));
			
			int userDigg = entity.getUserDigg();//当前用户是否赞过
			/*
			 * 如果diagg是1表示已经赞过，那么chbDiggCount必须禁用，所以用 != 1
			 */
			holder.chbDiggCount.setEnabled(userDigg != 1);
			
			
			
			
			int buryCount = entity.getBuryCount();
			
			holder.chbBuryCount.setText(Integer.toString(buryCount));
			
			int userBury = entity.getUserBury();
			/*
			 * 如果diagg是1表示已经踩过，那么chbBuryCount必须禁用，所以用 != 1
			 */
			holder.chbBuryCount.setEnabled(userBury != 1);
			
			
			int commentCount = entity.getCommentCount();
			holder.txtCommentCount.setText(Integer.toString(commentCount));
			
			//2.设置图片内容的数据
			
			//TODO 需要加载各种图片数据
			
			
		}
		return ret;
	}

	private static class ViewHolder {
		// 头像
		public ImageView imgProfileImage;
		// 昵称
		public TextView txtProfileNick;
		// 文本内容
		public TextView txtContent;
		// 下载进度
		public ProgressBar pbDownloadProgress;
		// 图片显示
		public GifImageView gifImageView;
		// 图片显示部分按钮，开启GIF播放
		public TextView btnGifPlay;
		// 赞 ， 包含个数，如果已经赞过了，那么禁用这个空间
		public CheckBox chbDiggCount;
		// 踩 ， 包含个数，如果已经踩过了，那么禁用这个空间
		public CheckBox chbBuryCount;
		// 评论轮的个数，点击可以查看评论
		public TextView txtCommentCount;
		// 分享操作
		public ImageButton btnShare;

	}

}
