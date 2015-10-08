package com.github.flyyan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.github.flyyan.R;

public class AutoListView extends ListView implements OnScrollListener {

	// ���ֵ�ǰ������ˢ�»��Ǽ���
	public static final int REFRESH = 0;
	public static final int LOAD = 1;

	// ����PULL��RELEASE�ľ���Ĵ�С
	private static final int SPACE = 20;


	private LayoutInflater inflater;
	private View footer;


	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;


	private int firstVisibleItem;

	private boolean loadEnable = true;// �������߹رռ��ظ��๦��
	// private boolean isLoadFull;
	private int pageSize = 10;

	// private OnRefreshListener onRefreshListener;
	private OnLoadListener onLoadListener;

	public AutoListView(Context context) {
		super(context);
		initView(context);
	}

	public AutoListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public AutoListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}


	// ���ظ������
	public void setOnLoadListener(OnLoadListener onLoadListener) {
		this.loadEnable = true;
		this.onLoadListener = onLoadListener;
	}

	public boolean isLoadEnable() {
		return loadEnable;
	}

	// ����Ŀ������߹رռ��ظ��࣬����֧�ֶ�̬����
	public void setLoadEnable(boolean loadEnable) {
		this.loadEnable = loadEnable;
		this.removeFooterView(footer);
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	// ��ʼ�����
	private void initView(Context context) {

		// ���ü�ͷ��Ч
		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(100);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(100);
		reverseAnimation.setFillAfter(true);

		inflater = LayoutInflater.from(context);
		footer = inflater.inflate(R.layout.footer, null);

		// Ϊlistview���ͷ����β���������г�ʼ��
		this.addFooterView(footer);
		this.setOnScrollListener(this);
	}

	public void onLoad() {
		if (onLoadListener != null) {
			onLoadListener.onLoad();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.firstVisibleItem = firstVisibleItem;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		ifNeedLoad(view, scrollState);
	}

	// ����listview������״̬�ж��Ƿ���Ҫ���ظ���
	private void ifNeedLoad(AbsListView view, int scrollState) {
		if (!loadEnable) {
			return;
		}
		try {
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
					&& view.getLastVisiblePosition() == view
							.getPositionForView(footer)) {
				onLoad();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * ���������¼����������
	 */
//	@Override
//	public boolean onTouchEvent(MotionEvent ev) {
//		switch (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			if (firstVisibleItem == 0) {
//				isRecorded = true;
//				startY = (int) ev.getY();
//			}
//			break;
//		case MotionEvent.ACTION_CANCEL:
//		case MotionEvent.ACTION_UP:
//
//			isRecorded = false;
//			break;
//		case MotionEvent.ACTION_MOVE:
//			// whenMove(ev);
//			break;
//		}
//		return super.onTouchEvent(ev);
//	}

	/**
	 * ��������Ǹ��ݽ���Ĵ�С������footer��ʾ�ġ�
	 * <p>
	 * ����ٶ�ÿ�����������Ϊ10�����������10��������Ϊ�������ݡ�����������10��������Ϊ�����Ѿ�ȫ�����أ���ʱfooter��ʾ�Ѿ�ȫ������
	 * </p>
	 * 
	 * @param resultSize
	 */
	// public void setResultSize(int resultSize) {
	// if (resultSize == 0) {
	// isLoadFull = true;
	// loadFull.setVisibility(View.GONE);
	// loading.setVisibility(View.GONE);
	// more.setVisibility(View.GONE);
	// noData.setVisibility(View.VISIBLE);
	// } else if (resultSize > 0 && resultSize < pageSize) {
	// isLoadFull = true;
	// loadFull.setVisibility(View.VISIBLE);
	// loading.setVisibility(View.GONE);
	// more.setVisibility(View.GONE);
	// noData.setVisibility(View.GONE);
	// } else if (resultSize == pageSize) {
	// isLoadFull = false;
	// loadFull.setVisibility(View.GONE);
	// loading.setVisibility(View.VISIBLE);
	// more.setVisibility(View.VISIBLE);
	// noData.setVisibility(View.GONE);
	// }
	//
	// }
	//
	//

	/*
	 * ������ظ���ӿ�
	 */
	public interface OnLoadListener {
		public void onLoad();
	}

}