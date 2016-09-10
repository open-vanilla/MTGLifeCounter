package vanilla.mtg.models;

/**
 * Created by Atem on 9/6/2016.
 */
public class Player
{
	protected int lifeTotal;

	public Player(int lifeTotal)
	{
		this.lifeTotal = lifeTotal;
	}

	public int updateLifeTotal(int delta)
	{
		this.lifeTotal += delta;
		return this.lifeTotal;
	}

	public int getLifeTotal()
	{
		return lifeTotal;
	}
}
