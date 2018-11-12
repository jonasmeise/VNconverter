package type.LegArchive;

import java.util.ArrayList;

public class LegArchive {
	private String name;
	private ArrayList<RawFile> files;
	private ArrayList<DataPosition> table;
	
	public LegArchive(String name) {
		setName(name);
		files = new ArrayList<RawFile>();
		table = new ArrayList<DataPosition>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<RawFile> getFiles() {
		return files;
	}
	public void addFile(RawFile rawFile) {
		this.files.add(rawFile);
	}
	public ArrayList<DataPosition> getTable() {
		return table;
	}
	public void addTable(DataPosition dp) {
		this.table.add(dp);
	}
	
	
}
