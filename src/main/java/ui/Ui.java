package ui;

import json.Formatter;
import rest.enums.RestMethod;
import rest.request.RestRequest;
import rest.response.RestResponse;
import rules.Rule;
import rules.classes.ApplyTo;
import rules.classes.CollectFrom;
import rules.classes.Destination;
import rules.classes.RuleValueType;
import settings.Properties;
import utils.CommonUtils;
import utils.ImageFileChooser;
import utils.PathUtils;
import utils.TextUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Ui extends JFrame {
    private static final long serialVersionUID = -2669800895909059399L;
    private Map<String, Rule> rulesMap = Properties.get().getRulesMap();
    private String fileOpenDir = null;
    private String fileSaveDir = null;

    private JButton browseFs;
    private JButton browseAndSave;
    private JButton applyBinaryBody;
    private JButton applyStoringToFile;

    private JTextArea headersField;
    private JTextArea bodyField;
    private JTextArea responseHeadersField;
    private JTextArea responseBodyField;
    private JTextField url;
    private JTextField pathToBinaryFile;
    private JTextField pathToBodyFile;

    private JCheckBox binaryBodyMode;
    private JCheckBox storeBodyToFile;
    private JCheckBox prefixChecked = new JCheckBox();
    private JCheckBox useProxyChecked = new JCheckBox();

    private JComboBox<RestMethod> method;
    private JComboBox<String> prefix = new JComboBox<>();
    private JComboBox<String> proxy = new JComboBox<>();

    private JPanel buttonsPanel = new JPanel();
    private List<JButton> rulesButton = new ArrayList<>();
    private JPanel binaryMainPanel;

    private byte[] binaryBody = null;

    private HelpWindow helpWindow;

    private void setBinaryPanelTitle() {
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

        if (binaryBodyMode.isSelected())
            pathToBinaryFile.setBackground(Color.YELLOW);
        else
            pathToBinaryFile.setBackground(Color.WHITE);

        if (storeBodyToFile.isSelected())
            pathToBodyFile.setBackground(Color.YELLOW);
        else
            pathToBodyFile.setBackground(Color.WHITE);


        binaryMainPanel.setBorder(BorderFactory.createTitledBorder(String.format(binaryPanelTitle, binadyBodyOnOff, binaryBodyFilesNs, filePath, saveBodyOnOff, saveBodyFilesNs, outputFilePath)));
    }

    public Ui() throws ParseException, IOException {
        helpWindow = new HelpWindow();

        this.setTitle("Request sender");
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResource("/images/blazon.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setIconImage(image);

        // Instantiation
        JButton sendButton = new JButton("Send");
        JButton formatJson = new JButton("Format JSON");
        JButton showHelpWindow = new JButton("HelpWindow");
        headersField = new JTextArea();
        responseHeadersField = new JTextArea();
        responseBodyField = new JTextArea();
        bodyField = new JTextArea();
        url = new JTextField();
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

        // Customizing
        headersField.setEditable(true);
        headersField.setLineWrap(true);
        JScrollPane scrollHeaders = new JScrollPane(headersField);
        scrollHeaders.setBorder(BorderFactory.createTitledBorder("Headers"));
        bodyField.setEditable(true);
        bodyField.setLineWrap(true);
        JScrollPane scrollBody = new JScrollPane(bodyField);
        scrollBody.setBorder(BorderFactory.createTitledBorder("Body"));
        responseHeadersField.setEditable(false);
        responseHeadersField.setLineWrap(true);
        JScrollPane scrollResponceHeaders = new JScrollPane(responseHeadersField);
        scrollResponceHeaders.setBorder(BorderFactory.createTitledBorder("Responce headers"));
        responseBodyField.setEditable(false);
        responseBodyField.setLineWrap(true);
        JScrollPane scrollResponceBody = new JScrollPane(responseBodyField);
        scrollResponceBody.setBorder(BorderFactory.createTitledBorder("Responce body"));

        method = new JComboBox<>();
        method.addItem(RestMethod.GET);
        method.addItem(RestMethod.POST);
        method.addItem(RestMethod.PUT);
        method.addItem(RestMethod.DELETE);
        method.addItem(RestMethod.OPTIONS);
        method.setBorder(BorderFactory.createTitledBorder("Method"));

        prefix.setEditable(true);
        Properties.get().getServersList().forEach(prefix::addItem);

        proxy.setEditable(true);
        Properties.get().getProxiesList().forEach(proxy::addItem);

        List<JComboBox<String>> examples = new ArrayList<>();
        for (String group : Properties.get().getPresetsGroups()) {
            JComboBox<String> requestExample = new JComboBox<>();
            requestExample.setName(group);
            requestExample.setBorder(BorderFactory.createTitledBorder(group));
            PathUtils.getFolderNames(PathUtils.getOsPath(Properties.get().getPresetsDir(), group)).forEach(requestExample::addItem);
            requestExample.addActionListener(new RequestExampleAction());
            examples.add(requestExample);
        }

        sendButton.addActionListener(new SendRequestAction());
        sendButton.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "pressed");

        formatJson.addActionListener(new JsonFormat());
        showHelpWindow.addActionListener(new ShowHelpWindow());
        browseFs.addActionListener(new BrowseFileSystem());
        browseAndSave.addActionListener(new BrowseFileSystem());
        applyBinaryBody.addActionListener(new ApplyBinaryFile());
        applyStoringToFile.addActionListener(new ApplyBinaryFile());

        binaryBodyMode.addActionListener(new ChangeBodyMode());
        storeBodyToFile.addActionListener(new ChangeBodyMode());

        // placing of UI elements
        JPanel urlPanel = new JPanel(new BorderLayout());
        urlPanel.add(url);
        urlPanel.setBorder(BorderFactory.createTitledBorder("Url"));

        JPanel prefixPanel = new JPanel();
        prefixPanel.setLayout(new BoxLayout(prefixPanel, BoxLayout.LINE_AXIS));
        prefixPanel.setBorder(BorderFactory.createTitledBorder("Url prefix"));
        prefixPanel.add(prefixChecked);
        prefixPanel.add(prefix);

        JPanel proxyPanel = new JPanel();
        proxyPanel.setLayout(new BoxLayout(proxyPanel, BoxLayout.LINE_AXIS));
        proxyPanel.setBorder(BorderFactory.createTitledBorder("Proxy settings"));
        proxyPanel.add(useProxyChecked);
        proxyPanel.add(proxy);

        JPanel methodGroupPanel = new JPanel();
        methodGroupPanel.setLayout(new BoxLayout(methodGroupPanel, BoxLayout.LINE_AXIS));
        methodGroupPanel.add(method);

        JPanel prefixProxyPanel = new JPanel(new BorderLayout());
        prefixProxyPanel.add(prefixPanel, BorderLayout.CENTER);
        prefixProxyPanel.add(proxyPanel, BorderLayout.WEST);

        JPanel restConfigurationPanel = new JPanel(new BorderLayout());
        restConfigurationPanel.add(methodGroupPanel, BorderLayout.WEST);
        restConfigurationPanel.add(urlPanel, BorderLayout.CENTER);

        JPanel examplesPanel = new JPanel();
        examplesPanel.setLayout(new GridLayout(0, 5));
        for (int i = 0; i < examples.size(); i++)
            examplesPanel.add(examples.get(i), i);

        buttonsPanel.setLayout(new GridLayout(0, 5));
        buttonsPanel.setBorder(BorderFactory.createTitledBorder("Choose request"));

        buttonsPanel.add(sendButton);
        buttonsPanel.add(showHelpWindow);
        buttonsPanel.add(formatJson);

        for (Map.Entry<String, Rule> rule : rulesMap.entrySet()) {
            JButton ruleButton = new JButton(rule.getKey());
            ruleButton.setEnabled(rule.getValue().getSource().isDefined());
            ruleButton.addActionListener(new ApplyRule());
            rulesButton.add(ruleButton);
            buttonsPanel.add(ruleButton);
        }

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
        binaryRestConfigExamplesButtonsPanel.add(prefixProxyPanel);
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
        updateRuleButtons();
    }

    private class SendRequestAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            RestRequest request = new RestRequest();
            RestResponse response;
            RestMethod restMethod = (RestMethod) method.getSelectedItem();
            request.setUrl(url.getText())
                    .setMethod(restMethod)
                    .setHeaders(headersField.getText());

            if (useProxyChecked.isSelected())
                try {
                    String[] proxyParameters = ((String) Objects.requireNonNull(proxy.getSelectedItem())).split(":");
                    request.setProxy(proxyParameters[0], Integer.valueOf(proxyParameters[1]));
                } catch (Exception e1) {
                    responseHeadersField.setText(e1.getMessage());
                    responseBodyField.setText(CommonUtils.exceptionAsString(e1));
                }

            responseHeadersField.setText("");
            responseBodyField.setText("");

            if (binaryBodyMode.isSelected())
                if (binaryBody != null)
                    request.setBinaryBody(binaryBody);
                else {
                    JOptionPane.showMessageDialog(null, "Unable to perform request with empty binary body");
                    return;
                }
            else if (!restMethod.equals(RestMethod.GET))
                request.setBody(bodyField.getText());

            try {
                response = new RestResponse(request.executeBasic(binaryBodyMode.isSelected()));
                responseHeadersField.setText("Status code : " + response.getCode() + "\n\n" + response.getHeadersAsString());
                if (!storeBodyToFile.isSelected())
                    responseBodyField.setText(response.getBody());
                else
                    CommonUtils.storeBinaryToFile(response.getBinaryBody(), pathToBodyFile.getText());
                updateRules();
                updateRuleButtons();
            } catch (RuntimeException | IOException e1) {
                responseHeadersField.setText(e1.getMessage());
                responseBodyField.setText(CommonUtils.exceptionAsString(e1));
            }
        }
    }

    private class RequestExampleAction implements ActionListener {
        @SuppressWarnings("unchecked")
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> sourceObject = (JComboBox<String>) e.getSource();
            String requestName = sourceObject.getName();
            String pathToRequest = PathUtils.getOsPath(Properties.get().getPresetsDir(), requestName, sourceObject.getSelectedItem().toString());
            headersField.setText(CommonUtils.readFileToString(PathUtils.getOsPath(pathToRequest, "headers.txt")));
            bodyField.setText(CommonUtils.readFileToString(PathUtils.getOsPath(pathToRequest, "body.txt")));
            if (prefixChecked.isSelected())
                url.setText(prefix.getSelectedItem().toString() + CommonUtils.readFileToString(PathUtils.getOsPath(pathToRequest, "url.txt")));
            else
                url.setText(CommonUtils.readFileToString(PathUtils.getOsPath(pathToRequest, "url.txt")));
            method.setSelectedItem(RestMethod.get(CommonUtils.readFileToString(PathUtils.getOsPath(pathToRequest, "method.txt"))));
            buttonsPanel.setBorder(BorderFactory.createTitledBorder("Request : [" + requestName + "]"));
        }
    }

    private class JsonFormat implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Formatter f;
            try {
                f = new Formatter(responseBodyField.getText());
                responseBodyField.setText(f.getFormattedText());
            } catch (ParseException e1) {
                JOptionPane.showMessageDialog(null, "Body format isn't a valid JSON, operation skipped", "Invalid structure", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Ui.class.getResource("/images/drunk_pony.png")));
            }
        }
    }

    private void updateRules() {
        for (Map.Entry<String, Rule> rule : rulesMap.entrySet()) {
            if (!rule.getValue().getSource().getType().equals(RuleValueType.REGEX))
                continue;
            for (CollectFrom collectFrom : rule.getValue().getSource().getCollectFrom()) {
                String regex = rule.getValue().getSource().getRegex().getValue();
                int group = rule.getValue().getSource().getRegex().getGroup();
                String text = (collectFrom.equals(CollectFrom.RESPONSE_BODY)) ? responseBodyField.getText() : responseHeadersField.getText();
                String groupValue = TextUtils.getFromGroupIfPresented(regex, group, text);
                if (groupValue != null) {
                    rule.getValue().getSource().setValue(groupValue);
                }
            }

        }
    }

    private void updateRuleButtons() {
        for (JButton ruleButton : rulesButton) {
            String buttonName = ruleButton.getText();
            if (rulesMap.containsKey(buttonName))
                ruleButton.setEnabled(rulesMap.get(buttonName).getSource().isDefined());
        }
    }

    private class ApplyRule implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String ruleName = ((JButton) e.getSource()).getText();
            Rule rule = rulesMap.get(ruleName);
            String ruleValue = rule.getSource().getValue();
            if (ruleValue == null)
                return;
            for (Destination destination : rule.getDestination()) {
                for (ApplyTo applyTo : destination.getApplyTo()) {
                    JTextComponent target = getTarget(applyTo);
                    String text = target.getText();
                    if (destination.getType().equals(RuleValueType.REGEX)) {
                        if (TextUtils.isHappensInText(destination.getRegex().getSearchPattern(), text)) {
                            text = TextUtils.replaceInText(
                                    destination.getRegex().getSearchPattern(),
                                    destination.getRegex().getReplacePattern().replace("${VALUE}", ruleValue),
                                    text);
                            target.setText(text);
                        }

                    } else {
                        StringBuilder sb = new StringBuilder(text);
                        if (!text.isEmpty() && sb.charAt(sb.length() - 1) == '\n')
                            sb.append(ruleValue);
                        else
                            sb.append("\n").append(ruleValue);
                        target.setText(sb.toString());
                    }
                }
            }
        }

        private JTextComponent getTarget(ApplyTo applyTo) {
            switch (applyTo) {
                case REQUEST_BODY:
                    return bodyField;
                case REQUEST_HEADERS:
                    return headersField;
                default:
                    return url;
            }
        }
    }


    private class ShowHelpWindow implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (helpWindow.isVisible()) {
                helpWindow.setState(JFrame.NORMAL);
                helpWindow.toFront();
                return;
            }
            helpWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            helpWindow.setVisible(true);
            helpWindow.setBounds(400, 400, 600, 600);
        }
    }

    private class ChangeBodyMode implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setBinaryPanelTitle();
        }
    }

    private class BrowseFileSystem implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser dialog = new JFileChooser();
            ImageFileChooser imageDialog = new ImageFileChooser();
            imageDialog.setMultiSelectionEnabled(false);
            if (fileOpenDir != null && !fileOpenDir.isEmpty())
                imageDialog.setCurrentDirectory(new File(fileOpenDir));
            if (fileSaveDir != null && !fileSaveDir.isEmpty())
                dialog.setCurrentDirectory(new File(fileSaveDir));
            dialog.setMultiSelectionEnabled(false);
            if (e.getSource().equals(browseFs))
                if (imageDialog.showOpenDialog(Ui.this) == JFileChooser.APPROVE_OPTION) {
                    pathToBinaryFile.setText(imageDialog.getSelectedFile().getAbsolutePath());
                    fileOpenDir = imageDialog.getSelectedFile().getParent();
                }
            if (e.getSource().equals(browseAndSave))
                if (dialog.showSaveDialog(Ui.this) == JFileChooser.APPROVE_OPTION) {
                    pathToBodyFile.setText(dialog.getSelectedFile().getAbsolutePath());
                    fileSaveDir = dialog.getSelectedFile().getParent();
                }
        }
    }

    private class ApplyBinaryFile implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(applyBinaryBody)) {
                if (pathToBinaryFile.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Input file path is empty");
                    return;
                }
                binaryBody = CommonUtils.readBinaryFile(pathToBinaryFile.getText());
            }
            if (e.getSource().equals(applyStoringToFile)) {
                if (pathToBodyFile.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Output file path is empty");
                    return;
                }
            }
            setBinaryPanelTitle();
        }
    }
}