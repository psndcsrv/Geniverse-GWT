package org.concord.geniverse.client;

import java.util.ArrayList;


import com.google.gwt.user.client.rpc.AsyncCallback;

public interface OrganismServiceAsync {
	void getOrganism(AsyncCallback<GOrganism> callback);
	void getOrganism(int sex, String alleles, AsyncCallback<GOrganism> callback);
	void getOrganism(int sex, AsyncCallback<GOrganism> callback);
	void getPhenotypes(GOrganism gOrg, AsyncCallback<ArrayList<String>> callback);
}
