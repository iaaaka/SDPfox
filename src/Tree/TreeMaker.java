package Tree;

import java.util.StringTokenizer;
import java.io.*;
import java.util.*;
import Util.Numerator;
import Math.StaticFunction;
import Objects.Grouping;

public class TreeMaker {
	public TreeMaker() {
	}

	/**
	 * 
	 * @param fname
	 *            File name. File format: node_id node_name parent_id weight
	 *            length
	 * @return root
	 * @throws Exception
	 *             bad file format
	 */
	public static Node makeTree(String fname) throws Exception {
		return makeTree(new FileInputStream(fname));
	}
	
	public static void main(String[] args) {
		System.out.println(makeTreeFromParentheses("(((BGAT_HUMAN:0.25440,O77563_PIG:0.25440):0.26877,Q95158_CAN:0.52317):0.15346," +
				"((Q28855_9PR:0.10635,GGTA1_BOVI:0.10635):0.05695,GGTA1_MOUS:0.16330):0.51334);"));
	}
	
	public static Node makeTreeFromParentheses(String tree){
		Node r = new Node();
		if(tree.charAt(tree.length()-1) == ';')
			tree = tree.substring(0,tree.length()-1);
		tree = tree.substring(1,tree.length()-1);
		parseNodeFromParentheses(r,tree);
		return r;
	}
	
	private static void parseNodeFromParentheses(Node parent,String branch){
		//System.out.println(branch);
		int comma = findComma(branch);
		processPart(parent,branch.substring(0,comma));
		processPart(parent,branch.substring(comma+1));
	}
	
	private static void processPart(Node parent,String part){
		int colon = part.lastIndexOf(':');
		double l = Double.parseDouble(part.substring(colon+1));
		part = part.substring(0,colon);
		if(part.charAt(0) == '('){
			part = part.substring(1,part.length()-1);
			Node n = new Node(0,l);
			parent.addChild(n);
			parseNodeFromParentheses(n,part);
		}else
			parent.addChild(new Node(part,0,l));
	}
	
	private static int findComma(String tree){
		int par_no = 0;
		for(int i=0;i<tree.length();i++){
			if(tree.charAt(i) == '(')
				par_no++;
			if(tree.charAt(i) == ')')
				par_no--;
			if(tree.charAt(i) == ',' && par_no == 0)
				return i;
		}
		return -1;
	}
	
