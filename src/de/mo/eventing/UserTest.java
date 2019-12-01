package de.mo.eventing;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import de.mo.eventing.events.IamMaleNow;
import de.mo.eventing.events.UserLogin;

public class UserTest {

	@Test
	public void test0() throws Exception {
		{ // given
			final EventDispatcherWeak eventDispatcher = new EventDispatcherWeak();
			User user = new User(eventDispatcher);
			Assert.assertEquals(0, user.loginsToday);
			{// when
				eventDispatcher.dispatch(new UserLogin(today()));
				eventDispatcher.dispatch(new UserLogin(yesterday()));
				{// then
					Assert.assertEquals(1, user.loginsToday);
					{ // clear user and therefore its references to its methods
						user = null;
						System.gc();
					}
					eventDispatcher.dispatch(new UserLogin(today()));
					Assert.assertEquals(2, eventDispatcher.dispatchedEvents);
				}
			}

		}
	}

	@Test
	public void test1() throws Exception {
		{ // given
			final EventDispatcher eventDispatcher = new EventDispatcher();
			User user = new User(eventDispatcher);
			Assert.assertEquals(0, user.loginsToday);

			{// when
				eventDispatcher.dispatch(new UserLogin(today()));
				eventDispatcher.dispatch(new UserLogin(yesterday()));

				{// then
					Assert.assertEquals(1, user.loginsToday);

					{ // clear user and unfortunately not its references to its methods
						user = null;
						System.gc();
					}
					eventDispatcher.dispatch(new UserLogin(today()));
					Assert.assertEquals(3, eventDispatcher.dispatchedEvents);
				}
			}
		}
	}

	@Test
	public void test2() throws Exception {
		{ // given
			final EventDispatcherWeak eventDispatcher = new EventDispatcherWeak();
			User user = new User(eventDispatcher);
			Assert.assertEquals(false, user.male);
			{// when
				eventDispatcher.dispatch(new IamMaleNow());
				{// then
					Assert.assertEquals(true, user.male);
					{ // clear user and therefore its references to its methods
						user = null;
						System.gc();
					}
					eventDispatcher.dispatch(new IamMaleNow());
					Assert.assertEquals(1, eventDispatcher.dispatchedEvents);
				}
			}

		}
	}

	private LocalDate today() {
		return LocalDate.now();
	}

	private LocalDate yesterday() {
		return LocalDate.now().minusDays(1);
	}

}
