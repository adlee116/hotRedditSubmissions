package com.holidayswap_android.data.repository.storage.dao

import androidx.room.*

@Dao
abstract class BaseDao<in Type> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(item: Type)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSync(item: Type)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(items: List<Type>)

    @Update
    abstract suspend fun update(item: Type)

    @Delete
    abstract suspend fun delete(item: Type)

    // helper functions for RawQueries
    fun select(columnNames: List<String>): String {
        var value = "SELECT"
        for (index in 0 until columnNames.size - 1) {
            value += " ${columnNames[index]},"
        }
        value += " ${columnNames[columnNames.lastIndex]}\n"
        return value
    }

    // Room does not allow abstract fields or functions with Dao annotations. So have to pass through tableName
    fun from(tables: List<String>) = from(tables.joinToString(separator = " , "))

    fun from(table: String) = " FROM $table\n"

    fun where(conditions: String) =
        if (conditions.isNotEmpty()) " WHERE $conditions "
        else ""

    fun conditionsAND(conditions: List<String>): String =
        when (conditions.size) {
            0 -> ""
            1 -> conditions[0]
            else -> conditions.joinToString(separator = " AND ", prefix = "(", postfix = ")")
        }

    fun conditionsOR(conditions: List<String>): String =
        when (conditions.size) {
            0 -> ""
            1 -> conditions[0]
            else -> conditions.joinToString(separator = " OR ", prefix = "(", postfix = ")")
        }

    fun conditionEquals(columnName: String, expectedValue: String?) =
        "$columnName = '$expectedValue'"

    fun conditionLike(columnName: String, expectedValue: String) =
        "$columnName LIKE '$expectedValue'"

    fun conditionEquals(columnName: String, expectedValue: Number) =
        "$columnName = $expectedValue"

    fun conditionNotEquals(columnName: String, expectedValue: String) =
        "$columnName != '$expectedValue'"

    fun conditionNotEquals(columnName: String, expectedValue: Number) =
        "$columnName != $expectedValue"

    fun conditionLessThen(columnName: String, expectedValue: Number) =
        "$columnName < $expectedValue"

    fun conditionGreaterThen(columnName: String, expectedValue: Number) =
        "$columnName > $expectedValue"

//    fun conditionLessThen(columnName: String, expectedValue: DateTime) =
//        "datetime($columnName) < datetime('$expectedValue')"
//
//    fun conditionGreaterThen(columnName: String, expectedValue: DateTime) =
//        "datetime($columnName) > datetime('$expectedValue')"
//
//    fun conditionEqualMonthAndDay(columnName: String, expectedValue: DateTime) =
//        "strftime('%m-%d',$columnName) = strftime('%m-%d','$expectedValue')"

    fun orderBy(columns: List<String>, ignoreCase: Boolean = false): String {
        val collationSeparator = if (ignoreCase) " COLLATE NOCASE, " else ", "

        return if (columns.isNotEmpty()) {
            " ORDER BY " + columns.joinToString(separator = collationSeparator) + " "
        } else ""
    }
}