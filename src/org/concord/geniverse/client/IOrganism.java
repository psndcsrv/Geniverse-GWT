package org.concord.geniverse.client;

import java.util.ArrayList;
import java.util.HashMap;

public interface IOrganism {
	public void setName(String name);

	public String getName();

	public void setSex(int sex);

	public int getSex();

	public void setAlleles(String alleles);

	public String getAlleles();

	public void setMetaInfo(HashMap<String, Object> metaInfo);

	public HashMap<String, Object> getMetaInfo();

	public void setCharacteristics(ArrayList<String> characteristics);

	public ArrayList<String> getCharacteristics();

	public void setImageURL(String imageURL);

	public String getImageURL();
}
