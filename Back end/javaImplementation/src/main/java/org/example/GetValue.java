package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@WebServlet("/getValue")
public class GetValue extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse responseToFront) throws ServletException,IOException{

        StringBuilder  inPuts= new StringBuilder();
        BufferedReader reader1 = request.getReader();

        String amountS = request.getParameter("amount");
        String valueFrom = request.getParameter("valueFrom");
        String valueTo = request.getParameter("valueTo");

        float amount = Float.parseFloat(amountS);


        String apiURL = "https://openexchangerates.org/api/latest.json?app_id=5d299c0b2fcf497cabd03aa07c95cf8f";
        try{
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //Get String by reading response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response1 = new StringBuilder();
            //new code start here
            String line2;
            while ((line2 = reader.readLine()) != null){
                response1.append(line2);
            }

            Gson gson2 = new Gson();
            JsonObject linkInput = gson2.fromJson(response1.toString(),JsonObject.class);

            JsonObject rates = linkInput.getAsJsonObject("rates");
            Float from = rates.get(valueFrom).getAsFloat();
            Float to = rates.get(valueTo).getAsFloat();
            System.out.println(from);


            reader.close();
            float convertAmount = (amount/from)*to;
            String send =  amount + " " + valueFrom + " " + "equals to " + convertAmount + " " + valueTo;
            System.out.println(send);

            //Create send data

            JsonObject sendObject = new JsonObject();
            sendObject.addProperty("status","succsess");
            sendObject.addProperty("convertValue" , send);

            responseToFront.setContentType("application/json");
            responseToFront.setCharacterEncoding("UTF-8");

            Gson gson3 = new Gson();
            String sendOut = gson3.toJson(sendObject);

            PrintWriter out = responseToFront.getWriter();
            out.print(sendOut);
            out.flush();

            conn.disconnect();

        }catch(Exception e){
            System.out.println(e);
        }
    }



}
