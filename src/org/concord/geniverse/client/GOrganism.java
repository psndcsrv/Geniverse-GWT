package org.concord.geniverse.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GOrganism implements Serializable, IsSerializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private int sex;
	private String alleles;
	private String imageURL;
	private ArrayList<String> characteristics;
	private HashMap<String, String> characteristicMap = new HashMap<String, String>();
	private HashMap<String, String> metaInfo = new HashMap<String, String>();

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getSex() {
		return sex;
	}

	public void setAlleles(String alleles) {
		this.alleles = alleles;
	}

	public String getAlleles() {
		return alleles;
	}

	public void setMetaInfo(HashMap<String, String> metaInfo) {
		this.metaInfo = metaInfo;
	}

	public HashMap<String, String> getMetaInfo() {
		return metaInfo;
	}
	
	public void setCharacteristicMap(HashMap<String, String> characteristicMap) {
        this.characteristicMap = characteristicMap;
    }

    public HashMap<String, String> getCharacteristicMap() {
        return characteristicMap;
    }

	public void setCharacteristics(ArrayList<String> characteristics) {
		this.characteristics = characteristics;
	}

	public ArrayList<String> getCharacteristics() {
		return characteristics;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getImageURL() {
		return imageURL;
	}
}
