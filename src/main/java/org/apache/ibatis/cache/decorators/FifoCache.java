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
package org.apache.ibatis.cache.decorators;

import java.util.Deque;
import java.util.LinkedList;

import org.apache.ibatis.cache.Cache;

/**
 * FIFO (first in, first out) cache decorator.
 *
 * @author Clinton Begin
 *
 *
 *
 *  FifoCacbe 是先
 * 入先出版本的装饰器， 当 向缓存添加数据时，如果缓存项的个数已经达到上限， 则会将缓存中最老（ 即最早进入缓存 ） 的缓存项删除。
 */
public class FifoCache implements Cache {
  /**
   * 装饰的 Cache 对象
   */
  private final Cache delegate;
  /**
   * 双端队列，记录缓存键的添加 用于记 录 key 进入缓存的 先后顺序 ， 使用的是 LinkedList <Object ＞类型的集合对象
   */
  private final Deque<Object> keyList;
  /**
   * 队列上限
   */
  private int size;

  public FifoCache(Cache delegate) {
    this.delegate = delegate;
    this.keyList = new LinkedList<>();// 使用了 LinkedList
    this.size = 1024;// 默认为 1024
  }

  @Override
  public String getId() {
    return delegate.getId();
  }

  @Override
  public int getSize() {
    return delegate.getSize();
  }

  public void setSize(int size) {
    this.size = size;
  }

  @Override
  public void putObject(Object key, Object value) {
    // 循环 keyList 检测并清理缓存
    cycleKeyList(key);
    delegate.putObject(key, value);
  }

  @Override
  public Object getObject(Object key) {
    return delegate.getObject(key);
  }

  @Override
  public Object removeObject(Object key) {
    // <2> 此处，理论应该也要移除 keyList 呀
    return delegate.removeObject(key);
  }

  @Override
  public void clear() {
    delegate.clear();
    keyList.clear();
  }

  private void cycleKeyList(Object key) {
    // <1> 添加到 keyList 对位
    keyList.addLast(key);
    // 超过上限，将队首位移除
    if (keyList.size() > size) {
      Object oldestKey = keyList.removeFirst();
      delegate.removeObject(oldestKey);
    }
  }

}
