package FileLoader.util.html;

public class MultipartParam {
	public String name;
	public String value;	// Для файлов тут название, иначе значение параметра
	public byte[] data;		// Для файлов тут содержимое, иначе null
	public boolean file;
	public MultipartParam(String name,String value,byte[] data,
			boolean file) {
		this.name = name;
		this.value = value;
		this.data = data;
		this.file = file;
	}
}
