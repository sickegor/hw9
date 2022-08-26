package hw9;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import hw9.domain.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class FileParseTest {

    ClassLoader classLoader = FileParseTest.class.getClassLoader();

    @DisplayName("Домашняя работа 2. csvZip")
    @Test
    void parseCsvZipTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("csv.zip");
        ZipInputStream zip = new ZipInputStream(is);
        ZipFile file = new ZipFile(new File("src/test/resources/" + "csv.zip"));
        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            try (InputStream stream = file.getInputStream(entry);
                 CSVReader reader = new CSVReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
                List<String[]> csv = reader.readAll();
                assertThat(csv).contains(
                        new String[]{"Value", "Description", "Incident", "number"},
                        new String[]{"123", "desr", "3rf4", "333"}
                );
            }
        }
    }

    @DisplayName("Домашняя работа 2. pdfZip")
    @Test
    void parsePdfZipTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("pdf.zip");
        ZipInputStream zip = new ZipInputStream(is);
        ZipFile file = new ZipFile(new File("src/test/resources/" + "pdf.zip"));
        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            try (InputStream stream = file.getInputStream(entry)) {
                PDF pdf = new PDF(stream);
                assertThat(pdf.text).contains("PDF");
                System.out.println();
            }
        }
    }

    @DisplayName("Домашняя работа 2. xlsxZip")
    @Test
    void parseXlsxZipTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("xlsx.zip");
        ZipInputStream zip = new ZipInputStream(is);
        ZipFile file = new ZipFile(new File("src/test/resources/" + "xlsx.zip"));
        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            try (InputStream stream = file.getInputStream(entry)) {
                XLS xls = new XLS(stream);
                assertThat(
                        xls.excel.getSheetAt(0)
                                .getRow(1)
                                .getCell(1)
                                .getStringCellValue()
                ).contains("desr");
            }
        }
    }


    @DisplayName("Домашняя работа 2")
    @Test
    void jsonTestNG() {
        InputStream is = classLoader.getResourceAsStream("student.json");
        Gson gson = new Gson();
        Student student = gson.fromJson(new InputStreamReader(is), Student.class);
        assertThat(student.getName()).isEqualTo("egor");
        assertThat(student.isHuman()).isEqualTo(true);
        assertThat(student.getAge()).isEqualTo(29);
        assertThat(student.getFamily()).isEqualTo(new String[]{"Mom", "Dad", "Sister"});
        assertThat(student.getAnimals().getDog()).isEqualTo("Dog");
        assertThat(student.getAnimals().getCat()).isEqualTo("Cat");
    }

    @DisplayName("С лекции")
    @Test
    void pdfTest() throws Exception {
        open("https://junit.org/junit5/docs/current/user-guide/");
        File file = $("a[href*='junit-user-guide-5.9.0.pdf']").download();
        PDF pdf = new PDF(file);
        assertThat(pdf.author).isEqualTo("Stefan Bechtold, Sam Brannen, Johannes Link, Matthias Merdes, Marc Philipp, Juliette de Rancourt, Christian Stein");
    }

    @DisplayName("С лекции")
    @Test
    void xlsTest() throws Exception {
        open("http://romashka2008.ru/price");
        File file = $(".site-content__right a[href*='/f/prajs_ot_2508.xls']").download();
        XLS xls = new XLS(file);
        assertThat(
                xls.excel.getSheetAt(0)
                        .getRow(22)
                        .getCell(2)
                        .getStringCellValue()
        ).contains("Бумага для цветной печати");
    }

    @DisplayName("С лекции")
    @Test
    void csvTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("example.csv");
        CSVReader csvReader = new CSVReader(new InputStreamReader(is, UTF_8));
        List<String[]> csv = csvReader.readAll();
        assertThat(csv).contains(
                new String[]{"teacher", "lesson", "date"},
                new String[]{"Tuchs", "junit", "03.06"},
                new String[]{"Eroshenko", "allure", "07.06"}
        );
    }

    @Test
    void zipTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("sample-zip-file.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            assertThat(entry.getName()).isEqualTo("sample.txt");
        }
    }

    @DisplayName("С лекции")
    @Test
    void jsonTest() {
        InputStream is = classLoader.getResourceAsStream("student.json");
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(new InputStreamReader(is), JsonObject.class);
        assertThat(jsonObject.get("name").getAsString()).isEqualTo("egor");
        assertThat(jsonObject.get("human").getAsBoolean()).isEqualTo(true);
        assertThat(jsonObject.get("age").getAsInt()).isEqualTo(29);
        assertThat(jsonObject.get("animals").getAsJsonObject().get("dog").getAsString()).isEqualTo("Dog");
        assertThat(jsonObject.get("animals").getAsJsonObject().get("cat").getAsString()).isEqualTo("Cat");
    }


}
