package org.concord.geniverse.client;

import java.util.ArrayList;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("organism")
public interface OrganismService extends RemoteService {
	GOrganism getOrganism();
	GOrganism getOrganism(int sex);
	GOrganism getOrganism(int sex, String alleles);

	ArrayList<String> getPhenotypes(GOrganism gOrg);
}
