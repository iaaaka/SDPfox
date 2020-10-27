package Win.panels.Event;

import java.util.*;

public class ThresholdEvent {
	/**
	 * Array of positions with Zscore more then threshold. 
	 */
	public int[] positions;
	/**
	 * real threshold value in Zscore.
	 */
	public double threshold;
	
	public ThresholdEvent(int[] p,double t){
		this.positions = p;
		Arrays.sort(positions);
		threshold = t;
	}
}
