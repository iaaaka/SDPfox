package Objects;

import java.util.*;
import Math.StaticFunction;
import Exception.AlignmentException;
import Util.Numerator;;

public class Alignment {
	private ArrayList<String> seqs;
	private int seqLength = -1;
	private int[][] aligInIntForColumn;
	private int[][] aligInIntForSeq;
	private int[][] acidPositionCount;
	public double[] acidFreqs = new double[20];
	public int[] noForAllseq; //������ ������ 1.....N ���  N - ���-�� �������������������. �� ������� ����� ����� ����� �������������������
	private double[][] identityMatrix;
	private double averageIdentity;
	/**
	 * ����� ������� - ������������������. � ������� ������ �� ����������������� ���������������
	 * ������ �� ������������. ���� ��� - �� -1.
	 */
	private int[][] reference;
	
	
	public Alignment() {
		seqs = new ArrayList<String>();
	}
	
	public int getAlignmetnLength(){
		return seqLength;
	}
	
	public int getAlignmentPositionForSeqPosition(int seqNo,int seqPos){
		for(int i=seqPos-1;i<reference[seqNo].length;i++)
			if(reference[seqNo][i] == seqPos)
				return i;
		return -1;
	}

	public void addSeq(String seq,String name) throws AlignmentException {
		if (seqLength == -1) {
			seqLength = seq.length();
		} else if (seqLength != seq.length()) {
			throw new AlignmentException(name);
		}
		seqs.add(seq);
	}

	public String getSequenceInString(int seqNo) {
		return seqs.get(seqNo);
	}
	
	public int[] getSeqInInt(int seqNo){
		return aligInIntForSeq[seqNo];
	}
	
	public char getAcidInChar(int seq,int pos){
		return seqs.get(seq).charAt(pos);
	}
	
	public int getAcidInInt(int seq,int pos){
		return aligInIntForSeq[seq][pos];
	}
	/**
	 * 
	 * @param seqNo ����� ������������������
	 * @return ������ � ������� � ������ ��� ������ ������� ��������������� ����� ����� ��
	 * ������ ������������������. ���� ��� �� -1.
	 */
	public int[] getReference(int seqNo){
		return reference[seqNo];
	}
	
	
	/**
	 * 
	 * @param s sequence number
	 * @param removedVertexes - sequences that should be removed from seqrching. Should be sorted
	 * @return
	 */
	public int getNearestNeighbour(int s, int[] removedVertexes){
		double d = 0;
		int result = 0;
		for(int i=0;i<identityMatrix.length;i++){
			if(i != s && identityMatrix[i][s] > d){
				int idx = Arrays.binarySearch(removedVertexes, i);
				if(idx < 0 || removedVertexes[idx] != i){
					d = identityMatrix[i][s];
					result = i;
				}
			}
		}
		return result;
	}
	
	public int getNearestNeighbour(int s){
		double d = 0;
		int result = 0;
		for(int i=0;i<identityMatrix.length;i++){
			if(i != s && identityMatrix[i][s] > d){
				d = identityMatrix[i][s];
				result = i;
			}
		}
		return result;
	}

	public void calculateIntMatrix(Numerator sN) throws AlignmentException {
		aligInIntForColumn = new int[this.seqLength][seqs.size()];
		aligInIntForSeq = new int[seqs.size()][this.seqLength];
		noForAllseq = new int[seqs.size()+1];
		reference = new int[seqs.size()][this.seqLength];
		int nonGaps = 0;	
		noForAllseq[0] = seqs.size();
		for (int j = 0; j < seqs.size(); j++) {
			noForAllseq[j+1] = j;
		}
		
		for (int i = 0; i < seqLength; i++) {
			for (int j = 0; j < seqs.size(); j++) {
				int r = StaticFunction.getIndexForAcid((seqs.get(j))
						.charAt(i));
				if(r == -1) throw new AlignmentException(sN.getNameForNum(j),i);
				aligInIntForColumn[i][j] = r;
				aligInIntForSeq[j][i] = r;
				if(r != 20){
				acidFreqs[r]++;
				nonGaps++;
				}
			}
		}
		for(int i=0;i<20;i++){
			acidFreqs[i] /= nonGaps;
		}
		for(int i=0;i<reference.length;i++){
			int no=1;
			for(int j=0;j<reference[i].length;j++){
				if(aligInIntForSeq[i][j] != 20){
					reference[i][j] = no;
					no++;
				}
				else{
					reference[i][j]=-1;
				}
			}
		}
		identityMatrix = StaticFunction.getIdentityMatrix(this);
		averageIdentity = StaticFunction.getAvarageIdentityForAllAlignment(identityMatrix);
		//заполнение позиционно частотной матрицы
		acidPositionCount = new int[this.getAlignmetnLength()][20];
		for(int i=0;i<acidPositionCount.length;i++){
			for(int j=0;j<aligInIntForColumn[i].length;j++){
				if(aligInIntForColumn[i][j] != 20){
					acidPositionCount[i][aligInIntForColumn[i][j]]++;
				}
			}
		}
	}
	/**
	 * 
	 * @return number of sequences
	 */
	public int getAlignmentCount(){
		return seqs.size();
	}
	/**
	 * 
	 * @param i ������ ������������������
	 * @param j ������ ������������������
	 * @return ������������ ����� ��������������������
	 */
	public double getIdentity(int i,int j){
		return identityMatrix[i][j];
	}
	
	public int[] getAcidCountsForPosition(int p){
		return acidPositionCount[p];
	}
	
	public double[][] getIdentityMatrix(){
		return identityMatrix;
	}
	
	public double getAvarageIdentityForWholeAlignment(){
		return averageIdentity;
	}

	public int[] getColumnInInt(int no) {
		return aligInIntForColumn[no];
	}

}
