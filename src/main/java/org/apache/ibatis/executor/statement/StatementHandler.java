/**
 *    Copyright 2009-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.executor.statement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.ResultHandler;

/**
 * @author Clinton Begin
 *
 * 实现向数据库发起 SQL 命令。 Statement 处理器
 */
public interface StatementHandler {

  /**
   * 准备操作，可以理解成创建 Statement 对象
   *
   * @param connection         Connection 对象
   * @param transactionTimeout 事务超时时间
   * @return Statement 对象
   */
  Statement prepare(Connection connection, Integer transactionTimeout)
      throws SQLException;


  /**
   * 绑定 statement 执行时所需的实参
   *
   * @param statement Statement 对象
   */
  void parameterize(Statement statement)
      throws SQLException;


  /**
   * 批量执行 SQL 语句
   *
   * @param statement Statement 对象
   */
  void batch(Statement statement)
      throws SQLException;

  /**
   * 执行 update/insert/delete 语句
   *
   * @param statement Statement 对象
   * @return 影响的条数
   */
  int update(Statement statement)
      throws SQLException;


  /**
   * 执行查询操作
   * @param statement
   * @param resultHandler
   * @param <E>
   * @return
   * @throws SQLException
   */
  <E> List<E> query(Statement statement, ResultHandler resultHandler)
      throws SQLException;


  /**
   * 执行读操作，返回 Cursor 对象
   *
   * @param statement Statement 对象
   * @param <E> 泛型
   * @return Cursor 对象
   */
  <E> Cursor<E> queryCursor(Statement statement)
      throws SQLException;


  /**
   * 获取绑定的sql
   * @return
   */
  BoundSql getBoundSql();


  /**
   * ParameterHandler
   * @return
   */
  ParameterHandler getParameterHandler();

}
