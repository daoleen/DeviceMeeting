package com.daoleen.devicemeeting.web.validator;

import com.daoleen.devicemeeting.web.validator.annotation.UniqueField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by alex on 18.6.14.
 */
public class UniqueFieldValidator implements ConstraintValidator<UniqueField, String> {
    private final static Logger logger = LoggerFactory.getLogger(UniqueFieldValidator.class);
    private String table;
    private String field;

    @Resource(name = "dataSource")
    private DataSource dataSource;

    @Override
    public void initialize(UniqueField constraintAnnotation) {
        table = constraintAnnotation.tableName();
        field = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            String sql = String.format("select count(id) from %s where %s = ?", table, field);
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            logger.debug("SQL-query is: {}", sql);
            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
