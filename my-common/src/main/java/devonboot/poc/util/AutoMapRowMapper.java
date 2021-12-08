package devonboot.poc.util;

import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

public class AutoMapRowMapper {

    public enum ColumnNameCase {
        CAMEL_CASE {
            @Override
            String convert(String fieldName) {
                //CamelCase일 경우 이름을 그대로 반환한다.
                return fieldName;
            }
        }, SNAKE_CASE {
            @Override
            String convert(String fieldName) {
                //각 대문자 앞에 underscore를 넣어준 뒤 대문자는 소문자로 변경.
                return fieldName.replaceAll("([^_A-Z])([A-Z])", "$1_$2").toLowerCase();
            }
        };

        abstract String convert(String fieldName);
    }


    public static <T> RowMapper<T> createRowMapper(Class<T> clazz) {
        return create(ColumnNameCase.SNAKE_CASE, clazz);
    }

    public static <T> RowMapper<T> create(ColumnNameCase columnNameCase, final Class<T> clazz) {

        // reflection으로 필드값을 모두 가져온다.
        Field[] fields = clazz.getDeclaredFields();

        return (resultSet, i) -> {
            T resultObj = null;
            try {
                resultObj = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {e.printStackTrace();}

            PropertyAccessor accessor = PropertyAccessorFactory.forBeanPropertyAccess(resultObj);

            for (Field field : fields) {
                Class<?> type = field.getType();
                String fieldName = field.getName();
                String columnName = columnNameCase.convert(fieldName);

                if (type.isAssignableFrom(String.class))
                    accessor.setPropertyValue(fieldName, resultSet.getString(columnName));
                else if(type.isAssignableFrom(Date.class))
                    accessor.setPropertyValue(fieldName, resultSet.getTimestamp(columnName));
                else if(type.isAssignableFrom(BigDecimal.class))
                    accessor.setPropertyValue(fieldName, resultSet.getBigDecimal(columnName));
                else if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(int.class))
                    accessor.setPropertyValue(fieldName, resultSet.getInt(columnName));
                else if(type.isAssignableFrom(Double.class) || type.isAssignableFrom(double.class))
                    accessor.setPropertyValue(fieldName, resultSet.getDouble(columnName));
                else if(type.isAssignableFrom(Long.class) || type.isAssignableFrom(long.class))
                    accessor.setPropertyValue(fieldName, resultSet.getLong(columnName));
                else
                    throw new IllegalArgumentException("변환이 불가능한 형식입니다. " + field.getType());
            }
            
            return resultObj;
        };
    }
}