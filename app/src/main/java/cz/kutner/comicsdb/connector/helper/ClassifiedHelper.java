package cz.kutner.comicsdb.connector.helper;

public class ClassifiedHelper {
    public static int getCategoryId(String categoryName) {
        int categoryId = 0;
        switch (categoryName) {
            case "Prodám":
                categoryId = 1;
                break;
            case "Koupím":
                categoryId = 2;
                break;
            case "Vyměním":
                categoryId = 3;
                break;
            case "Ostatní":
                categoryId = 10;
                break;
        }
        return categoryId;
    }
}
