package bootstrap

import au.com.bytecode.opencsv.CSVReader
import bus.stop.StopLocation
import grails.transaction.Transactional
import org.grails.plugins.csv.CSVMapReader

@Transactional
class StopLocationImportService {

    private static final String CSV_PATH = "bootstrap/bus/stop/stoplocations.csv"
    private static final String CODE_HEADER = "Code"
    private static final String NAME_HEADER = "Name"
    private static final String VIX_NAME_HEADER = "VIX name"
    private static final String LATITUDE_HEADER = "Latitude"
    private static final String LONGITUDE_HEADER = "Longitude"


    def doImport() {
        CSVReader stopLocationCSV = importCSV(CSV_PATH)

        new CSVMapReader(stopLocationCSV).each { csvRow ->
            String code = csvRow.getAt(CODE_HEADER)
            String name = csvRow.getAt(NAME_HEADER)
            String vixName = csvRow.getAt(VIX_NAME_HEADER)
            BigDecimal latitude = stringToBigDecimal(csvRow, LATITUDE_HEADER)
            BigDecimal longitude = stringToBigDecimal(csvRow, LONGITUDE_HEADER)

            if (code && name && vixName && latitude && longitude) {
                StopLocation stopLocation = new StopLocation(code: code, name: name, vixName: vixName, latitude: latitude, longitude: longitude)
                stopLocation.save(failOnError: true)
            }
        }
    }

    private stringToBigDecimal(def csvRow, String header) {
        String stringToConvert = csvRow.getAt(header)
        BigDecimal bigDecimalToReturn = 0
        if (stringToConvert) {
            bigDecimalToReturn = stringToConvert.toBigDecimal()
        }
        return bigDecimalToReturn
    }

    private CSVReader importCSV(String csvFileName){
        def csvInputStream = loadFileFromClassPath(csvFileName)
        CSVReader csvReader = convertCSVFileToCSVMapReader(csvInputStream)
        return csvReader
    }

    private InputStream loadFileFromClassPath(String filePath){
        InputStream csvInputStream = this.class.classLoader.getResourceAsStream(filePath)
        return csvInputStream
    }

    private CSVReader convertCSVFileToCSVMapReader(InputStream csvFile){
        def csvInputStream = csvFile

        def csvReader = csvInputStream.toCsvReader(['charset':'UTF-8'])
        return csvReader
    }
}
