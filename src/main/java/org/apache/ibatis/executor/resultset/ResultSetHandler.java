/**
 *    Copyright 2009-2019 the original author or authors.
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
package org.apache.ibatis.executor.resultset;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.ibatis.cursor.Cursor;

/**
 * @author Clinton Begin
 *
 * 实现 ResultSet 结果集的处理，将其映射成对应的结果对象。
 */
public interface ResultSetHandler {
  /**
   * 处理结采集，生成相应的结果对象集合
   *
   * @param stmt Statement 对象
   * @param <E> 泛型
   * @return 结果数组
   */
  <E> List<E> handleResultSets(Statement stmt) throws SQLException;
  /**
   * 处理结采集，返回相应的游标对象
   *
   * @param stmt Statement 对象
   * @param <E> 泛型
   * @return Cursor 对象
   */
  <E> Cursor<E> handleCursorResultSets(Statement stmt) throws SQLException;


//处理存储过程的输出参数
  void handleOutputParameters(CallableStatement cs) throws SQLException;

}
