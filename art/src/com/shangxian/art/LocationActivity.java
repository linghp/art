package com.shangxian.art;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shangxian.art.base.BaseActivity;
import com.shangxian.art.base.DataTools;
import com.shangxian.art.bean.NearlyShopInfo;
import com.shangxian.art.bean.NearlyShopStat;
import com.shangxian.art.bean.ShopLocInfo;
import com.shangxian.art.bean.ShopsModel;
import com.shangxian.art.constant.Constant;
import com.shangxian.art.net.HttpUtils;
import com.shangxian.art.net.NearlyServer;
import com.shangxian.art.net.NearlyServer.OnNearlyShopListener;
import com.shangxian.art.utils.MyLogger;
import com.shangxian.art.utils.Options;
import com.shangxian.art.view.CircleImageView1;
import com.shangxian.art.view.TopView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LocationActivity extends BaseActivity implements
		OnGetGeoCoderResultListener {
	private MapView mp_loc;
	private BaiduMap mMap;
	// private LocationClient mLocClient;
	public boolean isFirstLoc = true;

	BitmapDescriptor bdShops = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_gcoding);// 商铺地图图标

	BitmapDescriptor bdnA = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_marka);// A标签
	BitmapDescriptor bdnB = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markb);// B标签
	BitmapDescriptor bdnC = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markc);// C标签
	BitmapDescriptor bdnD = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markd);// D标签
	BitmapDescriptor bdnE = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_marke);// E标签
	BitmapDescriptor bdnF = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markf);// F标签
	BitmapDescriptor bdnG = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markg);// G标签
	BitmapDescriptor bdnH = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markh);// H标签
	BitmapDescriptor bdnI = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_marki);// I标签
	BitmapDescriptor bdnJ = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_markj);// J标签

	List<BitmapDescriptor> bits = new ArrayList<BitmapDescriptor>();

	public static void startTihsActivity(int map_Type, Activity context) {
		Bundle bundle = new Bundle();
		bundle.putInt(Constant.INT_LOC_TOTYPE, MAP_REGIST);
		Intent intent = new Intent(context, LocationActivity.class);
		intent.putExtras(bundle);
		context.startActivityForResult(intent, 1);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_location);
		// mLoc = app.getMLoc();
		initData();
		initView();
		initListener();
	}

	private final static int MAP_SHOPS = Constant.MAP_SHOPS_2_LOC;
	private final static int MAP_NEARLY = Constant.MAP_NEARLY_LOC;
	private final static int MAP_REGIST = Constant.MAP_REGIST_LOC;
	private int curType;

	// TODO:
	// --------------------------------------------------------------------------------------------
	// private BDLocation mLoc;
	private Random mRandom = new Random();
	private LatLng mShopLatlng;
	// private List<LatLng> mNearlyLatlngs = new ArrayList<LatLng>();
	private ShopsModel model;
	private NearlyShopInfo shopInfo;
	private ShopLocInfo locInfo;
	protected List<NearlyShopInfo> nearlyShops = new ArrayList<NearlyShopInfo>();
	private LocationClient mLocClient;
	private GeoCoder mGeoCoder;
	private LinearLayout ll_info;
	private TextView tv_info;
	private TextView tv_ok;
	private String lng;
	private MyLocationData locData;

	private void initData() {
		BDLocation bdl = app.getMLoc();
		if (bdl != null) {
			ll = new LatLng(bdl.getLatitude(), bdl.getLongitude());
		} else {
			myToast("获取位置失败");
			finish();
		}
		
		try {
			locData = new MyLocationData.Builder().accuracy(100f)
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(ll.latitude).longitude(ll.longitude)
					.build();
			lng = ll.longitude + "," + ll.latitude;
			MyLogger.i(lng);
		} catch (Exception e) {
			myToast("获取位置失败");
			finish();
			return;
		}
		
		Intent intent = getIntent();
		int type = intent.getIntExtra(Constant.INT_LOC_TOTYPE,
				Integer.MIN_VALUE);
		curType = type;
		switch (type) {
		case MAP_SHOPS:
			try {
				shops();
			} catch (Exception e) {
				myToast("获取地理位置错误");
				e.printStackTrace();
			}
			break;
		case MAP_NEARLY:
			nearly();
			break;
		case MAP_REGIST:
			regist();
			break;
		case Integer.MIN_VALUE:
			myToast("请求错误");
			finish();
			break;
		}
	}

	private void regist() {
		mGeoCoder = GeoCoder.newInstance();
		mGeoCoder.setOnGetGeoCodeResultListener(this);
	}

	@SuppressWarnings({ "unchecked", "unchecked" })
	private void nearly() {
		bits.add(bdnA);
		bits.add(bdnB);
		bits.add(bdnC);
		bits.add(bdnD);
		bits.add(bdnE);
		bits.add(bdnF);
		bits.add(bdnG);
		bits.add(bdnH);
		bits.add(bdnI);
		bits.add(bdnJ);

		if (DataTools.newInstance().getDatas(mAc.getClass().getSimpleName()) != null) {
			nearlyShops = (List<NearlyShopInfo>) DataTools.newInstance()
					.getDatas(mAc.getClass().getSimpleName());
		}
		if (!HttpUtils.checkNetWork(mAc)) {
			final AlertDialog dialog = new AlertDialog.Builder(this)
					.setTitle("提示")
					.setMessage("系统连接网络失败,请打开网络连接后重试")
					.setNegativeButton("重试",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									if (dialog != null) {
										dialog.dismiss();
									}
									nearly();
								}
							})
					.setNeutralButton("返回",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							}).show();
		} else {
			new NearlyServer().toNearlyShop(lng, 10000, 0, new OnNearlyShopListener() {
				@Override
				public void onNearly(NearlyShopStat stat) {
					MyLogger.i(stat != null ? stat.toString() : "null");
					if (stat != null && !stat.isNull()) {
						LocationActivity.this.nearlyShops = stat.getContents();
						DataTools.newInstance().put(
								mAc.getClass().getSimpleName(),
								LocationActivity.this.nearlyShops);
						overNearly();
					} else {
						if (nearlyShops.size() == 0) {
							final AlertDialog dialog = new AlertDialog.Builder(
									mAc)
									.setTitle("提示")
									.setMessage("请求数据失败")
									.setNegativeButton(
											"重试",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													if (dialog != null) {
														dialog.dismiss();
													}
													nearly();
												}
											})
									.setNeutralButton(
											"返回",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													finish();
												}
											}).show();
						}
					}
				}
			});
		}
	}

	private void shops() {
		locInfo = (ShopLocInfo) getIntent().getSerializableExtra(
				Constant.INT_SHOPS_2_LOC);
		if (locInfo == null) {
			return;
			//throw new NullPointerException("ShopLocInfo is null");
		}
		mShopLatlng = locInfo.getLatLng();
	}

	private void initView() {
		topView = (TopView) findViewById(R.id.top_title);
		topView.setActivity(this);
		topView.hideRightBtn_invisible();
		topView.hideCenterSearch();
		topView.hideRightBtn();
		topView.showTitle();
		topView.setBack(R.drawable.back);// 返回
		topView.setTitle("附近商铺");// title文字
		mp_loc = (MapView) findViewById(R.id.locb_bm_loc);
		mp_loc.showZoomControls(false);
		mp_loc.showScaleControl(false);
		mMap = mp_loc.getMap();
		
		if (curType == MAP_REGIST) {
			ll_info = (LinearLayout) findViewById(R.id.locl_ll_info);
			tv_info = (TextView) findViewById(R.id.loct_tv_info);
			tv_ok = (TextView) findViewById(R.id.loct_tv_ok);
		}
		
		upDataMap();
	}

	private void upDataMap() {
		// mMap.set
		// 定位初始化
		// mLocClient = new LocationClient(this);
		// mLocClient.registerLocationListener(myListener);
		//
		// LocationClientOption option = new LocationClientOption();
		// option.setOpenGps(true);// 打开gps
		// option.setCoorType("bd09ll"); // 设置坐标类型
		// option.setScanSpan(2000);
		// mLocClient.setLocOption(option);
		// mLocClient.start();

		// 开启定位图层
		if (curType == MAP_SHOPS) {
			mMap.setMyLocationEnabled(false);
		} else if (curType == MAP_NEARLY) {
			mMap.setMyLocationEnabled(true);
		} else if (curType == MAP_REGIST) {
			mMap.setMyLocationEnabled(true);
		}

		mMap.setMyLocationData(locData);

		MapStatus u = new MapStatus.Builder(mMap.getMapStatus())
				.target(getLat()).zoom(16f).build();
		MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(u);
		mMap.setMapStatus(msu);

		initOverlay();
	}

	/**
	 * 定位SDK监听函数
	 */
	// public MyLocationListenner myListener = new MyLocationListenner();
	private Marker mMarkerShop;
	public LatLng ll;

	// public class MyLocationListenner implements BDLocationListener {
	// @Override
	// public void onReceiveLocation(BDLocation location) {
	// // map view 销毁后不在处理新接收的位置
	// if (location != null && mp_loc != null) {
	// MyLocationData locData = new MyLocationData.Builder()
	// .accuracy(location.getRadius())
	// // 此处设置开发者获取到的方向信息，顺时针0-360
	// .direction(100).latitude(location.getLatitude())
	// .longitude(location.getLongitude()).build();
	// mMap.setMyLocationData(locData);
	// }
	// }
	//
	// public void onReceivePoi(BDLocation poiLocation) {
	// }
	// }

	private void initOverlay() {
		switch (curType) {
		case MAP_SHOPS:
			if(getLat()!=null)
			overShop();
			break;
		case MAP_NEARLY:
			overNearly();
			break;
		case MAP_REGIST:
			overRegist();
			break;
		}
	}

	private void overRegist() {

	}

	List<Marker> markers;

	private void overNearly() {
		if (nearlyShops.size() > 0) {
			MapStatus u = new MapStatus.Builder(mMap.getMapStatus())
					.target(getLat()).zoom(16f).build();
			MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(u);
			mMap.setMapStatus(msu);

			markers = new ArrayList<Marker>();
			for (int i = 0; i < nearlyShops.size() && bits.size() > 0; i++) {
				OverlayOptions ooA = new MarkerOptions()
						.position(
								new LatLng(nearlyShops.get(i).getLat(),
										nearlyShops.get(i).getLng()))
						.icon(bits.get(i > bits.size() - 1 ? bits.size() - 1
								: i)).zIndex(16);
				markers.add((Marker) (mMap.addOverlay(ooA)));
			}
			mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
				@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
				@Override
				public boolean onMarkerClick(Marker marker) {
					mMap.hideInfoWindow();
					final int mark = markers.indexOf(marker);
					View view = getLayoutInflater().inflate(
							R.layout.act_loc_pupo_bg, null);
					TextView title = (TextView) view
							.findViewById(R.id.loct_tv_title);
					TextView content = (TextView) view
							.findViewById(R.id.loct_tv_content);
					CircleImageView1 icon = (CircleImageView1) view
							.findViewById(R.id.loci_iv_icon);
					final NearlyShopInfo info = nearlyShops.get(mark);
					title.setText(info.getTitle());
					content.setText(info.getServiceDesc());
					ImageLoader.getInstance().displayImage(
							Constant.BASEURL + info.getIndexLogo(), icon,
							Options.getListOptions(false));
					mInfoWindow = new InfoWindow(BitmapDescriptorFactory
							.fromView(view), marker.getPosition(), -47,
							new OnInfoWindowClickListener() {
								@Override
								public void onInfoWindowClick() {
									// myToast("点击测试商铺" + mark);
									NearlyShopInfo info1 = info;
									ShopsActivity.startThisActivity(
											info.getShopId(), mAc);
									mMap.hideInfoWindow();
								}
							});
					mMap.showInfoWindow(mInfoWindow);
					isShowWindowing = true;
					toHideWindow(marker);
					return true;
				}
			});
		}
	}

	private InfoWindow mInfoWindow;

	private boolean isShowWindowing = false;
	private Marker registMarker;
	protected LatLng registLat;
	private boolean isShowing = false;

	private void overShop() {
		OverlayOptions ooA = new MarkerOptions().position(getLat())
				.icon(bdShops).zIndex(9).draggable(false);
		mMarkerShop = (Marker) (mMap.addOverlay(ooA));
		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			@SuppressLint("NewApi")
			@Override
			public boolean onMarkerClick(Marker mark) {
				View view = getLayoutInflater().inflate(
						R.layout.act_loc_pupo_bg, null);
				TextView title = (TextView) view
						.findViewById(R.id.loct_tv_title);
				TextView content = (TextView) view
						.findViewById(R.id.loct_tv_content);
				CircleImageView1 icon = (CircleImageView1) view
						.findViewById(R.id.loci_iv_icon);
				title.setText(locInfo.getTitle());
				content.setText(locInfo.getAddress());
				ImageLoader.getInstance().displayImage(
						Constant.BASEURL + locInfo.getPhoto(), icon,
						Options.getListOptions(false));
				LatLng ll = mark.getPosition();
				mInfoWindow = new InfoWindow(BitmapDescriptorFactory
						.fromView(view), ll, -47,
						new OnInfoWindowClickListener() {
							@Override
							public void onInfoWindowClick() {
								// myToast("跳转商铺详情");
								ShopsActivity.startThisActivity(
										locInfo.getId(), mAc);
								mMap.hideInfoWindow();
							}
						});
				mMap.showInfoWindow(mInfoWindow);
				isShowWindowing = true;
				toHideWindow(mark);
				return true;
			}
		});
	}

	private void showRegist(){
		if (!isShowing) {
			ll_info.setVisibility(View.VISIBLE);
			ll_info.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_bottom_in));
		}	
		isShowing  = true;	
	}
	
	protected void toHideWindow(final Marker mark) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (mInfoWindow != null) {
						if (isShowWindowing && !mark.isVisible()) {
							new Handler().post(new Runnable() {
								@Override
								public void run() {
									isShowWindowing = false;
									mMap.hideInfoWindow();
								}
							});
						} else {
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}).start();
	}

	private LatLng getLat() {
		if (curType == MAP_SHOPS) {
			return mShopLatlng;
		} else if (curType == MAP_NEARLY) {
			double lat = 0;
			double lon = 0;
			if (nearlyShops.size() == 0) {
				return ll;
			} else {
				for (int i = 0; i < nearlyShops.size(); i++) {
					lat += nearlyShops.get(i).getLat();
					lon += nearlyShops.get(i).getLng();
				}
				lat += ll.latitude;
				lon += ll.longitude;
				lat = lat / (nearlyShops.size() + 1.0);
				lon = lon / (nearlyShops.size() + 1.0);
				return new LatLng(lat, lon);
			}
		} else if (curType == MAP_REGIST) {
			return ll;
		}
		return ll;
	}

	private void initListener() {
		mMap.setOnMapClickListener(new OnMapClickListener() {
			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0) {
				mMap.hideInfoWindow();
			}
		});
		
		if (curType == MAP_REGIST) {
			mMap.setOnMapClickListener(new OnMapClickListener() {
				@Override
				public boolean onMapPoiClick(MapPoi arg0) {
					return false;
				}

				@Override
				public void onMapClick(LatLng lat) {
					registLat = lat;
					mMap.clear();
					OverlayOptions ooA = new MarkerOptions().position(lat)
							.icon(bdShops).zIndex(9).draggable(false);
					registMarker = (Marker) mMap.addOverlay(ooA);
					mMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(registLat));
					mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(registLat));
				}
			});
			
			tv_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String res = tv_info.getText().toString();
					if (TextUtils.isEmpty(res)) {
						setResult(RESULT_OK, getIntent());
						finish();
					} else {
						Bundle bundle = new Bundle();
						bundle.putString("add", res);
						bundle.putDouble("lat", registLat.latitude);
						bundle.putDouble("lng", registLat.longitude);
						setResult(RESULT_OK, getIntent().putExtras(bundle));
						finish();
					}			
				}
			});
		}
	}

	@Override
	protected void onPause() {
		// MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		mp_loc.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		mp_loc.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		mp_loc.onDestroy();
		super.onDestroy();
		bdShops.recycle();
		bdnA.recycle();
		bdnB.recycle();
		bdnC.recycle();
		bdnD.recycle();
		bdnE.recycle();
		bdnF.recycle();
		bdnG.recycle();
		bdnH.recycle();
		bdnI.recycle();
		bdnJ.recycle();
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult res) {
		if (res == null || res.error == SearchResult.ERRORNO.NO_ERROR) {
//			OverlayOptions ooA = new MarkerOptions().position(getLat())
//					.icon(bdShops).zIndex(9).draggable(false);
//			registMarker = (Marker) mMap.addOverlay(ooA);
//			mMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(registLat));
			if (!TextUtils.isEmpty(res.getAddress())) {
				tv_info.setText(res.getAddress());
				showRegist();
			} else {
				myToast("抱歉，获取地址信息失败");
			}
		} else {
			myToast("抱歉，获取地址信息失败");
		}
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult res) {
		if (res == null || res.error == SearchResult.ERRORNO.NO_ERROR) {
			String address = res.getAddress();
			if (!TextUtils.isEmpty(address)) {
				tv_info.setText(address);
				showRegist();
			} else {
				myToast("抱歉，获取地址信息失败");
			}
		} else {
			myToast("抱歉，获取地址信息失败");
		}
	}
}
