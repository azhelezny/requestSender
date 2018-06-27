package settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import rules.Rule;
import utils.CommonUtils;
import utils.PathUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Andrey Zhelezny
 * Date: 4/10/18
 */
public class Properties {
    //private final String presetsDir;
    private List<String> presetsGroups = new ArrayList<>();
    private List<String> serversList = new ArrayList<>();
    private List<String> proxiesList = new ArrayList<>();
    private Map<String, Rule> rules = new LinkedHashMap<>();
    private String presetsDir;

    private static Properties properties = null;

    private Properties() {
        File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        String rulesFile;
        String serversListFile, proxiesListFile;

        if (jarFile.isFile()) {
            // we are working from JAR file
            String path = jarFile.getParentFile().getPath();
            presetsDir = PathUtils.getOsPath(path, "/../requests/presets");
            rulesFile = PathUtils.getOsPath(path, "/../requests/rules.json");
            serversListFile = PathUtils.getOsPath(path, "/../requests/servers.json");
            proxiesListFile = PathUtils.getOsPath(path, "/../requests/proxies.json");
        } else {
            // we are working from IDE
            String path = System.getProperty("user.dir");
            presetsDir = PathUtils.getOsPath(path, "/requests/presets");
            rulesFile = PathUtils.getOsPath(path, "/requests/rules.json");
            serversListFile = PathUtils.getOsPath(path, "/requests/servers.json");
            proxiesListFile = PathUtils.getOsPath(path, "/requests/proxies.json");
        }

        if (new File(presetsDir).exists()) {
            try {
                presetsGroups.addAll(new ArrayList<>(PathUtils.getFolderNames(presetsDir)));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Unable to process list of presets: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }

        if (new File(serversListFile).exists()) {
            try {
                JSONArray serverList = new JSONObject(CommonUtils.readFileToString(serversListFile)).getJSONArray("servers");
                for (int i = 0; i < serverList.length(); i++)
                    serversList.add(serverList.get(i).toString());
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Unable to process list of servers: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }

        if (new File(proxiesListFile).exists()) {
            try {
                JSONArray proxyList = new JSONObject(CommonUtils.readFileToString(proxiesListFile)).getJSONArray("proxies");
                for (int i = 0; i < proxyList.length(); i++)
                    proxiesList.add(proxyList.get(i).toString());
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Unable to process list of servers: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }

        if (new File(rulesFile).exists()) {
            ObjectMapper mapper = new ObjectMapper();
            Rule[] rulesArray;
            try {
                rulesArray = mapper.readValue(new File(rulesFile), Rule[].class);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Unable to process rules: " + e.getMessage());
                throw new RuntimeException(e);
            }
            for (Rule rule : rulesArray)
                this.rules.put(rule.getCaption(), rule);
        }
    }

    public static Properties get() {
        if (properties == null)
            properties = new Properties();
        return properties;
    }

    public List<String> getServersList() {
        return serversList;
    }

    public List<String> getProxiesList() {
        return proxiesList;
    }

    public Map<String, Rule> getRulesMap() {
        return this.rules;
    }

    public List<String> getPresetsGroups() {
        return presetsGroups;
    }

    public String getPresetsDir() {
        return presetsDir;
    }
}