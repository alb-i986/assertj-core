/*
 * Created on Dec 20, 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2010 the original author or authors.
 */
package org.fest.assertions.internal;

import static org.fest.assertions.error.DoesNotContainOnly.doesNotContainOnly;
import static org.fest.assertions.test.ArrayFactory.arrayOfChars;
import static org.fest.assertions.test.ExpectedException.none;
import static org.fest.assertions.test.FailureMessages.*;
import static org.fest.assertions.util.ArrayWrapperList.wrap;
import static org.fest.util.Collections.set;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import org.fest.assertions.core.AssertionInfo;
import org.fest.assertions.core.WritableAssertionInfo;
import org.fest.assertions.test.ExpectedException;
import org.junit.*;

/**
 * Tests for <code>{@link CharArrays#assertContainsOnly(AssertionInfo, char[], char[])}</code>.
 *
 * @author Alex Ruiz
 */
public class CharArrays_assertContainsOnly_Test {

  private static WritableAssertionInfo info;

  @Rule public ExpectedException thrown = none();

  private Failures failures;
  private char[] actual;
  private CharArrays arrays;

  @BeforeClass public static void setUpOnce() {
    info = new WritableAssertionInfo();
  }

  @Before public void setUp() {
    failures = spy(Failures.instance());
    actual = arrayOfChars('a', 'b', 'c');
    arrays = new CharArrays(failures);
  }

  @Test public void should_pass_if_actual_contains_given_values_only() {
    arrays.assertContainsOnly(info, actual, arrayOfChars('a', 'b', 'c'));
  }

  @Test public void should_pass_if_actual_contains_given_values_only_in_different_order() {
    arrays.assertContainsOnly(info, actual, arrayOfChars('c', 'b', 'a'));
  }

  @Test public void should_pass_if_actual_contains_given_values_only_more_than_once() {
    actual = arrayOfChars('a', 'b', 'c', 'c', 'c');
    arrays.assertContainsOnly(info, actual, arrayOfChars('a', 'b', 'c'));
  }

  @Test public void should_pass_if_actual_contains_given_values_only_even_if_duplicated() {
    arrays.assertContainsOnly(info, actual, arrayOfChars('a', 'b', 'c', 'a', 'b', 'c'));
  }

  @Test public void should_throw_error_if_array_of_values_to_look_for_is_empty() {
    thrown.expectIllegalArgumentException(arrayToLookForIsEmpty());
    arrays.assertContainsOnly(info, actual, new char[0]);
  }

  @Test public void should_throw_error_if_array_of_values_to_look_for_is_null() {
    thrown.expectNullPointerException(arrayToLookForIsNull());
    arrays.assertContainsOnly(info, actual, null);
  }

  @Test public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(unexpectedNull());
    arrays.assertContainsOnly(info, null, arrayOfChars('a'));
  }

  @Test public void should_fail_if_actual_does_not_contain_given_values_only() {
    char[] expected = { 'a', 'b', 'd' };
    try {
      arrays.assertContainsOnly(info, actual, expected);
      fail();
    } catch (AssertionError e) {}
    verify(failures).failure(info, doesNotContainOnly(wrap(actual), wrap(expected), set('c'), set('d')));
  }
}