package org.concord.geniverse.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("organism")
public interface OrganismService extends RemoteService {
	IOrganism getOrganism();
	IOrganism getOrganism(int sex);
	IOrganism getOrganism(int sex, String alleles);

	String getOrganismImageURL();
	String getOrganismImageURL(IOrganism organism, int imageSize);

	ArrayList<String> getOrganismPhenotypes(IOrganism gOrg);
	IOrganism breedOrganism(IOrganism org1, IOrganism org2);
}
