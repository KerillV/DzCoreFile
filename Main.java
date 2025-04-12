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

        File rootDir = new File(ROOT_DIR); // создание корневой директории

        if (rootDir.mkdirs()) {
            addLog("Корневая директория " + rootDir.getPath() + " создана успешно.");
        } else {
            addLog("Ошибка при создании корневой директории " + rootDir.getPath() + ".");
        }

        // Создание папок в корне
        createDirectory(new File(rootDir, "src"));
        createDirectory(new File(rootDir, "res"));
        createDirectory(new File(rootDir, "savegames"));
        createDirectory(new File(rootDir, "temp"));

        // Создание папок внутри src
        createDirectory(new File(rootDir + "/src/main"));
        createDirectory(new File(rootDir + "/src/test"));

        // Создание файлов в папках
        createFile(new File(rootDir + "/src/main/Main.java"));
        createFile(new File(rootDir + "/src/main/Utils.java"));

        // Создание папок внутри res
        createDirectory(new File(rootDir + "/res/drawables"));
        createDirectory(new File(rootDir + "/res/vectors"));
        createDirectory(new File(rootDir + "/res/icons"));

        // Создание файла для записи логов
        createFile(new File(rootDir + "/temp/" + TEMP_FILE_NAME));

        writeLogToTempFile(log.toString()); // Запись лога в файл
    }

    private static void createDirectory(File directory) {
        if (directory.mkdirs()) {
            addLog("Директория " + directory.getPath() + " создана успешно.");
        } else {
            addLog("Ошибка при создании директории " + directory.getPath() + ".");
        }
    }

    private static void createFile(File file) {
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