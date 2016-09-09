package vanilla.mtg.models;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;

import vanilla.mtg.R;

/**
 * Created by Atem on 9/6/2016.
 */
public class Player
{
	private final Context context;
	protected Land land;
	protected View view;

	protected Button lifeInc;
	protected Button lifeDec;

	protected Button settings;

	protected final TextView lifeCounter;

	public Player(View playerView, Context context)
	{
		this.context = context;
		this.view = playerView;

		lifeInc = (Button) playerView.findViewById(R.id.lifeInc);
		lifeDec = (Button) playerView.findViewById(R.id.lifeDec);

		settings = (Button) playerView.findViewById(R.id.settings);

		settings.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				RotateAnimation ra = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				ra.setFillAfter(true);
				ra.setDuration(500);

				settings.startAnimation(ra);
			}
		});

		lifeCounter = (TextView) playerView.findViewById(R.id.lifeCounter);
	}

	public void setLand(Land land)
	{
        this.view.setBackground(ContextCompat.getDrawable(this.context, land.background));

		int resourceId = R.color.light_controls;
		if(land == Land.Plains)
		{
			resourceId = R.color.dark_controls;
		}

		int color = this.resolveColor(resourceId);

		this.lifeInc.setTextColor(color);
		GradientDrawable lifeIncDrawable = (GradientDrawable)this.lifeInc.getBackground();
		lifeIncDrawable.setStroke(5, color);

		this.lifeDec.setTextColor(color);
		GradientDrawable lifeDecDrawable = (GradientDrawable)this.lifeDec.getBackground();
		lifeDecDrawable.setStroke(5, color);

		LayerDrawable settingsDrawable = (LayerDrawable) this.settings.getBackground();
		GradientDrawable g1 = (GradientDrawable) settingsDrawable.findDrawableByLayerId(R.id.topMid);
		GradientDrawable g2 = (GradientDrawable) settingsDrawable.findDrawableByLayerId(R.id.midLeft);
		GradientDrawable g3 = (GradientDrawable) settingsDrawable.findDrawableByLayerId(R.id.midRight);
		GradientDrawable g4 = (GradientDrawable) settingsDrawable.findDrawableByLayerId(R.id.botLeft);
		GradientDrawable g5 = (GradientDrawable) settingsDrawable.findDrawableByLayerId(R.id.botRight);

		GradientDrawable[] drawables = {g1, g2, g3, g4, g5};

		for(int i = 0; i < drawables.length; i++)
		{
			drawables[i].setColor(color);
		}

		this.lifeCounter.setTextColor(color);
	}

	public int resolveColor(int resourceId)
	{
		return ContextCompat.getColor(this.context, resourceId);
	}

	public enum Land
	{
		Forest(R.drawable.paper_repeating),
		Island(R.drawable.seigaiha_repeating),
		Plains(R.drawable.wov_repeating),
		Swamp(R.drawable.irongrip_repeating),
		Mountain(R.drawable.wool_repeating);

		private int background;

		Land(int background)
		{
			this.background = background;
		}
	}
}
