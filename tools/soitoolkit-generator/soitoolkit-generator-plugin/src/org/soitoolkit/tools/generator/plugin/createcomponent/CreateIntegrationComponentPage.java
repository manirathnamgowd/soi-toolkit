/* 
 * Licensed to the soi-toolkit project under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The soi-toolkit project licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.soitoolkit.tools.generator.plugin.createcomponent;

import static org.soitoolkit.tools.generator.plugin.model.enums.MuleVersionEnum.MULE_2_2_5;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.soitoolkit.tools.generator.plugin.model.enums.EnumUtil;
import org.soitoolkit.tools.generator.plugin.model.enums.MuleVersionEnum;
import org.soitoolkit.tools.generator.plugin.model.enums.TransportEnum;
import org.soitoolkit.tools.generator.plugin.util.SwtUtil;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */

public class CreateIntegrationComponentPage extends WizardPage {

//	@Override
//	public boolean isPageComplete() {
//		boolean isPageComplete = super.isPageComplete();
//		System.err.println("CreateIntegrationComponentPage.isPageComplete() returns: " + isPageComplete);
//		return super.isPageComplete();
//	}
//
//	@Override
//	public void setPageComplete(boolean complete) {
//		System.err.println("CreateIntegrationComponentPage.setPageComplete() sets complete  to: " + complete);
//		super.setPageComplete(complete);
//	}
//
//	@Override
//	public IWizardPage getPreviousPage() {
//		IWizardPage p = super.getPreviousPage();
//		setMustBeDisplayed(false);	
//		System.err.println("CreateIntegrationComponentPage.getPreviousPage() returns: " + ((p == null)? "NULL" : p.getTitle()));
//		return p;
//	}

	public void setMustBeDisplayed(boolean mustBeDisplayed) {
		this.mustBeDisplayed = mustBeDisplayed;
		dialogChanged();
//		System.err.println("CreateIntegrationComponentPage.setMustBeDisplayed() sets complete  to: " + mustBeDisplayed);
	}

	private boolean mustBeDisplayed = false;
	
	private MuleVersionEnum muleVersion = MULE_2_2_5;
	
	private Combo muleVersionCombo;
	private Button genServiceButton;
//	private Button genSchemaButton;
	private Button genWarButton;
	private Button jmsButton;
	private Button jdbcButton;
	private Button sftpButton;
	private Button servletButton;

	private ISelection selection;

	/**
	 * Constructor for CreateIntegrationComponentPage.
	 * 
	 * @param pageName
	 */
	public CreateIntegrationComponentPage(ISelection selection) {
		super("wizardPage");
		setTitle("SOI Toolkit - Create a new component, page 2");
		setDescription("Configuration the integration component");
		setImageDescriptor(ImageDescriptor.createFromFile(this.getClass(), "component-large.png"));
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 1;
		layout.verticalSpacing = 9;

		FocusListener fokusListener = new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				System.err.println("CreateIntegrationComponentPage.focusLost() called");				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				System.err.println("CreateIntegrationComponentPage.focusGained() called");				
				setMustBeDisplayed(false);	
			}
		};
		
		container.addFocusListener(fokusListener);
//		Label label = new Label(container, SWT.NULL);
//		label.setText("&Container:");
//
//		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
//		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
//		containerText.setLayoutData(gd);
//		containerText.addModifyListener(new ModifyListener() {
//			public void modifyText(ModifyEvent e) {
//				dialogChanged();
//			}
//		});

		// Combo for Mule Version
		Label label = new Label(container, SWT.NULL);
		label.setText("Mule version:");

		muleVersionCombo = new Combo (container, SWT.READ_ONLY);
		muleVersionCombo.setItems (EnumUtil.getLabels(MuleVersionEnum.values()));

		
		/*
		 * Mule Version-Combo-Listener
		 */
		SelectionListener muleVersionComboListener = new SelectionListener () {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				System.err.println("### Set default selected MuleVersion");
				Combo c = (Combo)e.widget;
				muleVersion =  MuleVersionEnum.get(c.getSelectionIndex());
				System.err.println("### Set default selected MuleVersion to: " + muleVersion);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				System.err.println("### Set selected MuleVersion");
				Combo c = (Combo)e.widget;
				muleVersion =  MuleVersionEnum.get(c.getSelectionIndex());
				System.err.println("### Set selected MuleVersion to: " + muleVersion);

				dialogChanged();

			}
		};
		muleVersionCombo.addSelectionListener(muleVersionComboListener);


		// Gen service, schema and war modules button
		label = new Label(container, SWT.NULL);
		label.setText("Select modules in the integration component:");

		// Service module is mandatory, should not be selectable
		genServiceButton = SwtUtil.createCheckboxButton(container, null,-1, "Service module");
		genServiceButton.setSelection(true);
		genServiceButton.setEnabled(false);

