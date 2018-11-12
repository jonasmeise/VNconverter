package type.LegArchive;

import java.util.ArrayList;

public class RawFile { //currently unsupported, since data is still written while the raw source is in main memory; no need to save it unless packing is a planned feature.
	private String name;
	private ArrayList<String> raw;
	private DataPosition archivePos;
}
