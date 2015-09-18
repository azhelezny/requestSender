package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import json.Formatter;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;

import request.RequestSender;
import request.RequestStructure;
import responce.ResponseBinaryStructure;
import responce.ResponseStructure;
import text.data.TextData;
import utils.ImageFileChooser;
import utils.Utils;

public class Ui extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2669800895909059399L;
	private String fileOpenDir = null;
	private String fileSaveDir = null;

	private JButton sendButton;
	private JButton formatJson;
	private JButton applySecurityToken;
	private JButton applyTennatId;
	private JButton applyObjectInstance;
	private JButton applyObjectId;
	private JButton applyVersionId;
	private JButton applyStepId;
	private JButton applyApplicationKey;
	private JButton showHelpWindow;
	private JButton browseFs;
	private JButton browseAndSave;
	private JButton applyBinaryBody;
	private JButton applyStoringToFile;
	private JButton applyChannelType;
	private JButton useCookie;

	private JTextArea headersField;
	private JScrollPane scrollHeaders;
	private JTextArea bodyField;
	private JScrollPane scrollBody;
	private JTextArea responceHeadersField;
	private JScrollPane scrollResponceHeaders;
	private JTextArea responceBodyField;
	private JScrollPane scrollResponceBody;
	private JTextField url;
	private JTextField pathToBinaryFile;
	private JTextField pathToBodyFile;

	private JCheckBox binaryBodyMode;
	private JCheckBox storeBodyToFile;
	private JCheckBox prefixChecked;

	private JComboBox<String> appKey;
	private JComboBox<String> tenantId;
	private JComboBox<String> prefix;
	private JComboBox<HttpMethod> method;
	private JComboBox<String> reportsApi;
	private JComboBox<String> interactionRuntimeApi;
	private JComboBox<String> interactionApi;
	private JComboBox<String> userManagementApi;
	private JComboBox<String> variablesApi;
	private JComboBox<String> appKeyApi;
	private JComboBox<String> interactionPointsApi;
	private JComboBox<String> imagesApi;
	private JComboBox<String> emailApi;
	private JComboBox<String> dnisApi;
	private JComboBox<String> foldersApi;
	private JComboBox<String> afterHoursCallIntentApi;
	private JComboBox<String> dashdoardApi;
	private JComboBox<String> allowedAddressesApi;
	private JComboBox<String> chatApi;
	private JComboBox<String> loggingLicensingApi;
	private JComboBox<String> historyFlowApi;
	private JComboBox<String> designerUpgradeActions;
	private JComboBox<String> channelType;

	private JPanel buttonsPanel;
	private JPanel binaryMainPanel;

	private String securityToken;
	private String objectVersion;
	private String objectId;
	private String objectInstanceId;
	private String objectStepId;

	private byte[] binaryBody = null;

	private HelpWindow helpWindow;

	private void setBinaryPanelTitle()
	{
		String binaryPanelTitle = "Binary body mode [%s], binary body %s [%s]. Saving responce to file [%s], output file %s [%s]";
		// mode = "on/off", body = "is selected/isn't selected", response body
		// storing to file
		// = on/off file = selected isn't selected
		String binadyBodyOnOff = (binaryBodyMode.isSelected()) ? "On" : "Off";
		String binaryBodyFilesNs = (binaryBody == null) ? "isn't selected" : "is selected";
		String saveBodyOnOff = (storeBodyToFile.isSelected()) ? "On" : "Off";
		String saveBodyFilesNs = (pathToBinaryFile.getText().isEmpty()) ? "isn't selected" : "is selected";
		String filePath = (pathToBinaryFile.getText().isEmpty() || binaryBody == null) ? "Unknown" : pathToBinaryFile.getText();
		String outputFilePath = (pathToBodyFile.getText().isEmpty()) ? "Unknown" : pathToBodyFile.getText();
		
		if(binaryBodyMode.isSelected())
			pathToBinaryFile.setBackground(Color.YELLOW);
		else
			pathToBinaryFile.setBackground(Color.WHITE);

		if(storeBodyToFile.isSelected())
			pathToBodyFile.setBackground(Color.YELLOW);
		else
			pathToBodyFile.setBackground(Color.WHITE);
			
		
		binaryMainPanel.setBorder(BorderFactory.createTitledBorder(String.format(binaryPanelTitle, binadyBodyOnOff, binaryBodyFilesNs, filePath, saveBodyOnOff, saveBodyFilesNs, outputFilePath)));
	}

	public Ui()
	{
		helpWindow = new HelpWindow();

		this.setTitle("Request sender");
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(this.getClass().getResource("/images/blazon.jpg"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		this.setIconImage(image);
		// Instantiation
		sendButton = new JButton("Send");
		applySecurityToken = new JButton("Access token");
		applySecurityToken.setEnabled(false);
		applyObjectInstance = new JButton("Instance ID");
		applyObjectInstance.setEnabled(false);
		applyObjectId = new JButton("Object ID");
		applyObjectId.setEnabled(false);
		applyVersionId = new JButton("Version ID");
		applyVersionId.setEnabled(false);
		applyStepId = new JButton("Step ID");
		applyStepId.setEnabled(false);
		formatJson = new JButton("Format JSON");
		showHelpWindow = new JButton("HelpWindow");
		applyApplicationKey = new JButton("Application key");
		applyTennatId = new JButton("Tenant name");
		useCookie = new JButton("Use cookie");
		headersField = new JTextArea();
		responceHeadersField = new JTextArea();
		responceBodyField = new JTextArea();
		bodyField = new JTextArea();
		url = new JTextField();
		appKey = new JComboBox<String>();
		appKey.setEditable(true);
		for(Entry<String, String> e: Utils.getApplicationKey().entrySet())
			appKey.addItem(e.getValue());
		appKey.setPreferredSize(new Dimension(200, url.getPreferredSize().height));
		tenantId = new JComboBox<String>();
		tenantId.addItem("12345678");
		tenantId.addItem("system");
		tenantId.setEditable(true);
		tenantId.setPreferredSize(new Dimension(100, url.getPreferredSize().height));
		prefixChecked = new JCheckBox();
		prefixChecked.setSelected(true);
		binaryBodyMode = new JCheckBox();
		binaryBodyMode.setSelected(false);
		storeBodyToFile = new JCheckBox();
		storeBodyToFile.setSelected(false);
		pathToBodyFile = new JTextField();
		pathToBinaryFile = new JTextField();
		browseAndSave = new JButton("Browse");
		browseFs = new JButton("Browse");
		applyBinaryBody = new JButton("Apply");
		applyStoringToFile = new JButton("Apply");
		applyChannelType = new JButton("Channel");
		// Customizing

		headersField.setEditable(true);
		headersField.setLineWrap(true);
		scrollHeaders = new JScrollPane(headersField);
		scrollHeaders.setBorder(BorderFactory.createTitledBorder("Headers"));
		bodyField.setEditable(true);
		bodyField.setLineWrap(true);
		scrollBody = new JScrollPane(bodyField);
		scrollBody.setBorder(BorderFactory.createTitledBorder("Body"));
		responceHeadersField.setEditable(false);
		responceHeadersField.setLineWrap(true);
		scrollResponceHeaders = new JScrollPane(responceHeadersField);
		scrollResponceHeaders.setBorder(BorderFactory.createTitledBorder("Responce headers"));
		responceBodyField.setEditable(false);
		responceBodyField.setLineWrap(true);
		scrollResponceBody = new JScrollPane(responceBodyField);
		scrollResponceBody.setBorder(BorderFactory.createTitledBorder("Responce body"));

		method = new JComboBox<HttpMethod>();
		method.addItem(HttpMethod.GET);
		method.addItem(HttpMethod.POST);
		method.addItem(HttpMethod.PUT);
		method.addItem(HttpMethod.DELETE);
		method.addItem(HttpMethod.OPTIONS);
		method.setBorder(BorderFactory.createTitledBorder("Method"));

		prefix = new JComboBox<String>();
		prefix.setEditable(true);
		for (String item : Utils.getPredefinedServers())
			prefix.addItem(item);

		reportsApi = new JComboBox<String>();
		for (String item : Utils.getReportsRequests())
			reportsApi.addItem(item);
		reportsApi.setBorder(BorderFactory.createTitledBorder("Reports API"));
		reportsApi.addActionListener(new RequestExampleAction());

		interactionRuntimeApi = new JComboBox<String>();
		for (String item : Utils.getInteractioRuntimeRequests())
			interactionRuntimeApi.addItem(item);
		interactionRuntimeApi.setBorder(BorderFactory.createTitledBorder("JMA actions"));
		interactionRuntimeApi.addActionListener(new RequestExampleAction());

		interactionApi = new JComboBox<String>();
		for (String item : Utils.getInteractioApiRequests())
			interactionApi.addItem(item);
		interactionApi.setBorder(BorderFactory.createTitledBorder("Interactions API"));
		interactionApi.addActionListener(new RequestExampleAction());

		userManagementApi = new JComboBox<String>();
		for (String item : Utils.getUserManagementRequests())
			userManagementApi.addItem(item);
		userManagementApi.setBorder(BorderFactory.createTitledBorder("User/Tenant management"));
		userManagementApi.addActionListener(new RequestExampleAction());

		variablesApi = new JComboBox<String>();
		for (String item : Utils.getVariablesRequests())
			variablesApi.addItem(item);
		variablesApi.setBorder(BorderFactory.createTitledBorder("Variables API"));
		variablesApi.addActionListener(new RequestExampleAction());

		appKeyApi = new JComboBox<String>();
		for (String item : Utils.getAppKeyRequests())
			appKeyApi.addItem(item);
		appKeyApi.setBorder(BorderFactory.createTitledBorder("AppKey API"));
		appKeyApi.addActionListener(new RequestExampleAction());

		interactionPointsApi = new JComboBox<String>();
		for (String item : Utils.getInteractioPointsRequests())
			interactionPointsApi.addItem(item);
		interactionPointsApi.setBorder(BorderFactory.createTitledBorder("Interaction Points API"));
		interactionPointsApi.addActionListener(new RequestExampleAction());

		imagesApi = new JComboBox<String>();
		for (String item : Utils.getImagesRequests())
			imagesApi.addItem(item);
		imagesApi.setBorder(BorderFactory.createTitledBorder("Images API"));
		imagesApi.addActionListener(new RequestExampleAction());

		emailApi = new JComboBox<String>();
		for (String item : Utils.getEmailRequests())
			emailApi.addItem(item);
		emailApi.setBorder(BorderFactory.createTitledBorder("E-mail API"));
		emailApi.addActionListener(new RequestExampleAction());
		
		dnisApi = new JComboBox<String>();
		for (String item : Utils.getDnisRequests())
			dnisApi.addItem(item);
		dnisApi.setBorder(BorderFactory.createTitledBorder("DNIS API"));
		dnisApi.addActionListener(new RequestExampleAction());
		
		foldersApi = new JComboBox<String>();
		for (String item : Utils.getFolderRequests())
			foldersApi.addItem(item);
		foldersApi.setBorder(BorderFactory.createTitledBorder("Folders API"));
		foldersApi.addActionListener(new RequestExampleAction());
		
		afterHoursCallIntentApi = new JComboBox<String>();
		for (String item : Utils.getAfterHoursCallIntentRequests())
			afterHoursCallIntentApi.addItem(item);
		afterHoursCallIntentApi.setBorder(BorderFactory.createTitledBorder("After Hours/Call Intent API"));
		afterHoursCallIntentApi.addActionListener(new RequestExampleAction());
		
		dashdoardApi = new JComboBox<String>();
		for (String item : Utils.getDashboardRequests())
			dashdoardApi.addItem(item);
		dashdoardApi.setBorder(BorderFactory.createTitledBorder("Dashboard API"));
		dashdoardApi.addActionListener(new RequestExampleAction());
		
		allowedAddressesApi = new JComboBox<String>();
		for (String item : Utils.getAllowedIpsRequests())
			allowedAddressesApi.addItem(item);
		allowedAddressesApi.setBorder(BorderFactory.createTitledBorder("Allowed IPs API"));
		allowedAddressesApi.addActionListener(new RequestExampleAction());
		
		chatApi = new JComboBox<String>();
		for (String item : Utils.getChatRequests())
			chatApi.addItem(item);
		chatApi.setBorder(BorderFactory.createTitledBorder("Chat API"));
		chatApi.addActionListener(new RequestExampleAction());
		
		loggingLicensingApi = new JComboBox<String>();
		for (String item : Utils.getLoggingLicensingRequests())
			loggingLicensingApi.addItem(item);
		loggingLicensingApi.setBorder(BorderFactory.createTitledBorder("Logging/Licensing API"));
		loggingLicensingApi.addActionListener(new RequestExampleAction());
		
		historyFlowApi = new JComboBox<String>();
		for (String item : Utils.getHistoryFlowRequests())
			historyFlowApi.addItem(item);
		historyFlowApi.setBorder(BorderFactory.createTitledBorder("History flow API"));
		historyFlowApi.addActionListener(new RequestExampleAction());
		
		designerUpgradeActions = new JComboBox<String>();
		for (String item : Utils.getDesignerUpgradeActionsRequests())
			designerUpgradeActions.addItem(item);
		designerUpgradeActions.setBorder(BorderFactory.createTitledBorder("Designer/Upgrade actions"));
		designerUpgradeActions.addActionListener(new RequestExampleAction());
		
		channelType = new JComboBox<String>();
		for (String item : Utils.getChannelTypes())
			channelType.addItem(item);
		channelType.setBorder(BorderFactory.createTitledBorder("Channel"));
		channelType.setEditable(true);
		
		sendButton.addActionListener(new SendRequestAction());
		sendButton.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "pressed"); 

		formatJson.addActionListener(new JsonFormat());
		applySecurityToken.addActionListener(new UseSecurityToken());
		applyStepId.addActionListener(new UseObjectStepId());
		applyChannelType.addActionListener(new ApplyChannelType());
		applyVersionId.addActionListener(new UseObjectVersion());
		applyObjectId.addActionListener(new UseObjectId());
		applyObjectInstance.addActionListener(new UseObjectInstanceId());
		applyApplicationKey.addActionListener(new ApplyApplicationKey());
		applyTennatId.addActionListener(new ApplyTenantId());
		showHelpWindow.addActionListener(new ShowHelpWindow());
		browseFs.addActionListener(new BrowseFileSystem());
		browseAndSave.addActionListener(new BrowseFileSystem());
		applyBinaryBody.addActionListener(new ApplyBinaryFile());
		applyStoringToFile.addActionListener(new ApplyBinaryFile());
		useCookie.addActionListener(new useCookieInHeader());

		binaryBodyMode.addActionListener(new ChangeBodyMode());
		storeBodyToFile.addActionListener(new ChangeBodyMode());

		// placing of UI elements
		
		JPanel urlPanel = new JPanel(new BorderLayout());
		urlPanel.add(url);
		urlPanel.setBorder(BorderFactory.createTitledBorder("Url"));
		
		JPanel appKeyPanel = new JPanel(new BorderLayout());
		appKeyPanel.add(appKey);
		appKeyPanel.setBorder(BorderFactory.createTitledBorder("Application key"));
		
		JPanel tenantIdPanel = new JPanel(new BorderLayout());
		tenantIdPanel.add(tenantId, BorderLayout.WEST);
		tenantIdPanel.setBorder(BorderFactory.createTitledBorder("Tenant name"));
		
		JPanel prefixPanel = new JPanel();
		prefixPanel.setLayout(new BoxLayout(prefixPanel, BoxLayout.LINE_AXIS));
		prefixPanel.setBorder(BorderFactory.createTitledBorder("Url prefix"));
		prefixPanel.setPreferredSize(new Dimension(250, 0));
		prefixPanel.add(prefixChecked);
		prefixPanel.add(prefix);
		
		JPanel appKeyTenantMethodGroupPanel = new JPanel();
		appKeyTenantMethodGroupPanel.setLayout(new BoxLayout(appKeyTenantMethodGroupPanel, BoxLayout.LINE_AXIS));
		appKeyTenantMethodGroupPanel.add(appKeyPanel);
		appKeyTenantMethodGroupPanel.add(tenantIdPanel);
		appKeyTenantMethodGroupPanel.add(channelType);
		appKeyTenantMethodGroupPanel.add(method);

		JPanel urlPrefixGroupPanel = new JPanel(new BorderLayout());
		urlPrefixGroupPanel.add(prefixPanel,BorderLayout.WEST);
		urlPrefixGroupPanel.add(urlPanel,BorderLayout.CENTER);
		
		JPanel restConfigurationPanel = new JPanel(new BorderLayout());
		restConfigurationPanel.add(appKeyTenantMethodGroupPanel,BorderLayout.WEST);
		restConfigurationPanel.add(urlPrefixGroupPanel,BorderLayout.CENTER);

		JPanel examplesPanel = new JPanel();
		examplesPanel.setLayout(new GridLayout(0, 9));
		examplesPanel.add(interactionApi);
		examplesPanel.add(interactionRuntimeApi);
		examplesPanel.add(userManagementApi);
		examplesPanel.add(variablesApi);
		examplesPanel.add(appKeyApi);
		examplesPanel.add(interactionPointsApi);
		examplesPanel.add(reportsApi);
		examplesPanel.add(imagesApi);
		examplesPanel.add(emailApi);
		examplesPanel.add(dnisApi);
		examplesPanel.add(chatApi);
		examplesPanel.add(foldersApi);
		examplesPanel.add(afterHoursCallIntentApi);
		examplesPanel.add(dashdoardApi);
		examplesPanel.add(allowedAddressesApi);
		examplesPanel.add(loggingLicensingApi);
		examplesPanel.add(historyFlowApi);
		examplesPanel.add(designerUpgradeActions);

		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 1));
		buttonsPanel.setBorder(BorderFactory.createTitledBorder("Choose request"));
		
		buttonsPanel.add(sendButton);
		buttonsPanel.add(applyApplicationKey);
		buttonsPanel.add(applyTennatId);
		buttonsPanel.add(applySecurityToken);
		buttonsPanel.add(applyChannelType);
		buttonsPanel.add(applyObjectInstance);
		buttonsPanel.add(applyObjectId);
		buttonsPanel.add(applyStepId);
		buttonsPanel.add(applyVersionId);
		buttonsPanel.add(showHelpWindow);
		buttonsPanel.add(formatJson);
		buttonsPanel.add(useCookie);
		
		JPanel binaryOperationsGroupPanel = new JPanel();
		binaryOperationsGroupPanel.setLayout(new BoxLayout(binaryOperationsGroupPanel, BoxLayout.Y_AXIS));
		binaryOperationsGroupPanel.add(pathToBinaryFile);
		binaryOperationsGroupPanel.add(pathToBodyFile);
		JPanel browseApplyHighPanel = new JPanel();
		browseApplyHighPanel.setLayout(new BoxLayout(browseApplyHighPanel, BoxLayout.LINE_AXIS));
		browseApplyHighPanel.add(binaryBodyMode);
		browseApplyHighPanel.add(browseFs);
		browseApplyHighPanel.add(applyBinaryBody);
		JPanel browseApplyLowPanel = new JPanel();
		browseApplyLowPanel.setLayout(new BoxLayout(browseApplyLowPanel, BoxLayout.LINE_AXIS));
		browseApplyLowPanel.add(storeBodyToFile);
		browseApplyLowPanel.add(browseAndSave);
		browseApplyLowPanel.add(applyStoringToFile);
		JPanel bottomControlPanel = new JPanel();
		bottomControlPanel.setLayout(new BoxLayout(bottomControlPanel, BoxLayout.Y_AXIS));
		bottomControlPanel.add(browseApplyHighPanel);
		bottomControlPanel.add(browseApplyLowPanel);
		
		binaryMainPanel = new JPanel();
		binaryMainPanel.setLayout(new BoxLayout(binaryMainPanel, BoxLayout.LINE_AXIS));
		setBinaryPanelTitle();
		binaryMainPanel.add(bottomControlPanel);
		binaryMainPanel.add(binaryOperationsGroupPanel);
		
		JPanel binaryRestConfigExamplesButtonsPanel = new JPanel();
		binaryRestConfigExamplesButtonsPanel.setLayout(new BoxLayout(binaryRestConfigExamplesButtonsPanel, BoxLayout.Y_AXIS));
		binaryRestConfigExamplesButtonsPanel.add(binaryMainPanel);
		binaryRestConfigExamplesButtonsPanel.add(restConfigurationPanel);
		binaryRestConfigExamplesButtonsPanel.add(examplesPanel);

		JPanel topPanel = new JPanel(new BorderLayout(2, 1));
		topPanel.add(binaryRestConfigExamplesButtonsPanel, BorderLayout.NORTH);
		topPanel.add(buttonsPanel, BorderLayout.CENTER);
		
		JPanel centerPanel = new JPanel(new GridLayout(2, 2));
		centerPanel.add(scrollHeaders);
		centerPanel.add(scrollResponceHeaders);
		centerPanel.add(scrollBody);
		centerPanel.add(scrollResponceBody);

		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
	}

	private class SendRequestAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			RequestStructure requestStructure = null;
			ResponseStructure response = null;
			ResponseBinaryStructure binaryResponse = null;
			try
			{
				responceHeadersField.setText("");
				responceBodyField.setText("");
				requestStructure = new RequestStructure(url.getText(), method.getSelectedItem(), headersField.getText(), bodyField.getText());
				if (binaryBodyMode.isSelected())
				{
					if (binaryBody != null)
					{
						requestStructure.setBynaryBody(binaryBody);
						requestStructure.setIsBynaryBody(true);
					} else
					{
						requestStructure.setIsBynaryBody(false);
						JOptionPane.showMessageDialog(null, "Unable to perform request with empty binary body");
						return;
					}
				}
				RequestSender s = new RequestSender(requestStructure);
				if (!storeBodyToFile.isSelected())
				{
					response = new ResponseStructure(s.execute());
					responceHeadersField.setText("Status code : " + response.getStatusCode().value() + " (" + response.getStatusCode().getReasonPhrase() + ")\n\n" + response.getHeadersAsString());
					responceBodyField.setText(response.getBody());
				} else
				{
					binaryResponse = new ResponseBinaryStructure(s.executeBinary());
					responceHeadersField.setText("Status code : " + binaryResponse.getStatusCode().value() + " (" + binaryResponse.getStatusCode().getReasonPhrase() + ")\n\n" + binaryResponse.getHeadersAsString());
					Utils.storeBynaryToFile(binaryResponse.getBody(), pathToBodyFile.getText());
				}

				if (TextData.findSecurityToken(responceBodyField.getText()))
				{
					securityToken = TextData.getSecurityToken();
					applySecurityToken.setEnabled(true);
				}
				if (TextData.findObjectId(responceHeadersField.getText()))
				{
					objectId = TextData.getObjectId();
					applyObjectId.setEnabled(true);
				}
				if (TextData.findObjectVersion(responceHeadersField.getText()))
				{
					objectVersion = TextData.getObjectVersion();
					applyVersionId.setEnabled(true);
				}
				if (TextData.findObjectStepId(responceHeadersField.getText()))
				{
					objectStepId = TextData.getObjectStepId();
					applyStepId.setEnabled(true);
				}
				if (TextData.findObjectInstanceId(responceHeadersField.getText()))
				{
					objectInstanceId = TextData.getObjectInstanceId();
					applyObjectInstance.setEnabled(true);
				}
			} catch (HttpClientErrorException e1)
			{
				responceHeadersField.setText(e1.getMessage());
				responceBodyField.setText(e1.getResponseBodyAsString());
			} catch (RuntimeException | IOException e1)
			{
				responceHeadersField.setText(e1.getMessage());
				responceBodyField.setText(Utils.exceptionAsString(e1));
			}
		}
	}

	private class RequestExampleAction implements ActionListener
	{
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e)
		{
			RequestStructure example = new RequestStructure();
			JComboBox<String> sourceObject = (JComboBox<String>) e.getSource();
			String requestName = sourceObject.getSelectedItem().toString();
			example.loadFromResources(requestName);
			headersField.setText(example.getHeaders());
			bodyField.setText(example.getBody());
			if (prefixChecked.isSelected())
				url.setText(prefix.getSelectedItem().toString() + example.getUrl());
			else
				url.setText(example.getUrl());
			method.setSelectedItem(example.getMethod());
			buttonsPanel.setBorder(BorderFactory.createTitledBorder("Request : [" + requestName + "]"));
		}
	}

	private class JsonFormat implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Formatter f;
			try
			{
				f = new Formatter(responceBodyField.getText());
				responceBodyField.setText(f.getFormattedText());
			} catch (ParseException e1)
			{
				JOptionPane.showMessageDialog(null, "Body format isn't a valid JSON, operation skipped", "Invalid structure", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Ui.class.getResource("/images/drunk_pony.png")));
			}
		}
	}

	private class UseSecurityToken implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			String newText = TextData.replaceSecurityToken(headersField.getText(), securityToken);
			if (newText != null)
				headersField.setText(newText);
		}
	}
	
	private class ApplyChannelType implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String newText = TextData.replaceChannelType(headersField.getText(), " "+channelType.getSelectedItem().toString());
			if (newText != null)
				headersField.setText(newText);
		}
	}

	private class UseObjectStepId implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			String newText = TextData.replaceObjectStepId(headersField.getText(), objectStepId);
			if (newText != null)
				headersField.setText(newText);
			newText = TextData.replaceObjectStepIdInBody(bodyField.getText(), objectStepId);
			if (newText != null)
				bodyField.setText(newText);
		}
	}

	private class UseObjectVersion implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String newText = TextData.replaceVersionId(headersField.getText(), objectVersion);
			if (newText != null)
				headersField.setText(newText);
		}
	}

	private class UseObjectId implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String newText = TextData.replaceObjectId(url.getText(), objectId);
			if (newText != null)
				url.setText(newText);
		}
	}

	private class UseObjectInstanceId implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String newText = TextData.replaceObjectInstanceId(url.getText(), objectInstanceId);
			if (newText != null)
				url.setText(newText);
		}
	}

	private class ApplyApplicationKey implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(!TextData.findApplicationKey(headersField.getText()))
			{
				if(!headersField.getText().isEmpty() && !headersField.getText().endsWith("\n"))
					headersField.append("\n");
				headersField.append("Application-Key : "+appKey.getSelectedItem().toString());
				return;
			}
			String newText = TextData.replaceApplicationKey(headersField.getText(), appKey.getSelectedItem().toString());
			if (newText != null)
				headersField.setText(newText);
		}
	}

	private class ApplyTenantId implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String newText = TextData.replaceTenantIdInUrl(url.getText(), tenantId.getSelectedItem().toString());
			if (newText != null)
				url.setText(newText);
			if(!TextData.findTenantId(headersField.getText()))
			{
				if(!headersField.getText().isEmpty() && !headersField.getText().endsWith("\n"))
					headersField.append("\n");
				headersField.append("Tenant-Id : "+tenantId.getSelectedItem().toString());
				return;
			}
			newText = TextData.replaceTenantIdHeaders(headersField.getText(), tenantId.getSelectedItem().toString());
			if (newText != null)
				headersField.setText(newText);
		}
	}
	
	private class ShowHelpWindow implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (helpWindow.isVisible())
			{
				helpWindow.setState(JFrame.NORMAL);
				helpWindow.toFront();
				return;
			}
			helpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			helpWindow.setVisible(true);
			helpWindow.setBounds(400, 400, 600, 600);
		}
	}

	private class ChangeBodyMode implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			setBinaryPanelTitle();
		}
	}

	private class BrowseFileSystem implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JFileChooser dialog = new JFileChooser();
			ImageFileChooser imageDialog = new ImageFileChooser();
			imageDialog.setMultiSelectionEnabled(false);
			if (fileOpenDir != null && !fileOpenDir.isEmpty())
				imageDialog.setCurrentDirectory(new File(fileOpenDir));
			if (fileSaveDir != null && !fileSaveDir.isEmpty())
				dialog.setCurrentDirectory(new File(fileSaveDir));
			dialog.setMultiSelectionEnabled(false);
			if (e.getSource().equals(browseFs))
				if (imageDialog.showOpenDialog(Ui.this) == JFileChooser.APPROVE_OPTION)
				{
					pathToBinaryFile.setText(imageDialog.getSelectedFile().getAbsolutePath());
					fileOpenDir = imageDialog.getSelectedFile().getParent();
				}
			if (e.getSource().equals(browseAndSave))
				if (dialog.showSaveDialog(Ui.this) == JFileChooser.APPROVE_OPTION)
				{
					pathToBodyFile.setText(dialog.getSelectedFile().getAbsolutePath());
					fileSaveDir = dialog.getSelectedFile().getParent();
				}
		}
	}

	private class ApplyBinaryFile implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource().equals(applyBinaryBody))
			{
				if (pathToBinaryFile.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Input file path is empty");
					return;
				}
				binaryBody = Utils.readBinaryFile(new File(pathToBinaryFile.getText()));
			}
			if (e.getSource().equals(applyStoringToFile))
			{
				if (pathToBodyFile.getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Output file path is empty");
					return;
				}
			}
			setBinaryPanelTitle();
		}
	}
	
	private class useCookieInHeader implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(headersField.getText().endsWith("Cookie: SERVERID=c52"))
				return;
			if(!headersField.getText().endsWith("\n"))
				headersField.append("\n");
			headersField.append("Cookie: SERVERID=c52");
		}
	}
}