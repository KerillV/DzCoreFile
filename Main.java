import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Main {

    private static final String ROOT_DIR = "D:/Games"; // путь к корневой папке
    private static final String TEMP_FILE_NAME = "temp.txt"; // имя файла для записи логов

    // Статическое поле StringBuilder
    private static StringBuilder log = new StringBuilder();

    public static void main(String[] args) throws IOException {

        // Создание корневой директории
        createDirectory(ROOT_DIR);

        // Создание папок в корне
        createDirectory(ROOT_DIR + "/src");
        createDirectory(ROOT_DIR + "/res");
        createDirectory(ROOT_DIR + "/savegames");
        createDirectory(ROOT_DIR + "/temp");

        // Создание папок внутри src
        createDirectory(ROOT_DIR + "/src/main");
        createDirectory(ROOT_DIR + "/src/test");

        // Создание файлов в папках
        createFile(ROOT_DIR + "/src/main", "Main.java");
        createFile(ROOT_DIR + "/src/main", "Utils.java");

        // Создание папок внутри res
        createDirectory(ROOT_DIR + "/res/drawables");
        createDirectory(ROOT_DIR + "/res/vectors");
        createDirectory(ROOT_DIR + "/res/icons");

        // Создание файла для записи логов
        createFile(ROOT_DIR + "/temp", TEMP_FILE_NAME);

        writeLogToTempFile(log.toString()); // Запись лога в файл
    }

    private static void createDirectory(String pathDirectory) {
        File directory = new File(pathDirectory);
        if (directory.mkdirs()) {
            addLog("Директория " + directory.getPath() + " создана успешно.");
        } else {
            addLog("Ошибка при создании директории " + directory.getPath() + ".");
        }
    }

    private static void createFile(String pathDirectory, String pathFile) {
        File file = new File(pathDirectory, pathFile);
        try {
            if (file.createNewFile()) {
                addLog("Файл " + file.getName() + " создан успешно.");
            } else {
                addLog("Ошибка при создании файла " + file.getName() + ".");
            }
        } catch (IOException e) {
            addLog("Ошибка при создании файла " + file.getName() + ": " + e.getMessage());
        }
    }

    private static void addLog(String message) { // метод для логирования
        log.append(message).append("\n");
    }

    private static void writeLogToTempFile(String logContent) throws IOException {
        File tempFile = new File(ROOT_DIR + "/temp/" + TEMP_FILE_NAME);

        // используем try с ресурсами для избежания утечек памяти
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(logContent); // запись всей строки
            System.out.println("Лог записи успешен.");
        } catch (IOException e) {
            System.err.println("Ошибка при записи лога: " + e.getMessage());
        }
    }
}