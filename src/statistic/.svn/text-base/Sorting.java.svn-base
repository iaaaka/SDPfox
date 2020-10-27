package statistic;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.*;
import painter.ColorPanel;
import java.math.*;

public class Sorting {
	private double[] singleSDPfrequency;
	private double[][] singleSDPfrequencySorted;
	final private double[][] pairwiseSDPfrequency;
	private double[][] pairwiseSDPfrequencySorted;
	private float[][][] tripleSDPfrequency;
	public int numOfGaps;
	String alignmentName;
	int scale = 50;
	public static Vector<Integer> Gaps;

	Sorting(double[] sin, double[][] pair, float[][][] tri, String name) {
		singleSDPfrequency = sin;
		singleSDPfrequencySorted = singleSDPsorting(singleSDPfrequency);
		pairwiseSDPfrequency = pair;
		pairwiseSDPfrequencySorted = pairwiseSDPsorting(pairwiseSDPfrequency);
		tripleSDPfrequency = tri;
		alignmentName = name;
		tripleSDPsorting(tripleSDPfrequency);
	}

	Sorting(double[] sin, double[][] pair, String name) {
		singleSDPfrequency = sin;
		singleSDPfrequencySorted = singleSDPsorting(singleSDPfrequency);
		pairwiseSDPfrequency = pair;
		pairwiseSDPfrequencySorted = pairwiseSDPsorting(pairwiseSDPfrequency);
		alignmentName = name;
	}

	Sorting(double[][] pair, String name) {
		pairwiseSDPfrequency = pair;
		pairwiseSDPfrequencySorted = pairwiseSDPsorting(pairwiseSDPfrequency);
		alignmentName = name;
	}

	public static int[][] singleSDPsorting(int[] sorted) {
		double[] doubSort = new double[sorted.length];
		for (int i = 0; i < sorted.length - 1; i++) {
			doubSort[i] = (double) sorted[i];
		}
		double[][] doubRes = singleSDPsorting(doubSort);
		int[][] intRes = new int[sorted.length][2];
		for (int i = 0; i < sorted.length - 1; i++) {
			for (int j = 0; j < 2; j++) {
				intRes[i][j] = (int) doubRes[i][j];
			}
		}
		return intRes;
	}

	public static double[][] singleSDPsorting(double[] sorted) {
		double[][] unsort = new double[sorted.length][2];
		for (int i = 0; i < sorted.length; i++) {
			unsort[i][0] = i + 1;
			unsort[i][1] = sorted[i];
		}
		Arrays.sort(unsort, new Comparator<double[]>() {
			public int compare(double[] a, double[] b) {
				return Double.compare(b[1], a[1]);
			}
		});
		return unsort;
	}

	public double[][] getPairwiseSDPmatrix() {
		return pairwiseSDPfrequencySorted;
	}

	public void saveMatrixForNJ() throws Exception {
		DecimalFormat df = new DecimalFormat("0.000000");
		FileWriter NJfile = new FileWriter("Laci_NJmatrix.txt");
		NJfile.write("   " + (pairwiseSDPfrequencySorted.length - 1) + "\n");
		for (int i = 1; i < pairwiseSDPfrequencySorted.length; i++) {
			NJfile.write("no" + (int) pairwiseSDPfrequencySorted[i][0]
					+ "_pos   ");
			for (int j = 1; j < pairwiseSDPfrequencySorted.length; j++) {
				if (i == j)
					NJfile.write("0.000000" + " ");
				else
					NJfile.write(df.format(-Math
							.log(pairwiseSDPfrequencySorted[i][j]))
							+ "\t");
			}
			NJfile.write("\n");
		}
		NJfile.close();
	}

	public double[][] pairwiseSDPsorting(double[][] sortedMatr) {
		double[] monobuf = new double[sortedMatr.length - 1];
		double[][] dibuf, result = new double[sortedMatr.length][sortedMatr.length];
		Arrays.fill(monobuf, 0);
		for (int i = 0; i < sortedMatr.length - 1; i++) {
			for (int j = 1; j < sortedMatr.length; j++) {
				monobuf[i] = monobuf[i] + sortedMatr[i + 1][j];
			}
		}
		dibuf = singleSDPsorting(monobuf);
		for (double[] x : dibuf) {
			for (double y : x) {
				System.out.print(y + "\t");
			}
			System.out.print("\n");
		}
		for (int i = 1; i < sortedMatr.length; i++) {
			result[0][i] = result[i][0] = dibuf[i - 1][0];
		}
		for (int i = 1; i < sortedMatr.length; i++) {
			for (int j = 1; j < sortedMatr.length; j++) {
				result[i][j] = sortedMatr[(int) (dibuf[i - 1][0])][(int) (dibuf[j - 1][0])];
			}
		}
		double nol = 0;
		int nolpos = result.length;
		for (int i = 1; i < result.length; i++) {
			for (int j = 1; j < result.length; j++) {
				nol += result[i][j];
			}
			if (nol == 1) {
				nolpos = i;
				break;
			} else
				nol = 0;
		}
		result = Arrays.copyOf(result, nolpos);
		for (int i = 0; i < result.length; i++) {
			result[i] = Arrays.copyOf(result[i], nolpos);
		}
		numOfGaps = sortedMatr.length - nolpos;
		Gaps = new Vector<Integer>();
		for (int i = nolpos - 1; i < dibuf.length; i++) {
			Gaps.addElement((int) dibuf[i][0]);
		}
		for (int x : Gaps) {
			System.out.print(x + "\t");
		}
		return result;

	}

