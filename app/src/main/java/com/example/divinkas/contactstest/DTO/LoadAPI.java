package com.example.divinkas.contactstest.DTO;

import android.os.AsyncTask;
import android.util.Log;

import com.example.divinkas.contactstest.Data.ContactItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LoadAPI extends AsyncTask<String, Void, List<ContactItem>> {
    private List<ContactItem> contactItemList;

    @Override
    protected List<ContactItem> doInBackground(String... strings) {
        try {
            String query = URLEncoder.encode(strings[0], "UTF-8");
            String link_query = "https://public-api.nazk.gov.ua/v1/declaration/?q=" + query;
            Document document = Jsoup.connect(link_query).ignoreContentType(true).get();
            String result = document.text();

            JSONObject jsonObject = new JSONObject(result);
            JSONArray arrayTovars = jsonObject.getJSONArray("items");

            int count = jsonObject.getJSONObject("page").getInt("totalItems");

            if (count < 500) {
                contactItemList = new ArrayList<>();
                for (int i = 0; i < arrayTovars.length(); i++) {
                    ContactItem contact = new ContactItem();
                    JSONObject item = arrayTovars.getJSONObject(i);

                    contact.setId(item.getString("id"));
                    contact.setMisce_rob(item.getString("placeOfWork"));
                    contact.setFirst_name(item.getString("firstname"));
                    contact.setLast_name(item.getString("lastname"));
                    contact.setLink_pdf(item.getString("linkPDF"));
                    contact.setLink_pdf(item.getString("linkPDF"));

                    contactItemList.add(contact);
                }
            }
            else {
                return null;
            }

            return contactItemList;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<ContactItem> list) {
        super.onPostExecute(list);

    }

}

