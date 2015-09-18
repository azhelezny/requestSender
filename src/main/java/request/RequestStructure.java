package request;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import ui.Ui;
import utils.Utils;

public class RequestStructure
{
	private String body;
	private byte[] binaryBody;
	private boolean isBynaryBody;
	private String headers;
	private HttpMethod method;
	private String url;

	public RequestStructure()
	{
	}

	public RequestStructure(String url, Object object, String headers, String body)
	{
		this.url = url;
		this.method = (HttpMethod) object;
		setHeaders(headers);
		setBody(body);
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public String getHeaders()
	{
		return headers;
	}

	public void setHeaders(String header)
	{
		this.headers = header;
	}

	public HttpMethod getMethod()
	{
		return method;
	}

	public void setMethod(HttpMethod method)
	{
		this.method = method;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public boolean isBynaryBody()
	{
		return isBynaryBody;
	}

	public void setIsBynaryBody(boolean value)
	{
		isBynaryBody = value;
	}

	public byte[] getBinaryBody()
	{
		return binaryBody;
	}

	public String getBinaryBodyAsString()
	{
		StringBuilder sb = new StringBuilder().append("[");
		for (int i = 0; i < binaryBody.length; i++)
		{
			byte b = binaryBody[i];
			sb.append(b);
			if (i + 1 < binaryBody.length)
			{
				sb.append(", ");
			}
		}
		return sb.append("]").toString();
	}

	public void setBynaryBodyByFile(File file)
	{
		if (!isBynaryBody)
			return;
		byte[] result = Utils.readBinaryFile(file);
		if (result == null)
			return;
		binaryBody = result;
	}

	public void setBynaryBody(byte[] newBody)
	{
		binaryBody = newBody;
	}

	public HttpHeaders getHeadersAsHttpHeaders()
	{
		if (headers.isEmpty())
			return null;
		HttpHeaders m = new HttpHeaders();
		String[] pairs = headers.split("\n");
		for (String s : pairs)
		{
			try
			{
				if (!s.contains(":"))
					throw new Exception("Unable to process string without delimiter \":\" -> " + s);
				m.add(new String(beforeSeparator(s).getBytes("utf-8")), new String(afterSeparator(s).getBytes("utf-8")));
			} catch (Exception e)
			{
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				JOptionPane.showMessageDialog(null, "Unable to process data " + s + "\n" + e.getMessage());
			}
		}

		return m;
	}

	private String beforeSeparator(String s)
	{
		int point = s.indexOf(":");
		return s.substring(0, point).trim();
	}

	private String afterSeparator(String s)
	{
		int point = s.indexOf(":");
		return s.substring(point + 1).trim();
	}

	public void loadFromResources(String resName)
	{
		String line;
		InputStream resource = null;
		BufferedReader br = null;
		try
		{
			// org.apache.comon.io
			// IOUtils
			resource = Ui.class.getResourceAsStream("/requests/" + resName + "/headers.txt");
			br = new BufferedReader(new InputStreamReader(resource));
			headers = "";
			while ((line = br.readLine()) != null)
				headers += line + "\n";
			body = "";
			resource = Ui.class.getResourceAsStream("/requests/" + resName + "/body.txt");
			br = new BufferedReader(new InputStreamReader(resource));
			while ((line = br.readLine()) != null)
				body += line + "\n";
			resource = Ui.class.getResourceAsStream("/requests/" + resName + "/method.txt");
			br = new BufferedReader(new InputStreamReader(resource));
			line = br.readLine();
			method = HttpMethod.valueOf(line);
			resource = Ui.class.getResourceAsStream("/requests/" + resName + "/url.txt");
			br = new BufferedReader(new InputStreamReader(resource));
			line = br.readLine();
			url = line;
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		try
		{
			br.close();
			resource.close();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}
}