	public double[][] pairwiseSDPsortingCloning(double[][] sortingMatr) {
		double[][] sortedMatr = new double[sortingMatr.length][sortingMatr.length];
		for (int i = 0; i < sortedMatr.length; i++) {
			for (int j = 0; j < sortedMatr.length; j++) {
				sortedMatr[i][j] = sortingMatr[i][j];
			}
		}
		pairwiseSDPsorting(sortedMatr);
		double nol = 0;
		int nolpos = sortedMatr.length;
		for (int i = 1; i < sortedMatr.length; i++) {
			for (int j = 1; j < sortedMatr.length; j++) {
				nol += sortedMatr[i][j];
			}
			if (nol == 1) {
				nolpos = i + 1;
				break;
			} else
				nol = 0;
		}
		sortedMatr = Arrays.copyOf(sortedMatr, nolpos);
		return sortedMatr;
	}

	public void tripleSDPsorting(float[][][] freqCube) {
		for (int i = 0; i < freqCube.length; i++) {
			double[][] doublLayer = new double[freqCube.length][freqCube.length];
			for (int j = 0; j < freqCube.length; j++) {
				for (int k = 0; k < freqCube.length; k++) {
					doublLayer[j][k] = freqCube[i][j][k];
				}
			}
			pairwiseSDPsorting(doublLayer);
			for (int j = 0; j < freqCube.length; j++) {
				for (int k = 0; k < freqCube.length; k++) {
					freqCube[i][j][k] = (float) doublLayer[j][k];
				}
			}
		}
	}

	public void saveAllmatrixes() throws Exception {
		if (singleSDPfrequency != null) {
			FileWriter load = new FileWriter(alignmentName + "_SDPFREQ.txt");
			for (double x : singleSDPfrequency) {
				load.write(x + "\t");
			}
			load.close();
			FileWriter sortload = new FileWriter(alignmentName
					+ "_SDPFREQSORT.txt");
			for (double[] x : singleSDPfrequencySorted) {
				for (double y : x) {
					sortload.write(y + "\t");
				}
				sortload.write('\n');
			}
			sortload.close();
		}

		FileWriter matrloadS = new FileWriter(alignmentName
				+ "_SDPPAIRSFREQSORT.txt");
		for (double[] x : pairwiseSDPfrequencySorted) {
			for (double y : x) {
				matrloadS.write(y + "\t");
			}
			matrloadS.write('\n');
		}
		matrloadS.close();

		FileWriter matrload = new FileWriter(alignmentName
				+ "_SDPPAIRSFREQ.txt");
		for (double[] x : pairwiseSDPfrequency) {
			for (double y : x) {
				matrload.write(y + "\t");
			}
			matrload.write('\n');
		}
		matrload.close();
		if (tripleSDPfrequency != null) {
			FileWriter tri = new FileWriter(alignmentName
					+ "_SDPTRIPLEFREQ.txt");
			for (int i = 1; i < tripleSDPfrequency.length; i++) {
				tri.write(tripleSDPfrequency[0][0][i] + "\n");
				for (int j = 0; j < tripleSDPfrequency.length; j++) {
					for (int k = 0; k < tripleSDPfrequency.length; k++) {
						tri.write(" " + tripleSDPfrequency[i][j][k] + "\t");
					}
					tri.write("\n");
				}
				tri.write("\n" + "\n" + "\n");
			}
			tri.close();

			FileWriter tris = new FileWriter(alignmentName
					+ "_SDPTRIPLEFREQshort.txt");
			for (int i = 1; i < tripleSDPfrequency.length; i++) {
				tris.write(tripleSDPfrequency[0][0][i] + "\n");
				for (int j = 0; j < scale + 1; j++) {
					for (int k = 0; k < scale + 1; k++) {
						tris.write(" "
								+ Arrays.copyOf(tripleSDPfrequency[i],
										scale + 1)[j][k] + "\t");
					}
					tris.write("\n");
				}
				tris.write("\n" + "\n" + "\n");
			}
			tris.close();
		}
	}

	public void saveAllmatrixes(int scale) throws Exception {
		this.scale = scale;
		saveAllmatrixes();
	}

	public void savePairwisingMatrixImage() {
		double[][] onlyFreq = new double[pairwiseSDPfrequencySorted.length - 1][pairwiseSDPfrequencySorted.length - 1];
		for (int j = 0; j < onlyFreq.length; j++) {
			for (int i = 0; i < onlyFreq.length; i++) {
				onlyFreq[i][j] = pairwiseSDPfrequencySorted[i + 1][j + 1];
			}
		}
		ColorPanel.saveImageJPG(alignmentName + "_MATRIX", 4, onlyFreq);

	}

	public void saveLayersMatrixes() {
		for (int i = 1; i < tripleSDPfrequency.length; i++) {
			double[][] onlyFreq = new double[tripleSDPfrequency[i].length - 1][tripleSDPfrequency[i].length - 1];
			for (int j = 0; j < onlyFreq.length; j++) {
				for (int k = 0; k < onlyFreq.length; k++) {
					onlyFreq[k][j] = tripleSDPfrequency[i][k + 1][j + 1];
				}
			}
			ColorPanel.saveImageJPG(alignmentName + "Layer_" + i + " _MATRIX",
					4, onlyFreq);
		}
	}
}
