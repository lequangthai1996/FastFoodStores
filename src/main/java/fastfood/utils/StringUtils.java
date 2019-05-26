package fastfood.utils;

import com.sun.istack.internal.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class StringUtils {


    public static String buildSelectQuery(String[] columns, String[] tables, String[] conditions) {
        StringBuilder selectQuery = new StringBuilder();
        selectQuery.append(" SELECT ");
        selectQuery.append(String.join(", ", columns));
        selectQuery.append(" FROM ");
        selectQuery.append(String.join(" ", tables));
        selectQuery.append(" WHERE ");
        selectQuery.append(String.join(" AND ", conditions));
        return selectQuery.toString();
    }


    public static boolean isEmpty(@Nullable Object str) {
        return (str == null || "".equals(str));
    }


    public static String toSQLLower(String field) {
        return new StringBuilder("lower(").append(field).append(")").toString();
    }
    public static boolean isValidString(Object temp) {
        if (temp == null || temp.toString().trim().equals("")) {
            return false;
        }
        return true;
    }


    public static Boolean convertStringToBooleanOrNull(String input) {
        try {
            return Boolean.valueOf(input);
        } catch (Exception e) {
            return null;
        }
    }


    public static Long convertStringToLongOrNull(String input) {
        try {
            return Long.valueOf(input);
        } catch (Exception e) {
            return null;
        }
    }


    public static Integer convertStringToIntegerOrNull(String input) {
        try {
            return Integer.valueOf(input);
        } catch (Exception e) {
            return null;
        }
    }




    public static String convertObjectToString(Object input) {
        return input == null ? null : input.toString();

    }

    public static String convertDateToString(Date input) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = dateFormat.format(input).toString();
        return date;

    }



    public static Double convertStringToDoubleOrNull(String amountNumber) {
        try {
            return Double.valueOf(amountNumber);
        } catch (Exception e) {
            return null;
        }
    }





    public static String makeSortTwoLevel(String firstField, String secondField, String firstFieldDirection) {
        StringBuilder sortString = new StringBuilder();
        sortString.append(firstField);
        sortString.append(" ");
        sortString.append(firstFieldDirection);
        sortString.append(", ");
        sortString.append(secondField);
        return sortString.toString();
    }

}