package capstone.android.digitalflashcard;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class GetRequest extends AsyncTask<String, Integer, String>{

	@Override
	protected String doInBackground(String... query) {
		String result = "";
    	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	nameValuePairs.add(new BasicNameValuePair("query", query[0]));
    	Log.i("POS 0", "added query and about to start out try. Query: "+query[0]);
    	try{	
    		Log.i("POS 1", "Accessed try");
    		HttpClient httpclient = new DefaultHttpClient();
    		Log.i("POS 2", "DefaultHttpClient() set");
    		HttpPost httppost = new HttpPost("http://angela-billings.com/test.php");
    		Log.i("POS 3", "HttpPost successfull");
    		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    		Log.i("before", "httppost.setEntity set successfully");
    		HttpResponse response = httpclient.execute(httppost);
    		Log.i("After", "recieved response");
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            Log.i("entity", "getEntity and getContent complete");
    	
            //convert response to string
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            
            while ((line = reader.readLine()) != null) {
            		Log.i("line", "line: "+line);
            		Log.i("sb", "sb: "+sb);
                    sb.append(line + "\n");
            }
            is.close();
     
            result=sb.toString();
        }catch(Exception e){
                Log.e("getRequest", "Error converting result "+e.toString());
        }
    	
    	return result;
	}
	
}