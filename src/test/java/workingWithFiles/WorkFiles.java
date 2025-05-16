package workingWithFiles;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import workingWithFiles.modeli.PersonalObject;
import workingWithFiles.modeli.arr;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class WorkFiles {
    private ClassLoader cl = WorkFiles.class.getClassLoader();
    //public static final ObjectMapper jackson = new ObjectMapper();

    @Test
    @DisplayName("№ 1 Проверка содержимого в test123.pdf из архива arhive3.zip")
    void zipPdfTest() throws Exception {
        //Используется try-with-resources для автоматического закрытия потоков
        //
        //getResourceAsStream загружает ZIP-архив arhive3.zip из ресурсов теста
        //
        //Создается ZipInputStream для чтения содержимого ZIP-архива
        try (InputStream zipStream = getClass().getClassLoader().getResourceAsStream("arhive3.zip");
             ZipInputStream zis = new ZipInputStream(zipStream)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().equalsIgnoreCase("test123.pdf")) {
                    PDF pdf = new PDF(zis);
                    assertEquals("Test123", pdf.text.trim());
                    return;
                }
            }
            Assertions.fail("Файл test123.pdf не найден в архиве!");
        }
    }

    @Test
    @DisplayName(" № 2 Проверка содержимого в .xls файле из архива arhive3.zip")
    public final void zipXlsTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("arhive3.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".xls")) {
                    XLS xls = new XLS(zis);
                    String value = xls.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
                    assertEquals("test", value);

                }
            }

        }
    }

    @Test
    @DisplayName(" № 3 Проверка содержимого в .csv файле из архива arhive3.zip")
    public void reedCSVFileInArchiveTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("arhive3.zip");
             ZipInputStream zis = new ZipInputStream(is)) {

            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                if (zipEntry.getName().endsWith(".csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> data = csvReader.readAll();
                    assertEquals(2, data.size());
                    Assertions.assertArrayEquals(new String[]{"test11;test12;test13"}, data.get(0));
                    Assertions.assertArrayEquals(new String[]{"test22;test23;test24"}, data.get(1));
                }
            }
        }
    }

    @Test
    @DisplayName(" № 4 тестовый разбор .json файла")
    public void reedJsonFileTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        {
            arr arrTest = mapper.readValue(new File("src/test/resources/arr.json"), arr.class);
            assertEquals("France", arrTest.getName());
            assertEquals("Paris", arrTest.getCapital());
            assertEquals(67364357, arrTest.getPopulation());
            assertEquals("Europe", arrTest.getRegion());
            assertEquals("https://upload.wikimedia.org/wikipedia/commons/c/c3/Flag_of_France.svg", arrTest.getFlag());
            //  список языков
            assertNotNull(arr.getLanguages());
            assertEquals(2, arrTest.getLanguages().size());
            assertTrue(arrTest.getLanguages().containsAll(List.of("French", "English")));
        }
    }

    @Test
    @DisplayName(" № 5 Проверка содержимого в personalObject.json файле")
    public void reedPersonJsonFileTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("personalObject.json");
        PersonalObject arrTest = mapper.readValue(inputStream, PersonalObject.class);
            assertEquals("стол", arrTest.getName());
            assertEquals(75, arrTest.getHeight());
            assertEquals(100, arrTest.getWidth());
            assertNotNull(arrTest.getEquipment().getTabletop());
            assertEquals(3, arrTest.getEquipment().getTabletop().size()); // проверяем размер списка
            assertTrue(arrTest.getEquipment().getTabletop().containsAll(List.of("белая", "мрамор", "матовая")));
            assertEquals(1, arrTest.getEquipment().getBase());

    }
}


