package Util;

import java.util.Hashtable;
import java.util.ArrayList;
import Exception.NumeratorException;

class MyString{
	private String s;
	public MyString(String s){
		this.s =s;
	}
	public String getString(){
		return s;
	}
	
	public int hashCode(){
		return s.toLowerCase().hashCode();
	}
	
	public boolean equals(Object o){
		if(o instanceof MyString){
			return ((MyString)o).s.equalsIgnoreCase(s);
		}
		return false;
	}
}

public class Numerator {
	private ArrayList<MyString> namesForNum;
	private Hashtable<MyString, Integer> numsForName;
	private int numOfNames = 0;

	public Numerator() {
		namesForNum = new ArrayList<MyString>();
		numsForName = new Hashtable<MyString, Integer>();
	}

	public void rename(int no, String newName) {
		MyString oldName = namesForNum.get(no);
		numsForName.remove(oldName);
		numsForName.put(new MyString(newName), new Integer(no));
		namesForNum.set(no, new MyString(newName));
	}

	public void rename(String oldName, String newName) {
		rename(this.getNumForName(oldName), newName);
	}

	public boolean removeName(String name) {
		return removeName(this.getNumForName(name));
	}

	public boolean removeName(int no) {
		if (no >= namesForNum.size())
			return false;
		numsForName.remove(namesForNum.remove(no));
		for (int i = no; i <= namesForNum.size(); i++) {
			numsForName.put(namesForNum.get(i), new Integer(i));
		}
		return true;
	}

	public int addName(String name) throws NumeratorException {
		MyString k = new MyString(name);
		if(numsForName.containsKey(k)){
			throw new NumeratorException(k.getString());
		}
		namesForNum.add(k);
		numsForName.put(k, new Integer(numOfNames));
		return numOfNames++;
	}

	public String getNameForNum(int num) {
		return namesForNum.get(num).getString();
	}

	public int getNumForName(String name) {
		MyString k = new MyString(name);
		if(!numsForName.containsKey(k))
			return -1;
		return numsForName.get(k);
	}
	/**
	 * 
	 * @param obj ������ ���������
	 * @return ���������� ������ ������ ���� ���������� ������������ �
	 * ��������� ����������� ���� �� ������������
	 */

	public String equalsWithNumerator(Numerator obj) {
		if (namesForNum.size() != obj.getNumOfNames()){	
			return "incorrect size: "+namesForNum.size()+" and "+obj.getNumOfNames();
		}
		for (int i = 0; i < namesForNum.size(); i++) {
			if (!this.getNameForNum(i).equals(obj.getNameForNum(i)))
				return "incorrect name in position "+i+": "+namesForNum.get(i)+" not equals "+obj.getNameForNum(i);
		}
		return "";
	}

	public int getNumOfNames() {
		return numOfNames;
	}
}
