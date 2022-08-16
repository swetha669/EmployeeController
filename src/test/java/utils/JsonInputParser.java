package utils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonInputParser {
    private static JSONObject data;

    public static JSONObject inputdatainit()
    {
        JSONParser parser = new JSONParser();
        try
        {
            data = (JSONObject) parser.parse(new FileReader("src/test/resources/testData.json"));
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return data;


    }

}
