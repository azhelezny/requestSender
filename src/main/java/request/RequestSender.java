package request;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public class RequestSender
{
	private RestTemplate restTemplate;
	HttpEntity<String> request;
	HttpEntity<String> binaryRequest;
	private RequestStructure requestStructure;

	public RequestSender(RequestStructure structure) throws IOException
	{
		requestStructure = structure;
		if (!requestStructure.isBynaryBody())
			request = new HttpEntity<String>(requestStructure.getBody(), requestStructure.getHeadersAsHttpHeaders());
		else
			binaryRequest = new HttpEntity<String>(requestStructure.getBinaryBodyAsString(), requestStructure.getHeadersAsHttpHeaders());
	}

	public ResponseEntity<String> execute() throws IOException
	{
		return exchante(String.class);
	}

	@SuppressWarnings("unchecked")
	public ResponseEntity<byte[]> executeBinary() throws IOException
	{
		return exchante((Class<byte[]>) new byte[0].getClass());
	}

	@SuppressWarnings("unchecked")
	private <T> ResponseEntity<T> exchante(Class<T> clazz) throws IOException
	{
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		int timeout = 15000; // 15 seconds
		factory.setConnectTimeout(timeout);
		factory.setReadTimeout(timeout);
		restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(factory);
		try
		{
			return restTemplate.exchange(new URI(requestStructure.getUrl()), requestStructure.getMethod(), requestStructure.isBynaryBody() ? binaryRequest : request, clazz);
		} catch (HttpStatusCodeException ex)
		{
			return new ResponseEntity<T>((T) new String(ex.getResponseBodyAsByteArray()), ex.getResponseHeaders(), ex.getStatusCode());
		} catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
