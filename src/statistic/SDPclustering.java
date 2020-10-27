package statistic;

import Math.StaticFunction;
import Util.Numerator;

import java.io.FileWriter;
import java.util.*;
import Exception.*;

public class SDPclustering {
	public Numerator posNum;
	final double[][] SDPfreqMatr;
	int[] clusterPerPosition;
	int[][] trueClusters;

	SDPclustering(double[][] pairwiseSDPMatr, int NumofClust) throws Exception {
		SDPfreqMatr = new double[pairwiseSDPMatr.length - 1][pairwiseSDPMatr.length - 1];
		posNum = new Numerator();
		for (int i = 1; i < pairwiseSDPMatr.length; i++) {
			posNum.addName("" + (int) (pairwiseSDPMatr[i][0]));
			for (int j = 1; j < pairwiseSDPMatr.length; j++) {
				SDPfreqMatr[i - 1][j - 1] = -Math.log(pairwiseSDPMatr[i][j]);
			}
		}
		clusteringConnectedComponent(NumofClust);
	}

	public double searchThreshold(int numOfClusters) throws Exception {
		double minThreshold = SDPfreqMatr[0][1], maxThreshold = 6, Threshold = 0;
		int[] minAr = StaticFunction.searchConnectedComponents(SDPfreqMatr,
				minThreshold);
		Arrays.sort(minAr);
		int[] maxAr = StaticFunction.searchConnectedComponents(SDPfreqMatr,
				maxThreshold);
		Arrays.sort(maxAr);
		while (true) {
			int[] midAr = StaticFunction.searchConnectedComponents(SDPfreqMatr,
					minThreshold + (maxThreshold - minThreshold) / 2);
			Arrays.sort(midAr);
			
			int c = 0, curClustNum=0;
			for (int i = 0; i < midAr.length-1; i++) {
                   if(midAr[i]==midAr[i+1]&& midAr[i]!=c){
                	   curClustNum++;
                	   c=midAr[i];
                   }
			}
			if (maxThreshold == maxThreshold - (maxThreshold - minThreshold)
					/ 2) {
				System.out.println("Sorry, the rate of clusterisation "
						+ numOfClusters + " is not possible, only "
						+ curClustNum + " is!");
				Threshold = maxThreshold;
				break;
			} else if (numOfClusters == curClustNum) {
				Threshold = maxThreshold - (maxThreshold - minThreshold) / 2;
				break;
			} else if (numOfClusters > curClustNum) {
				maxThreshold = maxThreshold - (maxThreshold - minThreshold) / 2;

			} else if (numOfClusters < curClustNum) {
				minThreshold = minThreshold + (maxThreshold - minThreshold) / 2;
			}
		}
		System.out.println(Threshold);
		return Threshold;
	}

	public void clusteringConnectedComponent(int NumofClust) throws Exception {
		clusterPerPosition = StaticFunction.searchConnectedComponents(
				SDPfreqMatr, searchThreshold(NumofClust));
	}

	public void getClusters() {
		int p = 0;
		int sdp;
		int[] gapClust = clusterPerPosition.clone();
		int[] gaps = SDPdata.getGaps().clone();
		int[] SDPs = SDPdata.getSDPs().clone();
		Hashtable<Integer, Hashtable<Integer, Integer>> clustirizeSDP = new Hashtable<Integer, Hashtable<Integer, Integer>>();
		for (int i = 0; i < gaps.length; i++) {
			gapClust[gaps[i] - 1] = 0;
		}
		int notSDPnotinClust = clusterPerPosition.length - gaps.length
				- SDPs.length;
		int notSDP = notSDPnotinClust;
		int SDPnotinClust = SDPs.length;
		int clustSize;
		trueClusters = Sorting.singleSDPsorting(gapClust);
		int clustNo = 1;
		Hashtable<Integer, Integer> cluster;
		while (trueClusters[p][1] != 0) {
			if (trueClusters[p][1] == trueClusters[p + 1][1]) {
				cluster = new Hashtable<Integer, Integer>();
				clustSize = 0;
				do {
					for (int i = 0; i < SDPs.length; i++) {
						if (trueClusters[p][0] == SDPs[i]) {
							cluster.put(SDPs[i], i);
							break;
						}
					}
					p++;
					clustSize++;
				} while (trueClusters[p][1] == trueClusters[p - 1][1]);
				clustirizeSDP.put(clustNo, cluster);
				clustNo++;
				notSDPnotinClust -= (clustSize - cluster.size());
			}
			p++;
		}
		System.out.println("Not clasterized non-SDPs: " + notSDPnotinClust
				+ " of " + notSDP);
		for (int i = 1; i <= clustirizeSDP.size(); i++) {
			System.out.println("Cluster " + i);
			Enumeration<Integer> SDP = clustirizeSDP.get(i).keys();
			while (SDP.hasMoreElements()) {
				sdp = SDP.nextElement();
				System.out.println("\t" + sdp + "\t"
						+ clustirizeSDP.get(i).get(sdp));
			}
			SDPnotinClust -= clustirizeSDP.get(i).size();
		}
		System.out.println("Not clasterized SDPs " + SDPnotinClust + " of "
				+ SDPdata.getSDPs().length);
	}
}
