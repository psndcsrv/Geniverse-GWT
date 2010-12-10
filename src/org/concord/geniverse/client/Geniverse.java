package org.concord.geniverse.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Geniverse implements EntryPoint {
	private static OrganismServiceAsync organismSvc = GWT.create(OrganismService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		publish();
	}

	public static void generateDragonWithAlleleString(String allele, int sex, final JavaScriptObject successFunction, final JavaScriptObject failureFunction) {
		// generateButton.setEnabled(false);
		AsyncCallback<GOrganism> callback = createGOrganismCallback(successFunction, failureFunction);
		organismSvc.getOrganism(sex, allele, callback);
	}

	public static void generateDragonWithAlleleString(String allele, final JavaScriptObject successFunction, final JavaScriptObject failureFunction) {
		// generateButton.setEnabled(false);
		AsyncCallback<GOrganism> callback = createGOrganismCallback(successFunction, failureFunction);
		int sex = (int) Math.round(Math.random());
		organismSvc.getOrganism(sex, allele, callback);
	}

	public static void generateDragonWithSex(int sex, final JavaScriptObject successFunction, final JavaScriptObject failureFunction) {
		// generateButton.setEnabled(false);
		AsyncCallback<GOrganism> callback = createGOrganismCallback(successFunction, failureFunction);
		organismSvc.getOrganism(sex, callback);
	}

	public static void generateDragonWithCallback(final JavaScriptObject successFunction, final JavaScriptObject failureFunction) {
		AsyncCallback<GOrganism> callback = createGOrganismCallback(successFunction, failureFunction);
		organismSvc.getOrganism(callback);
	}

	public static void getDragonImageURL(GOrganism org, int imageSize, final JavaScriptObject successFunction, final JavaScriptObject failureFunction) {
		AsyncCallback<String> callback = createStringCallback(successFunction, failureFunction);
		organismSvc.getOrganismImageURL(org, imageSize, callback);
	}

	public static void getRandomDragonImageURL(final JavaScriptObject successFunction, final JavaScriptObject failureFunction) {
		AsyncCallback<String> callback = createStringCallback(successFunction, failureFunction);
		organismSvc.getOrganismImageURL(callback);
	}

	public static void getDragonCharacteristics(GOrganism gOrg, final JavaScriptObject sucessFunction, final JavaScriptObject failureFunction) {
		AsyncCallback<ArrayList<String>> callback = createStringArrayListCallback(sucessFunction, failureFunction);
		organismSvc.getOrganismPhenotypes(gOrg, callback);
	}

	public static void breedDragon(GOrganism org1, GOrganism org2, final JavaScriptObject successFunction, final JavaScriptObject failureFunction) {
		AsyncCallback<GOrganism> callback = createGOrganismCallback(successFunction, failureFunction);
		organismSvc.breedOrganism(org1, org2, callback);
	}
	
	public static void breedDragons(int count, GOrganism org1, GOrganism org2, final JavaScriptObject successFunction, final JavaScriptObject failureFunction) {
        AsyncCallback<ArrayList<GOrganism>> callback = createGOrganismArrayListCallback(successFunction, failureFunction);
        System.err.println("Breeding " + count + " dragons.");
        organismSvc.breedOrganisms(count, org1, org2, callback);
    }
	
	public static void breedDragons(int count, GOrganism org1, GOrganism org2, boolean crossingOver, final JavaScriptObject successFunction, final JavaScriptObject failureFunction) {
        AsyncCallback<ArrayList<GOrganism>> callback = createGOrganismArrayListCallback(successFunction, failureFunction);
        System.err.println("Breeding " + count + " dragons " + (crossingOver ? "with" : "without") + "crossover");
        organismSvc.breedOrganisms(count, org1, org2, crossingOver, callback);
    }

	public static AsyncCallback<GOrganism> createGOrganismCallback(final JavaScriptObject successFunction, final JavaScriptObject failureFunction) {
		AsyncCallback<GOrganism> callback = new AsyncCallback<GOrganism>() {
			public void onFailure(Throwable caught) {
				callFunc(failureFunction, caught);
			}

			public void onSuccess(GOrganism result) {
				callFunc(successFunction, result);
			}
		};
		return callback;
	}
	
	public static AsyncCallback<ArrayList<GOrganism>> createGOrganismArrayListCallback(final JavaScriptObject successFunction, final JavaScriptObject failureFunction) {
        AsyncCallback<ArrayList<GOrganism>> callback = new AsyncCallback<ArrayList<GOrganism>>() {
            public void onFailure(Throwable caught) {
                callFunc(failureFunction, caught);
            }

            public void onSuccess(ArrayList<GOrganism> result) {
                callFunc(successFunction, result);
            }
        };
        return callback;
    }
	
	public static AsyncCallback<GOrganism[]> createGOrganismArrayCallback(final JavaScriptObject successFunction, final JavaScriptObject failureFunction) {
        AsyncCallback<GOrganism[]> callback = new AsyncCallback<GOrganism[]>() {
            public void onFailure(Throwable caught) {
                callFunc(failureFunction, caught);
            }

            public void onSuccess(GOrganism[] result) {
                callFunc(successFunction, result);
            }
        };
        return callback;
    }

	public static AsyncCallback<String> createStringCallback(final JavaScriptObject successFunction, final JavaScriptObject failureFunction) {
		AsyncCallback<String> callback = new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				callFunc(failureFunction, caught);
			}

			public void onSuccess(String result) {
				callFunc(successFunction, result);
			}
		};
		return callback;
	}

	public static AsyncCallback<ArrayList<String>> createStringArrayListCallback(final JavaScriptObject successFunction, final JavaScriptObject failureFunction) {
		AsyncCallback<ArrayList<String>> callback = new AsyncCallback<ArrayList<String>>() {
			public void onFailure(Throwable caught) {
				callFunc(failureFunction, caught);
			}

			public void onSuccess(ArrayList<String> result) {
				callFunc(successFunction, result);
			}
		};
		return callback;
	}

	public static GOrganism createGOrganismFromJSONString(String jsonString){
		JSONObject jsonOrg = (JSONObject)JSONParser.parse(jsonString);
		GOrganism gOrg = new GOrganism();
		gOrg.setName(((JSONString)getValue(jsonOrg, "name_0")).stringValue());
		gOrg.setAlleles(((JSONString)getValue(jsonOrg, "alleles")).stringValue());
		gOrg.setSex((int) ((JSONNumber)getValue(jsonOrg, "sex")).doubleValue());
		gOrg.setImageURL(((JSONString)getValue(jsonOrg, "imageURL")).stringValue());
		return gOrg;
	}

	/**
	 * Because we can't be sure how JSON will name the keys, we check all values from
	 * "key" through to "key_9"
	 * @param obj
	 * @param key
	 * @return
	 */
	public static JSONValue getValue(JSONObject obj, String key){
		JSONValue val = obj.get(key);
		int i = 0;
		while ((val == null) && (i < 10)){
			if (key.matches(".*\\d")){
				val = obj.get(key.substring(0, key.length()-1) + i++);		// ought to check from init value
			} else {
				val = obj.get(key + "_" + i++);
			}
		}
		return val;
	}

	private static native void callFunc(JavaScriptObject func, Object arg) /*-{
	  func(arg);
	}-*/;

	/*
	 *  Set up the JS-callable signature as a global JS function. Basically this
	 *  provides an easy way to publish static methods as JavaScript methods.
	 */
	private native void publish() /*-{
	    $wnd.generateDragonWithCallback = 
	      @org.concord.geniverse.client.Geniverse::generateDragonWithCallback(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;);

	    $wnd.generateDragonWithAlleleString = 
	      @org.concord.geniverse.client.Geniverse::generateDragonWithAlleleString(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;);

	    $wnd.generateDragonWithAlleleStringAndSex = 
	      @org.concord.geniverse.client.Geniverse::generateDragonWithAlleleString(Ljava/lang/String;ILcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;);

	    $wnd.generateDragonWithSex = 
	      @org.concord.geniverse.client.Geniverse::generateDragonWithSex(ILcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;);

        $wnd.getDragonImageURL = 
	      @org.concord.geniverse.client.Geniverse::getDragonImageURL(Lorg/concord/geniverse/client/GOrganism;ILcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;);

	    $wnd.getRandomDragonImageURL = 
	      @org.concord.geniverse.client.Geniverse::getRandomDragonImageURL(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;);

		$wnd.getDragonCharacteristics = 
	      @org.concord.geniverse.client.Geniverse::getDragonCharacteristics(Lorg/concord/geniverse/client/GOrganism;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;);

	    $wnd.breedDragon = 
	      @org.concord.geniverse.client.Geniverse::breedDragon(Lorg/concord/geniverse/client/GOrganism;Lorg/concord/geniverse/client/GOrganism;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;);
        
        $wnd.breedDragons = 
          @org.concord.geniverse.client.Geniverse::breedDragons(ILorg/concord/geniverse/client/GOrganism;Lorg/concord/geniverse/client/GOrganism;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;);

		$wnd.breedDragonsWithCrossover = 
          @org.concord.geniverse.client.Geniverse::breedDragons(ILorg/concord/geniverse/client/GOrganism;Lorg/concord/geniverse/client/GOrganism;ZLcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;);
          
		$wnd.createGOrganismFromJSONString =
		  @org.concord.geniverse.client.Geniverse::createGOrganismFromJSONString(Ljava/lang/String;);
	  }-*/;

}
