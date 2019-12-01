package de.mo.eventing.events;

import java.time.LocalDate;

public class UserLogin implements Event {

	private final LocalDate loginDate;

	public UserLogin(final LocalDate loginDate) {
		this.loginDate = loginDate;
	}

	public boolean today() {
		return LocalDate.now().isEqual(this.loginDate);
	}
}
