package org.concord.geniverse.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Geniverse implements EntryPoint {
	private static OrganismServiceAsync organismSvc = GWT.create(OrganismService.class);
	private static Button generateButton;
	private static Label dragonGenomeLabel;
	private static HTML characteristicsLabel;
	private static DialogBox dialogBox;
	private static Button closeButton;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		generateButton = new Button("Generate Dragon");

		// We can add style names to widgets
		generateButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("sendButtonContainer").add(generateButton);

		// Focus the cursor on the name field when the app loads
		generateButton.setFocus(true);

		// Create the popup dialog box
		dialogBox = new DialogBox();
		dialogBox.setText("Your Dragon");
		dialogBox.setAnimationEnabled(true);
		closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		dragonGenomeLabel = new Label();
		characteristicsLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>You've generated a dragon!</b>"));
		dialogVPanel.add(new HTML("<br><b>Your dragon's genome is:</b>"));
		dialogVPanel.add(dragonGenomeLabel);
		dialogVPanel.add(new HTML("<br><b>Your dragon's characteristics are:</b>"));
		dialogVPanel.add(characteristicsLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				generateButton.setEnabled(true);
				generateButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				generateDragon();
			}


		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		generateButton.addClickHandler(handler);
		publish();
	}

	/**
	 * Send the name from the nameField to the server and wait for a response.
	 */
	public static void generateDragon() {
		generateButton.setEnabled(false);
		organismSvc.getOrganism(new MyAsyncCallback());
	}

	public static void generateDragonWithAlleleString(int sex, String allele) {
		generateButton.setEnabled(false);
		organismSvc.getOrganism(sex, allele, new MyAsyncCallback());
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

	public static class MyAsyncCallback implements AsyncCallback<GOrganism> {

		public void onFailure(Throwable caught) {
			dragonGenomeLabel.addStyleName("serverResponseLabelError");
			dragonGenomeLabel.setText(caught.toString());
			dialogBox.center();
		}

		public void onSuccess(GOrganism result) {
			dragonGenomeLabel.removeStyleName("serverResponseLabelError");
			dragonGenomeLabel.setText(result.getAlleles());
			organismSvc.getPhenotypes(result, new AsyncCallback<ArrayList<String>>() {

				public void onFailure(Throwable caught) {
					characteristicsLabel.setText("unknown characteristics");
				}

				public void onSuccess(ArrayList<String> result) {
					String charList = "<p>";
					for (String ch : result) {
						charList += (ch + "<br/>");
					}
					charList += "</p>";
					characteristicsLabel.setHTML(charList);
					dialogBox.center();
				}

			});
		}
	}

	private static native void callFunc(JavaScriptObject func, Object arg) /*-{
	  func(arg);
	}-*/;

	/*
	 *  Set up the JS-callable signature as a global JS function. Basically this
	 *  provides an easy way to publish static methods as JavaScript methods.
	 */
	private native void publish() /*-{
	    $wnd.generateDragon = 
	      @org.concord.geniverse.client.Geniverse::generateDragon();

	    $wnd.generateDragonWithCallback = 
	      @org.concord.geniverse.client.Geniverse::generateDragonWithCallback(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;);

        $wnd.getDragonImageURL = 
	      @org.concord.geniverse.client.Geniverse::getDragonImageURL(Lorg/concord/geniverse/client/GOrganism;ILcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;);

	     $wnd.getRandomDragonImageURL = 
	      @org.concord.geniverse.client.Geniverse::getRandomDragonImageURL(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;);

	    $wnd.generateDragonWithAlleleString = 
	      @org.concord.geniverse.client.Geniverse::generateDragonWithAlleleString(ILjava/lang/String;);

	  }-*/;

}
