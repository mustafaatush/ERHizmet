package erqal.mylibrary.pulltorefreshlistviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import erqal.mylibrary.R;

public class EmptyLayout extends LinearLayout implements
		View.OnClickListener {// , ISkinUIObserver {

	public static final int HIDE_LAYOUT = 4;
	public static final int NETWORK_ERROR = 1;
	public static final int NETWORK_LOADING = 2;
	public static final int NODATA = 3;
	public static final int NODATA_ENABLE_CLICK = 5;
	public static final int NO_LOGIN = 6;


	private ProgressBar animProgress;
	private boolean clickEnable = true;
	private final Context context;
	public ImageView img;
	private OnClickListener listener;
	private int mErrorState=HIDE_LAYOUT;
	private RelativeLayout mLayout;
	private String strNoDataContent = "";
	private TextView messageView;



	public EmptyLayout(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public EmptyLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private void init() {
		View view = View.inflate(context,
				R.layout.swiperefresh_view_error_layout, null);
		img = (ImageView) view.findViewById(R.id.img_error_layout);
		messageView = (TextView) view.findViewById(R.id.tv_error_layout);
		mLayout = (RelativeLayout) view.findViewById(R.id.pageerrLayout);
		animProgress = (ProgressBar) view.findViewById(R.id.animProgress);
		setBackgroundColor(-1);
		setOnClickListener(this);
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (clickEnable) {
					// setErrorType(NETWORK_LOADING);
					if (listener != null)
						listener.onClick(v);
				}
			}
		});
		  LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, Gravity.CENTER_HORIZONTAL);
		addView(view,lp);
		changeErrorLayoutBgMode(context);
	}

	public void changeErrorLayoutBgMode(Context context1) {
		// mLayout.setBackgroundColor(SkinsUtil.getColor(context1,
		// "bgcolor01"));
		// tv.setTextColor(SkinsUtil.getColor(context1, "textcolor05"));
	}

	public void dismiss() {
		mErrorState = HIDE_LAYOUT;
		setVisibility(View.GONE);
	}

	public int getErrorState() {
		return mErrorState;
	}

	public boolean isLoadError() {
		return mErrorState == NETWORK_ERROR;
	}

	public boolean isLoading() {
		return mErrorState == NETWORK_LOADING;
	}

	@Override
	public void onClick(View v) {
		if (clickEnable) {
			// setErrorType(NETWORK_LOADING);
			if (listener != null)
				listener.onClick(v);
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		// MyApplication.getInstance().getAtSkinObserable().registered(this);
		onSkinChanged();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		// MyApplication.getInstance().getAtSkinObserable().unregistered(this);
	}

	public void onSkinChanged() {
		// mLayout.setBackgroundColor(SkinsUtil
		// .getColor(getContext(), "bgcolor01"));
		// tv.setTextColor(SkinsUtil.getColor(getContext(), "textcolor05"));
	}

	public void setDayNight(boolean flag) {
	}

	public void setErrorMessage(String msg) {
		messageView.setText(msg);
	}

	/**
	 * 新添设置背景
	 *
	 * @author 火蚁 2015-1-27 下午2:14:00
	 *
	 */
	public void setErrorImag(int imgResource) {
		try {
			img.setImageResource(imgResource);
		} catch (Exception e) {
		}
	}

	public void setErrorType(int i) {
		setVisibility(View.VISIBLE);
		switch (i) {
		case NETWORK_ERROR:
			mErrorState = NETWORK_ERROR;
			// img.setBackgroundDrawable(SkinsUtil.getDrawable(context,"pagefailed_bg"));
			// if (TDevice.hasInternet()) {
			// tv.setText(R.string.error_view_load_error_click_to_refresh);
			// img.setBackgroundResource(R.drawable.pagefailed_bg);
			// } else {
			// tv.setText(R.string.error_view_network_error_click_to_refresh);
			// img.setBackgroundResource(R.drawable.page_icon_network);
			// }
			img.setBackgroundResource(R.drawable.swiperefresh_page_icon_network);
			messageView.setText(R.string.error_network);
			img.setVisibility(View.VISIBLE);
			animProgress.setVisibility(View.GONE);
			clickEnable = true;
			break;
		case NETWORK_LOADING:
			mErrorState = NETWORK_LOADING;
			// animProgress.setBackgroundDrawable(SkinsUtil.getDrawable(context,"loadingpage_bg"));
			animProgress.setVisibility(View.VISIBLE);
			img.setVisibility(View.GONE);
			messageView.setText(R.string.loading);
			clickEnable = false;
			break;
		case NODATA:
			mErrorState = NODATA;
			// img.setBackgroundDrawable(SkinsUtil.getDrawable(context,"page_icon_empty"));
			img.setBackgroundResource(R.drawable.swiperefresh_page_icon_empty);
			img.setVisibility(View.VISIBLE);
			animProgress.setVisibility(View.GONE);
			messageView.setText(R.string.error_view_no_data);
			clickEnable = true;
			break;
		case HIDE_LAYOUT:
			setVisibility(View.GONE);
			break;
		case NODATA_ENABLE_CLICK:
			mErrorState = NODATA_ENABLE_CLICK;
			img.setBackgroundResource(R.drawable.swiperefresh_page_icon_empty);
			// img.setBackgroundDrawable(SkinsUtil.getDrawable(context,"page_icon_empty"));
			img.setVisibility(View.VISIBLE);
			animProgress.setVisibility(View.GONE);
			messageView.setText(R.string.error_view_no_data);
			clickEnable = true;
			break;
		default:
			break;
		}
	}

	public void setNoDataContent(String noDataContent) {
		strNoDataContent = noDataContent;
	}

	public void setOnLayoutClickListener(OnClickListener listener) {
		this.listener = listener;
	}

	public void setTvNoDataContent() {
		
			messageView.setText(R.string.error_view_no_data);
	}

	@Override
	public void setVisibility(int visibility) {
		if (visibility == View.GONE)
			mErrorState = HIDE_LAYOUT;
		super.setVisibility(visibility);
	}
	public TextView getMessageView() {
		return messageView;
	}

	public void setMessageView(TextView messageView) {
		this.messageView = messageView;
	}
}
