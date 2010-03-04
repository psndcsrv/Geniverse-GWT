package org.concord.geniverse.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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
	private OrganismServiceAsync organismSvc = GWT.create(OrganismService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button generateButton = new Button("Generate Dragon");

		// We can add style names to widgets
		generateButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("sendButtonContainer").add(generateButton);

		// Focus the cursor on the name field when the app loads
		generateButton.setFocus(true);

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Your Dragon");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label dragonGenomeLabel = new Label();
		final HTML characteristicsLabel = new HTML();
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

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void generateDragon() {
				generateButton.setEnabled(false);

				organismSvc.getOrganism(new AsyncCallback<GOrganism>() {

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
				});

			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		generateButton.addClickHandler(handler);
	}
}
