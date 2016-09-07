package vanilla.mtg;

import android.util.Log;
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
		Log.d("ViewQuery", "Finding...");

		for (int i = 0; i < group.getChildCount(); i++)
		{
			View child = group.getChildAt(i);
			Log.d("ViewQuery", "Child: " + child);

			if(child instanceof ViewGroup)
			{
				Log.d("ViewQuery", "ViewGroup: " + child);

				ViewQuery query = new ViewQuery((ViewGroup) child);
				views.addAll(query.findByTag(tag));
			}

			Log.d("ViewQuery", "Tag: " + child.getTag());

			if(Objects.equals(tag, child.getTag()))
			{
				Log.d("ViewQuery", "Found: " + child);
				views.add(child);
			}
		}

		return views;
	}
}
