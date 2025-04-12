import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private int health; // здоровье
    private int weapons; // оружие
    private int lvl; // уровень
    private double distance; // расстояние
    // путь к директории для сохранения игр
    private static final String PATH_DIR = "D:/Games/savegames";

    public static void main(String[] args) {
        // Создадим три экземпляра класса сохраненной игры GameProgress
        GameProgress gameProgress_1 = new GameProgress(100, 50, 3, 10.5);
        GameProgress gameProgress_2 = new GameProgress(90, 40, 2, 5.5);
        GameProgress gameProgress_3 = new GameProgress(80, 30, 1, 1.5);

        // передадим экземпляр игры и путь для сериализации
        saveGame(gameProgress_1, PATH_DIR + "/save1.dat");
        saveGame(gameProgress_2, PATH_DIR + "/save2.dat");
        saveGame(gameProgress_3, PATH_DIR + "/save3.dat");

        // реализуйте метод zipFiles(), принимающий в качестве аргументов String полный путь к файлу архива
        // и список запаковываемых объектов в виде списка строчек String полного пути к файлу
        List<String> listZipFiles = new ArrayList<>();
        listZipFiles.add(PATH_DIR + "/save1.dat");
        listZipFiles.add(PATH_DIR + "/save2.dat");
        listZipFiles.add(PATH_DIR + "/save3.dat");

        zipFiles(PATH_DIR + "/zipGame.zip", listZipFiles); // архивация файлов

        deleteFiles(listZipFiles); // удалим файлы вне архива

        // Реализуйте метод openZip(), который принимал бы два аргумента: путь к файлу типа String
        // и путь к папке, куда стоит разархивировать файлы типа String
        openZip(PATH_DIR + "/zipGame.zip", PATH_DIR);
    }

    private static void openZip(String zipIn, String pathDir) {
        // создается объект ZipInputStream, который используется для чтения содержимого архива.
        // В конструкторе передается объект FileInputStream, указывающий на конкретный архивный файл
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipIn))) {
            ZipEntry entry;

            while ((entry = zin.getNextEntry()) != null) {
                String fileName = entry.getName(); // получим название файла из архива
                //System.out.println(fileName);
                File targetFile = new File(pathDir, fileName); // формируем полный путь для распаковки

                // распаковка архива
                FileOutputStream fileOut = new FileOutputStream(targetFile);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fileOut.write(c);
                }

                fileOut.flush(); // принудительное очищение буфера выходного потока и запись всех накопленных данных непосредственно в файл
                zin.closeEntry(); // закрываем текущую запись
                fileOut.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void deleteFiles(List<String> listZipFiles) {
        if (listZipFiles != null && listZipFiles.size() >= 0) { // если список не null и не пустой
            for (String filePathToDelete : listZipFiles) { // перебираем список с адресами файлов на удаление
                File fileToDelete = new File(filePathToDelete); // создаем объект в JVM
                boolean isDeleted = fileToDelete.delete();
                if (isDeleted) {
                    System.out.println("Файл " + fileToDelete.getName() + " удален.");
                } else {
                    System.out.println("Не удалось удалить файл " + filePathToDelete + ".");
                }
            }
        }
    }

    private static void zipFiles(String zipOut, List<String> listZipFiles) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipOut))) {
            for (int i = 0; i < listZipFiles.size(); i++) {
                try (FileInputStream fis = new FileInputStream(listZipFiles.get(i))) { // получение пути из списка
                    // для записи файлов в архив для каждого файла создается объект ZipEntry в
                    // конструктор которого передается имя архивируемого файла
                    ZipEntry entry = new ZipEntry("packed_save_" + (i + 1)); // имя файла в архиве
                    // для добавления каждого объекта в архив применяется метод putNextEntry
                    zout.putNextEntry(entry);

                    // считываем содержимое файла в массив byte
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    // добавляем содержимое к архиву
                    zout.write(buffer);
                    // закрываем текущую запись для новой записи
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void saveGame(GameProgress gameProgress, String path) {
        // откроем выходной поток для записи в файл
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream copyGame = new ObjectOutputStream(fos)) {
            // запишем экземпляр класса в файл
            copyGame.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }
}