package Objects;

import Math.StaticFunction;


public class Grouping {
  private int[] groupNoArray;
  private int[] groupsSize;
  private int seqNum = 0;
  private int realGroupCount;

  public Grouping() {
    groupsSize = new int[2000];
    groupNoArray = new int[4];
  }
  
  public Grouping(int noSeq) {
	    groupsSize = new int[2000];
	    groupNoArray = new int[noSeq];
	  }
  
  public Grouping(int noSeq,int rGrCount) {
	    groupsSize = new int[2000];
	    groupNoArray = new int[noSeq];
	    this.realGroupCount = rGrCount;
	  }
  
  public Grouping(int[] gr){
	  groupsSize = new int[2000];
	  groupNoArray = gr;
	  seqNum = gr.length;
	  realGroupCount = 0; 
	  for(int i=0;i<gr.length;i++){
		  realGroupCount = Math.max(realGroupCount, gr[i]);
		  groupsSize[gr[i]]++;
	  }
	  realGroupCount++;
  }
  
  public void addSeq(int groupNo) {
    if (seqNum == groupNoArray.length) {
      int[] temp = new int[groupNoArray.length + 1];
      for (int i = 0; i < groupNoArray.length; i++) {
        temp[i] = groupNoArray[i];
      }
      groupNoArray = temp;
    }
    groupNoArray[seqNum] = groupNo;
    groupsSize[groupNo]++;
    seqNum++;
  }
  
  public boolean equals(Grouping g){
	  for(int i=0;i<realGroupCount;i++){
		  int tempGr = -1;
		  if(groupsSize[i]>0)
		  for(int j=0;j<groupNoArray.length;j++){
			  if(groupNoArray[j] == i){
				  if(tempGr == -1) tempGr = g.groupNoArray[j];
				  else if(tempGr != g.groupNoArray[j]) return false;
			  }
		  }
	  }
	  return true;
  }
  
  public int getGroupForSeq(int seqNo){
	  return groupNoArray[seqNo];
  }
  
  public int getRealGroupCount(){
	  return realGroupCount;
  }
  
  /**
   * 
   * @return [������ �� �������, ������� � nongrouped][� ������� - ���������� ������������������� � ������, ������ ������ �������������������]
   */
  public int[][] getGroupingForAllGroup() {
    int[][] ret = new int[realGroupCount][groupNoArray.length + 1];
    for (int i = 0; i < groupNoArray.length; i++) {
      ret[groupNoArray[i]][0]++;
      ret[groupNoArray[i]][ret[groupNoArray[i]][0]] = i; ;
    }
    return ret;
  }
  
  public int[] getGroupingForGroup(int grNo){
	  int[] result = new int[groupNoArray.length + 1];
	  for(int i=0;i<groupNoArray.length;i++){
		  if(groupNoArray[i] == grNo){
			  result[0]++;
			  result[result[0]] = i;
		  }
	  }
	  return result;
  }

  public int[] getGroupNoArray(){
	  return groupNoArray;
  }
  
  public int getGroupSize(int grNo){
	  return groupsSize[grNo];
  }
  
  public int getNumberOfGoodGroup(){
	  int result =0;
	  for(int i=1;i<groupsSize.length;i++){
		  if(groupsSize[i]>= StaticFunction.MINIMAL_GROUP_SIZE)
			  result++;
	  }
	  return result;
  }

  public int[] getSequencesForGr(int gr){
	  int[] result = new int[groupsSize[gr]];
	  int j=0;
	  for(int i=0;i<groupNoArray.length;i++){
		  if(groupNoArray[i] == gr){
			  result[j] = i;
			  j++;
		  }
	  }
	  return result;
  }
  public boolean moveSeq(int seqNo, int newGroup) {
    if (groupNoArray[seqNo] != newGroup) {
      groupsSize[groupNoArray[seqNo]]--;
      groupsSize[newGroup]++;
      groupNoArray[seqNo] = newGroup;
      return true;
    }
    return false;
  }
  
  public void moveSeqs(int[] seqsNo,int newGroup){
	  for(int i=0;i<seqsNo.length;i++){
		  moveSeq(seqsNo[i],newGroup);
	  }
  }
  
  public void removeGroup(int gr,int newGr){
	  realGroupCount--;
	  for(int i=0;i<groupNoArray.length;i++){
		  if(groupNoArray[i] == gr){
			  this.moveSeq(i, newGr);
		  }
		  if(groupNoArray[i] > gr){
			  this.moveSeq(i, groupNoArray[i]-1);
		  }
	  }
  }
  
  public void setRealGroupCount(int rGrCount){
	  this.realGroupCount = rGrCount;
  }
  
  public void shuffleGrouping(){
	  StaticFunction.shuffleIntArray(groupNoArray);
  }
  
}
