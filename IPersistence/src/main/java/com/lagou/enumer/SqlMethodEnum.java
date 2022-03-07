package com.lagou.enumer;


/**
 * @author Mason
 */

public enum SqlMethodEnum {


    /**
     * select
     */
    SELECT(1),

    /**
     * delete
     */
    DELETE(2),

    /**
     * update
     */
    UPDATE(3),

    /**
     * insert
     */
    INSERT(4),
    ;

    private final int method;

    SqlMethodEnum(int value) {
        this.method = value;
    }
}
