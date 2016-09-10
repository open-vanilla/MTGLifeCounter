package vanilla.mtg.ui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import vanilla.mtg.R;
import vanilla.mtg.models.Player;

/**
 * Created by Atem on 9/9/2016.
 */
public class PlayerLayout extends FrameLayout
{
	private Player player;

	protected Button lifeInc;
	protected Button lifeDec;

	protected Button settings;

	protected TextView lifeCounter;

	protected LinearLayout settingsMenu;

	public PlayerLayout(Context context) throws Exception
	{
		super(context);
	}

	public PlayerLayout(Context context, AttributeSet attrs) throws Exception
	{
		super(context, attrs);
	}

	public PlayerLayout(Context context, AttributeSet attrs, int defStyle) throws Exception
	{
		super(context, attrs, defStyle);
	}

	public void onFinishInflate()
	{
		super.onFinishInflate();

		this.init(new Player(20));
	}

	public void init(Player player)
	{
		this.player = player;

		lifeInc = (Button) this.findViewById(R.id.lifeInc);

		lifeDec = (Button) this.findViewById(R.id.lifeDec);
		lifeCounter = (TextView) this.findViewById(R.id.lifeCounter);

		settings = (Button) this.findViewById(R.id.settings);

		settingsMenu = (LinearLayout) this.findViewById(R.id.playerSettingsMenu);

		this.configure();
	}

	public void configure()
	{
		settings.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				RotateAnimation ra = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				ra.setFillAfter(true);
				ra.setDuration(500);

				//TODO: move this into a settings menu view type as a toggle method
				if(settingsMenu.getVisibility() == View.GONE)
				{
					settingsMenu.setVisibility(View.VISIBLE);
				}
				else
				{
					settingsMenu.setVisibility(View.GONE);
				}

				settings.startAnimation(ra);
			}
		});

		lifeInc.setOnClickListener(new LifeChangeListener(1));

		lifeDec.setOnClickListener(new LifeChangeListener(-1));

		settingsMenu.findViewById(R.id.mountain_icon).setOnClickListener(new BackgroundChangeListener(Land.MOUNTAIN));
		settingsMenu.findViewById(R.id.plains_icon).setOnClickListener(new BackgroundChangeListener(Land.PLAINS));
		settingsMenu.findViewById(R.id.island_icon).setOnClickListener(new BackgroundChangeListener(Land.ISLAND));
		settingsMenu.findViewById(R.id.forest_icon).setOnClickListener(new BackgroundChangeListener(Land.FOREST));
		settingsMenu.findViewById(R.id.swamp_icon).setOnClickListener(new BackgroundChangeListener(Land.SWAMP));

		this.update(this.player);
	}

	public int resolveColor(int resourceId)
	{
		return ContextCompat.getColor(this.getContext(), resourceId);
	}

	public void setLand(Land landResource)
	{
		this.setBackground(ContextCompat.getDrawable(this.getContext(), landResource.background));

		int resourceId = R.color.light_controls;
		if(landResource == Land.PLAINS)
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

	protected void update(Player player)
	{
		this.updateLifeCounter(player.getLifeTotal());
	}

	protected void updateLifeCounter(int life)
	{
		this.lifeCounter.setText("" + life);
	}

	public enum Land
	{
		FOREST(R.drawable.background_forest_repeating),
		ISLAND(R.drawable.background_island_repeating),
		PLAINS(R.drawable.background_plains_repeating),
		SWAMP(R.drawable.background_swamp_repeating),
		MOUNTAIN(R.drawable.background_mountain_repeating);

		private int background;

		Land(int background)
		{
			this.background = background;
		}
	}

	class LifeChangeListener implements View.OnClickListener
	{
		protected int delta;

		public LifeChangeListener(int delta)
		{
			this.delta = delta;
		}

		@Override
		public void onClick(View v)
		{
			int life = player.updateLifeTotal(delta);
			updateLifeCounter(life);
		}
	}

	class BackgroundChangeListener implements View.OnClickListener
	{
		protected Land land;

		public BackgroundChangeListener(Land land)
		{
			this.land = land;
		}

		@Override
		public void onClick(View v)
		{
			settingsMenu.setVisibility(View.GONE);
			setLand(land);
		}
	}
}
