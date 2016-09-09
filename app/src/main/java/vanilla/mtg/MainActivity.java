package vanilla.mtg;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import vanilla.mtg.models.Player;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		final Context self = this;

		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				ViewGroup main = (ViewGroup) getWindow().getDecorView().findViewById(R.id.main);

				Log.d("MainActivity", "View " + main);

				ViewQuery query = new ViewQuery(main);

				ArrayList<View> players = query.findByTag("player");

				Log.d("MainActivity", "Found " + players.size() + " players");

				int rotationSegment = 360 / players.size();

				for (int i = 0; i < players.size(); i++)
				{
					ViewGroup player = (ViewGroup) players.get(i);

					int rotationAmount = 360 - (rotationSegment * (i + 1));

					Log.d("MainActivity", "Rotation: " + rotationAmount);

					player.setRotation(rotationAmount);

					Log.d("MainActivity", "Player: " + player);

					Button lifeInc = (Button) player.findViewById(R.id.lifeInc);
					Button lifeDec = (Button) player.findViewById(R.id.lifeDec);

					final TextView lifeCounter = (TextView) player.findViewById(R.id.lifeCounter);

					Log.d("MainActivity", "lifeCounter: " + lifeCounter);

					Log.d("MainActivity", "lifeCounter value: " + lifeCounter.getText());

					lifeCounter.setText("20");

					Log.d("MainActivity", "lifeCounter value: " + lifeCounter.getText());

					lifeInc.setOnClickListener(new LifeChangeListener(lifeCounter, 1));

					lifeDec.setOnClickListener(new LifeChangeListener(lifeCounter, -1));
				}

                Player p1 = new Player(players.get(0), self);
                p1.setLand(Player.Land.Mountain);
                Player p2 = new Player(players.get(1), self);
                p2.setLand(Player.Land.Plains);
            }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	class LifeChangeListener implements View.OnClickListener
	{
		protected int delta;
		protected TextView lifeCounter;

		public LifeChangeListener(TextView lifeCounter, int delta)
		{
			this.lifeCounter = lifeCounter;
			this.delta = delta;
		}

		@Override
		public void onClick(View v)
		{
			int value = Integer.parseInt(lifeCounter.getText().toString()) + delta;
			lifeCounter.setText("" + value);
		}
	}
}
