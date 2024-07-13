package controller;


/**
 *
 * @author 
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.Scanner;
import java.text.*;

import model.*;
import view.*;

public class Principal {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String uriCurrencies = "https://openexchangerates.org/api/currencies.json";
        String currenciesName = Connection.FetchHTTP(uriCurrencies);
        JsonObject currenciesNameJson = gson.fromJson(currenciesName, JsonObject.class);

        String[] currenciesCode = {"USD", "BRL", "EUR", "PYG", "ARS", "CLP", "UYU"};
        int currenciesLength = currenciesCode.length;

        String operating = "y";
        while (operating.equals("y")) {

            Vista.menu();
            
            for (int i = 0; i < currenciesLength; i++) {
                try {
                    String currencyName = currenciesNameJson.get(currenciesCode[i]).getAsString();
                    System.out.println((i + 1) + ". " + currencyName);
                } catch (NullPointerException e) {
                    System.out.println("Moneda no encontrada");
                }
            }
            
            String sInputMessageCurrencyIhave = "TENGO: ";
            String sInputMessageCurrencyIwant = "QUIERO: ";
            String inputAmountMessage = "Ingrese un MONTO: ";
            String errorMessage = "Opción invalida";
            int inputBase = Input.inputNumber(sInputMessageCurrencyIhave, errorMessage, currenciesLength);
            int inputTarget = Input.inputNumber(sInputMessageCurrencyIwant, errorMessage, currenciesLength);
            int inputAmount = Input.inputNumber(inputAmountMessage, errorMessage, 0);

            String baseCurrency = currenciesCode[inputBase - 1];
            String targetCurrency = currenciesCode[inputTarget - 1];
            String uriExchange = "https://v6.exchangerate-api.com/v6/" + ApiKey.API_KEY + "/pair/"
                    + baseCurrency + "/" + targetCurrency + "/" + inputAmount;

            String currencyExchange = Connection.FetchHTTP(uriExchange);
            CurrencyExchangeAPI currencyExchangeAPI = gson.fromJson(currencyExchange, CurrencyExchangeAPI.class);
            Currency currency = new Currency(currencyExchangeAPI);
            String currencyName = currenciesNameJson.get(targetCurrency).getAsString();
            
            double valor = currency.getConversion();
            DecimalFormat df = new DecimalFormat("#,###");

            System.out.println(Color.YELLOW + "La conversión es: " + df.format(valor) + " "
                    + targetCurrency + " (" + currencyName +")" + Color.RESET);
            
            //System.out.println("es de tipo " +  ((Object)currency.getConversion()).getClass().getSimpleName());

            System.out.println("Desea continuar: [y/n]");
            operating = keyboard.nextLine();
        }
    }
}