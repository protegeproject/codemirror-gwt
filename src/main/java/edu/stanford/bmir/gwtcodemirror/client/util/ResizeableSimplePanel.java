package edu.stanford.bmir.gwtcodemirror.client.util;

import com.google.gwt.user.client.ui.ProvidesResize;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.SimplePanel;

public class ResizeableSimplePanel extends SimplePanel implements RequiresResize, ProvidesResize {

	@Override
	public void onResize() {
		if (getWidget() instanceof RequiresResize) {
			((RequiresResize) getWidget()).onResize();
		}
	}
}