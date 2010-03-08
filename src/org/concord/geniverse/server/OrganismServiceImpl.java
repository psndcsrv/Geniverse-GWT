package org.concord.geniverse.server;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.concord.biologica.engine.Characteristic;
import org.concord.biologica.engine.Organism;
import org.concord.biologica.engine.Species;
import org.concord.biologica.engine.SpeciesImage;
import org.concord.biologica.engine.World;
import org.concord.biologica.ui.StaticOrganismView;
import org.concord.geniverse.client.GOrganism;
import org.concord.geniverse.client.OrganismService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class OrganismServiceImpl extends RemoteServiceServlet implements OrganismService {
	private static final Logger logger = Logger.getLogger(OrganismServiceImpl.class.getName());
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

	public ArrayList<String> getOrganismPhenotypes(GOrganism gOrg) {
		ArrayList<String> phenotypes = new ArrayList<String>();
		Organism org = createOrg(gOrg);
		Enumeration<Characteristic> chars = org.getCharacteristics();
		while (chars.hasMoreElements()) {
			Characteristic c = chars.nextElement();
			phenotypes.add(c.getName());
		}
		return phenotypes;
	}

	public String getOrganismImageURL() {
		try {
			return getOrganismImageURL(getOrganism(), SpeciesImage.XLARGE_IMAGE_SIZE);
		} catch(Exception e) {
			return e.toString();
		}
	}

	public String getOrganismImageURL(GOrganism organism, int imageSize) {
		Organism dragon = createOrg(organism);
		String filename = generateFilename(dragon);
		ServletContext context = getServletContext();
		String realpath = context.getRealPath("/cache/" + filename);

		if (realpath == null) {
			String url = context.getContextPath() + "/cache/unknown.png";
			return url;
		}

		File outputfile = new File(realpath);
		if (! outputfile.exists()) {
			try {
				StaticOrganismView view = new StaticOrganismView();
				view.setOrganism(dragon);
				try {
					BufferedImage image = view.getOrganismImage(dragon, imageSize);
					ImageIO.write(image, "png", outputfile);
				} catch (java.lang.ExceptionInInitializerError e) {
					logger.log(Level.SEVERE, "Couldn't generate a buffered image!", e);
				}


			} catch (IOException e) {
				logger.log(Level.SEVERE, "Couldn't write the image for the organism! " + realpath, e);
			}
		}

		return context.getContextPath() + "/cache/" + filename;
	}

	private String generateFilename(Organism org) {
		return org.getAlleleString(true) + ".png";
	}
}
