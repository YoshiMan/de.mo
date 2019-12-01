package de.mo.eventing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import de.mo.eventing.events.Event;

public class EventDispatcher {

	private final Map<Class<? extends Event>, List<Consumer<Event>>> eventToConsumer = new HashMap<>();
	int dispatchedEvents;

	public void addDispatching(final Class<? extends Event> class1, final Consumer<? extends Event> consumer) {
		this.eventToConsumer.computeIfAbsent(class1, x -> new ArrayList<>()).add((Consumer<Event>) consumer);
	}

	public void dispatch(final Event event) {
		final List<Consumer<Event>> consumers = this.eventToConsumer.getOrDefault(event.getClass(),
				Collections.emptyList());
		consumers.parallelStream().forEach(x -> {
			x.accept(event);
			this.dispatchedEvents++;
		});
	}

}
