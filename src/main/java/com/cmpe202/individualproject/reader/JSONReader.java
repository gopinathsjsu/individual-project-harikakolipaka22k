package com.cmpe202.individualproject.reader;

import com.cmpe202.individualproject.main.CreditCardEntry;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.rmi.registry.LocateRegistry;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JSONReader implements Reader {
    File file;

    public JSONReader() {
    }

    public JSONReader(File file) {
        this.file = file;
    }

    @Override
    public List<CreditCardEntry> readFile(String inputFile) {
        FileReader fr;
        List<CreditCardEntry> result = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");

        try {
            fr = new FileReader(file);
        JSONParser parser = new JSONParser();
        JSONArray cards = (JSONArray) parser.parse(fr);  // Directly parse as JSONArray

        for (Object cardObj : cards) {
            JSONObject card = (JSONObject) cardObj;
            List<String> cardDetails = new ArrayList<>();

            // Extract card details
            String cardNumber = (String) card.get("cardNumber");
            String expirationDate = (String) card.get("expirationDate");
            String cardHolderName = (String) card.get("cardHolderName");

            Date eDate = sdf.parse(expirationDate);
            result.add(new CreditCardEntry(cardNumber, eDate, cardHolderName));
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
