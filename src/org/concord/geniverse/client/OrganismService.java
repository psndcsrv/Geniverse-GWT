package org.concord.geniverse.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("organism")
public interface OrganismService extends RemoteService {
	GOrganism getOrganism();
	GOrganism getOrganism(int sex);
	GOrganism getOrganism(int sex, String alleles);

	String getOrganismImageURL();
	String getOrganismImageURL(GOrganism organism, int imageSize);

	ArrayList<String> getOrganismPhenotypes(GOrganism gOrg);
	GOrganism breedOrganism(GOrganism org1, GOrganism org2);
	ArrayList<GOrganism> breedOrganisms(int number, GOrganism org1, GOrganism org2);
	ArrayList<GOrganism> breedOrganisms(int number, GOrganism org1,
			GOrganism org2, boolean crossingOver);
}
