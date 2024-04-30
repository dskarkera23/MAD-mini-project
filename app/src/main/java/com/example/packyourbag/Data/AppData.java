package com.example.packyourbag.Data;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.packyourbag.Constants.MyConstants;
import com.example.packyourbag.Database.RoomDB;
import com.example.packyourbag.Model.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppData extends Application {
    RoomDB database;
    Context context;

    public static final String LAST_VERSION = "LAST_VERSION";
    public static final int NEW_VERSION = 1;

    public AppData(RoomDB database) {
        this.database = database;
    }

    public AppData(RoomDB database, Context context) {
        this.database = database;
        this.context = context;
    }

//    public List<Items> getBasicData() {
//        String[] data = {"Wallet", "ID/Passport", "Keys", "Cash", "Credit/debit cards",
//                "Travel documents", "Pen and notebook", "Travel itinerary", "Travel insurance information",
//                "Snacks"};
//        return prepareItemsList(MyConstants.BASIC_NEEDS_CAMEL_CASE, data);
//    }

    public List<Items> getPersonalCareData() {
        String[] data = {"Toothbrush", "Toothpaste", "Soap", "Shampoo", "Deodorant",
                "Razor and shaving cream", "Hairbrush", "Makeup","Travel pillows", "Perfume"};
        List<Items> itemsList = prepareItemsList(MyConstants.PERSONAL_CARE_CAMEL_CASE, data);
        Log.d("AppData", "Retrieved personal care data: " + itemsList.size() + " items");
        return itemsList;
    }

    public List<Items> getClothingData() {
        String[] data = {"T-shirts", "Jeans", "Shorts","Cargo Joggers" ,"Underwear", "Socks",
                "Jacket", "Topi", "Belt", "Gloves","Top",
                "Blankets"};
        return prepareItemsList(MyConstants.CLOTHING_CAMEL_CASE, data);
    }

    public List<Items> getBabyNeedsData() {
        String[] data = {"Diapers", "Baby wipes", "Baby Food", "Bottles",
                "Pacifiers", "Baby lotion", "Toys"};
        return prepareItemsList(MyConstants.BABY_NEEDS_CAMEL_CASE, data);
    }

    public List<Items> getHealthData() {
        String[] data = {"Medications",
                "Pain Relievers",
                "Band-aids",
                "Insect repellent",
                "Sunscreen",
                "Sanitizer",
                "First aid kit",
                "Multivitamins"};
        return prepareItemsList(MyConstants.HEALTH_CAMEL_CASE, data);
    }

    public List<Items> getTechnologyData() {
        String[] data = {"Laptop",
                "Tablet",
                "Speakers",
                "Headphone",
                "Powerbank",
                "Charger",
                "Cables",
                "Camera"};
        return prepareItemsList(MyConstants.TECHNOLOGY_CAMEL_CASE, data);
    }

    public List<Items> getFoodData() {
        String[] data = {"Chats",
                "Chocolate Bars",
                "Instant Noodles",
                "Chakuli/Chips",
                "Utensils (spoon, fork, knife)",
                "Reusable Water Bottle",
                "Tissue Paper",
                "Paper Plates/Cups",
                "Soft Drinks"};
        return prepareItemsList(MyConstants.FOOD_CAMEL_CASE, data);
    }

    public List<Items> getBeachSuppliesData() {
        String[] data = {"Swimsuit",
                "Beach Towel",
                "Sun hat",
                "Sunglasses",
                "Beach bag",
                "Sunscreen",
                "Beach toys",
                "Cooler",
                "Slippers"};
        return prepareItemsList(MyConstants.BEACH_SUPPLIES_CAMEL_CASE, data);
    }

//    public List<Items> getCarSuppliesData() {
//        String[] data = {"Driver's license",
//                "Vehicle registration and insurance",
//                "Car keys",
//                "GPS or maps",
//                "Emergency car kit",
//                "Travel pillows/blankets"};
//        return prepareItemsList(MyConstants.CAR_SUPPLIES_CAMEL_CASE, data);
//    }

    public List<Items> getDocsData() {
        String[] data = {"Driver's license",
                "Vehicle registration and insurance",
                "Passport",
                "Aadhaar Card / ID",
                "Tickets",
                "Wallet",
                "Keys",
                "Cards"};
        return prepareItemsList(MyConstants.DOCS_CAMEL_CASE, data);
    }

    public List<Items> prepareItemsList(String categoryy, String[] data) {
        List<String> list = Arrays.asList(data);
        List<Items> dataList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            dataList.add(new Items(list.get(i), categoryy, false));
        }
        return dataList;
    }

    public List<List<Items>> getAllData() {
        List<List<Items>> listOfAllItems = new ArrayList<>();

//        listOfAllItems.add(getBasicData());
        listOfAllItems.add(getClothingData());
        listOfAllItems.add(getPersonalCareData());
        listOfAllItems.add(getBabyNeedsData());
        listOfAllItems.add(getHealthData());
        listOfAllItems.add(getTechnologyData());
        listOfAllItems.add(getFoodData());
        listOfAllItems.add(getBeachSuppliesData());
//        listOfAllItems.add(getCarSuppliesData());
        listOfAllItems.add(getDocsData());

        return listOfAllItems;
    }

    public void persistDataByCategory(String category, boolean onlyDelete) {
        try {
            List<Items> list = deleteAndGetListByCategory(category, onlyDelete);

            if (!onlyDelete) {
                for (Items item : list) {
                    database.mainDao().saveItems(item);
                }
                Toast.makeText(context, category + " Reset Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, category + " Reset Successfully", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    public void persistAllData() {
        List<List<Items>> listOfAllItems = getAllData();
        for (List<Items> list : listOfAllItems) {
            for (Items items : list) {
                database.mainDao().saveItems(items);
            }
        }
        System.out.println("Data added");

    }

    private List<Items> deleteAndGetListByCategory(String category, boolean onlyDelete) {
        if (onlyDelete) {
            database.mainDao().deleteAllByCategoryAndAddedBy(category, MyConstants.SYSTEM_SMALL);
        } else {
            database.mainDao().deleteAllByCategory(category);
        }

        switch (category) {
//            case MyConstants.BASIC_NEEDS_CAMEL_CASE:
//                return getBasicData();

            case MyConstants.CLOTHING_CAMEL_CASE:
                return getClothingData();

            case MyConstants.PERSONAL_CARE_CAMEL_CASE:
                return getPersonalCareData();

            case MyConstants.BABY_NEEDS_CAMEL_CASE:
                return getBabyNeedsData();

            case MyConstants.HEALTH_CAMEL_CASE:
                return getHealthData();

            case MyConstants.TECHNOLOGY_CAMEL_CASE:
                return getTechnologyData();

            case MyConstants.FOOD_CAMEL_CASE:
                return getFoodData();

            case MyConstants.BEACH_SUPPLIES_CAMEL_CASE:
                return getBeachSuppliesData();

//            case MyConstants.CAR_SUPPLIES_CAMEL_CASE:
//                return getCarSuppliesData();

            case MyConstants.DOCS_CAMEL_CASE:
                return getDocsData();

            default:
                return new ArrayList<>();

        }
    }
}
