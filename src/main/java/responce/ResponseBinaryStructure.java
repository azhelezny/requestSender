package responce;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBinaryStructure
{
	private ResponseEntity<byte []> responce;
	
	public ResponseBinaryStructure(ResponseEntity<byte []> responce)
	{
		this.responce = responce;
	}
	
	public String getHeadersAsString()
	{
		return splitHeaders();
	}
	
	public byte [] getBody()
	{
		return responce.getBody();
	}
	
	public HttpStatus getStatusCode()
	{
		return responce.getStatusCode();
	}
	
	private String splitHeaders()
	{
		String result = ""; 
		for (Entry<String, List<String>> e: responce.getHeaders().entrySet())
		{
			result+=e.getKey()+": ";
			for(String str: e.getValue())
			{
				result+=str+", ";
			}
			result+="\n";
		}
		return result;
	}

}
