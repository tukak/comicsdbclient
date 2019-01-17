object Tools {

    fun getVersionCode(): Int {
        val versionCode = Runtime.getRuntime().exec("git rev-list --first-parent --count origin/master").inputStream.reader().use { it.readText() }.trim()
        return Integer.parseInt(versionCode) * 100 + 4000000
    }

    fun getVersionName(): String = Runtime.getRuntime().exec("git describe --tags --dirty").inputStream.reader().use { it.readText() }.trim()

}