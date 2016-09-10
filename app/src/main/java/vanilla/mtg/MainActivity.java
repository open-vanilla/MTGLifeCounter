package vanilla.mtg;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import vanilla.mtg.ui.PlayerLayout;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				ViewGroup main = (ViewGroup) getWindow().getDecorView().findViewById(R.id.main);

				ViewQuery query = new ViewQuery(main);

				ArrayList<View> players = query.findByTag("player");

				int rotationSegment = 360 / players.size();

				for (int i = 0; i < players.size(); i++)
				{
					ViewGroup player = (ViewGroup) players.get(i);

					int rotationAmount = 360 - (rotationSegment * (i + 1));

					player.setRotation(rotationAmount);
				}

				Random random = new Random();

                PlayerLayout p1 = (PlayerLayout) players.get(0);
                p1.setLand(PlayerLayout.Land.values()[random.nextInt(PlayerLayout.Land.values().length)]);
				PlayerLayout p2 = (PlayerLayout) players.get(1);
                p2.setLand(PlayerLayout.Land.values()[random.nextInt(PlayerLayout.Land.values().length)]);
            }
		});
	}
}
