package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

public class Utils
{
	public static byte[] readBinaryFile(File file)
	{
		int length = (int) file.length();
		if (length <= 0)
		{
			JOptionPane.showMessageDialog(null, "File " + file.getAbsolutePath() + " is empty or doesn't exist");
			return null;
		}
		byte[] binaryBody = new byte[length];
		InputStream input = null;
		int totalBytesRead = 0;
		try
		{
			input = new BufferedInputStream(new FileInputStream(file));
			while (totalBytesRead < length)
			{
				int bytesRemaining = length - totalBytesRead;
				int bytesRead = input.read(binaryBody, totalBytesRead, bytesRemaining);
				if (bytesRead > 0)
					totalBytesRead = totalBytesRead + bytesRead;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
		return binaryBody;
	}

	public static boolean storeBynaryToFile(byte[] binary, String fileName)
	{
		if (binary == null || binary.length <= 0)
		{
			JOptionPane.showMessageDialog(null, "Empty binary data");
			return false;
		}
		try
		{
			OutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
			stream.write(binary);
			stream.close();
		} catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, exceptionAsString(e));
			return false;
		}
		return false;
	}

	public static String exceptionAsString(Throwable e)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	public static List<String> getFolderRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("foldersCreate");
		retVal.add("foldersUpdate");
		retVal.add("foldersDelete");
		retVal.add("foldersMove");
		retVal.add("foldersFindOne");
		retVal.add("foldersFindAll");
		retVal.add("foldersFindByInteractionId");
		return retVal;
	}

	public static List<String> getReportsRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("reportInteractionHistory");
		retVal.add("reportTransactionPerTenant");
		retVal.add("reportHitMapPerformance");
		retVal.add("reportBilling");
		return retVal;
	}

	public static List<String> getImagesRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("imageGetByName");
		retVal.add("imageGetMetadataByName");
		retVal.add("imageCreateMetadata");
		retVal.add("imageUpdateMetadata");
		retVal.add("imageCreateDeviceObject");
		retVal.add("imageUpdateDeviceObject");
		retVal.add("imageCreateBitmap");
		retVal.add("imageUpdateBitmap");
		retVal.add("imageDeleteImageByName");
		retVal.add("imageDeleteImageByNameAndDeviceId");
		retVal.add("imageExportAll");
		retVal.add("imageGetByHeightAndWidth");
		return retVal;
	}

	public static List<String> getEmailRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("emailCreateUpdateSettings");
		retVal.add("emailGetSettings");
		retVal.add("emailConnectToServer");
		retVal.add("emailCheckConnection");
		retVal.add("emailSendContactUsLetter");
		retVal.add("emailTestEmail");
		return retVal;
	}

	public static List<String> getDnisRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("dnisGetList");
		retVal.add("dnisCreatePhone");
		retVal.add("dnisAllocatePhone");
		retVal.add("dnisGetInteractionInstanceId");
		retVal.add("dnisFreePhoneAllocation");
		return retVal;
	}

	public static List<String> getInteractioRuntimeRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("getInteractionsList");
		retVal.add("createInteraction");
		retVal.add("navigationCurrent");
		retVal.add("navigationNext");
		retVal.add("navigationBack");
		retVal.add("navigationBackParam");
		retVal.add("navigationFinish");
		retVal.add("navigationReloadElement");
		retVal.add("navigationReloadInstance");
		return retVal;
	}

	public static List<String> getInteractioApiRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("intAuthentication");
		retVal.add("intGetAuthUrl");
		retVal.add("intAuthenticationAdmin");
		retVal.add("intValidation");
		retVal.add("intApprove");
		retVal.add("intDraft");
		retVal.add("intDeleteInteractionByDesignerId");
		retVal.add("intUpdateInteractionHeader");
		retVal.add("intGetAll");
		retVal.add("intGetInteraction");
		retVal.add("intFindAllHeaders");
		retVal.add("intFindAllHeadersDeleted");
		retVal.add("intFindHeadersByPatternAndState");
		retVal.add("intFindHeadersDeletedByName");
		retVal.add("intFindHeaderByDesignerIdAndVersion");
		retVal.add("intFindInteractionByDesignerIdAndVersion");
		retVal.add("intDownloadInteraction");
		retVal.add("intGetPublicExecutionLink");
		retVal.add("intGetExecutionLink");
		return retVal;
	}

	public static List<String> getUserManagementRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("createUser");
		retVal.add("listOfUsers");
		retVal.add("listOfTenants");
		retVal.add("editUser");
		retVal.add("deleteUser");
		retVal.add("getCurrentUser");
		retVal.add("createTenant");
		retVal.add("createTrialTenant");
		retVal.add("updateTenant");
		retVal.add("getTenant");
		retVal.add("deleteTenant");
		retVal.add("changeTenantState");
		retVal.add("changeUserState");
		return retVal;
	}

	public static List<String> getVariablesRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("getVariables");
		retVal.add("addVariable");
		retVal.add("updateVariable");
		retVal.add("deleteVariable");
		retVal.add("getVariablesSetData");
		retVal.add("getServerVariablesXml");
		return retVal;
	}

	public static List<String> getAppKeyRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("appGetAppKeys");
		retVal.add("appCreateAppKey");
		retVal.add("appUpdateAppKey");
		retVal.add("appEnableDisableAppKey");
		retVal.add("appDeleteAppKey");
		retVal.add("appRegenerateAppKey");
		retVal.add("appOAuthLoginRequest");
		retVal.add("appAccessTokenRequest");
		return retVal;
	}

	public static List<String> getInteractioPointsRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("ipCreateRest");
		retVal.add("ipUpdateRest");
		retVal.add("ipCreateWsdl");
		retVal.add("ipUpdateWsdl");
		return retVal;
	}

	public static List<String> getAllowedIpsRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("secGetAllowedAddresses");
		retVal.add("secAddAllowedAddresse");
		retVal.add("secUpdateAllowedAddresse");
		retVal.add("secDeleteAllowedAddresse");
		return retVal;
	}

	public static List<String> getChatRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("chatStart");
		retVal.add("chatSendMessage");
		retVal.add("chatGetMessages");
		retVal.add("chatTerminate");
		retVal.add("chatGetSettings");
		retVal.add("chatSetSettings");
		retVal.add("chatGetProviders");
		return retVal;
	}

	public static Map<String,String> getApplicationKey()
	{
		Map<String,String> appKeys = new HashMap<String, String>();
		appKeys.put("adminConsole", "adminconsoleapplicationkey");
		appKeys.put("designer", "designer");
		appKeys.put("agentHistory", "agenthistoryonlyapplicationkey");
		appKeys.put("agent", "agent");
		appKeys.put("mobile", "mobile");		
		appKeys.put("selfService", "selfService");
		appKeys.put("iphone", "iphone");
		appKeys.put("android", "android");
		return appKeys;
	}

	public static List<String> getPredefinedServers()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("http://172.23.33.53:8080");
		retVal.add("http://172.23.33.48:8080");
		return retVal;
	}

	public static List<String> getAfterHoursCallIntentRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("ahCheckVoiceAvailability");
		retVal.add("ahCheckChatAvailability");
		retVal.add("callIntentGetNavigationId");
		retVal.add("callIntentPutIntoVarSet");
		retVal.add("callIntentPutIntoVarByNavigationId");
		retVal.add("callIntentGetVarSet");
		retVal.add("callIntentGetVarSetByNavigationId");
		return retVal;
	}

	public static List<String> getDashboardRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("dashboardUsageReport");
		retVal.add("dashboardUsageIntReport");
		retVal.add("dashboardTopactiveReport");
		retVal.add("dashboardTopactiveIntReport");
		retVal.add("dashboardConcurrencyReport");
		retVal.add("dashboardConcurrencyIntReport");
		retVal.add("dashboardConnectivityReport");
		retVal.add("dashboardConnectivityIntReport");
		retVal.add("dashboardCallResolutionReport");
		retVal.add("dashboardCallResolutionIntReport");
		retVal.add("dashboardAppUsageReport");
		retVal.add("dashboardAppUsageIntReport");
		return retVal;
	}

	public static List<String> getLoggingLicensingRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("logSettingsGet");
		retVal.add("logSettingsCreate");
		retVal.add("logSettingsSetThirdParty");
		retVal.add("licenseGet");
		retVal.add("licenseSet");
		return retVal;
	}

	public static List<String> getHistoryFlowRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("uniqueIdGetByDtmf");
		retVal.add("uniqueIdGetByCallReturnNumber");
		retVal.add("uniqueIdGetByClickToCallId");
		retVal.add("fullFlowGetInteraction");
		retVal.add("fullFlowGetInteractionByCallerAttribute");
		retVal.add("fullFlowGetDataVarSet");
		retVal.add("fullFlowGetAgentInfoSet");
		retVal.add("fullFlowGetDataVarSetByCallerAttribute");
		retVal.add("summaryFlowGetInteraction");
		retVal.add("summaryFlowGetInteractionByCallerAttribute");
		return retVal;
	}
	
	public static List<String> getDesignerUpgradeActionsRequests()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("designerPublish");
		retVal.add("designerCaptureInteraction");
		retVal.add("upgradeStart");
		retVal.add("upgradeGetList");
		return retVal;
	}
	
	public static List<String> getChannelTypes()
	{
		List<String> retVal = new ArrayList<String>();
		retVal.add("AGENT_FULL");
		retVal.add("AGENT_HISTORY");
		retVal.add("MOBILE_WEB");
		retVal.add("WEB_SELF_SERVICE");
		retVal.add("DESIGNER");
		retVal.add("ADMIN_CONSOLE");
		retVal.add("DNIS");
		retVal.add("DNIS_SERVICE");
		retVal.add("LAUNCH_PAD");
		retVal.add("MOBILE_NATIVE");
		retVal.add("AGENT");
		retVal.add("MOBILE");
		return retVal;
	}
}
