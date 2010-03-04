package org.concord.geniverse.server;

import java.util.ArrayList;
import java.util.Enumeration;

import org.concord.biologica.engine.Characteristic;
import org.concord.biologica.engine.Organism;
import org.concord.biologica.engine.Species;
import org.concord.biologica.engine.World;
import org.concord.geniverse.client.GOrganism;
import org.concord.geniverse.client.OrganismService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class OrganismServiceImpl extends RemoteServiceServlet implements OrganismService {
	private static final long serialVersionUID = 1L;
	private static World world = new World("org/concord/biologica/worlds/dragon.xml");
	private static Species species = world.getCurrentSpecies();
	private static int currentDragonNumber = 0;

	private GOrganism createGOrg(Organism org) {
		GOrganism gOrg = new GOrganism();
		gOrg.setName(org.getName());
		gOrg.setSex(org.getSex());
		gOrg.setAlleles(org.getAlleleString());
		return gOrg;
	}

	private Organism createOrg(GOrganism gOrg) {
		Organism org = new Organism(world, gOrg.getName(), species, gOrg.getSex(), gOrg.getAlleles());
		return org;
	}

	public GOrganism getOrganism(int sex) {
		Organism dragon = new Organism(world, sex, "Organism " + (++currentDragonNumber), world.getCurrentSpecies());
		return createGOrg(dragon);
	}

	public GOrganism getOrganism(int sex, String alleles) {
		Organism dragon = new Organism(world, "Organism " + (++currentDragonNumber), world.getCurrentSpecies(), sex, alleles) ;
		return createGOrg(dragon);
	}

	public GOrganism getOrganism() {
		Organism dragon = new Organism(world, Organism.RANDOM_SEX, "Organism " + (++currentDragonNumber), world.getCurrentSpecies());
		return createGOrg(dragon);
	}

	public ArrayList<String> getPhenotypes(GOrganism gOrg) {
		ArrayList<String> phenotypes = new ArrayList<String>();
		Organism org = createOrg(gOrg);
		Enumeration<Characteristic> chars = org.getCharacteristics();
		while (chars.hasMoreElements()) {
			Characteristic c = chars.nextElement();
			phenotypes.add(c.getName());
		}
		return phenotypes;
	}
}
