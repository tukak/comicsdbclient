package cz.kutner.comicsdb.connector.helper

public object ClassifiedHelper {
    public fun getCategoryId(categoryName: String): Int {
        var categoryId = 0
        when (categoryName) {
            "Prodám" -> categoryId = 1
            "Koupím" -> categoryId = 2
            "Vyměním" -> categoryId = 3
            "Ostatní" -> categoryId = 10
        }
        return categoryId
    }
}
