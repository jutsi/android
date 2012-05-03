package org.domain.mobile.android.mymapview;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MyMapViewActivity extends MapActivity implements OnTouchListener{
	
	private MapView mapView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mapView = (MapView) findViewById(R.id.mapview);
//        mapView.setBuiltInZoomControls(true);
        mapView.setOnTouchListener(this);
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean onTouch(View v, MotionEvent event) {
		GeoPoint point;
		if(mapView.getOverlays().size()==0) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if(event.getPointerCount() == 1) {
					point = mapView.getProjection().fromPixels((int)event.getX(), (int)event.getY());
					addOverlay(point);
				}
				break;
			}
		}
		return false;
	}

	private void addOverlay(GeoPoint p) {
		Drawable defaultMarker = this.getResources().getDrawable(R.drawable.marker_red_dot);
		HelloItemizedOverlay itemizedOverlay = new HelloItemizedOverlay(defaultMarker, this);
		OverlayItem overlayItem = new OverlayItem(p, null, null);
		itemizedOverlay.addOverlay(overlayItem);
		mapView.getOverlays().add(itemizedOverlay);
	}
}