package com.boob.repository;

/**
 * jpa持久层接口
 *
 * @param <T>
 * @param <ID>
 */
public interface JpaRepository<T, ID> extends CrudRepository<T, ID> {

}
