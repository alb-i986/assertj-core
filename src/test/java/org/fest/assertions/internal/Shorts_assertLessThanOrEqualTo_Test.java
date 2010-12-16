/*
 * Created on Oct 20, 2010
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

import static org.fest.assertions.error.IsNotLessThanOrEqualTo.isNotLessThanOrEqualTo;
import static org.fest.assertions.test.ExpectedException.none;
import static org.fest.assertions.test.FailureMessages.unexpectedNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import org.fest.assertions.core.*;
import org.fest.assertions.test.ExpectedException;
import org.junit.*;

/**
 * Tests for <code>{@link Shorts#assertLessThanOrEqualTo(AssertionInfo, Short, short)}</code>.
 *
 * @author Alex Ruiz
 */
public class Shorts_assertLessThanOrEqualTo_Test {

  private static WritableAssertionInfo info;

  @Rule public ExpectedException thrown = none();

  private Failures failures;
  private Shorts shorts;

  @BeforeClass public static void setUpOnce() {
    info = new WritableAssertionInfo();
  }

  @Before public void setUp() {
    failures = spy(Failures.instance());
    shorts = new Shorts();
    shorts.failures = failures;
  }

  @Test public void should_fail_if_actual_is_null() {
    thrown.expectAssertionError(unexpectedNull());
    shorts.assertLessThanOrEqualTo(info, null, (short)8);
  }

  @Test public void should_pass_if_actual_is_less_than_other() {
    shorts.assertLessThanOrEqualTo(info, (short)6, (short)8);
  }

  @Test public void should_pass_if_actual_is_equal_to_other() {
    shorts.assertLessThanOrEqualTo(info, (short)6, (short)6);
  }

  @Test public void should_fail_if_actual_is_greater_than_other() {
    try {
      shorts.assertLessThanOrEqualTo(info, (short)8, (short)6);
      fail();
    } catch (AssertionError e) {}
    verify(failures).failure(info, isNotLessThanOrEqualTo((short)8, (short)6));
  }
}