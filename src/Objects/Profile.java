package Objects;

import Math.StaticFunction;
import java.text.DecimalFormat;

public class Profile {
	private final double[][] PROFILE;
	private final int[] SDP;
	private double[][] FREQUENCIES;
	private double[] INFORMATION;
	private int[][] acidCounts;
	private int[] groupComp;
	Alignment al;

	public Profile(int[] sdp, int[] groupCompound, Alignment a) {
		SDP = sdp;
		PROFILE = new double[20][sdp.length];
		acidCounts = new int[sdp.length][20];
		calcProfile(groupCompound, a);
		groupComp = groupCompound;
		al = a;
	}

	private void calcProfile(int[] groupCompound, Alignment a) {
		for (int i = 0; i < SDP.length; i++) {
			double m = 0;
			double d = 0;

			for (int j = 1; j <= groupCompound[0]; j++) {
				int ac = a.getAcidInInt(groupCompound[j], SDP[i]);
				if (ac != 20)
					acidCounts[i][ac]++;
			}

			for (int j = 0; j < 20; j++) {
				PROFILE[j][i] = Math.log(StaticFunction
						.calculateSmoothWithPseudocount(acidCounts[i], j,
								groupCompound[0], a.acidFreqs)
						/ (groupCompound[0] + 1));
				m += a.acidFreqs[j] * PROFILE[j][i];
				d += a.acidFreqs[j] * PROFILE[j][i] * PROFILE[j][i];
			}

			d = Math.sqrt((d - m * m) * (SDP.length + 1));

			for (int j = 0; j < 20; j++) {
				PROFILE[j][i] = (PROFILE[j][i] - m) / d;
			}
		}
	}

	public double[] getInformations() {
		getFrequencies();
		if (INFORMATION == null) {
			INFORMATION = new double[SDP.length];
			for (int i = 0; i < SDP.length; i++) {
				INFORMATION[i] = StaticFunction.calculateEntropy(
						FREQUENCIES[i], al.acidFreqs);
			}
		}
		return INFORMATION;
	}

	public double[][] getFrequencies() {
		if (FREQUENCIES == null) {
			FREQUENCIES = new double[SDP.length][20];
			for (int i = 0; i < SDP.length; i++) {
				for (int j = 0; j < 20; j++) {
					FREQUENCIES[i][j] = StaticFunction.calculateSmooth(
							acidCounts[i], j, groupComp[0]);
				}
			}
		}
		return FREQUENCIES;
	}

	public String toString() {
		StringBuffer result = new StringBuffer("acid/position\t");
		for(int sdp : SDP)
			result.append(sdp).append("\t");
		result.append("\n");
		for (int i = 0; i < PROFILE.length; i++) {
			result.append(StaticFunction.getAcidForIndex(i));
			for (int j = 0; j < PROFILE[i].length; j++) {
				result.append("\t" + PROFILE[i][j]);
			}
			result.append("\n");
		}
		result.append("\n");
		return result.toString();
	}

	public double getWeightForSeq(int[] seq) {
		double result = 0;
		for (int i = 0; i < SDP.length; i++) {
			if (seq[SDP[i]] != 20)
				result += PROFILE[seq[SDP[i]]][i];
		}
		return result / SDP.length;
	}
}
