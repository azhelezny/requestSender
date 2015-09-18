package text.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.JOptionPane;

public class TextData
{
	private static String securityToken;
	private static String objectId;
	private static String objectVersion;
	private static String objectStepId;
	private static String objectInstanceId;

	private static String exprSecurityToken = "(\"Access-Token\"[^:]*:.*\")(.*)(\")";
	private static String repSecurityToken = "(?smx)Access-Token:\\s*[\\w\\s]*$";
	private static String newRepSecurityToken = "Access-Token: ";

	private static String exprObjectId = "(Object-Id[^:]*:[^A-Za-z0-9_]*)(.*)(,)";
	private static String repObjectId = "/interaction/[A-Za-z0-9-]*";
	private static String newRepObjectId = "/interaction/";

	private static String exprObjectVersion = "(Object-Version[^:]*:[^A-Za-z0-9_:]*)(.*)(,)";
	private static String repObjectVersion = "(?smx)Object-Version:\\s[A-Za-z0-9_:-]*$";
	private static String newRepObjectVersion = "Object-Version: ";

	private static String exprObjectStepId = "(Object-Step-Id[^:]*:[^A-Za-z0-9_]*)(.*)(,)";
	private static String repObjectStepId = "(?smx)Object-Step-Id:\\s*[A-Za-z0-9_-]*$";
	private static String newRepObjectStepId = "Object-Step-Id: ";
	private static String repObjectStepIdInBody = "(?smx)\"paramName\"[^:]*:[^\"]*\"[^\"]*";
	private static String newRepObjectStepIdInBody = "\"paramName\":\"";

	private static String exprObjectInstanceId = "(Object-Instance-Id[^:]*:[^A-Za-z0-9_]*)(.*)(,)";
	private static String repObjectInstanceId = "[A-Za-z0-9-]*/navigation";
	private static String newRepObjectInstanceId = "/navigation";

	private static String exprApplicationKey = "Application-Key[^:]*:.*";
	private static String replApplicationKey = "(?smx)Application-Key[^:]*:\\s[A-Za-z0-9_:-]*$";
	private static String newReplApplicationKey = "Application-Key: ";

	private static String exprChannelType = "Channel-Type[^:]*:.*";
	private static String replChannelType = "Channel-Type:";

	private static String exprTenantId = "Tenant-Id[^:]*:.*";
	private static String repTenantIdUrl = "tenantId";
	private static String repTenantIdHeader = "(?smx)Tenant-Id[^:]*:\\s[A-Za-z0-9_:-]*$";
	private static String newRepTenantIdHeader = "Tenant-Id: ";

	private static String getFromSecoundMathesGroup(String expresion, String text) throws PatternSyntaxException
	{
		Pattern regex = Pattern.compile(expresion, Pattern.MULTILINE | Pattern.COMMENTS);
		Matcher regexMatcher = regex.matcher(text);
		if (regexMatcher.find())
			return regexMatcher.group(2);
		throw new PatternSyntaxException("losos", "tunec", 0);
	}

	public static boolean isHappensInText(String expression, String text)
	{
		try
		{
			Pattern regex = Pattern.compile(expression, Pattern.MULTILINE | Pattern.COMMENTS);
			Matcher regexMatcher = regex.matcher(text);
			return regexMatcher.find();

		} catch (PatternSyntaxException ex)
		{
			return false;
		}

	}

	public static String replaceInText(String expression, String newValue, final String text) throws PatternSyntaxException
	{
		String ns = text.replaceFirst(expression, newValue);
		return ns;
	}

	public static String getObjectVersion()
	{
		return objectVersion;
	}

	public static String getObjectStepId()
	{
		return objectStepId;
	}

	public static String getObjectInstanceId()
	{
		return objectInstanceId;
	}

	public static String getSecurityToken()
	{
		return securityToken;
	}

	public static String getObjectId()
	{
		return objectId;
	}

	public static boolean findApplicationKey(String text)
	{
		return isHappensInText(exprApplicationKey, text);
	}

	public static boolean findTenantId(String text)
	{
		return isHappensInText(exprTenantId, text);
	}

	public static boolean findSecurityToken(String text)
	{
		try
		{
			securityToken = getFromSecoundMathesGroup(exprSecurityToken, text);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	public static boolean findObjectId(String text)
	{
		try
		{
			objectId = getFromSecoundMathesGroup(exprObjectId, text);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	public static boolean findObjectInstanceId(String text)
	{
		try
		{
			objectInstanceId = getFromSecoundMathesGroup(exprObjectInstanceId, text);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	public static boolean findObjectVersion(String text)
	{
		try
		{
			objectVersion = getFromSecoundMathesGroup(exprObjectVersion, text);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	public static boolean findObjectStepId(String text)
	{
		try
		{
			objectStepId = getFromSecoundMathesGroup(exprObjectStepId, text);
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}

	public static String replaceSecurityToken(String text, String newValue)
	{
		try
		{
			return replaceInText(repSecurityToken, newRepSecurityToken + newValue, text);
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Security Token", 1);
			return null;
		}
	}

	public static String replaceObjectStepId(String text, String newValue)
	{
		try
		{
			return replaceInText(repObjectStepId, newRepObjectStepId + newValue, text);
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Object version Id", 1);
			return null;
		}
	}

	public static String replaceVersionId(String text, String newValue)
	{
		try
		{
			return replaceInText(repObjectVersion, newRepObjectVersion + newValue, text);
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Object version Id", 1);
			return null;
		}
	}

	public static String replaceObjectId(String text, String newValue)
	{
		try
		{
			return replaceInText(repObjectId, newRepObjectId + newValue, text);
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Object Id", 1);
			return null;
		}
	}

	public static String replaceObjectInstanceId(String text, String newValue)
	{
		try
		{
			return replaceInText(repObjectInstanceId, newValue + newRepObjectInstanceId, text);
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Object instance Id", 1);
			return null;
		}
	}

	public static String replaceObjectStepIdInBody(String text, String newValue)
	{
		try
		{
			return replaceInText(repObjectStepIdInBody, newRepObjectStepIdInBody + newValue, text);
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Object instance Id", 1);
			return null;
		}
	}

	public static String replaceTenantIdInUrl(String text, String newValue)
	{
		try
		{
			return replaceInText(repTenantIdUrl, newValue, text);
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Tenant Id", 1);
			return null;
		}
	}

	public static String replaceTenantIdHeaders(String text, String newValue)
	{
		try
		{
			return replaceInText(repTenantIdHeader, newRepTenantIdHeader + newValue, text);
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Tenant Id", 1);
			return null;
		}
	}

	public static String replaceApplicationKey(String text, String newValue)
	{
		try
		{
			return replaceInText(replApplicationKey, newReplApplicationKey + newValue, text);
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Application Key", 1);
			return null;
		}
	}

	public static String replaceChannelType(String text, String newValue)
	{
		try
		{
			return replaceInText(exprChannelType, replChannelType + newValue, text);
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Channel Type", 1);
			return null;
		}
	}

}
