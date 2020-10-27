package Objects;

import Util.Normal;

public class Position {
	public int aligNo;
	public double inf = 0;
	public double inf_sh = 0;
	public double sigma = 0;
	public double z_score = 0;
	public double probability;
	public double positionProb;
	public double zScoreWithoutRegression = 0;
	public Position(int aligN) {
		aligNo = aligN;
	}

	public void set_z_score(double a, double b) {
		zScoreWithoutRegression = (inf - inf_sh) / sigma;
		inf_sh = inf_sh * a + b;
		sigma = sigma * Math.abs(a);
		if (sigma == 0) {
			z_score = 0;
		}else
			z_score = (inf - inf_sh) / sigma;
		probability = Normal.probGrater(z_score);
		if (probability == 0) {
			probability = Normal.probGraterApprox(z_score);
		} else {
			probability = Math.log(probability);
		}

	}

	public String toString() {
		return "Position no=" + aligNo + " inf=" + inf + " inf_sh=" + inf_sh
				+ " sigma=" + sigma + " Z-score=" + z_score + " posProb="
				+ positionProb + "\n";
	}
}
