package com.gyzj.core.util;

import android.util.SparseArray;
import android.view.View;

/**
 * ViewHolder
 * 
 * @author 程才
 * @date  2014年6月16日
 */
public class ViewHolder
{
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id)
    {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null)
        {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        
        View childView = viewHolder.get(id);
        if (childView == null)
        {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        
        return (T) childView;
    }
}