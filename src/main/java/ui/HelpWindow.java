package ui;

import org.apache.commons.codec.binary.Base64;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelpWindow extends JFrame {
    /**     */
    private static final long serialVersionUID = 1L;

    private JTextField decodedUrl;
    private JTextField encodedUrl;
    private JTextField base64decodedText;
    private JTextField base64encodedText;
    private JTextField unixTime;
    private JTextField dateFromUnixTime;
    private JCheckBox getMillisecondsInDate;
    private JCheckBox getMilliseconds;
    private JCheckBox alternativeSpaces;

    public HelpWindow() {
        this.setTitle("Help window");
        unixTime = new JTextField();
        unixTime.setHorizontalAlignment(JTextField.CENTER);
        dateFromUnixTime = new JTextField();
        dateFromUnixTime.setHorizontalAlignment(JTextField.CENTER);
        decodedUrl = new JTextField();
        encodedUrl = new JTextField();
        base64decodedText = new JTextField();
        base64encodedText = new JTextField();
        JButton doEncode = new JButton("\\/");
        JButton doDecode = new JButton("/\\");
        JButton doBase64Encode = new JButton("\\/");
        JButton doBase64Decode = new JButton("/\\");
        JButton getDateFromUnixTime = new JButton("Get Date time");
        getDateFromUnixTime.setPreferredSize(new Dimension(140, HEIGHT));
        JButton getUnixTime = new JButton("Get Unix time");
        getUnixTime.setPreferredSize(new Dimension(140, HEIGHT));
        JTextArea memoryArea = new JTextArea();
        memoryArea.setLineWrap(true);
        JScrollPane scrollMemoryArea = new JScrollPane(memoryArea);

        getUnixTime.addActionListener(new GetUnixTime());
        getDateFromUnixTime.addActionListener(new GetDateFromUnixTime());

        doEncode.addActionListener(new EncodeURL());
        doDecode.addActionListener(new DecodeURL());

        doBase64Decode.addActionListener(new DecodeBase64());
        doBase64Encode.addActionListener(new EncodeBase64());

        getMilliseconds = new JCheckBox("ms");
        getMilliseconds.setSelected(true);

        getMillisecondsInDate = new JCheckBox("ms");
        getMillisecondsInDate.setSelected(true);

        alternativeSpaces = new JCheckBox("%20 as spaces");
        alternativeSpaces.setSelected(true);

        JPanel alternativeSpacesPanel = new JPanel();
        alternativeSpacesPanel.setLayout(new BoxLayout(alternativeSpacesPanel, BoxLayout.LINE_AXIS));
        alternativeSpacesPanel.add(alternativeSpaces);

        JPanel decodePanel = new JPanel(new BorderLayout());
        decodePanel.setBorder(BorderFactory.createTitledBorder("Decoded URL"));
        decodePanel.add(decodedUrl);

        JPanel encodePanel = new JPanel(new BorderLayout());
        encodePanel.setBorder(BorderFactory.createTitledBorder("Encoded URL"));
        encodePanel.add(encodedUrl);

        JPanel urlButtonsPanel = new JPanel();
        urlButtonsPanel.setLayout(new BoxLayout(urlButtonsPanel, BoxLayout.LINE_AXIS));
        urlButtonsPanel.add(doDecode);
        urlButtonsPanel.add(doEncode);

        JPanel unixTimePanel = new JPanel();
        unixTimePanel.setBorder(BorderFactory.createTitledBorder("UNIX time"));
        unixTimePanel.setLayout(new BoxLayout(unixTimePanel, BoxLayout.Y_AXIS));
        JPanel unixTimeToLong = new JPanel();
        unixTimeToLong.setLayout(new BoxLayout(unixTimeToLong, BoxLayout.LINE_AXIS));
        unixTimeToLong.add(unixTime);
        unixTimeToLong.add(getUnixTime);
        unixTimeToLong.add(getMilliseconds);
        JPanel unixTimeToDate = new JPanel();
        unixTimeToDate.setLayout(new BoxLayout(unixTimeToDate, BoxLayout.LINE_AXIS));
        unixTimeToDate.add(dateFromUnixTime);
        unixTimeToDate.add(getDateFromUnixTime);
        unixTimeToDate.add(getMillisecondsInDate);
        unixTimePanel.add(unixTimeToLong);
        unixTimePanel.add(unixTimeToDate);

        JPanel memoryAreaPanel = new JPanel(new GridLayout(1, 1));
        memoryAreaPanel.setBorder(BorderFactory.createTitledBorder("Memory area"));
        memoryAreaPanel.add(scrollMemoryArea);

        JPanel encode64Panel = new JPanel(new BorderLayout());
        encode64Panel.setBorder(BorderFactory.createTitledBorder("Encoded base64"));
        encode64Panel.add(base64encodedText);

        JPanel decode64Panel = new JPanel(new BorderLayout());
        decode64Panel.setBorder(BorderFactory.createTitledBorder("Decoded base64"));
        decode64Panel.add(base64decodedText);

        JPanel base64ButtonsPanel = new JPanel();
        base64ButtonsPanel.setLayout(new BoxLayout(base64ButtonsPanel, BoxLayout.LINE_AXIS));
        base64ButtonsPanel.add(doBase64Decode, BorderLayout.WEST);
        base64ButtonsPanel.add(doBase64Encode, BorderLayout.EAST);

        JPanel urlPanel = new JPanel();
        urlPanel.setLayout(new BoxLayout(urlPanel, BoxLayout.Y_AXIS));
        urlPanel.add(decodePanel);
        urlPanel.add(urlButtonsPanel);
        urlPanel.add(alternativeSpacesPanel);
        urlPanel.add(encodePanel);

        JPanel base64Panel = new JPanel();
        base64Panel.setLayout(new BoxLayout(base64Panel, BoxLayout.Y_AXIS));
        base64Panel.add(decode64Panel);
        base64Panel.add(base64ButtonsPanel);
        base64Panel.add(encode64Panel);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(unixTimePanel, BorderLayout.NORTH);
        centerPanel.add(memoryAreaPanel, BorderLayout.CENTER);

        add(urlPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(base64Panel, BorderLayout.SOUTH);
    }

    private class EncodeURL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (alternativeSpaces.isSelected())
                    encodedUrl.setText(URLEncoder.encode(decodedUrl.getText(), "UTF-8").replace("+", "%20"));
                else
                    encodedUrl.setText(URLEncoder.encode(decodedUrl.getText(), "UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                JOptionPane.showMessageDialog(null, e1.getCause());
            }
        }
    }

    private class DecodeURL implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                decodedUrl.setText(URLDecoder.decode(encodedUrl.getText(), "UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                JOptionPane.showMessageDialog(null, e1.getCause());
            }
        }
    }

    private class EncodeBase64 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                base64encodedText.setText(new String(Base64.encodeBase64(base64decodedText.getText().getBytes("utf-8"))));

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, e1.getStackTrace());
            }
        }
    }

    private class DecodeBase64 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                base64decodedText.setText(new String(Base64.decodeBase64(base64encodedText.getText().getBytes("utf-8"))));

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, e1.getStackTrace());
            }
        }
    }

    private class GetUnixTime implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (getMilliseconds.isSelected())
                unixTime.setText(String.valueOf(System.currentTimeMillis()));
            else
                unixTime.setText(String.valueOf(System.currentTimeMillis() / 1000L));
        }
    }

    private class GetDateFromUnixTime implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            long time;
            try {
                time = Long.valueOf(unixTime.getText());
            } catch (Exception e1) {
                dateFromUnixTime.setText("Unable to perform");
                return;
            }
            if (!getMilliseconds.isSelected())
                time = time * 1000L;
            SimpleDateFormat format;
            if (!getMillisecondsInDate.isSelected())
                format = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
            else
                format = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss - SSS");
            dateFromUnixTime.setText(format.format(new Date(time)));
        }
    }
}