//		// TODO: Make genSchemaButton selectable
//		genSchemaButton = SwtUtil.createCheckboxButton(container, null,-1, "Schema module");
//		genSchemaButton.setSelection(false);
//		genSchemaButton.setEnabled(false);

		// TODO: Make genWarButton selectable
		genWarButton = SwtUtil.createCheckboxButton(container, null,-1, "War and Teststub War");
		genWarButton.setSelection(true);
		genWarButton.setEnabled(false);
		
		// CheckBoxes for transports
		label = new Label(container, SWT.NULL);
		label.setText("Select &transports used by the component:");

		int i = 0;
		// Jms transport is mandatory for logging, should not be selectable
		jmsButton = SwtUtil.createCheckboxButton(container, null, i++, "JMS");
		jmsButton.setSelection(true);
		jmsButton.setEnabled(false);
		jdbcButton = SwtUtil.createCheckboxButton(container, null, i++, "JDBC");
		jdbcButton.setSelection(true);
		sftpButton = SwtUtil.createCheckboxButton(container, null, i++, "SFTP");
		sftpButton.setSelection(true);
		servletButton = SwtUtil.createCheckboxButton(container, null, i++, "Servlet");
		servletButton.setSelection(true);

		// TODO: How to pack?
//		container.pack();
		
		initialize();
		dialogChanged();
		setControl(container);
	}


	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
//		if (selection != null && selection.isEmpty() == false
//				&& selection instanceof IStructuredSelection) {
//			IStructuredSelection ssel = (IStructuredSelection) selection;
//			if (ssel.size() > 1)
//				return;
//			Object obj = ssel.getFirstElement();
//			if (obj instanceof IResource) {
//				IContainer container;
//				if (obj instanceof IContainer)
//					container = (IContainer) obj;
//				else
//					container = ((IResource) obj).getParent();
//				containerText.setText(container.getFullPath().toString());
//			}
//		}
		muleVersionCombo.select(MULE_2_2_5.ordinal());
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

//	private void handleBrowseOrg() {
//		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
//				getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
//				"Select new file container");
//		if (dialog.open() == ContainerSelectionDialog.OK) {
//			Object[] result = dialog.getResult();
//			if (result.length == 1) {
//				containerText.setText(((Path) result[0]).toString());
//			}
//		}
//	}


	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
//		IResource container = ResourcesPlugin.getWorkspace().getRoot()
//				.findMember(new Path(getContainerName()));

//		if (getContainerName().length() == 0) {
//			updateStatus("File container must be specified");
//			return;
//		}
//		if (container == null
//				|| (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
//			updateStatus("File container must exist");
//			return;
//		}
//		if (!container.isAccessible()) {
//			updateStatus("Project must be writable");
//			return;
//		}
//		if (folderName.replace('\\', '/').indexOf('/', 1) > 0) {
//			updateStatus("Folder name must be valid");
//			return;
//		}
		
//		if (muleVersion != MULE_2_2_5) {
//			updateStatus("Only supported Mule version for now is: " + MULE_2_2_5.getLabel());
//			return;
//		}

		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete((message == null) && !mustBeDisplayed);
	}

//	public String getContainerName() {
//		return containerText.getText();
//	}

//	public boolean isGenSchemaSelected() {
//		return genSchemaButton.getSelection();
//	}

	public MuleVersionEnum getMuleVersion() {
		return muleVersion;
	}

	public boolean isGenWarSelected() {
		return genWarButton.getSelection();
	}

	public List<TransportEnum> getTransports() {
		List<TransportEnum> transports = new ArrayList<TransportEnum>();
		if (isJmsTransportSelected()) transports.add(TransportEnum.JMS);
		if (isJdbcTransportSelected()) transports.add(TransportEnum.JDBC);
		if (isSftpTransportSelected()) transports.add(TransportEnum.SFTP);
		if (isServletTransportSelected()) transports.add(TransportEnum.SERVLET);
		return transports;
	}

	// ---------------
	
	private boolean isJmsTransportSelected() {
		return jmsButton.getSelection();
	}

	private boolean isJdbcTransportSelected() {
		return jdbcButton.getSelection();
	}

	private boolean isSftpTransportSelected() {
		return sftpButton.getSelection();
	}

	private boolean isServletTransportSelected() {
		return servletButton.getSelection();
	}

	
}