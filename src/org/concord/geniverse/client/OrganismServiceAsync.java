package org.concord.geniverse.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface OrganismServiceAsync {
	void getOrganism(AsyncCallback<IOrganism> callback);
	void getOrganism(int sex, String alleles, AsyncCallback<IOrganism> callback);
	void getOrganism(int sex, AsyncCallback<IOrganism> callback);
	void getOrganismImageURL(IOrganism organism, int imageSize, AsyncCallback<String> callback);
	void getOrganismImageURL(AsyncCallback<String> callback);
	void getOrganismPhenotypes(IOrganism gOrg,
			AsyncCallback<ArrayList<String>> callback);
	void breedOrganism(IOrganism org1, IOrganism org2,
			AsyncCallback<IOrganism> callback);
}
