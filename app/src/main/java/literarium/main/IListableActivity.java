package com.example.com.literarium;

import java.util.List;

/**
 * describes an activity with the capability
 * of displaying some data in a list or in a map.
 */
public interface IListableActivity {

    public void populateList(List<? extends Cloneable> dataList);

    public void populateMapWithMarkers();
}
