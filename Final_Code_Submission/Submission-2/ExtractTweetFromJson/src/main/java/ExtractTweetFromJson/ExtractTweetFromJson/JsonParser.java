package ExtractTweetFromJson.ExtractTweetFromJson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParser {
	/*-----------------------------------------------------------------
	 * JsonParser class is used to parse the streaming twitter data that is retrieved in JSON format.
	 * ------------------------------------------------------------------*/
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {

		int i = 0;
		int j = 5;
		while (i < 4) {
			// Reading the twiiter files that contains streaming data.
			BufferedReader bf = new BufferedReader(new FileReader("tweets" + i + "_aapl.txt"));
			BufferedWriter b1 = new BufferedWriter(new FileWriter("tweets" + j + "_aapl.txt"));
			String line = "";
			while ((line = bf.readLine()) != null) {
				if (line.isEmpty() || line.trim().equals("") || line.trim().equals("\n"))
					continue;
				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(line);
				String txt = (String) json.get("text");
				b1.write(txt);
				b1.write("\n");

			}

			bf.close();
			System.out.println(i);
			i++;
			j++;
			b1.close();

		}

	}
}
