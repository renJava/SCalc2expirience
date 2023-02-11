package academy.kata;

import java.util.Scanner;

import static java.lang.System.*;

//  "654Ъiёtgh" + "e47и4ergj"     //
//    "ejrgЁ12" - "kegjЫ123"     //
//    "ЫkegjЫ12Йы" - "kegjЫ12"     //
//    "ehjrg12" *   9           //
//      "e47и4ergj" / 10       //


public class SCalc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(in);
        String announcement = """
                Вводите строчные операнды (не более 10 символов каждый) и строго в двойных кавычках.
                Первый операнд - всегда строчный, не более 10 символов, например, "123456789Ё".
                Второй - как первый, но только при сложении или вычитании.
                При сложении и вычитании, второй операнд - в кавычках
                При умножении или делении второй операнд - натуральное число <=10 - без кавычек:
                """;
        out.println(announcement);
        String expression = scanner.nextLine(); // Сканируем всю строку с выражением целиком в expression
        String validate = isValidate(expression);
        out.println("\"" + validate + "\"");
    }

    //     Регулярное выражение для + и - : ^\s*\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\"\s*[+,-]\s*\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\"\s*
    //                                      ^ *"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}" *[+,-] *"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}" *
    //
    //     Регулярное выражение для * и / : ^\s*\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\"\s*[*,\/]\s*(?:[1-9]|10)\s*$
    //      3-й вариант

    
    //    Задаем все переменные метода.
    public static String isValidate  (String expression) {
        boolean validatePM;
        boolean validateMD;
        String validateControlPrint;
        String trimExpressionPM = "";
        String trimExpressionMD = "";
        String aPM = "";
        String bPM = "";
        String aMD = "";
        int bMD = 0;
        int[] quotePosition = new int[3];
        int lengthET;


//                                      Сложение и вычитание

        if (expression.matches(
                "^ *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *[+,-] *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *")) {
            validatePM = true;

            trimExpressionPM = expression.trim();

            lengthET = trimExpressionPM.length();
            int i = 0;                                      //Запоминаем позиции кавычек в теле выражения PM
            int j = 0;
            while (i < (lengthET -1) && j <= 1) {
                i++;
                if (trimExpressionPM.charAt(i) == '\"') {
                    quotePosition[j] = i; j++;
                }
            }
            aPM = trimExpressionPM.substring(1, quotePosition[0]);
            bPM = trimExpressionPM.substring(quotePosition[1]+1, lengthET -1);

            return  (trimExpressionPM.indexOf('+') > -1) ? sPlus(aPM, bPM) : sCut(aPM, bPM);
        }

        else validatePM = false;


//                                      Умножение и деление

        if (expression.matches(
                "^ *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *[*,/] *(?:[1-9]|10) *$")) {
            validateMD = true;
            trimExpressionMD = expression.trim();

            lengthET = trimExpressionMD.length();
            int i = 0;                                   //Запоминаем позиции кавычек в середине выражения MD без краёв
            int j = 0;
            while (i < (lengthET -1) && j <= 0) {
                i++;
                if (trimExpressionMD.charAt(i) == '\"') {
                    quotePosition[j] = i; j++;
                }
            }
            aMD = trimExpressionMD.substring(1, quotePosition[0]);
            bMD = Integer.parseInt(trimExpressionMD.substring(lengthET -2, lengthET).trim());

            return  (trimExpressionMD.indexOf('*') > -1) ? sMultiple(aMD, bMD) : sDivision(aMD, bMD);

        }
        else validateMD = false;


//                Контроль ввода и промежуточных вычислений:

        if (validatePM || validateMD) {
            validateControlPrint = "Правильный ввод";
            out.println("trimExpressionPM: " + trimExpressionPM + "trimExpressionMD: " + trimExpressionMD + "\n");


            out.println("\nvalidatePM: " + validatePM + ", aPM: " + aPM + ", bPM: " + bPM + "\n");
            out.println("\nvalidateMD: " + validateMD + ", aMD: " + aMD + ", bMD: " + bMD + "\n");

        }

        else {
            validateControlPrint = "\n!!!Неправильный ввод!!!";}
        return validateControlPrint;

    }

//            Работающие методы

        public static String sPlus(String a, String b) {      //Сложение
            return a + b;
        }
        // При Сложении - Конкатенация строк

        public static String sCut(String a, String b) {       //Вычитание
            var substBegin = a.indexOf(b);
            return (substBegin > -1) ? a.substring(0, substBegin) +
                    a.substring(substBegin + b.length()) : a;
        }
        // При Вычетании - вырезаем найденное слово из строки или возвращаем уменьшаемое обратно

        public static String sMultiple(String a, int b) {      //Умножение
            String sMultiple = a.repeat(b);
            return (sMultiple.length() <= 40) ? sMultiple : sMultiple.substring(0, 40) + "...";
        }
        // При Умножении - повторяем заданное слово b раз и обрезаем результат после 40-го символа

        public static String sDivision(String a, int b) {       //Деление
            return (a.length()>=b)? a.substring(0, a.length() / b): "Делитель больше делимого";
        }
}
