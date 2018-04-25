package FriendsGroup.pro;

/*
Класс получает различные аргументы из командной строки
Аргументы (ключи) имеют имена, что бы использовать их в любом порядке, только указанная директория не имеет имени:
    указанная директория
    -f                      отображает список файлов в указанной директории
    -d                      отображает список поддиректорий в указанной директории

 */

import java.io.File;

public class Main {

    public static void main(String[] args) {
        boolean showFiles = false; // Показывать файлы (ключ -f)
//        boolean showDirectory = false; // Показывать директории (ключ -d)
        boolean showQuest = false; // Показывать справку (ключ -?)
        String targetPath = ""; // Путь из аргумента
        Integer CountFiles = 0; // Количество файлов

        // Перебор всех аргументов и определение задачи
        for (String argument:args) {
            switch (argument) {
                case "-f": showFiles = true; break;
//                case "-d": showDirectory = true; break;
                case "-?": showQuest = true; break;
                default: targetPath = argument; // Получили путь
            }
        }
        // Проверка наличия пути и вывод результата
        if (targetPath.length() > 0) {
            File objectsList[] = new File(targetPath).listFiles(); // Список объектов (файлов и директорий)

            // Перебор всех объектов
            for (File object: objectsList) {
                if(showFiles && object.isFile())
                {
                    System.out.println(object.getName());
                    CountFiles = CountFiles + 1;
                }
//                if(showDirectory && object.isDirectory()) System.out.println(object.getName());
            }
            System.out.println("Найдено файлов: " + CountFiles);
        } else {
            showQuest = true;
        }
        if (showQuest){
            System.out.println("Ошибка: директория не получена");
            System.out.println();
//            System.out.println("Синтаксис: java -jar LogSI3000.jar DIRECTORY [-d] [-f]");
            System.out.println("Синтаксис: java -jar LogSI3000.jar DIRECTORY [-f]");
            System.out.println("-f показать файлы в указанной директории");
//            System.out.println("-d показать директории в указанной директории");
            System.out.println("-? показать справку");
        }
    }
}