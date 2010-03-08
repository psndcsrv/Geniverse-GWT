package org.concord.geniverse.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface OrganismServiceAsync {
	void getOrganism(AsyncCallback<GOrganism> callback);
	void getOrganism(int sex, String alleles, AsyncCallback<GOrganism> callback);
	void getOrganism(int sex, AsyncCallback<GOrganism> callback);
	void getOrganismImageURL(GOrganism organism, int imageSize, AsyncCallback<String> callback);
	void getOrganismImageURL(AsyncCallback<String> callback);
	void getOrganismPhenotypes(GOrganism gOrg,
			AsyncCallback<ArrayList<String>> callback);
}
