package tests;

import com.google.gson.JsonSyntaxException;
import models.Employee;
import org.junit.jupiter.api.*;
import helpers.FileHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileHandlerTest {
    private static final String PROPERTY_TYPE = "os.name";
    private static final String PROPERTY_VALUE = "Windows";

    private static final String ACTUAL_TEXT_FILE = "test.txt";
    private static final char[] ILLEGAL_CHARACTERS = {'<', '>', ':', '|', '?', '*'};
    private static final String EXPECTED_TEXT_CONTENT = "Failure is not an option.";

    private static final String ACTUAL_JSON_FILE = "test.json";
    private static final String FAULTY_JSON_FILE ="faulty.json";
    private static final String EXPECTED_JSON_FILE = "expected.json";

    private static final Employee EXPECTED_OBJECT = new Employee(69 , "gitReal_420" , "password123");


    @org.junit.jupiter.api.BeforeAll
    static void setUpBeforeClass() throws Exception {

        Files.writeString(Paths.get(EXPECTED_JSON_FILE), "{\n" +
                "  \"id\": 69,\n" +
                "  \"username\": \"gitReal_420\",\n" +
                "  \"password\": \"password123\"\n" +
                "}");

        Files.writeString(Paths.get(FAULTY_JSON_FILE), "\n" +
                "  \"id\"= 69,\n" +
                "  \"username\"= \"gitReal_420\"\n" +
                "  \"password\"= \"password123\"");

    }

    @org.junit.jupiter.api.AfterAll
    static void tearDownAfterClass() throws Exception{
        Path textFilePath = Paths.get(ACTUAL_TEXT_FILE);
        Path actualJsonFilePath = Paths.get(ACTUAL_JSON_FILE);
        Path expectedJsonFilePath = Paths.get(EXPECTED_JSON_FILE);
        Path faultyJsonFilePath = Paths.get(FAULTY_JSON_FILE);

        if (Files.exists(textFilePath)) {Files.delete(textFilePath);
        } else {throw new Exception("Text File Does Not Exist");}

        if (Files.exists(actualJsonFilePath)) {Files.delete(actualJsonFilePath);
        } else {throw new Exception("Json File Does Not Exist");}

        if (Files.exists(expectedJsonFilePath)) {Files.delete(expectedJsonFilePath);
        } else {throw new Exception("Json File Does Not Exist");}

        if (Files.exists(faultyJsonFilePath)) {Files.delete(faultyJsonFilePath);
        } else {throw new Exception("Json File Does Not Exist");}

    }


    @Test
    @Order(1)
    @DisplayName("write(): Should Create File")
    void createFile() {

        Assumptions.assumeTrue(System.getProperty(PROPERTY_TYPE).contains(PROPERTY_VALUE));

        FileHandler.write(ACTUAL_TEXT_FILE , "");

        assertTrue(Files.exists(Paths.get(ACTUAL_TEXT_FILE)));

    }


    @Test
    @Order(2)
    @DisplayName("write(): Should Not Create File")
    void createFileWithIllegalChars() {

        Assumptions.assumeTrue(System.getProperty(PROPERTY_TYPE).contains(PROPERTY_VALUE));

        for (char illegalChar : ILLEGAL_CHARACTERS) {
            assertThrows(InvalidPathException.class, () -> FileHandler.write(ACTUAL_TEXT_FILE + String.valueOf(illegalChar) , ""));
        }

    }


    @org.junit.jupiter.api.Test
    @Order(3)
    @DisplayName("write(): Should Write File")
    void write() {

        Assumptions.assumeTrue(System.getProperty(PROPERTY_TYPE).contains(PROPERTY_VALUE));

        FileHandler.write(ACTUAL_TEXT_FILE , EXPECTED_TEXT_CONTENT);

        try {assertEquals(EXPECTED_TEXT_CONTENT, Files.readString(Paths.get(ACTUAL_TEXT_FILE)));
        } catch (IOException e) {throw new RuntimeException(e);}

    }


    @org.junit.jupiter.api.Test
    @Order(4)
    @DisplayName("read(): Should Read File")
    void read() {

        Assumptions.assumeTrue(System.getProperty(PROPERTY_TYPE).contains(PROPERTY_VALUE));

        assertEquals(EXPECTED_TEXT_CONTENT, FileHandler.read(ACTUAL_TEXT_FILE));

    }


    @org.junit.jupiter.api.Test
    @Order(5)
    @DisplayName("writeObjectAsJson(): Should Write Object As Json")
    void writeObjectAsJson() {

        Assumptions.assumeTrue(System.getProperty(PROPERTY_TYPE).contains(PROPERTY_VALUE));

        FileHandler.writeObjectAsJson(ACTUAL_JSON_FILE, EXPECTED_OBJECT);

        try {assertEquals(Files.readString(Paths.get(EXPECTED_JSON_FILE)), Files.readString(Paths.get(ACTUAL_JSON_FILE)));
        } catch (IOException e) {throw new RuntimeException(e);}

    }


    @org.junit.jupiter.api.Test
    @Order(6)
    @DisplayName("readJsonAsObject(): Should Read Json As Object")
    void readJsonAsObject() {
        Employee actualObject = FileHandler.readJsonAsObject(EXPECTED_JSON_FILE, Employee.class);
        String[] expectedObjectArray = {String.valueOf(EXPECTED_OBJECT.getId()) , EXPECTED_OBJECT.getUsername(), EXPECTED_OBJECT.getPassword()};
        String[] actualObjectArray = {String.valueOf(actualObject.getId()) , actualObject.getUsername(), actualObject.getPassword()};

        Assumptions.assumeTrue(System.getProperty(PROPERTY_TYPE).contains(PROPERTY_VALUE));

        assertInstanceOf(Employee.class, FileHandler.readJsonAsObject(EXPECTED_JSON_FILE, Employee.class));
        assertArrayEquals(expectedObjectArray , actualObjectArray);

    }


    @org.junit.jupiter.api.Test
    @Order(7)
    @DisplayName("readJsonAsObject(): Should Not Read Json As Object")
    void readJsonAsObjectWithIllegalSyntax() {

        Assumptions.assumeTrue(System.getProperty(PROPERTY_TYPE).contains(PROPERTY_VALUE));

        assertThrows(JsonSyntaxException.class , () -> FileHandler.readJsonAsObject(FAULTY_JSON_FILE, Employee.class));

    }


}