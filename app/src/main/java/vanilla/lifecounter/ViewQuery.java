package vanilla.lifecounter;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Atem on 9/6/2016.
 */
public class ViewQuery
{
	protected ViewGroup group;

	public ViewQuery(ViewGroup group)
	{
		this.group = group;
	}

	public ArrayList<View> findByTag(Object tag)
	{
		ArrayList<View> views = new ArrayList<View>();

		for (int i = 0; i < group.getChildCount(); i++)
		{
			View child = group.getChildAt(i);
			if(child instanceof ViewGroup)
			{
				ViewQuery query = new ViewQuery((ViewGroup) child);
				views.addAll(query.findByTag(tag));
			}

			if(Objects.equals(tag, child.getTag()))
			{
				views.add(child);
			}
		}

		return views;
	}
}