	public static Node makeTree(InputStream is) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		Hashtable<Integer, Node> nodes = new Hashtable<Integer, Node>();
		Node root = null;
		int i = 1;
		for (String line = br.readLine(); line != null; line = br.readLine(), i++) {
			StringTokenizer t = new StringTokenizer(line, " \n");
			Integer key = new Integer(Integer.parseInt(t.nextToken()));
			if (i != key.intValue())
				throw new Exception("bad file format");
			String name = t.nextToken();
			Integer parent_key = new Integer(Integer.parseInt(t.nextToken()));
			if (parent_key.intValue() >= i
					|| (parent_key.intValue() == 0 && i != 1))
				throw new Exception("bad file format");
			double weight = Double.parseDouble(t.nextToken());
			double length = Double.parseDouble(t.nextToken());
			if (t.hasMoreTokens())
				throw new Exception("bad file format");
			if (parent_key.intValue() == 0) {
				root = new Node(name, weight, length);
				nodes.put(key, root);
			} else {
				Node parent = nodes.get(parent_key);
				Node next = new Node(name, weight, weight);
				parent.addChild(next);
				nodes.put(key, next);
			}
		}
		return root;
	}

	/**
	 * 
	 * @param n1
	 *            all childs of first node
	 * @param n2
	 *            all childs of second node
	 * @param leafDistance
	 *            distance between leafs
	 * @return distance between node. Average distance between all childs of
	 *         first node and all childs of second node.
	 */
	private static double averageDistanceBetweenNodes(Vector<Node> n1,
			Vector<Node> n2, double[][] dist) {
		double result = 0;
		for (int i = 0; i < n1.size(); i++) {
			for (int j = 0; j < n2.size(); j++) {
				result += dist[n1.get(i).getNumber()][n2.get(j).getNumber()];
			}
		}
		result /= n1.size() * n2.size();
		return result;
	}

	/**
	 * 
	 * @param nodes all nodes
	 * @return two nearest nodes from nodes
	 */
	private static void agglomerateNearestNodes(Vector<Node> nodes,
			double[][] distM) {
		if(nodes.size() <= 1)return;
		Node[] temp = new Node[2];
		int[] index = { -1, -1 };
		double dist = 999999999E+200;
		for (int i = 0; i < nodes.size(); i++) {
			Node fn = nodes.get(i);
			for (int j = 0; j < i; j++) {
				Node sn = nodes.get(j);
				double tempD = averageDistanceBetweenNodes(fn.getAllLeafs(), sn
						.getAllLeafs(), distM);
				if (tempD < dist) {
					dist = tempD;
					temp[0] = fn;
					temp[1] = sn;
					index[0] = i;
					index[1] = j;
				}
			}
		}
		Node n = new Node();
		n.addChild(temp[0]);
		n.addChild(temp[1]);
		temp[0].setLength(dist/2 - temp[0].getOverallTreeLength());
		temp[1].setLength(dist/2 - temp[1].getOverallTreeLength());
		nodes.remove(index[0]);
		nodes.remove(index[1]);
		nodes.add(n);
		int[] clust = n.getSortedLeafNumberArray();
		n.setWeight(calculateWeight(clust,getOthersLeafs(clust,distM.length),distM));
	}
	
	private static int[] getOthersLeafs(int[] leafs,int seqCount){
		int[] others = new int[seqCount- leafs.length];
		int othersIndex= 0;
		for(int j=0;j<leafs[0];j++){
			others[othersIndex] = j;
			othersIndex++;
		}
		for(int i=0;i<leafs.length-1;i++){
			for(int j=leafs[i]+1;j<leafs[i+1];j++){
				others[othersIndex] = j;
				othersIndex++;
			}
		}
		for(int j=leafs[leafs.length-1]+1;j<seqCount;j++){
			others[othersIndex] = j;
			othersIndex++;
		}
		return others;
	}
	
	public static double[] calculateINOUT(int[] group, double[][] dist,int seq_cnt){
		return calculateINOUT(group, getOthersLeafs(group, seq_cnt), dist);
	}
	
	/**
	 * 
	 * @param clust
	 * @param other
	 * @param dist
	 * @return {max distance in,min dictance out}
	 */
	private static double[] calculateINOUT(int[] clust, int[] other, double[][] dist){
		double maxIn;
		double minOut;
		if(clust.length == 1) return new double[]{-1,-1};
		if(other.length == 0){
			maxIn = dist[0][0];
			for(int i=0;i<dist.length;i++){
				for(int j=0;j<i;j++){
					if(dist[i][j]>maxIn){
						maxIn = dist[i][j];
					}
				}
			}
			return new double[] {maxIn,0};
		}
		else{
			maxIn = dist[clust[0]][clust[1]];
			minOut = dist[clust[0]][other[0]];
			for(int i=0;i<clust.length;i++){
				for(int j=0;j<i;j++){
					if(dist[clust[i]][clust[j]]>maxIn){
						maxIn = dist[clust[i]][clust[j]];
					}
				}
				
				for(int j=0;j<other.length;j++){
					if(dist[clust[i]][other[j]]<minOut){
						minOut = dist[clust[i]][other[j]];
					}
				}
			}
		}
		//return maxIn-minOut;
		return new double[] {maxIn,minOut};
	}
	
	private static double calculateWeight(int[] clust, int[] other, double[][] dist){
		double[] t = calculateINOUT(clust,other,dist);
		return t[0]-t[1];
	}

	public static Node makeUPGMATreeFromDistantMatrix(double[][] dist,
			Numerator objNum) {
		Vector<Node> nodes = new Vector<Node>();
		for(int i=0;i<dist.length;i++){
			nodes.add(new Node(objNum.getNameForNum(i),i));
		}
		do{
			agglomerateNearestNodes(nodes,dist);	
		}while(nodes.size()>1);
		nodes.firstElement().setLength(0);
		return nodes.firstElement();
	}

}
