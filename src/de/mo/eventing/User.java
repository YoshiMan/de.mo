package de.mo.eventing;

import java.util.function.Consumer;

import de.mo.eventing.events.IamMaleNow;
import de.mo.eventing.events.UserLogin;

public class User {

	boolean male;
	int loginsToday;

	public User(final EventDispatcherWeak eventDispatcher) {
		eventDispatcher.addDispatching(UserLogin.class, (Consumer<UserLogin>) this::login);
		eventDispatcher.addDispatching(IamMaleNow.class, (Consumer<IamMaleNow>) this::apply);
	}

	public User(final EventDispatcher eventDispatcher) {
		eventDispatcher.addDispatching(UserLogin.class, (Consumer<UserLogin>) this::login);
		eventDispatcher.addDispatching(IamMaleNow.class, (Consumer<IamMaleNow>) this::apply);
	}

	private void login(final UserLogin userLogin) {
		if (userLogin.today()) {
			this.loginsToday++;
		}
	}

	private void apply(final IamMaleNow iamMaleNow) {
		this.male = true;
	}

}
