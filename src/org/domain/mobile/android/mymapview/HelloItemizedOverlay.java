package org.domain.mobile.android.mymapview;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class HelloItemizedOverlay extends ItemizedOverlay {
	
	private ArrayList<OverlayItem> mOverlayItems = new ArrayList<OverlayItem>();
	private Context mContext;

	public HelloItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}
	
	public HelloItemizedOverlay(Drawable defaultMarker, Context context) {
		this(defaultMarker);
		mContext = context;
	}
	
	public void addOverlay(OverlayItem overlayItem) {
		mOverlayItems.add(overlayItem);
		populate();
	}

	@Override
	protected OverlayItem createItem(int arg0) {
		return mOverlayItems.get(arg0);
	}

	@Override
	public int size() {
		return mOverlayItems.size();
	}
	
	protected boolean onTap(int index) {
		OverlayItem item = mOverlayItems.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		dialog.setTitle(item.getPoint().toString());
		dialog.show();
		return true;
	}
	
	public boolean onTap (final GeoPoint p, final MapView mapView) {
		if(super.onTap(p, mapView)) {
			// Do nothing here if overlay is tapped
            return true;
        }
        OverlayItem overlayItem = new OverlayItem(p, "", "");
        this.addOverlay(overlayItem);
        mapView.getOverlays().add(this);
        return true;
    }
	
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		Paint paint = new Paint();
		paint.setStrokeWidth(2);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
		Point screenCoords1 = new Point();
		Point screenCoords2 = new Point();
		int i;
		for (i = 1; i < mOverlayItems.size(); i++) {
			mapView.getProjection().toPixels(mOverlayItems.get(i-1).getPoint(), screenCoords1);			
			mapView.getProjection().toPixels(mOverlayItems.get(i).getPoint(), screenCoords2);
			canvas.drawLine(screenCoords1.x, screenCoords1.y, screenCoords2.x, screenCoords2.y, paint);
		}
		if(--i > 1) {
			mapView.getProjection().toPixels(mOverlayItems.get(i).getPoint(), screenCoords1);			
			mapView.getProjection().toPixels(mOverlayItems.get(0).getPoint(), screenCoords2);
			canvas.drawLine(screenCoords1.x, screenCoords1.y, screenCoords2.x, screenCoords2.y, paint);
		}
		populate();
	}
}
