package Tree;

import java.util.*;
import java.text.*;

/**
 * @(#)AbstractNode.java
 * <p>
 * Description: ����������� ����� ����������� ���� ������ � ������� �����������
 * ��� ����������� ������� ������
 * </p>
 * <p>
 * Company: MSU.FBB
 * </p>
 * @author Mazin P.
 * @version 1.0
 */

public class Node {
	private double worseWeightInChildClusters;
	private double weight;
	private double lenght;
	public String name;
	private Vector<Node> childs = new Vector<Node>();
	private int number = -1; // ������ ����� �������������� ������� �����
								// (������������������) �� ���������. ���
								// ���������� �����, ��� ���� �� ������������ -
								// -1;

	public Node() {
		name = "";
	}

	public Node(double w, double l) {
		name = "";
		weight = w;
		lenght = l;
	}
	
	public void setNumber(int i){
		number = i;
	}

	public Node(double w, double l, int num) {
		number = num;
		name = "";
		weight = w;
		lenght = l;
	}

	public Node(String n, double w, double l) {
		name = n;
		weight = w;
		lenght = l;
	}

	public Node(String n, double w, double l, int num) {
		number = num;
		name = n;
		weight = w;
		lenght = l;
	}

	public Node(String n, int num) {
		number = num;
		name = n;
	}

	public void addChild(Node c) {
		childs.add(c);
	}

	public Vector<Node> getAllLeafs() {
		Vector<Node> result = new Vector<Node>();
		if (this.getChildNum() == 0) {
			result.add(this);
		} else {
			for (int i = 0; i < this.getChildNum(); i++) {
				result.addAll(this.childs.get(i).getAllLeafs());
			}
		}
		return result;
	}
	
	public Vector<Node> cutAtLevel(double l){
		Vector<Node> branchs = new Vector<Node>();
		cutAtLevel(branchs,l,0);
		return branchs;
	}
	
	private void cutAtLevel(Vector<Node> branchs,double l,double curr_l){
		if(this.lenght+curr_l >= l || getChildNum() == 0)
			branchs.add(this);
		else
			for(Node n : childs)
				n.cutAtLevel(branchs,l,this.lenght+curr_l);
	}

	/**
	 * ������� ��� ������ ����������� ������� ������. ����������� ���������
	 * �����, ��� ��� ���������� �������� ������������� �� ���� ���������
	 * �������� ������.
	 * 
	 * @param branchs
	 *            Vector ������ ������ � ������� ����� ������� ����
	 *            ��������������� ������ ������� ���������, ������ �������� ��
	 *            ��������
	 * @return double ��� ���������� �������� �� ��� ��� ����� � branchs
	 */

	public double getBestCut(Vector<Node> branchs) {
		if (this.getChildNum() == 0)
			worseWeightInChildClusters = getWeight();
		else {
			worseWeightInChildClusters = getChild().get(0).getBestCut(branchs);
		}
		for (int i = 1; i < getChild().size(); i++) {
			Vector<Node> temp = new Vector<Node>();
			worseWeightInChildClusters = Math.max(worseWeightInChildClusters,
					getChild().get(i).getBestCut(temp));
			branchs.addAll(temp);
		}
		if (worseWeightInChildClusters >= getWeight()) {
			branchs.removeAllElements();
			branchs.add(this);
			return getWeight();
		}
		return worseWeightInChildClusters;
	}

	public Vector<Node> getChild() {
		return childs;
	}

	public Node getChild(int i) {
		return (Node) childs.get(i);
	}

	/**
	 * ���������� ������ ���������� ������� ���� AbstractNode. ���� ���� - ����
	 * �� ������ ������� - 0
	 * 
	 * @return Vector ������ ���� ����� ������� ����.
	 */
	public int getChildNum() {
		return childs.size();
	}

	public String getClusterData() {
		StringBuffer result = new StringBuffer(1000);
		result.append("tree: " + toString());
		result.append("\nall childs: " + getNamesOfAllChild());
		result.append("\nall leafs: " + getNamesOfAllLeafs());
		result.append("\nweight of this cluster: " + getWeight());
		result.append("\nweight of worse child`s cluster: "
				+ worseWeightInChildClusters);
		return result.toString();
	}
	
	public Vector<Node> getAllChild(){
		Vector<Node> result = new Vector<Node>();
		result.add(this);
		for(int i=0;i<childs.size();i++){
			result.addAll(childs.get(i).getAllChild());
		}
		return result;
	}

	public String getNamesOfAllChild() {
		StringBuffer result = new StringBuffer(1000);
		result.append(name + " ");
		for (int i = 0; i < getChildNum(); i++) {
			result.append(getChild(i).getNamesOfAllChild());
		}
		return result.toString();
	}

	public String getNamesOfAllLeafs() {
		StringBuffer result = new StringBuffer(1000);
		if (getChildNum() == 0) {
			result.append(name + " ");
		}
		for (int i = 0; i < getChildNum(); i++) {
			result.append(getChild(i).getNamesOfAllLeafs());
		}
		return result.toString();
	}

	public int getNumber() {
		return number;
	}

	public double getOverallTreeLength() {
		if (this.childs.size() == 0)
			return 0;
		return childs.get(0).getOverallTreeLength() + childs.get(0).lenght;
	}

	public int[] getSortedLeafNumberArray() {
		Vector<Node> leafs = this.getAllLeafs();
		int[] result = new int[leafs.size()];
		for (int i = 0; i < leafs.size(); i++) {
			result[i] = leafs.get(i).getNumber();
		}
		Arrays.sort(result);
		return result;
	}

	public double getWeight() {
		return weight;
	}

	public void setLength(double l) {
		this.lenght = l;
	}
	
	public double getLength() {
		return this.lenght;
	}

	/**
	 * ���������� ��� �������� ��������������� ������� ����. ��� ������ ��� -
	 * ��� ����� �������.
	 * 
	 * @return double ��� �������� ��������������� ������� ����
	 */

	public void setWeight(double w) {
		this.weight = w;
	}

	public String toString() {
		DecimalFormatSymbols s = new DecimalFormatSymbols();
		s.setDecimalSeparator('.');
		DecimalFormat f = new DecimalFormat("####0.###########", s);
		if (childs.size() == 0) {
			return name + ":" + f.format(lenght);
		}
		StringBuffer result = new StringBuffer(1000);
		result.append("(");
		for (int i = 0; i < getChild().size(); i++) {
			result.append(getChild().get(i) + ",");
		}
		result.delete(result.length() - 1, result.length());
		result.append("):" + lenght);
		return result.toString();
	}
}
