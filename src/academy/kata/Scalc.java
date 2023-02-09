package academy.kata;

import java.util.Arrays;
import java.util.Scanner;

public class Scalc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String announcement = """
                Вводите строчные операнды (не более 10 символов каждый) и строго в двойных кавычках.
                Первый операнд - всегда строчный, не более 10 символов, например, "123456789Ё".
                Второй - как первый, только при сложении или вычитании.
                При умножении или делении второй операнд - натуральное число <=10 - без кавычек:
                """;
        System.out.println(announcement);
        String expression = scanner.nextLine(); // Сканируем всю строку с выражением целиком в expression
        String validate = isValidate(expression);
        System.out.println("\"" + validate + "\"");
    }

    //     Регулярное выражение для + и - : ^\s*\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\"\s*[+,-]\s*\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\"\s*
    //                                      ^ *"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}" *[+,-] *"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}" *
    //
    //     Регулярное выражение для * и / : ^\s*\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\"\s*[*,\/]\s*(?:[1-9]|10)\s*$
    //      3-й вариант

    //    Задаем все переменные метода.
    public static String isValidate  (String expression) {
        boolean validateS;
        boolean validateM;
        String validateControlPrint;
        String trimExpressionS = "";
        String trimExpressionM = "";
        String operator = "";
        String operandSE = "";
        String operandS1 = "";
        String operandS2 = "";
        String sum = "";
        String operandM = "";
        String operandM1 = "";
        String operandM2 = "";
        int[] quotePosition = new int[2];
        int quoteArrayLength = 0;

        if (expression.matches(
                "^ *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *[+,-] *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *")) {
            validateS = true;
            trimExpressionS = expression.trim();

            operandSE = Arrays.toString(trimExpressionS.split("\"\\w*\""));

            int lengthT = trimExpressionS.length();     //Запоминаем позиции кавычек в теле выражения
            int i = 0;
            int j = 0;
            while (i < (lengthT-1) && j < 2) {
                i++;
                if (trimExpressionS.charAt(i) == '\"') {
                    quotePosition[j] = i; j++;
                }
            }

            operandS1 = trimExpressionS.substring(1, quotePosition[0]);
            operandS2 = trimExpressionS.substring(quotePosition[1]+1, lengthT-1);
            sum = operandS1 + operandS2;
            System.out.printf("\nРезультат сложения: "+"\"" + sum + "\""+"\n\n");

            if (trimExpressionS.indexOf('+') > -1) {
                operator = "+";


            } else operator = "-";

            quoteArrayLength = quotePosition.length;
        }
        else validateS = false;

        if (expression.matches(
                "^ *\"[a-zA-Z_0-9а-яА-ЯёЁ]{1,10}\" *[*,/] *(?:[1-9]|10) *$")) {
            validateM = true;
            trimExpressionM = expression.trim();

            if (trimExpressionS.indexOf('*') > -1) operator = "*"; else operator = "/";

        }
        else validateM = false;


        if (validateS || validateM) {
            validateControlPrint = "Правильный ввод";
            System.out.println("validateS: " + validateS + ", trimExpressionS: " + trimExpressionS + ", operandSE: " + operandSE +
                    "\nvalidateM: " + validateM + ", trimExpressionM: " + trimExpressionM + "quoteArrayLength: " + quoteArrayLength + "\n");


            System.out.println("\nvalidateM: " + validateM + ", trimExpressionM: " + trimExpressionM + "\n");

//            Arrays.stream(operand).forEach((e) -> {System.out.println("Оператор: "+e); });
//            for (int dQP: quotePosition) {
//                System.out.printf("Кавычка: "+ dQP +",  ");
//            }
        }
        else {
            validateControlPrint = "!!!Неправильный ввод!!!";}
        return validateControlPrint;

    }

}
